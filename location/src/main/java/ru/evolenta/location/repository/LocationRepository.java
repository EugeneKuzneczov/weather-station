package ru.evolenta.location.repository;

import org.springframework.data.repository.CrudRepository;
import ru.evolenta.location.models.Location;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    Optional<Location> findByLocalityName(String localityName);
}
