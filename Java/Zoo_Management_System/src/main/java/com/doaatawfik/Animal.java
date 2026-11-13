package com.doaatawfik;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Animal {

    private String name;

    private int age;
    private String species;
    private double weight;
    private boolean isHealthy;

    public void validateName(String name) {
        if (!name.isBlank()) {
            setName(name);
        } else {
            throw new RuntimeException("Name can't be empty");
        }
    }

    public void validateAge(int age) {
        if (age > 0 && age < 50) {
            setAge(age);
        } else {
            throw new RuntimeException("Age must be between 0 and 50");
        }
    }

    public void validateSpecies(String species) {
        if (!species.isBlank()) {
            setSpecies(species);
        } else {
            throw new RuntimeException("Species can't be empty");
        }
    }

    public void validateWeight(double weight) {
        if (weight > 0) {
            setWeight(weight);
        } else {
            throw new RuntimeException("Weight must be greater than 0");
        }
    }


    public void eat() {
        System.out.println(this.getName() + " is eating");
    }

    public void sleep() {
        System.out.println(this.getName() + " is sleeping");
    }

    public void makeSound() {
        System.out.println(this.getName() + " makes a  sound");
    }

    public void move() {
        System.out.println(this.getName() + " is moving");
    }

    public void displayInfo() {
        System.out.print(
                "name='" + name + '\'' +
                        ", age=" + age +
                        ", species='" + species + '\'' +
                        ", weight=" + weight +
                        ", isHealthy=" + isHealthy
        );
    }

}
