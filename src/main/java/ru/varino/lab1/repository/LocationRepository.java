package ru.varino.lab1.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.varino.lab1.entity.Location;

import java.util.List;

@ApplicationScoped
public class LocationRepository {

    @Inject
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public void save(Location location) {
        session().persist(location);
    }

    public Location findById(Long id) {
        return session().find(Location.class, id);
    }

    public List<Location> findAll() {
        return session().createQuery("SELECT l FROM Location l ORDER BY l.id", Location.class).getResultList();
    }

    public Location update(Location location) {
        return session().merge(location);
    }

    public boolean deleteById(Long id) {
        Location location = findById(id);
        if (location == null) {
            return false;
        }

        session().remove(location);
        session().flush();
        return true;
    }
}
