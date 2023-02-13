package ru.evolenta.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.evolenta.weather.models.Main;
import ru.evolenta.weather.service.WeatherService;


@RestController
@RequestMapping("/")
public class WeatherController {
    private final WeatherService service;

    @Value("${appId}")
    private String appId;

    @Value("${url.weather}")
    private String urlWeather;

    @Autowired
    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {
        return service.getCachedWeather(urlWeather, lat, lon, appId);
    }
}
