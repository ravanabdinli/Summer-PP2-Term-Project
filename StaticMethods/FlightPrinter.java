package StaticMethods;

import java.util.ArrayList;

import Models.Flight;

//     private int id;
//     private String flightNumber;
//     private String origin;
//     private String destination;
//     private String departureTime;
//     private String arrivalTime;
//     private double price;
//     private int availableSeats;

public class FlightPrinter {
    public static void FlightArrayListPrinter(ArrayList<Flight> flights){
        System.out.println("-".repeat(127));
        System.out.println("| ID | Flight Number |    Origin    |  Destination  |    Departure Time    |     Arrival Time     |  Price  | Available Seats |");
        System.out.println("-".repeat(127));
        for(var item :flights){
            System.out.printf("| %02d | %13s | %12s | %13s | %20s | %20s | %7.2f |      %5d      |\n", item.getId(),item.getFlightNumber(),
            item.getOrigin(), item.getDestination(), item.getDepartureTime(), item.getArrivalTime(), item.getPrice(), item.getAvailableSeats());
        }
        System.out.println("-".repeat(127));

    }

    public static void SingleFlightPrinter(Flight flight){
        System.out.println("-".repeat(127));
        System.out.println("| ID | Flight Number |    Origin    |  Destination  |    Departure Time    |     Arrival Time     |  Price  | Available Seats |");
        System.out.println("-".repeat(127));
        System.out.printf("| %02d | %13s | %12s | %13s | %20s | %20s | %7.2f |      %5d       |\n", flight.getId(),flight.getFlightNumber(),
        flight.getOrigin(), flight.getDestination(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getPrice(), flight.getAvailableSeats());
        System.out.println("-".repeat(127));

    }
}
