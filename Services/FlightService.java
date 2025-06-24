package Services;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import Models.*;

public class FlightService {
    private final String FILE_PATH = "StorageFiles/flights.txt";
    private Scanner sc = new Scanner(System.in);

    private ArrayList<Flight> loadFlights() {
        var flights = new ArrayList<Flight>();
        File file = new File(FILE_PATH);
        int maxId = 0;

        if (!file.exists()) {
            Flight.setAutoIncId(1);
            return flights;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 8) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String flightNumber = parts[1];
                        String origin = parts[2];
                        String destination = parts[3];
                        String departureTime = parts[4];
                        String arrivalTime = parts[5];
                        double price = Double.parseDouble(parts[6]);
                        int availableSeats = Integer.parseInt(parts[7]);

                        Flight flight = new Flight(id, flightNumber, origin, destination, departureTime, arrivalTime, price, availableSeats);
                        flights.add(flight);

                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed line in flights file: " + line + " - Error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading flights file: " + e.getMessage());
        }

        Flight.setAutoIncId(maxId + 1);
        return flights;
    }

    private void ensureStoragePath() {
        File dir = new File("StorageFiles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void saveFlights(ArrayList<Flight> flights) {
        ensureStoragePath();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Flight flight : flights) {
                bw.write(flight.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving flights to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void create() {
        var flights = loadFlights();

        System.out.print("Enter Flight Number: ");
        var flightNumber = sc.nextLine();

        System.out.print("Enter Flight Origin: ");
        var origin = sc.nextLine();

        System.out.print("Enter Flight Destination: ");
        var destination = sc.nextLine();

        String departureTime;
        while (true) {
            System.out.print("Enter Departure Time (DD/MM/YYYY HH:MM): ");
            departureTime = sc.nextLine();
            if (departureTime.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                break;
            }
            System.out.println("Invalid format. Please use DD/MM/YYYY HH:MM (e.g., 25/12/2024 14:30).");
        }

        String arrivalTime;
        while (true) {
            System.out.print("Enter Arrival Time (DD/MM/YYYY HH:MM): ");
            arrivalTime = sc.nextLine();
            if (arrivalTime.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                break;
            }
            System.out.println("Invalid format. Please use DD/MM/YYYY HH:MM (e.g., 25/12/2024 16:00).");
        }

        double price;
        while (true) {
            System.out.print("Enter Ticket Price: ");
            try {
                price = Double.parseDouble(sc.nextLine());
                if (price >= 0) {
                    break;
                } else {
                    System.out.println("Price cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a numeric value.");
            }
        }

        int availableSeats;
        while (true) {
            System.out.print("Enter Available Seats: ");
            try {
                availableSeats = Integer.parseInt(sc.nextLine());
                if (availableSeats >= 0) {
                    break;
                } else {
                    System.out.println("Available seats cannot be negative.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter an integer.");
            }
        }

        Flight flight = new Flight(flightNumber, origin, destination, departureTime, arrivalTime, price, availableSeats);
        flights.add(flight);
        saveFlights(flights);
        System.out.println("Flight added successfully with ID: " + flight.getId());
    }

    public void delete(int id) {
        var flights = loadFlights();
        boolean removed = flights.removeIf(f -> f.getId() == id);
        if (removed) {
            saveFlights(flights);
            System.out.println("Flight with ID " + id + " removed successfully.");
        } else {
            System.out.println("Flight with ID " + id + " not found.");
        }
    }

    public void update(int id) {
        var flights = loadFlights();
        Flight flight = flights.stream()
                               .filter(f -> f.getId() == id)
                               .findFirst()
                               .orElse(null);

        if (flight == null) {
            System.out.println("Flight with ID " + id + " not found.");
            return;
        }

        while (true) {
            System.out.println("\n--- Update Flight (ID: " + flight.getId() + ", Flight Number: " + flight.getFlightNumber() + ") ---");
            System.out.println("1. Flight Number (" + flight.getFlightNumber() + ")");
            System.out.println("2. Origin (" + flight.getOrigin() + ")");
            System.out.println("3. Destination (" + flight.getDestination() + ")");
            System.out.println("4. Departure Time (" + flight.getDepartureTime() + ")");
            System.out.println("5. Arrival Time (" + flight.getArrivalTime() + ")");
            System.out.println("6. Price (" + flight.getPrice() + ")");
            System.out.println("7. Available Seats (" + flight.getAvailableSeats() + ")");
            System.out.println("0. Finish Updating and Save");
            System.out.print("Enter your choice: ");
            var choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter New Flight Number: ");
                    flight.setFlightNumber(sc.nextLine());
                    System.out.println("Flight number updated.");
                    break;
                case "2":
                    System.out.print("Enter New Origin: ");
                    flight.setOrigin(sc.nextLine());
                    System.out.println("Origin updated.");
                    break;
                case "3":
                    System.out.print("Enter New Destination: ");
                    flight.setDestination(sc.nextLine());
                    System.out.println("Destination updated.");
                    break;
                case "4":
                    while (true) {
                        System.out.print("Enter New Departure Time (DD/MM/YYYY HH:MM): ");
                        String time = sc.nextLine();
                        if (time.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                            flight.setDepartureTime(time);
                            System.out.println("Departure time updated.");
                            break;
                        }
                        System.out.println("Invalid format. Please use DD/MM/YYYY HH:MM.");
                    }
                    break;
                case "5":
                    while (true) {
                        System.out.print("Enter New Arrival Time (DD/MM/YYYY HH:MM): ");
                        String time = sc.nextLine();
                        if (time.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                            flight.setArrivalTime(time);
                            System.out.println("Arrival time updated.");
                            break;
                        }
                        System.out.println("Invalid format. Please use DD/MM/YYYY HH:MM.");
                    }
                    break;
                case "6":
                    while (true) {
                        System.out.print("Enter New Price: ");
                        try {
                            double p = Double.parseDouble(sc.nextLine());
                            if (p >= 0) {
                                flight.setPrice(p);
                                System.out.println("Price updated.");
                                break;
                            } else {
                                System.out.println("Price cannot be negative.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price. Please enter a numeric value.");
                        }
                    }
                    break;
                case "7":
                    while (true) {
                        System.out.print("Enter New Available Seats: ");
                        try {
                            int s = Integer.parseInt(sc.nextLine());
                            if (s >= 0) {
                                flight.setAvailableSeats(s);
                                System.out.println("Available seats updated.");
                                break;
                            } else {
                                System.out.println("Available seats cannot be negative.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number. Please enter an integer.");
                        }
                    }
                    break;
                case "0":
                    saveFlights(flights);
                    System.out.println("Flight update complete and saved.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 0 to 7.");
                    break;
            }
        }
    }

    public ArrayList<Flight> GetAll() {
        return loadFlights();
    }

    public Flight getById(int id) {
        return loadFlights().stream()
                            .filter(f -> f.getId() == id)
                            .findFirst()
                            .orElse(null);
    }

    public ArrayList<Flight> searchByDestination() {
        System.out.print("Enter destination keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();
        var flights = loadFlights();
        var result = new ArrayList<Flight>();
        for (var f : flights) {
            if (f.getDestination().toLowerCase().contains(keyword)) {
                result.add(f);
            }
        }
        return result;
    }

    public ArrayList<Flight> searchByOrigin() {
        System.out.print("Enter origin keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();
        var flights = loadFlights();
        var result = new ArrayList<Flight>();
        for (var f : flights) {
            if (f.getOrigin().toLowerCase().contains(keyword)) {
                result.add(f);
            }
        }
        return result;
    }

    public ArrayList<Flight> filter(Predicate<Flight> predicate) {
        var result = new ArrayList<Flight>();
        for (var f : loadFlights()) {
            if (predicate.test(f)) {
                result.add(f);
            }
        }
        return result;
    }
}
