package ru.varino.lab1.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.varino.lab1.entity.Movie;
import ru.varino.lab1.entity.MovieGenre;

import java.util.List;

@ApplicationScoped
public class MovieOperationRepository {

    @Inject
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public Integer deleteOneByGenre(MovieGenre genre) {
        Session session = session();
        session.flush();
        Number id = (Number) session.createNativeQuery("SELECT lab_delete_one_movie_by_genre(:genre)")
                .setParameter("genre", genre.name())
                .getSingleResult();
        session.clear();
        return id == null ? null : id.intValue();
    }

    public long sumGoldenPalms() {
        Number sum = (Number) session().createNativeQuery("SELECT lab_sum_golden_palms()")
                .getSingleResult();
        return sum.longValue();
    }

    public long countGenreBefore(MovieGenre genre) {
        Number count = (Number) session().createNativeQuery("SELECT lab_count_movies_genre_before(:genre)")
                .setParameter("genre", genre.name())
                .getSingleResult();
        return count.longValue();
    }

    public List<Movie> moviesWithoutOscars() {
        return session().createNativeQuery("SELECT * FROM lab_movies_without_oscars()", Movie.class)
                .getResultList();
    }

    public int addOscarToRMovies() {
        Session session = session();
        session.flush();
        Number count = (Number) session.createNativeQuery("SELECT lab_add_oscar_to_r_movies()")
                .getSingleResult();
        session.clear();
        return count.intValue();
    }
}
