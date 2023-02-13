package ru.evolenta.person.repository;

import org.springframework.data.repository.CrudRepository;
import ru.evolenta.person.models.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
