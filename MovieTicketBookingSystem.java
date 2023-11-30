package movieticketbookingsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MovieTicketBookingSystem {
    private Map<String, Integer> availableSeats;
    private Map<Integer, Integer> rowSeatPrices;
    private int totalAmount;

    public MovieTicketBookingSystem() {
        availableSeats = new HashMap<>();
        rowSeatPrices = new HashMap<>();
        rowSeatPrices.put(1, 40);
        rowSeatPrices.put(2, 30);
        rowSeatPrices.put(3, 20);
        totalAmount = 0;

        for (int row = 1; row <= 3; row++) {
            for (int seatNumber = 1; seatNumber <= 4; seatNumber++) {
                String seat = String.format("%c%d", (char) ('A' + row - 1), seatNumber);
                availableSeats.put(seat, 1);
            }
        }
    }

    public void displayAvailableSeats() {
        System.out.println("Available Seats:");
        for (String seat : availableSeats.keySet()) {
            if (availableSeats.get(seat) == 1) {
                int row = seat.charAt(0) - 'A' + 1;
                int price = rowSeatPrices.get(row);
                System.out.print(seat + " ($" + price + ") ");
            }
        }
        System.out.println();
    }

    public void bookTicket() {
        displayAvailableSeats();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter seat number to book: ");
        String seatToBook = scanner.next();

        if (availableSeats.containsKey(seatToBook) && availableSeats.get(seatToBook) == 1) {
            int row = seatToBook.charAt(0) - 'A' + 1;
            int price = rowSeatPrices.get(row);
            availableSeats.put(seatToBook, 0);
            totalAmount += price;
            System.out.println("Payment amount for seat " + seatToBook + " is $" + price);

            System.out.println("\nOptions:");
            System.out.println("1. Book more seats");
            System.out.println("2. Checkout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookTicket();
                    break;
                case 2:
                    checkout();
                    break;
                default:
                    System.out.println("Invalid choice. Exiting...");
                    System.exit(0);
            }
        } else {
            System.out.println("Seat " + seatToBook + " is not available. Please choose another seat.");
        }
    }

    private void checkout() {
        System.out.println("Total amount to pay: $" + totalAmount);
        processPayment(totalAmount);
    }

    private void processPayment(int totalAmount) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nPayment Options:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. PayPal");
        System.out.println("4. Stripe");
        System.out.println("5. Cash");
        System.out.print("Select payment method: ");
        int paymentMethod = scanner.nextInt();

        switch (paymentMethod) {
            case 1:
            case 2:
                processCardPayment(paymentMethod, totalAmount);
                break;
            case 3:
                processPayPalPayment(totalAmount);
                break;
            case 4:
                processStripePayment(totalAmount);
                break;
            case 5:
                System.out.println("Payment successful with Cash.");
                displaySuccessMessage(totalAmount);
                break;
            default:
                System.out.println("Invalid payment method. Payment failed.");
        }
    }

    private void processCardPayment(int paymentMethod, int totalAmount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter card number: ");
        String cardNumber = scanner.next();
        System.out.print("Enter expiry date (MM/YYYY): ");
        String expiryDate = scanner.next();
        System.out.print("Enter CVV: ");
        int cvv = scanner.nextInt();

        if (validateCardDetails(cardNumber, expiryDate, cvv)) {
            System.out.println("Payment successful with " + (paymentMethod == 1 ? "Credit Card." : "Debit Card."));
            displaySuccessMessage(totalAmount);
        } else {
            System.out.println("Invalid card details. Payment failed.");
        }
    }

    private void processPayPalPayment(int totalAmount) {
        System.out.println("Initiating PayPal payment...");
        System.out.println("Payment successful with PayPal.");
        displaySuccessMessage(totalAmount);
    }

    private void processStripePayment(int totalAmount) {
        System.out.println("Initiating Stripe payment...");
        System.out.println("Payment successful with Stripe.");
        displaySuccessMessage(totalAmount);
    }

    private boolean validateCardDetails(String cardNumber, String expiryDate, int cvv) {
        return cardNumber.length() == 16 && expiryDate.matches("\\d{2}/\\d{4}") && cvv >= 100 && cvv <= 999;
    }

    private void displaySuccessMessage(int totalAmount) {
        System.out.println("Payment successful! Total amount paid: $" + totalAmount + ". Thank you for booking with us.");
        System.out.println("1. Continue Booking");
        System.out.println("2. Back to Main Menu");
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice == 2) {
            totalAmount = 0; // Reset total amount for the next booking
            return;
        } else {
            bookTicket();
        }
    }

    public static void main(String[] args) {
        MovieTicketBookingSystem bookingSystem = new MovieTicketBookingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Display available seats\n2. Book a ticket\n3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookingSystem.displayAvailableSeats();
                    break;
                case 2:
                    bookingSystem.bookTicket();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
