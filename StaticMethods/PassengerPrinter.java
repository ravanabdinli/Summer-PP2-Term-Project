package StaticMethods;

import java.util.ArrayList;
import Models.Passenger;

public class PassengerPrinter {

    private static final String HEADER = "|  ID  |         Name         |        Address        |  Phone Number   |           Email           | Passport Number |";
    private static final int WIDTH = HEADER.length();

    public static void PrintAll(ArrayList<Passenger> passengers) {
        printLine();
        System.out.println(HEADER);
        printLine();
        for (var p : passengers) {
            printRow(p);
        }
        printLine();
    }

    public static void PrintOne(Passenger p) {
        printLine();
        System.out.println(HEADER);
        printLine();
        printRow(p);
        printLine();
    }

    private static void printLine() {
        System.out.println("-".repeat(WIDTH));
    }

    private static void printRow(Passenger p) {
        System.out.printf("| %04d | %-20s | %-21s | %-16s | %-24s | %-15s |\n",
            p.getId(),
            truncate(p.getName(), 20),
            truncate(p.getAddress(), 21),
            truncate(p.getPhoneNumber(), 16),
            truncate(p.getEmail(), 24),
            truncate(p.getPassportNumber(), 15)
        );
    }

    private static String truncate(String s, int maxLength) {
        return s.length() > maxLength ? s.substring(0, maxLength - 3) + "..." : s;
    }
}
