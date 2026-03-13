/**
 * UseCase6RoomAllocationService.java
 *
 * This class demonstrates reservation confirmation and room allocation.
 * Booking requests are processed in FIFO order, unique room IDs are generated,
 * and inventory is updated immediately to prevent double-booking.
 *
 * @author YourName
 * @version 6.1
 */

import java.util.*;

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId; // assigned upon confirmation

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void assignRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayConfirmation() {
        System.out.println("Reservation Confirmed!");
        System.out.println("Guest: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println("-----------------------------------");
    }
}

// Centralized inventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean decrementAvailability(String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Availability: " + entry.getValue());
        }
        System.out.println();
    }
}

// Booking Service class
class BookingService {
    private Queue<Reservation> requestQueue;
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms; // roomType -> set of room IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        requestQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    // Add booking request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Process requests in FIFO order
    public void processRequests() {
        System.out.println("\nProcessing Booking Requests...");
        while (!requestQueue.isEmpty()) {
            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            if (inventory.decrementAvailability(roomType)) {
                // Generate unique room ID
                String roomId = generateUniqueRoomId(roomType);
                reservation.assignRoomId(roomId);

                // Record allocation
                allocatedRooms.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

                // Confirm reservation
                reservation.displayConfirmation();
            } else {
                System.out.println("Sorry, " + reservation.getGuestName() +
                        " -> " + roomType + " is not available.");
            }
        }
    }

    // Generate unique room ID
    private String generateUniqueRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 3).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 6);
        } while (allocatedRooms.containsKey(roomType) && allocatedRooms.get(roomType).contains(roomId));
        return roomId;
    }
}

// Application entry point
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v6.1 ");
        System.out.println("=======================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 1);

        inventory.displayInventory();

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        bookingService.addRequest(new Reservation("Alice", "Single Room"));
        bookingService.addRequest(new Reservation("Bob", "Double Room"));
        bookingService.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingService.addRequest(new Reservation("Diana", "Single Room"));
        bookingService.addRequest(new Reservation("Eve", "Single Room")); // should fail due to no availability

        // Process requests
        bookingService.processRequests();

        // Display final inventory
        inventory.displayInventory();

        System.out.println("Application terminated successfully.");
    }
}