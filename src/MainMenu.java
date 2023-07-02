import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    private static final  SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public static void mainMenu() {
        printMainMenu();
    }

    public static void printMainMenu() {
        List<String> options = new ArrayList<>();
        options.add("1. Find and reserve a room");
        options.add("2. See my reservations");
        options.add("3. Create an Account");
        options.add("4. Admin");
        options.add("5. Exit");
        options.stream().forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        int option = 0;
        boolean validInput = true;

        while(validInput) {
            try{
                System.out.println("Please select a number from the menu options:");
                option = scanner.nextInt();
                if (option < 6 && option > 0) {
                    validInput = false;
                }
            }catch(Exception ex) {
                scanner.next();
                System.out.println("Error: Invalid input! (Enter a number between 1-5)");
            }
        }

        switch (option) {
            case 1 -> findAndReserveRoom();
            case 2 -> seeMyReservation();
            case 3 -> createAccount();
            case 4 -> AdminMenu.adminMenu();
            case 5 -> System.out.println("Exiting");
        }
    }

    private static void seeMyReservation() {
        Scanner scanner = new Scanner(System.in);
        String email = "";

        boolean validInput = true;
        while(validInput) {
            try{
                System.out.println("Enter Email format: name@domain.com");
                email = scanner.next();
                Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
                if(reservations.isEmpty()) {
                    System.out.println("No reservations found\n");
                    printMainMenu();
                    return;
                }else {
                    reservations.forEach(System.out::println);
                    validInput = false;
                }
            }catch (Exception ex) {
                System.out.println("Error: Invalid email");
            }
        }
        printMainMenu();
    }

    private static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        String email = "";
        String firstName = "";
        String lastName = "";

        boolean validInput = true;
        while(validInput) {
            try {
                final String emailRegEx = "^[^@]*@[^@]*\\.com$";
                final Pattern pattern = Pattern.compile(emailRegEx);
                System.out.println("Enter Email format: name@domain.com");
                email = scanner.next();
                if(pattern.matcher(email).matches()) {
                    validInput = false;
                }else {
                    throw new IllegalArgumentException();
                }
            }catch (Exception ex) {
                System.out.println("Error: Invalid email");
            }
        }

        validInput = true;
        while (validInput) {
            try {
                System.out.println("First name: ");
                firstName = scanner.next();
                if(firstName.isEmpty()) {
                    throw new IllegalArgumentException();
                }
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: Invalid input");
            }
        }

        validInput = true;
        while (validInput) {
            try {
                System.out.println("Last name: ");
                lastName = scanner.next();
                if(lastName.isEmpty()) {
                    throw new IllegalArgumentException();
                }
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: Invalid input");
            }
        }

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created successfully!");
        printMainMenu();
    }

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        Date checkIn = null;
        boolean validInput = true;
        while(validInput) {
            try {
                System.out.println("Enter Check-In Date mm/dd/yyyy example 02/01/2020");
                String checkInDate = scanner.nextLine();
                checkIn = dateFormat.parse(checkInDate);
                validInput = false;
            } catch (Exception ex) {
                System.out.println("Error: invalid input");
            }
        }

        Date checkOut = null;
        validInput = true;
        while(validInput) {
            try {
                System.out.println("Enter Check-Out Date mm/dd/yyyy example 02/01/2020");
                String checkInDate = scanner.nextLine();
                checkOut = dateFormat.parse(checkInDate);
                validInput = false;
            } catch (Exception ex) {
                System.out.println("Error: invalid input");
            }
        }

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
        if(availableRooms.isEmpty()) {
            System.out.println("Unfortunately, There are no rooms available within these dates");
            printMainMenu();
            return;
        }

        availableRooms.forEach(System.out::println);

        validInput = true;
        while(validInput) {
            try {
                System.out.println("Would you like to book a room? y/n");
                String input = scanner.next();
                if(input.equalsIgnoreCase("y")) {
                    validInput = false;
                }else if (input.equalsIgnoreCase("n")) {
                    validInput = false;
                    printMainMenu();
                    return;
                }else {
                    System.out.println("Error: Invalid input");
                }
            }catch (Exception ex) {}
        }

        validInput = true;
        while (validInput) {
            try {
                System.out.println("Do you have an account with us? y/n");
                String input = scanner.next();
                if(input.equalsIgnoreCase("y")) {
                    validInput = false;
                }else if (input.equalsIgnoreCase("n")) {
                    validInput = false;
                    System.out.println("Please create an account in the main menu");
                    printMainMenu();
                    return;
                }else {
                    System.out.println("Error: Invalid input");
                }
            } catch (Exception ex) {}
        }

        String email = "";
        validInput = true;
        while (validInput) {
            try {
                System.out.println("Enter Email format: name@domain.com");
                email = scanner.next();
                hotelResource.getCustomer(email);
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: Invalid email");
            }
        }

        String roomNumber = "";
        validInput = true;
        while (validInput) {
            try {
                System.out.println("What room would you like to reserve?");
                roomNumber = scanner.next();
                System.out.println(hotelResource.bookARoom(email, hotelResource.getRoom(roomNumber), checkIn, checkOut));
                mainMenu();
                return;
            }catch (Exception ex) {

            }
        }
    }
}

