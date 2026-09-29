package ru.varino.lab1.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.varino.lab1.entity.Person;

import java.util.List;

@ApplicationScoped
public class PersonRepository {

    @Inject
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Person person) {
        session().persist(person);
    }

    public Person findById(Long id) {
        return session().find(Person.class, id);
    }

    public List<Person> findAll() {
        return session().createQuery("SELECT p FROM Person p ORDER BY p.id", Person.class).getResultList();
    }

    public Person update(Person person) {
        return session().merge(person);
    }

    public boolean deleteById(Long id) {
        Person person = findById(id);
        if (person == null) {
            return false;
        }

        session().remove(person);
        session().flush();
        return true;
    }
}
