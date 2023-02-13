package ru.evolenta.location.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.location.models.Location;
import ru.evolenta.location.models.Weather;
import ru.evolenta.location.repository.LocationRepository;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class LocationController {
    private final LocationRepository repository;
    private final RestTemplate restTemplate;

    @Value("${weather.url}")
    private String weatherUrl;

    @Autowired
    public LocationController(LocationRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public Optional<Location> getWeather(@RequestParam String localityName) {
        return repository.findByLocalityName(localityName);
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    @GetMapping("/weather")
    public Weather redirectRequestWeather(@RequestParam String localityName) {
        Location location = repository.findByLocalityName(localityName)
                .orElseThrow(()-> new EntityNotFoundException("Location not found by localityName"));
        String url = String.format("%s?lat=%s&lon=%s", weatherUrl, location.getLat(), location.getLon());
        return restTemplate.getForObject(url, Weather.class);
    }
}
