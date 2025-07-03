package Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Predicate;

import Models.Passenger;

public class PassengerService {
    private final String PASSENGER_FILE_PATH = "StorageFiles/passengers.txt";
    private Scanner sc = new Scanner(System.in);

    private ArrayList<Passenger> loadPassengers() {
        var passengers = new ArrayList<Passenger>();
        File file = new File(PASSENGER_FILE_PATH);
        int maxId = 0;

        if (!file.exists()) {
            Passenger.setAutoIncId(1);
            return passengers;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String address = parts[2];
                        String phone = parts[3];
                        String email = parts[4];
                        String passport = parts[5];

                        passengers.add(new Passenger(id, name, address, phone, email, passport));
                        if (id > maxId)
                            maxId = id;
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed passenger: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading passengers: " + e.getMessage());
        }

        Passenger.setAutoIncId(maxId + 1);
        return passengers;
    }

    private void ensureStoragePath() {
        File dir = new File("StorageFiles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void savePassengers(ArrayList<Passenger> passengers) {
        ensureStoragePath();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PASSENGER_FILE_PATH))) {
            for (Passenger p : passengers) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving passengers: " + e.getMessage());
        }
    }

    public void createPassenger() {
        var passengers = loadPassengers();

        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phoneNumber;
        while (true) {
            phoneNumber = sc.nextLine();
            if (phoneNumber.matches("\\+?\\d{7,15}")) {
                break;
            }
            System.out.print("Invalid phone number. Enter digits only, optionally starting with '+': ");
        }

        System.out.print("Enter Email: ");
        String email;
        while (true) {
            email = sc.nextLine();
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                break;
            }
            System.out.print("Invalid email format. Please enter a valid email: ");
        }

        String passportNumber;
        while (true) {
            System.out.print("Enter Passport Number: ");
            String input = sc.nextLine();

            boolean exists = passengers.stream()
                    .anyMatch(p -> p.getPassportNumber().equalsIgnoreCase(input));

            if (exists) {
                System.out
                        .println("A passenger with this passport number already exists. Please enter a different one.");
            } else {
                passportNumber = input;
                break;
            }
        }

        Passenger passenger = new Passenger(name, address, phoneNumber, email, passportNumber);
        passengers.add(passenger);
        savePassengers(passengers);
        System.out.println("Passenger added successfully with ID: " + passenger.getId());
    }

    public void deletePassenger(int id) {
        var ticketService=new TicketService();
        var passengers = loadPassengers();
        boolean removed = passengers.removeIf(p -> p.getId() == id);
        if (removed) {
            savePassengers(passengers);
            ticketService.deleteTicketsByPassengerId(id);
            System.out.println("Passenger with ID " + id + " removed successfully.");
        } else {
            System.out.println("Passenger with ID " + id + " not found.");
        }
    }

    public void updatePassenger(int id) {
        var passengers = loadPassengers();
        Passenger passenger = passengers.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (passenger == null) {
            System.out.println("Passenger with ID " + id + " not found.");
            return;
        }

        while (true) {
            System.out.println(
                    "\n--- Update Passenger (ID: " + passenger.getId() + ", Name: " + passenger.getName() + ") ---");
            System.out.println("1. Name (" + passenger.getName() + ")");
            System.out.println("2. Address (" + passenger.getAddress() + ")");
            System.out.println("3. Phone Number (" + passenger.getPhoneNumber() + ")");
            System.out.println("4. Email (" + passenger.getEmail() + ")");
            System.out.println("5. Passport Number (" + passenger.getPassportNumber() + ")");
            System.out.println("0. Finish Updating and Save");
            System.out.print("Enter your choice: ");
            var choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter New Name: ");
                    passenger.setName(sc.nextLine());
                    System.out.println("Name updated.");
                    break;
                case "2":
                    System.out.print("Enter New Address: ");
                    passenger.setAddress(sc.nextLine());
                    System.out.println("Address updated.");
                    break;
                case "3":
                    while (true) {
                        System.out.print("Enter New Phone Number: ");
                        String phone = sc.nextLine();
                        if (phone.matches("\\+?\\d{7,15}")) {
                            passenger.setPhoneNumber(phone);
                            System.out.println("Phone number updated.");
                            break;
                        }
                        System.out.println("Invalid phone number. Please try again.");
                    }
                    break;
                case "4":
                    while (true) {
                        System.out.print("Enter New Email: ");
                        String email = sc.nextLine();
                        if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                            passenger.setEmail(email);
                            System.out.println("Email updated.");
                            break;
                        }
                        System.out.println("Invalid email format. Please try again.");
                    }
                    break;
                case "5":
                    System.out.print("Enter New Passport Number: ");
                    passenger.setPassportNumber(sc.nextLine());
                    System.out.println("Passport number updated.");
                    break;
                case "0":
                    savePassengers(passengers);
                    System.out.println("Passenger update complete and saved.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 0 to 5.");
                    break;
            }
        }
    }

    public ArrayList<Passenger> getAll() {
        return loadPassengers();
    }

    public Passenger getPassengerById(int id) {
        return loadPassengers().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Passenger> searchPassengerByName() {
        System.out.print("Enter name keyword to search: ");
        String keyword = sc.nextLine().toLowerCase();
        var passengers = loadPassengers();
        var result = new ArrayList<Passenger>();
        for (var p : passengers) {
            if (p.getName().toLowerCase().contains(keyword)) {
                result.add(p);
            }
        }
        return result;
    }

    public ArrayList<Passenger> filterPassengers(Predicate<Passenger> predicate) {
        var result = new ArrayList<Passenger>();
        for (var p : loadPassengers()) {
            if (predicate.test(p)) {
                result.add(p);
            }
        }
        return result;
    }

    public ArrayList<Passenger> sortPassengers() {
        var passengers = loadPassengers();
        if (passengers.isEmpty()) {
            System.out.println("No passengers available to sort.");
            return passengers;
        }

        System.out.println("\nSort Passengers By:");
        System.out.println("1. Name");
        System.out.println("2. Address");
        System.out.println("3. Phone Number");
        System.out.println("4. Email");
        System.out.println("5. Passport Number");
        System.out.print("Choose an option (1-5): ");
        String choice = sc.nextLine();

        System.out.print("Sort Direction (asc/desc): ");
        String direction = sc.nextLine().toLowerCase();
        boolean ascending = direction.equals("asc");

        Comparator<Passenger> comparator;

        switch (choice) {
            case "1":
                comparator = Comparator.comparing(Passenger::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "2":
                comparator = Comparator.comparing(Passenger::getAddress, String.CASE_INSENSITIVE_ORDER);
                break;
            case "3":
                comparator = Comparator.comparing(Passenger::getPhoneNumber);
                break;
            case "4":
                comparator = Comparator.comparing(Passenger::getEmail, String.CASE_INSENSITIVE_ORDER);
                break;
            case "5":
                comparator = Comparator.comparing(Passenger::getPassportNumber);
                break;
            default:
                System.out.println("Invalid choice.");
                return passengers;
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        passengers.sort(comparator);

        System.out.print("Do you want to save the sorted list to file? (yes/no): ");
        String saveChoice = sc.nextLine().trim().toLowerCase();
        if (saveChoice.equals("yes") || saveChoice.equals("y")) {
            savePassengers(passengers);
            System.out.println("Sorted passenger list saved successfully.");
        } else {
            System.out.println("Sorted passenger list was not saved.");
        }

        return passengers;
    }

}