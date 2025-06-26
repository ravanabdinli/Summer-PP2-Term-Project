package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private static int _autoIncId = 1;
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

    public Ticket(String seatNumber, double totalCost, int flightId, int passengerId) {
        this.id = _autoIncId;
        this.seatNumber = seatNumber;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.issueDate = LocalDateTime.now().format(formatter);        
        this.totalCost = totalCost;
        this.flightId = flightId;
        this.passengerId = passengerId;
    }

    public static void setAutoIncId(int id) {
        _autoIncId = id;
    }

    public static int getAutoIncId() {
        return _autoIncId;
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

    @Override
    public String toString() {
        return id + ";" +
                seatNumber + ";" +
                issueDate + ";" +
                totalCost + ";" +
                flightId + ";" +
                passengerId;
    }

}