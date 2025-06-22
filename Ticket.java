public class Ticket {
    private static int _autoIncId=0;
    private int id;
    private String seatNumber;
    private String issueDate;
    private double totalCost;
    private Flight flight;
    private Passenger passenger;

    public Ticket(String ticketID, String seatNumber, String issueDate, 
                  Flight flight, Passenger passenger) {
        this.id = _autoIncId++;
        this.seatNumber = seatNumber;
        this.issueDate = issueDate;
        this.flight = flight;
        this.passenger = passenger;
        this.totalCost = flight.getPrice();
    }


    
    public int getId() {
        return id;
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

    
}