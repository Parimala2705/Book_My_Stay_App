import java.util.*;

class Booking {
    String bookingId;
    String guestName;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(String bookingId, String guestName, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

class Inventory {
    Map<String, Integer> roomCounts = new HashMap<>();

    public void addRoomType(String type, int count) {
        roomCounts.put(type, count);
    }

    public void allocateRoom(String type) {
        roomCounts.put(type, roomCounts.get(type) - 1);
    }

    public void restoreRoom(String type) {
        roomCounts.put(type, roomCounts.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + roomCounts);
    }
}

class CancellationService {
    Stack<String> rollbackStack = new Stack<>();
    Map<String, Booking> bookings;
    Inventory inventory;

    CancellationService(Map<String, Booking> bookings, Inventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }

    public void cancelBooking(String bookingId) {
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Cancellation failed: Booking does not exist.");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Cancellation failed: Booking already cancelled.");
            return;
        }

        // Record rollback info
        rollbackStack.push(booking.roomId);

        // Controlled mutation: restore inventory first
        inventory.restoreRoom(booking.roomType);

        // Update booking state
        booking.isCancelled = true;

        System.out.println("Booking " + bookingId + " cancelled successfully.");
        System.out.println("Room " + booking.roomId + " released back to inventory.");
    }
}

public class book_my_stay_app {
    public static void main(String[] args) {
        // Setup inventory
        Inventory inventory = new Inventory();
        inventory.addRoomType("Deluxe", 5);
        inventory.addRoomType("Suite", 3);

        // Setup bookings
        Map<String, Booking> bookings = new HashMap<>();
        bookings.put("B001", new Booking("B001", "Alice", "Deluxe", "D101"));
        bookings.put("B002", new Booking("B002", "Bob", "Suite", "S201"));

        // Allocate rooms (simulate confirmed bookings)
        inventory.allocateRoom("Deluxe");
        inventory.allocateRoom("Suite");

        inventory.displayInventory();

        // Cancellation service
        CancellationService cancellationService = new CancellationService(bookings, inventory);

        // Perform cancellations
        cancellationService.cancelBooking("B001");
        inventory.displayInventory();

        cancellationService.cancelBooking("B001"); // Duplicate cancellation attempt
        cancellationService.cancelBooking("B003"); // Non-existent booking
    }
}