public class Ticket {
    private String ticketID;
    private String seatNumber;
    private String issueDate;
    private double totalCost;
    private Flight flight;
    private Passenger passenger;

    // Constructor
    public Ticket(String ticketID, String seatNumber, String issueDate, 
                  Flight flight, Passenger passenger) {
        this.ticketID = ticketID;
        this.seatNumber = seatNumber;
        this.issueDate = issueDate;
        this.flight = flight;
        this.passenger = passenger;
        this.totalCost = flight.getPrice();
    }

    public void displayTicketDetails() {
        System.out.println("Ticket ID: " + ticketID);
        System.out.println("Seat: " + seatNumber);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Total Cost: $" + totalCost);
        System.out.println("Flight Details:");
        flight.displayFlightDetails();
        System.out.println("Passenger Details:");
        passenger.displayPassengerDetails();
    }
}