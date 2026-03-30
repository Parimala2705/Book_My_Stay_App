// File: UseCase7AddOnServiceSelection.java

import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Guest: " + guestName;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Attach a service to a reservation
    public void addService(String reservationId, Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Get all services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, Collections.emptyList());
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        return getServices(reservationId).stream()
                .mapToDouble(Service::getCost)
                .sum();
    }
}

public class book_my_stay_app {
    public static void main(String[] args) {
        // Create reservations
        Reservation r1 = new Reservation("R001", "Alice");
        Reservation r2 = new Reservation("R002", "Bob");

        // Create services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Access", 1500);
        Service airportPickup = new Service("Airport Pickup", 800);

        // Service Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest Alice selects services
        manager.addService(r1.getReservationId(), breakfast);
        manager.addService(r1.getReservationId(), spa);

        // Guest Bob selects services
        manager.addService(r2.getReservationId(), airportPickup);

        // Display results
        System.out.println(r1);
        System.out.println("Selected Services: " + manager.getServices(r1.getReservationId()));
        System.out.println("Additional Cost: ₹" + manager.calculateTotalCost(r1.getReservationId()));

        System.out.println();

        System.out.println(r2);
        System.out.println("Selected Services: " + manager.getServices(r2.getReservationId()));
        System.out.println("Additional Cost: ₹" + manager.calculateTotalCost(r2.getReservationId()));
    }
}