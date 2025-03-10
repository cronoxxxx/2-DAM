package org.example.domain.service;

import jakarta.inject.Inject;
import org.example.dao.hibernate.model.Animal;
import org.example.dao.hibernate.model.Habitat;
import org.example.dao.hibernate.repo.AnimalDAO;

import java.util.List;

public class AnimalService {

    private final AnimalDAO animalDAO;
    @Inject
    public AnimalService(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }
    public List<Animal> getAllAnimals() {
        return animalDAO.getAll();
    }
    public List<Animal> getAllSavannah(Habitat habitat) {
        return animalDAO.getAllSavanah(habitat);
    }

    public Animal findByName(String name) {
        return animalDAO.findByName(name);
    }
    public void delete(Animal animal) {
        animalDAO.delete(animal);
    }
}