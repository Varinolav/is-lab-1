package ru.varino.lab1.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.varino.lab1.entity.Movie;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MovieRepository {

    @Inject
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }


    public void save(Movie movie) {
        movie.setCreationDate(LocalDateTime.now());
        session().persist(movie);
    }

    public Movie findById(Integer id) {
        return session().find(Movie.class, id);
    }

    public List<Movie> findAll() {
        return session().createQuery("FROM Movie ORDER BY id", Movie.class).getResultList();
    }

    public Movie update(Movie movie) {
        return session().merge(movie);
    }

    public boolean deleteById(Integer id) {
        Movie movie = findById(id);
        if (movie == null) {
            return false;
        }

        session().remove(movie);
        session().flush();
        return true;
    }
}
