package com.github.gadzooks.weather.service.visualcrossing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class PeriodicWeatherRetrieverService {
    private final AtomicLong times = new AtomicLong();

    @Scheduled(fixedRate = 600_000L)
    public void forecast() {
        log.info("---Running scheduled forecast retriever for " + times.incrementAndGet() + " times.");
    }
}
