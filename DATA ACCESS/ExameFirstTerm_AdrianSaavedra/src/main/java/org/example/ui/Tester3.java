package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.model.Animal;
import org.example.domain.services.AnimalService;

import java.util.Scanner;

public class Tester3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();

        AnimalService animalService = container.select(AnimalService.class).get();
        Animal animal = animalService.getName("Nemo");
        System.out.println(animal);

        String text = null;


        if (animalService.count(animal)==0){
            animalService.callForDelete(animal);
            System.out.println("Success");
        } else {
            do{
                System.out.println("Do you want to eliminate the animal?");
                text = sc.nextLine();
                if (text.strip().equalsIgnoreCase("yes")){
                    System.out.println("Success");
                    animalService.callForDelete(animal);
                } else if (text.strip().equalsIgnoreCase("no")){
                    System.out.println("Negative");
                } else {
                    System.out.println("Error");
                }
            }while(!(text.strip().equalsIgnoreCase("yes") || text.strip().equalsIgnoreCase("no")));
        }


    }
}
