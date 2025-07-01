package StaticMethods;

import java.util.ArrayList;
import Models.Ticket;
import Models.Flight;
import Models.Passenger;
import Services.FlightService;
import Services.PassengerService;

public class TicketPrinter {
    // === Basic Print ===
    private static final String BASIC_HEADER = "|  ID  | Seat Number |     Issue Date     | Total Cost | Flight ID | Passenger ID |";
    private static final int BASIC_WIDTH = BASIC_HEADER.length();

    // === Full Print ===
    private static final String FULL_HEADER = "|  ID  | Seat |     Date      | Price  | Flight No | From -> To        | Passenger Name       | Passport Number |";
    private static final int FULL_WIDTH = FULL_HEADER.length();

    // === BASIC PRINTERS ===
    public static void printAllBasic(ArrayList<Ticket> tickets) {
        printBasicLine();
        System.out.println(BASIC_HEADER);
        printBasicLine();
        for (var t : tickets) {
            printBasicRow(t);
        }
        printBasicLine();
    }

    public static void printOneBasic(Ticket t) {
        printBasicLine();
        System.out.println(BASIC_HEADER);
        printBasicLine();
        printBasicRow(t);
        printBasicLine();
    }

    private static void printBasicRow(Ticket t) {
        System.out.printf("| %04d | %-11s | %-18s | %-10.2f | %-9d | %-13d |\n",
            t.getId(),
            truncate(t.getSeatNumber(), 11),
            truncate(t.getIssueDate(), 18),
            t.getTotalCost(),
            t.getFlightId(),
            t.getPassengerId()
        );
    }

    private static void printBasicLine() {
        System.out.println("-".repeat(BASIC_WIDTH));
    }

    // === FULL PRINTERS ===
    public static void printAllFull(ArrayList<Ticket> tickets) {
        var flightService = new FlightService();
        var passengerService = new PassengerService();

        var flights = flightService.GetAll();
        var passengers = passengerService.getAll();

        printFullLine();
        System.out.println(FULL_HEADER);
        printFullLine();
        for (var t : tickets) {
            var flight = flights.get(t.getFlightId());
            var passenger = passengers.stream()
                                      .filter(p -> p.getId() == t.getPassengerId())
                                      .findFirst()
                                      .orElse(null);
            printFullRow(t, flight, passenger);
        }
        printFullLine();
    }

    public static void printOneFull(Ticket t) {
        var flightService = new FlightService();
        var passengerService = new PassengerService();

        var flight = flightService.GetAll().get(t.getFlightId());
        var passenger = passengerService.getAll().stream()
                                        .filter(p -> p.getId() == t.getPassengerId())
                                        .findFirst()
                                        .orElse(null);

        printFullLine();
        System.out.println(FULL_HEADER);
        printFullLine();
        printFullRow(t, flight, passenger);
        printFullLine();
    }

    private static void printFullRow(Ticket t, Flight f, Passenger p) {
        System.out.printf("| %04d | %-4s | %-13s | %-6.2f | %-9s | %-17s | %-21s | %-15s |\n",
            t.getId(),
            truncate(t.getSeatNumber(), 4),
            truncate(t.getIssueDate(), 13),
            t.getTotalCost(),
            f.getFlightNumber(),
            truncate(f.getOrigin() + " -> " + f.getDestination(), 17),
            truncate(p.getName(), 21),
            truncate(p.getPassportNumber(), 15)
        );
    }

    private static void printFullLine() {
        System.out.println("-".repeat(FULL_WIDTH));
    }

    // === Utility ===
    private static String truncate(String s, int maxLength) {
        return s.length() > maxLength ? s.substring(0, maxLength - 3) + "..." : s;
    }
}
