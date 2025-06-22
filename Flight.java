public class Flight {
    private String flightNumber;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private int availableSeats;

    
    public Flight(String flightNumber, String destination, String departureTime, 
                  String arrivalTime, double price, int availableSeats) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getDestination() { return destination; }
    public double getPrice() { return price; }
    public int getAvailableSeats() { return availableSeats; }

    public void reduceSeats(int num) {
        if (num <= availableSeats) {
            availableSeats -= num;
        } else {
            System.out.println("Not enough seats available!");
        }
    }

    public void displayFlightDetails() {
        System.out.println("Flight: " + flightNumber + ", Destination: " + destination +
                           ", Departure: " + departureTime + ", Arrival: " + arrivalTime +
                           ", Price: $" + price + ", Seats Available: " + availableSeats);
    }
}