package ru.evolenta.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.person.models.Person;
import ru.evolenta.person.models.Weather;
import ru.evolenta.person.repository.PersonRepository;

import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonRepository repository;
    private final RestTemplate restTemplate;

    @Value("${location.url}")
    private String url;

    @Autowired
    public PersonController(PersonRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity<>(repository.findById(person.getId()).get(), HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(repository.save(person), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/weather")
    public ResponseEntity<Weather> getWeather(@PathVariable int id) {
        if (repository.existsById(id)) {
            String location = repository.findById(id).orElseThrow().getLocation();
            Weather weather = restTemplate.getForObject(url + location, Weather.class);
            return new ResponseEntity<>(weather, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
