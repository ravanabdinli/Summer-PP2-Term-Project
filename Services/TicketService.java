package Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

import Models.Passenger;
import Models.Ticket;

public class TicketService {
    private final String TICKET_FILE_PATH = "StorageFiles/tickets.txt";
    private Scanner sc = new Scanner(System.in);

    private ArrayList<Ticket> loadTickets() {
        var tickets = new ArrayList<Ticket>();
        File file = new File(TICKET_FILE_PATH);
        int maxId = 0;

        if (!file.exists()) {
            Ticket.setAutoIncId(1);
            return tickets;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String seatNumber = parts[1];
                        String issueDate = parts[2];
                        double totalCost = Double.parseDouble(parts[3]);
                        int flightId = Integer.parseInt(parts[4]);
                        int passengerId = Integer.parseInt(parts[5]);

                        Ticket ticket = new Ticket(id, seatNumber, issueDate, totalCost, flightId, passengerId);
                        tickets.add(ticket);

                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println(
                                "Skipping malformed line in tickets file: " + line + " - Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading tickets file: " + e.getMessage());
        }

        Ticket.setAutoIncId(maxId + 1);
        return tickets;
    }

    private void ensureTicketStoragePath() {
        File dir = new File("StorageFiles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void saveTickets(ArrayList<Ticket> tickets) {
        ensureTicketStoragePath();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TICKET_FILE_PATH))) {
            for (Ticket ticket : tickets) {
                bw.write(ticket.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tickets to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void create() {
        System.out.print("Enter Flight ID: ");
        int flightId;
        while (true) {
            try {
                flightId = Integer.parseInt(sc.nextLine());
                if (flightId > 0)
                    break;
                else
                    System.out.println("Flight ID must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid Flight ID. Please enter an integer.");
            }
        }

        // while (true) {
        // try {
        // passengerId = Integer.parseInt(sc.nextLine());
        // if (passengerId > 0)
        // break;
        // else
        // System.out.println("Passenger ID must be positive.");
        // } catch (NumberFormatException e) {
        // System.out.println("Invalid Passenger ID. Please enter an integer.");
        // }
        // }

        System.out.print("Enter Passport Number: ");
        String passportNumber;
        int passengerId;

        while (true) {
            System.out.print("Enter passport number: ");
            passportNumber = sc.nextLine();
            try {
                passengerId = this.getPassengerId(passportNumber);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Passenger not found. Please try again.");
            }
        }

        System.out.print("Enter Seat Number (e.g., 12A): ");
        String seatNumber = sc.nextLine();
        var flightService = new FlightService();
        var flight = flightService.GetAll().get(flightId);
        double price = flight.getPrice();
        var createdTicket = new Ticket(seatNumber, price, flightId, passengerId);
        System.out.println("Ticket is purchased. Your ticket ID: " + createdTicket.getId());

        var numberOfSeats = flight.getAvailableSeats();
        flight.setAvailableSeats(numberOfSeats - 1);
    }

    private int getPassengerId(String passportNumber) {
        var passengerService = new PassengerService();
        Passenger[] passengers = passengerService.getAll().toArray(new Passenger[0]);

        for (var item : passengers) {
            if (item.getPassportNumber().equals(passportNumber)) {
                return item.getId();
            }
        }

        throw new IllegalArgumentException("No passenger found with passport number: " + passportNumber);
    }

    public void delete(int id) {
        var tickets = loadTickets();
        boolean removed = tickets.removeIf(t -> t.getId() == id);
        if (removed) {
            saveTickets(tickets);
            System.out.println("Ticket with ID " + id + " removed successfully.");
            var flightService = new FlightService();
            var flight = flightService.GetAll().get(tickets.get(id).getFlightId());
            var nSeats = flight.getAvailableSeats();
            flight.setAvailableSeats(nSeats + 1);
        } else {
            System.out.println("Ticket with ID " + id + " not found.");
        }
    }

    public void update(int id) {
        var tickets = loadTickets();
        Ticket ticket = tickets.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (ticket == null) {
            System.out.println("Ticket with ID " + id + " not found.");
            return;
        }

        while (true) {
            System.out.println("\n--- Update Ticket (ID: " + ticket.getId() + ", Seat Number: " + ticket.getSeatNumber()
                    + ", Flight ID: " + ticket.getFlightId() + ") ---");
            System.out.println("1. Seat Number (" + ticket.getSeatNumber() + ")");
            System.out.println("2. Flight ID (" + ticket.getFlightId() + ")");
            System.out.println("0. Finish Updating and Save");
            System.out.print("Enter your choice: ");
            var choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter New Seat Number: ");
                    ticket.setSeatNumber(sc.nextLine());
                    System.out.println("Seat number updated.");
                    break;

                case "2":
                    int newFlightId;
                    while (true) {
                        System.out.print("Enter New Flight ID: ");
                        try {
                            newFlightId = Integer.parseInt(sc.nextLine());
                            if (newFlightId > 0) {
                                break;
                            } else {
                                System.out.println("Flight ID must be positive.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Flight ID. Please enter an integer.");
                        }
                    }

                    ticket.setFlightId(newFlightId);

                    System.out.print("Enter New Seat Number (required because flight changed): ");
                    ticket.setSeatNumber(sc.nextLine());

                    System.out.println("Flight ID and Seat number updated.");
                    break;

                case "0":
                    saveTickets(tickets);
                    System.out.println("Ticket update complete and saved.");
                    return;

                default:
                    System.out.println("Invalid choice. Please enter 0, 1, or 2.");
                    break;
            }
        }
    }

    public ArrayList<Ticket> getAll(){
        return loadTickets();
    }

    public Ticket getById(int id){
        return loadTickets().stream()
                            .filter(f -> f.getId() == id)
                            .findFirst()
                            .orElse(null);
    }

    public ArrayList<Ticket> filter(Predicate<Ticket> predicate) {
        var result = new ArrayList<Ticket>();
        for (var f : loadTickets()) {
            if (predicate.test(f)) {
                result.add(f);
            }
        }
        return result;
    }

    //filter based on dest origin logic elave ele
}