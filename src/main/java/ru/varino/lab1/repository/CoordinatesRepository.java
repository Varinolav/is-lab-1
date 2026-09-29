package ru.varino.lab1.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.varino.lab1.entity.Coordinates;

import java.util.List;

@ApplicationScoped
public class CoordinatesRepository {

    @Inject
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Coordinates coordinates) {
        session().persist(coordinates);
    }

    public Coordinates findById(Long id) {
        return session().find(Coordinates.class, id);
    }

    public List<Coordinates> findAll() {
        return session().createQuery("SELECT c FROM Coordinates c ORDER BY c.id", Coordinates.class).getResultList();
    }

    public Coordinates update(Coordinates coordinates) {
        return session().merge(coordinates);
    }

    public boolean deleteById(Long id) {
        Coordinates coordinates = findById(id);
        if (coordinates == null) {
            return false;
        }

        session().remove(coordinates);
        session().flush();
        return true;
    }
}
