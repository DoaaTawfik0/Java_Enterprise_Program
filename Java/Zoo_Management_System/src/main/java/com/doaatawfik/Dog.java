package com.doaatawfik;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Dog extends Animal {

    private String breed;

    public Dog(String name, int age, String species, double weight, boolean isHealthy, String breed) {
        super(name, age, species, weight, isHealthy);
        validateBreed(breed);

    }

    public void validateBreed(String breed) {
        if (breed.isBlank()) {
            throw new RuntimeException("Breed cannot be empty");
        } else {
            setBreed(breed);
        }
    }

    @Override
    public void makeSound() {
        System.out.println(this.getName() + " says: Woof! Woof!");
    }

    @Override
    public void move() {
        System.out.println(this.getName() + " is running");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(", Breed= " + getBreed());
    }

    public void bark() {
        System.out.println(this.getName() + " is barking loudly");
    }

    public void fetch() {
        System.out.println(this.getName() + " is fetching the ball");
    }

    public void wagTail() {
        System.out.println(this.getName() + " is wagging tail happily");
    }
}
