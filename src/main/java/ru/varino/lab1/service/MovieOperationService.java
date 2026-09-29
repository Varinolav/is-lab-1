package ru.varino.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.varino.lab1.entity.Movie;
import ru.varino.lab1.entity.MovieGenre;
import ru.varino.lab1.repository.MovieOperationRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class MovieOperationService {

    @Inject
    private MovieOperationRepository repository;

    public Integer deleteOneByGenre(MovieGenre genre) {
        return repository.deleteOneByGenre(genre);
    }

    public long sumGoldenPalms() {
        return repository.sumGoldenPalms();
    }

    public long countGenreBefore(MovieGenre genre) {
        return repository.countGenreBefore(genre);
    }

    public List<Movie> moviesWithoutOscars() {
        return repository.moviesWithoutOscars();
    }

    public int addOscarToRMovies() {
        return repository.addOscarToRMovies();
    }
}
