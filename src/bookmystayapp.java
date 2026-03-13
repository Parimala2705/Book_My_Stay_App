/**
 * UseCase5BookingRequestQueue.java
 *
 * This class demonstrates booking request intake using a Queue.
 * Requests are stored in arrival order (FIFO) without modifying inventory.
 *
 * @author YourName
 * @version 5.1
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class representing a guest’s booking intent
class Reservation {
    private String guestName;
    private String roomType;

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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Request Queue class
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a new booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all queued requests
    public void displayRequests() {
        System.out.println("\nCurrent Booking Requests (FIFO order):");
        for (Reservation reservation : requestQueue) {
            reservation.displayReservation();
        }
    }
}

// Application entry point
public class bookmystayapp {
    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println(" Welcome to Book My Stay Application ");
        System.out.println(" Hotel Booking System v5.1 ");
        System.out.println("=======================================\n");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate guest booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queued requests
        bookingQueue.displayRequests();

        System.out.println("\nApplication terminated successfully.");
    }
}