package org.example.domain.services;

import jakarta.inject.Inject;
import org.example.dao.jdbc.DaoAnimals;
import org.example.domain.model.Animal;

import java.util.List;

public class AnimalService {
    private final DaoAnimals daoAnimals;
    @Inject
    public AnimalService(DaoAnimals daoAnimals) {
        this.daoAnimals = daoAnimals;
    }
    public Animal getName(String name){
       List<Animal> animals =  daoAnimals.getAllAnimals().stream().filter(n -> n.getName().equals(name)).toList();
       return animals.isEmpty() ? null : animals.getFirst();
    }

    public Animal getSpecie(String especie){
        List<Animal> animals =  daoAnimals.getAllAnimals().stream().filter(n -> n.getSpecies().equals(especie)).toList();
        return animals.isEmpty() ? null : animals.getFirst();
    }

    public int count(Animal nemo){
       return daoAnimals.count(nemo.getAnimal_id());

    }

    public int callForDelete(Animal nemo){
        return daoAnimals.callforDeleteAnimal(nemo.getAnimal_id());
    }
}
