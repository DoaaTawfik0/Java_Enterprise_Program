package com.doaatawfik;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cat extends Animal {

    private String furColor;
    private boolean isIndoor;

    public Cat(String name, int age, String species, double weight, boolean isHealthy, String furColor, boolean isIndoor) {
        super(name, age, species, weight, isHealthy);
        validateFurColor(furColor);
        setIndoor(isIndoor);
    }

    public void validateFurColor(String furColor) {
        if (furColor.isBlank()) {
            throw new RuntimeException("furColor can't be empty");
        } else {
            setFurColor(furColor);
        }
    }

    @Override
    public void makeSound() {
        System.out.println(this.getName() + " says: Meow!");
    }

    @Override
    public void move() {
        System.out.println(this.getName() + " is sneaking quietly");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(", furColor = " + furColor + ", isIndoor = " + isIndoor);
    }

    public void meow() {
        System.out.println(this.getName() + " is meowing softly");
    }

    public void purr() {
        System.out.println(this.getName() + " is purring contentedly");
    }

    public void scratch() {
        System.out.println(this.getName() + " is scratching the post");
    }
}
