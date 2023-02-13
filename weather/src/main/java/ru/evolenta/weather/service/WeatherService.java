package ru.evolenta.weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.weather.models.Main;
import ru.evolenta.weather.models.Root;

import java.util.Objects;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("weatherCache")
    public Main getCachedWeather(String urlWeather, String lat, String lon, String appId) {
        String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s", urlWeather, lat, lon, appId);
        return Objects.requireNonNull(restTemplate.getForObject(request, Root.class)).getMain();
    }
}
