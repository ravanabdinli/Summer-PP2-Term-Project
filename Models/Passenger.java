package Models;

public class Passenger {

    private static int _autoIncId = 1;
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String passportNumber;

    public Passenger(String name, String address, String phoneNumber, String email, String passportNumber) {
        this.id = _autoIncId++;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
    }

    public Passenger(int id, String name, String address, String phoneNumber, String email, String passportNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
    }

    public static void setAutoIncId(int value) {
        _autoIncId = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return id + ";" + name + ";" + address + ";" + phoneNumber + ";" + email + ";" + passportNumber;
    }

}