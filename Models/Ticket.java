package Models;
public class Ticket {
    private static int _autoIncId=1;
    private int id;
    private String seatNumber;
    private String issueDate;
    private double totalCost;
    private int flightId;
    private int passengerId;

    public Ticket(int id, String seatNumber, String issueDate, double totalCost, int flightId, int passengerId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.issueDate = issueDate;
        this.totalCost = totalCost;
        this.flightId = flightId;
        this.passengerId = passengerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    
    
}