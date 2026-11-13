
package com.doaatawfik;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Zoo zoo = new Zoo("Zia Zoo");
        int choice;

        do {
            displayMenu();
            choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addAnimal(zoo);
                case 2 -> zoo.displayAllAnimals();
                case 3 -> searchAnimal(zoo);
                case 4 -> removeAnimal(zoo);
                case 5 -> zoo.feedAllAnimals();
                case 6 -> zoo.makeAllAnimalsSounds();
                case 7 -> zoo.moveAllAnimals();
                case 8 -> zoo.displayStatistics();
                case 9 -> searchBySpecies(zoo);
                case 10 -> displayHealthyAnimals(zoo);
                case 11 -> animalActionsMenu(zoo);
                case 0 -> System.out.println("Thank you for visiting "
                        + zoo.getZooName() + "! Goodbye!");
                default -> System.out.println("Invalid option. Try again.");
            }

            System.out.println();

        } while (choice != 0);

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println("========== ZOO MANAGEMENT SYSTEM ==========");
        System.out.println("1. Add Animal");
        System.out.println("2. Display All Animals");
        System.out.println("3. Search Animal by Name");
        System.out.println("4. Remove Animal");
        System.out.println("5. Feed All Animals");
        System.out.println("6. Make All Animals Sound");
        System.out.println("7. Move All Animals");
        System.out.println("8. Display Statistics");
        System.out.println("9. Search by Species");
        System.out.println("10. Show Healthy Animals");
        System.out.println("11. Animal Actions Menu");
        System.out.println("0. Exit");
        System.out.println("============================================");
    }

    private static void addAnimal(Zoo zoo) {

        System.out.println("\nChoose Animal Type:");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Bird");

        int type = readInt("Enter animal type: ");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        int age = readInt("Enter age: ");

        System.out.print("Enter species: ");
        String species = scanner.nextLine();

        double weight = readDouble("Enter weight: ");

        boolean isHealthy = readBoolean("Is the animal healthy? (true/false): ");

        Animal animal;

        switch (type) {

            case 1 -> {
                System.out.print("Enter breed: ");
                String breed = scanner.nextLine();

                animal = new Dog(
                        name, age, species, weight, isHealthy, breed
                );
            }

            case 2 -> {
                System.out.print("Enter fur color: ");
                String furColor = scanner.nextLine();

                boolean isIndoor = readBoolean("Is the cat indoor? (true/false): ");

                animal = new Cat(
                        name, age, species, weight, isHealthy,
                        furColor, isIndoor
                );
            }

            case 3 -> {
                double wingspan = readDouble("Enter wingspan: ");

                boolean canFly = readBoolean("Can the bird fly? (true/false): ");

                animal = new Bird(
                        name, age, species, weight, isHealthy,
                        wingspan, canFly
                );
            }

            default -> {
                System.out.println("Invalid animal type.");
                return;
            }
        }

        zoo.addAnimal(animal);
    }

    private static void searchAnimal(Zoo zoo) {

        System.out.print("Enter animal name: ");
        String name = scanner.nextLine();

        Animal animal = zoo.findAnimalByName(name);

        if (animal != null) {
            System.out.println("Animal found:");
            animal.displayInfo();
        } else {
            System.out.println("Animal not found.");
        }
    }

    private static void removeAnimal(Zoo zoo) {

        System.out.print("Enter animal name to remove: ");
        String name = scanner.nextLine();

        zoo.removeAnimal(name);

    }

    private static void searchBySpecies(Zoo zoo) {

        System.out.print("Enter species: ");
        String species = scanner.nextLine();

        zoo.searchBySpecies(species);
    }

    private static void displayHealthyAnimals(Zoo zoo) {

        var healthyAnimals = zoo.getHealthyAnimals();

        if (healthyAnimals.isEmpty()) {
            System.out.println("No healthy animals found.");
            return;
        }

        System.out.println("========== HEALTHY ANIMALS ==========");

        healthyAnimals.forEach(Animal::displayInfo);
    }

    private static void animalActionsMenu(Zoo zoo) {

        System.out.print("Enter animal name: ");
        String name = scanner.nextLine();

        Animal animal = zoo.findAnimalByName(name);

        if (animal == null) {
            System.out.println("Animal not found.");
            return;
        }

        if (animal instanceof Dog dog) {
            dogActionsMenu(dog);

        } else if (animal instanceof Cat cat) {
            catActionsMenu(cat);

        } else if (animal instanceof Bird bird) {
            birdActionsMenu(bird);

        } else {
            System.out.println("Unknown animal type.");
        }
    }

    private static void dogActionsMenu(Dog dog) {

        System.out.println("\n========== DOG ACTIONS ==========");
        System.out.println("1. Bark");
        System.out.println("2. Fetch");
        System.out.println("3. Wag Tail");

        int choice = readInt("Choose an action: ");

        switch (choice) {
            case 1 -> dog.bark();
            case 2 -> dog.fetch();
            case 3 -> dog.wagTail();
            default -> System.out.println("Invalid action.");
        }
    }

    private static void catActionsMenu(Cat cat) {

        System.out.println("\n========== CAT ACTIONS ==========");
        System.out.println("1. Meow");
        System.out.println("2. Purr");
        System.out.println("3. Scratch");

        int choice = readInt("Choose an action: ");

        switch (choice) {
            case 1 -> cat.meow();
            case 2 -> cat.purr();
            case 3 -> cat.scratch();
            default -> System.out.println("Invalid action.");
        }
    }

    private static void birdActionsMenu(Bird bird) {

        System.out.println("\n========== BIRD ACTIONS ==========");
        System.out.println("1. Chirp");
        System.out.println("2. Fly");
        System.out.println("3. Build Nest");

        int choice = readInt("Choose an action: ");

        switch (choice) {
            case 1 -> bird.chirp();
            case 2 -> bird.fly();
            case 3 -> bird.buildNest();
            default -> System.out.println("Invalid action.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static boolean readBoolean(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (value.equalsIgnoreCase("true")) {
                return true;
            }

            if (value.equalsIgnoreCase("false")) {
                return false;
            }

            System.out.println("Enter true or false.");
        }
    }
}
