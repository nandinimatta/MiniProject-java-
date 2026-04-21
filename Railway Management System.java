package projects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
class RailwayBookingSystem {
    private final int totalSeats;
    private final String[] seatOccupant;   
    private final List<String> bookingHistory; 
    public RailwayBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
        this.seatOccupant = new String[totalSeats + 1]; 
        this.bookingHistory = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            seatOccupant[i] = null;
        }
    }
    public void viewSeats() {
        System.out.println("\n--- Seat Availability ---");
        for (int i = 1; i <= totalSeats; i++) {
            if (seatOccupant[i] == null) {
                System.out.printf("Seat %2d: Available%n", i);
            } else {
                System.out.printf("Seat %2d: Booked by %s%n", i, seatOccupant[i]);
            }
        }
        System.out.println("-------------------------\n");
    }
    public boolean bookTicket(String passengerName, int seatNumber) {
        if (seatNumber < 1 || seatNumber > totalSeats) {
            System.out.printf("Error: Seat number must be between 1 and %d.%n", totalSeats);
            return false;
        }
        if (seatOccupant[seatNumber] != null) {
            System.out.printf("Seat %d is already booked by %s.%n", seatNumber, seatOccupant[seatNumber]);
            return false;
        }
        seatOccupant[seatNumber] = passengerName;
        bookingHistory.add("BOOKED: " + passengerName + " - Seat " + seatNumber);
        System.out.printf("Ticket booked successfully for %s on seat %d.%n", passengerName, seatNumber);
        return true;
    }
    public boolean cancelTicket(String passengerName, int seatNumber) {
        if (seatNumber < 1 || seatNumber > totalSeats) {
            System.out.printf("Error: Seat number must be between 1 and %d.%n", totalSeats);
            return false;
        }
        if (seatOccupant[seatNumber] == null) {
            System.out.printf("Seat %d is not booked.%n", seatNumber);
            return false;
        }
        if (!seatOccupant[seatNumber].equals(passengerName)) {
            System.out.printf("Seat %d is booked by %s, not by %s. Cancellation failed.%n",
                    seatNumber, seatOccupant[seatNumber], passengerName);
            return false;
        }
        seatOccupant[seatNumber] = null;
        bookingHistory.add("CANCELLED: " + passengerName + " - Seat " + seatNumber);
        System.out.printf("Ticket cancelled for %s on seat %d. Seat is now available.%n", passengerName, seatNumber);
        return true;
    }
    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Railway Booking System =====");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. View Seats");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Enter passenger name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter seat number (1-" + totalSeats + "): ");
                    int seat;
                    try {
                        seat = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid seat number. Please enter an integer.");
                        continue;
                    }
                    bookTicket(name, seat);
                    break;
                case "2":
                    System.out.print("Enter passenger name: ");
                    String cancelName = scanner.nextLine().trim();
                    System.out.print("Enter seat number to cancel (1-" + totalSeats + "): ");
                    int cancelSeat;
                    try {
                        cancelSeat = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid seat number.");
                        continue;
                    }
                    cancelTicket(cancelName, cancelSeat);
                    break;
                case "3":
                    viewSeats();
                    break;
                case "4":
                    System.out.println("Thank you for using the Railway Booking System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }
}
public class RailwayManagementSYS {
	public static void main(String[] args) {
		RailwayBookingSystem system = new RailwayBookingSystem(10);
        system.runMenu();
	}
}
