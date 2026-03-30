// File: UseCase9ErrorHandlingValidation.java

import java.util.HashMap;
import java.util.Map;

// Custom Exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType;
    }
}

// Validator class
class BookingValidator {
    private static final String[] VALID_ROOM_TYPES = {"Deluxe", "Suite", "Standard"};

    public static void validateRoomType(String roomType) throws InvalidBookingException {
        boolean valid = false;
        for (String type : VALID_ROOM_TYPES) {
            if (type.equalsIgnoreCase(roomType)) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public static void validateInventory(Map<String, Integer> inventory, String roomType) throws InvalidBookingException {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }
}

// Booking Manager
class BookingManager {
    private Map<String, Integer> inventory = new HashMap<>();

    public BookingManager() {
        // Initialize inventory
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
        inventory.put("Standard", 3);
    }

    public Reservation confirmBooking(String reservationId, String guestName, String roomType) throws InvalidBookingException {
        // Validate room type
        BookingValidator.validateRoomType(roomType);

        // Validate inventory
        BookingValidator.validateInventory(inventory, roomType);

        // Deduct inventory
        inventory.put(roomType, inventory.get(roomType) - 1);

        // Create reservation
        return new Reservation(reservationId, guestName, roomType);
    }
}

// Main class
public class book_my_stay_app {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");

        BookingManager manager = new BookingManager();

        try {
            // Valid booking
            Reservation r1 = manager.confirmBooking("R001", "Alice", "Deluxe");
            System.out.println("Booking confirmed: " + r1);

            // Invalid room type
            Reservation r2 = manager.confirmBooking("R002", "Bob", "Luxury");
            System.out.println("Booking confirmed: " + r2);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        try {
            // Exhaust inventory
            manager.confirmBooking("R003", "Charlie", "Suite");
            manager.confirmBooking("R004", "David", "Suite"); // should fail
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        System.out.println("\nSystem continues running safely...");
    }
}