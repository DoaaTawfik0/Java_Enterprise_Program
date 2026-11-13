package com.doaatawfik;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Bird extends Animal {

    private double wingSpan;
    private boolean canFly;
    static int birdsCount = 0;

    public Bird(String name, int age, String species, double weight, boolean isHealthy, double wingSpan, boolean canFly) {
        super(name, age, species, weight, isHealthy);
        validateWingSpan(wingSpan);
        setCanFly(canFly);
        birdsCount++;
    }

    public void validateWingSpan(double wingSpan) {
        if (wingSpan <= 0) {
            throw new IllegalArgumentException("Wing span must be greater than 0.");
        } else {
            setWingSpan(wingSpan);
        }
    }

    @Override
    public void makeSound() {
        System.out.println(this.getName() + " says: Tweet! Tweet!");
    }

    @Override
    public void move() {
        if (isCanFly()) {
            System.out.println(this.getName() + " is flying!");
        } else {
            System.out.println(this.getName() + " is hopping!");
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(", WingSpan= " + getWingSpan() + ", CanFly= " + isCanFly());
    }

    public void chirp() {
        System.out.println(this.getName() + " is chirping melodiously!");
    }

    public void fly() {
        if (isCanFly()) {
            System.out.println(this.getName() + " is soaring in the sky!");
        } else {
            System.out.println(this.getName() + " cannot fly!");
        }
    }

    public void buildNest() {
        System.out.println(this.getName() + " is building a Nest!");
    }
}
