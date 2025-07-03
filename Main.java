import Services.FlightService;
import Services.PassengerService;
import Services.TicketService;
import StaticMethods.FlightPrinter;
import StaticMethods.PassengerPrinter;
import StaticMethods.TicketPrinter;

import java.util.ArrayList;
import java.util.Scanner;

import Models.Flight;
import Models.Passenger;
import Models.Ticket;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var flightService = new FlightService();
        var passengerService = new PassengerService();
        var ticketService = new TicketService();
        String field;

        while (true) {
            System.out.println("Choose a field to take actions:");
            System.out.println("1. Flights");
            System.out.println("2. Passengers");
            System.out.println("3. Tickets");

            field = sc.nextLine();

            if (field.equals("1")) {
                clearScreen();
                flightMenu(flightService, sc);
            } else if (field.equals("2")) {
                clearScreen();
                passengerMenu(passengerService, sc);
            } else if (field.equals("3")) {
                clearScreen();
                ticketMenu(ticketService, sc);
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    private static void ticketMenu(TicketService ticketService, Scanner sc) {
        ticket_menu: while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Create Ticket");
            System.out.println("2. Update Ticket");
            System.out.println("3. See All Tickets (Basic)");
            System.out.println("4. See All Tickets (Full)");
            System.out.println("5. Remove a Ticket");
            System.out.println("6. Filtered Search");
            System.out.println("7. Sort Tickets");
            System.out.println("0. Back");

            String userChoice = sc.nextLine();

            switch (userChoice) {
                case "1":
                    ticketService.create();
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "2":
                    System.out.print("ID of the ticket: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    ticketService.update(updateId);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "3":
                    TicketPrinter.printAllBasic(ticketService.getAll());
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "4":
                    TicketPrinter.printAllFull(ticketService.getAll());
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "5":
                    System.out.print("ID of the ticket: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    ticketService.delete(deleteId);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "6":
                    while (true) {
                        System.out.println("\n--- Filter Tickets By ---");
                        System.out.println("1. Flight Number");
                        System.out.println("2. Origin");
                        System.out.println("3. Destination");
                        System.out.println("4. Passenger Passport Number");
                        System.out.println("5. Combine Filters");
                        System.out.println("0. Back");
                        System.out.print("Your choice: ");
                        String filterChoice = sc.nextLine();

                        ArrayList<Ticket> filtered = new ArrayList<>();

                        switch (filterChoice) {
                            case "1":
                                System.out.print("Enter flight number: ");
                                String flightNumber = sc.nextLine();
                                filtered = ticketService.filterByFlightAndPassenger(flightNumber, null, null, null);
                                break;
                            case "2":
                                System.out.print("Enter origin: ");
                                String origin = sc.nextLine();
                                filtered = ticketService.filterByFlightAndPassenger(null, origin, null, null);
                                break;
                            case "3":
                                System.out.print("Enter destination: ");
                                String destination = sc.nextLine();
                                filtered = ticketService.filterByFlightAndPassenger(null, null, destination, null);
                                break;
                            case "4":
                                System.out.print("Enter passport number: ");
                                String passport = sc.nextLine();
                                filtered = ticketService.filterByFlightAndPassenger(null, null, null, passport);
                                break;
                            case "5":
                                System.out.print("Flight Number (or press Enter to skip): ");
                                String fNum = sc.nextLine().trim();
                                if (fNum.isEmpty())
                                    fNum = null;

                                System.out.print("Origin (or press Enter to skip): ");
                                String ori = sc.nextLine().trim();
                                if (ori.isEmpty())
                                    ori = null;

                                System.out.print("Destination (or press Enter to skip): ");
                                String dest = sc.nextLine().trim();
                                if (dest.isEmpty())
                                    dest = null;

                                System.out.print("Passport Number (or press Enter to skip): ");
                                String pNum = sc.nextLine().trim();
                                if (pNum.isEmpty())
                                    pNum = null;

                                filtered = ticketService.filterByFlightAndPassenger(fNum, ori, dest, pNum);
                                break;
                            case "0":
                                clearScreen();
                                continue ticket_menu;
                            default:
                                System.out.println("Invalid choice.");
                                continue;
                        }

                        if (filtered.isEmpty()) {
                            System.out.println("No tickets matched the filter.");
                        } else {
                            TicketPrinter.printAllFull(filtered);
                        }
                        System.out.println("Press Enter to continue...");
                        sc.nextLine();
                        clearScreen();
                    }
                case "7":
                    var sorted = ticketService.sortTickets();
                    TicketPrinter.printAllFull(sorted);
                    System.out.println("Press Enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
                case "0":
                    clearScreen();
                    break ticket_menu;

                default:
                    System.out.println("Invalid input.");
                    System.out.println("Press Enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
            }
        }
    }

    private static void passengerMenu(PassengerService passengerService, Scanner sc) {
        passenger_menu: while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Create Passenger");
            System.out.println("2. Update Passenger");
            System.out.println("3. See All Passengers");
            System.out.println("4. Remove a Passenger");
            System.out.println("5. Search for Passengers");
            System.out.println("6. Filtered Search");
            System.out.println("7. Sort Passengers");
            System.out.println("0. Back");

            var userChoice = sc.nextLine();
            switch (userChoice) {
                case "1":
                    passengerService.createPassenger();
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "2":
                    System.out.print("ID of the passenger: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    passengerService.updatePassenger(updateId);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "3":
                    var allPassengers = passengerService.getAll();
                    PassengerPrinter.PrintAll(allPassengers);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "4":
                    System.out.print("ID of the passenger: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    passengerService.deletePassenger(deleteId);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "5":
                    var results = passengerService.searchPassengerByName();
                    PassengerPrinter.PrintAll(results);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "6":
                    while (true) {
                        System.out.println("\n--- Filter Passengers By ---");
                        System.out.println("1. Name (contains)");
                        System.out.println("2. Address (contains)");
                        System.out.println("3. Phone starts with");
                        System.out.println("4. Email ends with");
                        System.out.println("5. Passport Number (contains)");
                        System.out.println("0. Back");
                        System.out.print("Choose a field to filter by: ");
                        String filterChoice = sc.nextLine();

                        ArrayList<Passenger> filtered = new ArrayList<>();

                        switch (filterChoice) {
                            case "1":
                                System.out.print("Enter name keyword: ");
                                String name = sc.nextLine().toLowerCase();
                                filtered = passengerService
                                        .filterPassengers(p -> p.getName().toLowerCase().contains(name));
                                break;

                            case "2":
                                System.out.print("Enter address keyword: ");
                                String address = sc.nextLine().toLowerCase();
                                filtered = passengerService
                                        .filterPassengers(p -> p.getAddress().toLowerCase().contains(address));
                                break;

                            case "3":
                                System.out.print("Enter phone prefix: ");
                                String phonePrefix = sc.nextLine();
                                filtered = passengerService
                                        .filterPassengers(p -> p.getPhoneNumber().startsWith(phonePrefix));
                                break;

                            case "4":
                                System.out.print("Enter email domain (e.g., gmail.com): ");
                                String domain = sc.nextLine().toLowerCase();
                                filtered = passengerService
                                        .filterPassengers(p -> p.getEmail().toLowerCase().endsWith(domain));
                                break;

                            case "5":
                                System.out.print("Enter passport keyword: ");
                                String passport = sc.nextLine().toLowerCase();
                                filtered = passengerService
                                        .filterPassengers(p -> p.getPassportNumber().toLowerCase().contains(passport));
                                break;

                            case "0":
                                clearScreen();
                                continue passenger_menu;

                            default:
                                System.out.println("Invalid choice.");
                                continue;
                        }

                        if (filtered.isEmpty()) {
                            System.out.println("No passengers matched the filter.");
                        } else {
                            PassengerPrinter.PrintAll(filtered);
                        }
                        System.out.println("Press Enter to continue...");
                        sc.nextLine();
                        clearScreen();
                    }

                case "7":
                    var sorted = passengerService.sortPassengers();
                    PassengerPrinter.PrintAll(sorted);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "0":
                    clearScreen();
                    break passenger_menu;

                default:
                    System.out.println("Invalid input.");
                    System.out.println("Press Enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
            }
        }
    }

    private static void flightMenu(FlightService flightService, Scanner sc) {
        flight_menu: while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Create Flight");
            System.out.println("2. Update Flight");
            System.out.println("3. See all Flights");
            System.out.println("4. Remove a Flight");
            System.out.println("5. Search for Flights");
            System.out.println("6. Filtered Search");
            System.out.println("7. Sort Flights");
            System.out.println("0. Back");

            var userChoice = sc.nextLine();
            switch (userChoice) {
                case "1":
                    flightService.create();
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
                case "2":
                    System.out.print("Id of the flight: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    flightService.update(updateId);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
                case "3":
                    var allFlights = flightService.GetAll();
                    FlightPrinter.FlightArrayListPrinter(allFlights);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
                case "4":
                    System.out.print("Id of the flight: ");
                    int deleteId = sc.nextInt();
                    sc.nextLine();
                    flightService.delete(deleteId);
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
                case "5":
                    System.out.println("Choose one of the following:");
                    System.out.println("1. Search by Destination");
                    System.out.println("2. Search by Source");
                    String searchType = sc.nextLine();
                    if (searchType.equals("1")) {
                        var destResults = flightService.searchByDestination();
                        FlightPrinter.FlightArrayListPrinter(destResults);
                    } else if (searchType.equals("2")) {
                        var originResults = flightService.searchByOrigin();
                        FlightPrinter.FlightArrayListPrinter(originResults);
                    } else {
                        System.out.println("Invalid input.");
                    }
                    System.out.println("Press enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
                case "6":
                    while (true) {
                        System.out.println("\n--- Filter Flights By ---");
                        System.out.println("1. Flight Number");
                        System.out.println("2. Origin");
                        System.out.println("3. Destination");
                        System.out.println("4. Departure Time (contains)");
                        System.out.println("5. Arrival Time (contains)");
                        System.out.println("6. Price under");
                        System.out.println("7. Price over");
                        System.out.println("8. Available Seats at least");
                        System.out.println("0. Back");
                        System.out.print("Choose a field to filter by: ");
                        String filterChoice = sc.nextLine();

                        ArrayList<Flight> filteredFlights = new ArrayList<>();

                        switch (filterChoice) {
                            case "1":
                                System.out.print("Enter Flight Number keyword: ");
                                String flightNo = sc.nextLine().toLowerCase();
                                filteredFlights = flightService
                                        .filter(f -> f.getFlightNumber().toLowerCase().contains(flightNo));
                                break;
                            case "2":
                                System.out.print("Enter Origin keyword: ");
                                String origin = sc.nextLine().toLowerCase();
                                filteredFlights = flightService
                                        .filter(f -> f.getOrigin().toLowerCase().contains(origin));
                                break;
                            case "3":
                                System.out.print("Enter Destination keyword: ");
                                String dest = sc.nextLine().toLowerCase();
                                filteredFlights = flightService
                                        .filter(f -> f.getDestination().toLowerCase().contains(dest));
                                break;
                            case "4":
                                System.out.print("Enter Departure Time keyword: ");
                                String depTime = sc.nextLine();
                                filteredFlights = flightService
                                        .filter(f -> f.getDepartureTime().contains(depTime));
                                break;
                            case "5":
                                System.out.print("Enter Arrival Time keyword: ");
                                String arrTime = sc.nextLine();
                                filteredFlights = flightService
                                        .filter(f -> f.getArrivalTime().contains(arrTime));
                                break;
                            case "6":
                                System.out.print("Enter maximum price: ");
                                try {
                                    double maxPrice = Double.parseDouble(sc.nextLine());
                                    filteredFlights = flightService.filter(f -> f.getPrice() <= maxPrice);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid price input.");
                                    System.out.println("Press Enter to continue...");
                                    sc.nextLine();
                                    clearScreen();
                                    continue;
                                }
                                break;
                            case "7":
                                System.out.print("Enter minimum price: ");
                                try {
                                    double minPrice = Double.parseDouble(sc.nextLine());
                                    filteredFlights = flightService.filter(f -> f.getPrice() >= minPrice);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid price input.");
                                    System.out.println("Press Enter to continue...");
                                    sc.nextLine();
                                    clearScreen();
                                    continue;
                                }
                                break;
                            case "8":
                                System.out.print("Enter minimum available seats: ");
                                try {
                                    int minSeats = Integer.parseInt(sc.nextLine());
                                    filteredFlights = flightService
                                            .filter(f -> f.getAvailableSeats() >= minSeats);
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid seat input.");
                                    System.out.println("Press Enter to continue...");
                                    sc.nextLine();
                                    clearScreen();
                                    continue;
                                }
                                break;
                            case "0":
                                clearScreen();
                                continue flight_menu;
                            default:
                                System.out.println("Invalid choice.");
                                System.out.println("Press Enter to continue...");
                                sc.nextLine();
                                clearScreen();
                                continue;
                        }

                        if (filteredFlights.isEmpty()) {
                            System.out.println("No flights matched the filter.");
                        } else {
                            FlightPrinter.FlightArrayListPrinter(filteredFlights);
                        }
                        System.out.println("Press Enter to continue...");
                        sc.nextLine();
                        clearScreen();
                    }
                case "7":
                    ArrayList<Flight> sortedFlights = flightService.sortFlights();
                    if (!sortedFlights.isEmpty()) {
                        FlightPrinter.FlightArrayListPrinter(sortedFlights);
                    } else {
                        System.out.println("No flights to display.");
                    }
                    System.out.println("Press Enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;

                case "0":
                    clearScreen();
                    break flight_menu;
                default:
                    System.out.println("Invalid input.");
                    System.out.println("Press Enter to continue...");
                    sc.nextLine();
                    clearScreen();
                    break;
            }
        }
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J"); // ANSI escape code
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: print many new lines
            for (int i = 0; i < 50; i++)
                System.out.println();
        }
    }

}
