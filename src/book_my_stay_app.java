import java.util.*;
import java.util.concurrent.*;

// Represents a booking request
class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory class with synchronized methods
class Inventory {
    private Map<String, Integer> roomCounts = new HashMap<>();

    public Inventory() {
        roomCounts.put("Deluxe", 2); // only 2 Deluxe rooms
        roomCounts.put("Suite", 1);  // only 1 Suite room
    }

    public synchronized boolean allocateRoom(String roomType, String guestName) {
        int available = roomCounts.getOrDefault(roomType, 0);
        if (available > 0) {
            roomCounts.put(roomType, available - 1);
            System.out.println("Room allocated: " + roomType + " to " + guestName);
            return true;
        } else {
            System.out.println("No " + roomType + " rooms available for " + guestName);
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + roomCounts);
    }
}

// Processor that handles booking requests concurrently
class ConcurrentBookingProcessor implements Runnable {
    private BlockingQueue<BookingRequest> bookingQueue;
    private Inventory inventory;

    ConcurrentBookingProcessor(BlockingQueue<BookingRequest> bookingQueue, Inventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        try {
            while (true) {
                BookingRequest request = bookingQueue.poll(1, TimeUnit.SECONDS);
                if (request == null) break; // stop if no more requests
                inventory.allocateRoom(request.roomType, request.guestName);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class book_my_stay_app {
    public static void main(String[] args) throws InterruptedException {
        Inventory inventory = new Inventory();
        BlockingQueue<BookingRequest> bookingQueue = new LinkedBlockingQueue<>();

        // Simulate multiple guests submitting requests
        bookingQueue.add(new BookingRequest("Alice", "Deluxe"));
        bookingQueue.add(new BookingRequest("Bob", "Deluxe"));
        bookingQueue.add(new BookingRequest("Charlie", "Deluxe")); // should fail
        bookingQueue.add(new BookingRequest("Diana", "Suite"));
        bookingQueue.add(new BookingRequest("Eve", "Suite")); // should fail

        // Create multiple threads to process bookings concurrently
        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        inventory.displayInventory();
    }
}