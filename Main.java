import Models.Flight;
import Services.FlightService;
import StaticMethods.FlightPrinter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var flightService = new FlightService();
        String field;

        while (true) {
            System.out.println("Choose a field to take actions:");
            System.out.println("1. Flights");
            System.out.println("2. Passengers");
            System.out.println("3. Tickets");

            field = sc.nextLine();

            if (field.equals("1")) {
                flight_menu:
                while (true) {
                    System.out.println("Choose an action:");
                    System.out.println("1. Create Flight");
                    System.out.println("2. Update Flight");
                    System.out.println("3. See all Flights");
                    System.out.println("4. Remove a Flight");
                    System.out.println("5. Search for Flights");
                    System.out.println("6. Filtered Search");
                    System.out.println("0. Back");

                    var userChoice = sc.nextLine();
                    switch (userChoice) {
                        case "1":
                            flightService.create();
                            System.out.println("Press enter to contiue...");
                            sc.nextLine();
                            break;
                        case "2":
                            System.out.print("Id of the flight: ");
                            int updateId = sc.nextInt();
                            sc.nextLine(); 
                            flightService.update(updateId);
                            System.out.println("Press enter to contiue...");
                            sc.nextLine();
                            break;
                        case "3":
                            var allFlights = flightService.GetAll();
                            FlightPrinter.FlightArrayListPrinter(allFlights);
                            System.out.println("Press enter to contiue...");
                            sc.nextLine();
                            break;
                        case "4":
                            System.out.print("Id of the flight: ");
                            int deleteId = sc.nextInt();
                            sc.nextLine(); 
                            flightService.delete(deleteId);
                            System.out.println("Press enter to contiue...");
                            sc.nextLine();
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
                            System.out.println("Press enter to contiue...");
                            sc.nextLine();
                            break;
                        case "6":
                            System.out.println("Example filter: Price under 200");
                            var filteredFlights = flightService.filter(f -> f.getPrice() < 200);
                            FlightPrinter.FlightArrayListPrinter(filteredFlights);
                            System.out.println("Press enter to contiue...");
                            sc.nextLine();
                            break;
                        case "0":
                            break flight_menu;
                        default:
                            System.out.println("Invalid input.");
                            break;
                    }
                }
            } else {
                System.out.println("Invalid Input");
            }
        }
    }
}
