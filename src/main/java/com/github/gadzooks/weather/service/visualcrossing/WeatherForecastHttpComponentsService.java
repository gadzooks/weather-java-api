package com.github.gadzooks.weather.service.visualcrossing;

import com.github.gadzooks.weather.api.v1.mapper.forecast.ForecastResponseMapper;
import com.github.gadzooks.weather.api.v1.model.ForecastResponseDTO;
import com.github.gadzooks.weather.configuration.VisualCrossingConfig;
import com.github.gadzooks.weather.domain.visualcrossing.ForecastResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@Qualifier("http-components")
public class WeatherForecastHttpComponentsService implements WeatherForecastService {
    private final VisualCrossingConfig vcConfig;
    private final ForecastResponseMapper mapper;

    public WeatherForecastHttpComponentsService(VisualCrossingConfig vcConfig, ForecastResponseMapper mapper) {
        this.vcConfig = vcConfig;
        this.mapper = mapper;
    }

    @Override
    public ForecastResponseDTO forecast() {
        String fcstResponse = null;

        URIBuilder builder = null;
        try {
            builder = new URIBuilder(vcConfig.getVcUrl() + "/timeline/" + "46.266891,-119.222523");

            builder.setParameter("include", "obs,fcst,alerts")
                    .setParameter("key", vcConfig.getVisualCrossingApiKey());

            //Retrieve the result
            HttpGet get = new HttpGet(builder.build());
            CloseableHttpClient httpclient = HttpClients.createDefault();

            try (CloseableHttpResponse response = httpclient.execute(get)) {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    System.out.printf("Bad response status code:%d%n",
                            response.getStatusLine().getStatusCode());
                    return null;
                }

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    fcstResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    log.debug("forecast is : " + fcstResponse);

                    Gson gson = new Gson();
                    ForecastResponse forecastResponse = gson.fromJson(fcstResponse, ForecastResponse.class);
                    if (forecastResponse != null) {
                        log.debug("forecastResponse is : " + fcstResponse);
                        return mapper.toDto(forecastResponse);
                    }
                }

            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
