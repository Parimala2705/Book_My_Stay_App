// File: UseCase8BookingHistoryReport.java

import java.util.ArrayList;
import java.util.List;

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

// BookingHistory class
class BookingHistory {
    private List<Reservation> confirmedBookings = new ArrayList<>();

    // Add confirmed reservation to history
    public void addReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return confirmedBookings;
    }
}

// BookingReportService class
class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Generate summary report
    public void generateReport() {
        System.out.println("=== Booking Report ===");
        List<Reservation> reservations = history.getAllReservations();
        System.out.println("Total Confirmed Bookings: " + reservations.size());
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

// Main class
public class book_my_stay_app {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Confirm some reservations
        Reservation r1 = new Reservation("R001", "Alice", "Deluxe Room");
        Reservation r2 = new Reservation("R002", "Bob", "Suite");
        Reservation r3 = new Reservation("R003", "Charlie", "Standard Room");

        // Add to booking history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Generate report
        BookingReportService reportService = new BookingReportService(history);
        reportService.generateReport();

        System.out.println("\nSystem ready for further operations...");
    }
}