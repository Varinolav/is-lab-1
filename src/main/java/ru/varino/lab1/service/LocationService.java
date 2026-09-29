package ru.varino.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import ru.varino.lab1.entity.Location;
import ru.varino.lab1.repository.LocationRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class LocationService {

    @Inject
    private LocationRepository locationRepository;

    public void save(Location location) {
        locationRepository.save(location);
    }

    public Location findById(Long id) {
        return locationRepository.findById(id);
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location update(Long id, Location location) {
        if (locationRepository.findById(id) == null) {
            return null;
        }

        location.setId(id);
        return locationRepository.update(location);
    }

    public boolean deleteById(Long id) {
        return locationRepository.deleteById(id);
    }
}
