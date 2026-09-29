package ru.varino.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.varino.lab1.entity.Coordinates;
import ru.varino.lab1.repository.CoordinatesRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class CoordinatesService {

    @Inject
    private CoordinatesRepository coordinatesRepository;

    public void save(Coordinates coordinates) {
        coordinatesRepository.save(coordinates);
    }

    public Coordinates findById(Long id) {
        return coordinatesRepository.findById(id);
    }

    public List<Coordinates> findAll() {
        return coordinatesRepository.findAll();
    }

    public Coordinates update(Long id, Coordinates coordinates) {
        if (coordinatesRepository.findById(id) == null) {
            return null;
        }

        coordinates.setId(id);
        return coordinatesRepository.update(coordinates);
    }

    public boolean deleteById(Long id) {
        return coordinatesRepository.deleteById(id);
    }
}
