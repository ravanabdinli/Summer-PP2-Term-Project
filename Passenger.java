public class Passenger {
    private String passengerID;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String passportNumber;

    // Constructor
    public Passenger(String passengerID, String name, String address, String phoneNumber, 
                     String email, String passportNumber) {
        this.passengerID = passengerID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passengerID = passportNumber;
    }

    // Getters
    public String getPassengerID() { return passengerID; }
    public String getName() { return name; }

    public void displayPassengerDetails() {
        System.out.println("Passenger ID: " + passengerID);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Passport: " + passportNumber);
    }
}