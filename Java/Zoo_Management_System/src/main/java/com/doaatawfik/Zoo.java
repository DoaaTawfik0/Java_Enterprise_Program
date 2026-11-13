package com.doaatawfik;

import lombok.Data;

import java.util.ArrayList;


@Data
public class Zoo {

    private ArrayList<Animal> animals = new ArrayList<>();
    private String zooName;

    public Zoo(String zooName) {
        this.zooName = zooName;
    }

    public void addAnimal(Animal animal) {
        if (animal != null) {
            animals.add(animal);
            System.out.println("Animal " + animal.getName() + " has been added to " + getZooName());
        } else {
            throw new NullPointerException("Animal is null");
        }
    }

    public void displayAllAnimals() {
        if (animals.isEmpty()) {
            System.out.println("No animals have been added to " + getZooName());
        }

        System.out.println("===================================  Animals List     ==================================");

        for (Animal animal : animals) {
            animal.displayInfo();
            System.out.println("--------------------------------------------------");
        }
    }

    public Animal findAnimalByName(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }

    public void removeAnimal(Animal animal) {
        Animal foundAnimal = findAnimalByName(animal.getName());
        if (foundAnimal != null) {
            animals.remove(animal);
            System.out.println("Animal " + animal.getName() + " has been removed from " + getZooName());
        } else {
            System.out.println("Animal " + animal.getName() + " not found in " + getZooName());
        }
    }

    public void feedAllAnimals() {
        for (Animal animal : animals) {
            animal.eat();
        }
    }

    public void makeAllAnimalsSounds() {
        for (Animal animal : animals) {
            animal.makeSound();
        }
    }

    public void moveAllAnimals() {
        for (Animal animal : animals) {
            animal.move();
        }
    }

    public int getTotalAnimals() {
        return animals.size();
    }

    public double getAverageAge() {
        if (animals.isEmpty()) {
            return 0;
        }

        double averageAge = 0;
        for (Animal animal : animals) {
            averageAge += animal.getAge();
        }
        return averageAge / animals.size();
    }

    public void displayStatistics() {
        System.out.println("========================== Zoo Statistics ============================");
        System.out.println("Zoo Name: " + getZooName());
        System.out.println("Total Animals: " + getTotalAnimals());
        System.out.println("Average Age: " + getAverageAge());
        displayAnimalsTypeCount();
        System.out.println("Healthy animals: " + countHealthyAnimals());

    }

    private void displayAnimalsTypeCount() {
        System.out.println("Cats = " + Cat.catsCount + ", Dogs = " + Dog.dogsCount + ", Birds = " + Bird.birdsCount);
    }

    private int countHealthyAnimals() {
        if (animals.isEmpty()) {
            return 0;
        }
        int healthyAnimals = 0;
        for (Animal animal : animals) {
            if (animal.isHealthy()) {
                healthyAnimals++;
            }
        }
        return healthyAnimals;
    }

    public void searchBySpecies(String species) {
        boolean found = false;
        for (Animal animal : animals) {
            if (animal.getSpecies().equalsIgnoreCase(species)) {
                System.out.println("Animal " + animal.getName() + " with species " + species + " has been found in " + getZooName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Animal with species " + species + " not found in " + getZooName());
        }
    }

    public ArrayList<Animal> getHealthyAnimals() {
        ArrayList<Animal> healthyAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.isHealthy()) {
                healthyAnimals.add(animal);
            }
        }
        return healthyAnimals;
    }

    public ArrayList<Animal> getAnimalsByAgeRange(double min, double max) {
        ArrayList<Animal> animalsFound = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.getAge() >= min && animal.getAge() <= max) {
                animalsFound.add(animal);
            }
        }
        return animalsFound;
    }


}
