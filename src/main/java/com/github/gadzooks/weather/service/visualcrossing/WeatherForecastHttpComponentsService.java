package com.github.gadzooks.weather.service.visualcrossing;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@Qualifier("http-components")
public class WeatherForecastHttpComponentsService implements WeatherForecastService {
    //NOTE : read from ENV variable
    @Value("${VISUAL_CROSSING_API_KEY}")
    private String VisualCrossingApiKey;

    @Override
    public void forecast() {
        String csvWeatherForecast = null;

        URIBuilder builder = null;
        try {
            builder = new URIBuilder("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/forecast");

            builder.setParameter("aggregateHours", "24")
                    .setParameter("contentType", "csv")
                    .setParameter("unitGroup", "metric")
                    .setParameter("key", VisualCrossingApiKey)
                    .setParameter("locations", "London,UK");

            //Retrieve the result
            HttpGet get = new HttpGet(builder.build());
            CloseableHttpClient httpclient = HttpClients.createDefault();

            try (CloseableHttpResponse response = httpclient.execute(get)) {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    System.out.printf("Bad response status code:%d%n",
                            response.getStatusLine().getStatusCode());
                    return;
                }

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    csvWeatherForecast = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    log.info("forecast is : " + csvWeatherForecast);
                }

            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }
}
