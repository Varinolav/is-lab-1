package ru.varino.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.varino.lab1.entity.Person;
import ru.varino.lab1.repository.PersonRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class PersonService {

    @Inject
    private PersonRepository personRepository;

    public void save(Person person) {
        personRepository.save(person);
    }

    public Person findById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person update(Long id, Person person) {
        if (personRepository.findById(id) == null) {
            return null;
        }

        person.setId(id);
        return personRepository.update(person);
    }

    public boolean deleteById(Long id) {
        return personRepository.deleteById(id);
    }
}
