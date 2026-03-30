import java.io.*;
import java.util.*;

// Serializable Booking class
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + bookingId + '\'' +
                ", guest='" + guestName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomId='" + roomId + '\'' +
                ", cancelled=" + isCancelled +
                '}';
    }
}

// Serializable Inventory class
class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
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
        System.out.println("Inventory: " + roomCounts);
    }
}

// Persistence Service
class PersistenceService {
    private static final String FILE_NAME = "system_state.ser";

    public static void saveState(Map<String, Booking> bookings, Inventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bookings);
            oos.writeObject(inventory);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Booking> loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Map<String, Booking>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No persisted bookings found. Starting fresh.");
            return new HashMap<>();
        }
    }

    public static Inventory loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            ois.readObject(); // skip bookings
            return (Inventory) ois.readObject();
        } catch (Exception e) {
            System.out.println("No persisted inventory found. Starting fresh.");
            return new Inventory();
        }
    }
}

public class book_my_stay_app {
    public static void main(String[] args) {
        // Attempt to restore state
        Map<String, Booking> bookings = PersistenceService.loadBookings();
        Inventory inventory = PersistenceService.loadInventory();

        // If fresh start, initialize inventory
        if (inventory.roomCounts.isEmpty()) {
            inventory.addRoomType("Deluxe", 2);
            inventory.addRoomType("Suite", 1);
        }

        // Add a booking if not already present
        if (!bookings.containsKey("B001")) {
            Booking b1 = new Booking("B001", "Alice", "Deluxe", "D101");
            bookings.put("B001", b1);
            inventory.allocateRoom("Deluxe");
        }

        if (!bookings.containsKey("B002")) {
            Booking b2 = new Booking("B002", "Bob", "Suite", "S201");
            bookings.put("B002", b2);
            inventory.allocateRoom("Suite");
        }

        // Display current state
        System.out.println("Current Bookings:");
        for (Booking b : bookings.values()) {
            System.out.println(b);
        }
        inventory.displayInventory();

        // Save state before shutdown
        PersistenceService.saveState(bookings, inventory);
    }
}