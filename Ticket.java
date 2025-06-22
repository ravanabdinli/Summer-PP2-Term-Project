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


    
    public String getTicketID() {
        return ticketID;
    }



    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }



    public String getSeatNumber() {
        return seatNumber;
    }



    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }



    public String getIssueDate() {
        return issueDate;
    }



    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }



    public double getTotalCost() {
        return totalCost;
    }



    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }



    public Flight getFlight() {
        return flight;
    }



    public void setFlight(Flight flight) {
        this.flight = flight;
    }



    public Passenger getPassenger() {
        return passenger;
    }



    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
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