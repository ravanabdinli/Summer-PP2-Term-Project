public class Passenger {
    private String passengerID;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String passportNumber;

    public Passenger(String passengerID, String name, String address, String phoneNumber, 
                     String email, String passportNumber) {
        this.passengerID = passengerID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passengerID = passportNumber;
    }

    

    public void setPassengerID(String passengerID) {
        this.passengerID = passengerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void displayPassengerDetails() {
        System.out.println("Passenger ID: " + passengerID);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Passport: " + passportNumber);
    }
}