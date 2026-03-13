/**
 * UseCase2RoomInitialization.java
 *
 * This class demonstrates basic room types and static availability
 * for the Hotel Booking Management System.
 * It introduces abstraction, inheritance, and encapsulation.
 *
 * @author YourName
 * @version 2.1
 */

// Abstract class representing a generalized Room
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    // Constructor
    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    // Encapsulated getters
    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    // Common method to display room details
    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350.0);
    }
}

// Application entry point
public class bookmystayapp {

    /**
     * Main method initializes room objects and prints availability.
     *
     * @param args Command-line arguments (not used here).
     */
    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v2.1 ");
        System.out.println("=======================================\n");

        // Initialize room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display room details and availability
        System.out.println("Available Rooms:");
        single.displayRoomDetails();
        System.out.println("Availability: " + singleRoomAvailability + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Availability: " + doubleRoomAvailability + "\n");

        suite.displayRoomDetails();
        System.out.println("Availability: " + suiteRoomAvailability + "\n");

        System.out.println("Application terminated successfully.");
    }
}
