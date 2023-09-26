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
        List<String> options = List.of("1. Find and reserve a room",
                "2. See my reservations",
                "3. Create an Account",
                "4. Admin",
                "5. Exit");
        options.forEach(System.out::println);

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
                scanner.nextLine();
                System.out.println("Error: Invalid input! (Enter a number between 1-5)");
            }
        }

        switch (option) {
            case 1 -> findAndReserveRoom();
            case 2 -> seeMyReservation();
            case 3 -> createAccount();
            case 4 -> AdminMenu.adminMenu();
            case 5 -> System.out.println("Exiting...");
        }
    }

    private static void seeMyReservation() {
        Scanner scanner = new Scanner(System.in);
        String email;

        boolean validInput = true;
        while(validInput) {
            try{
                System.out.println("Enter Email format: name@domain.com");
                email = scanner.nextLine();
                Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
                if(reservations.isEmpty()) {
                    System.out.println("No reservations found\n");
                    printMainMenu();
                    validInput = false;
                }else {
                    reservations.forEach(System.out::println);
                    validInput = false;
                    printMainMenu();
                }
            }catch (Exception ex) {
                System.out.println("Error: Invalid email");
            }
        }
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
                email = scanner.nextLine();

                if (hotelResource.getAllCustomersEmails().contains(email)) {
                    System.out.println("Error: Duplicate email");
                    continue;
                }
                if(pattern.matcher(email).matches()) {
                    validInput = false;
                } else {
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
                firstName = scanner.nextLine();
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
                lastName = scanner.nextLine();
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

    private static void findAlternativeRooms(Date checkIn, Date checkOut) {
        System.out.println("There are no rooms available for the given dates");
        System.out.println("Finding alternative rooms available the following week...\n");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkIn);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date newCheckIn = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(checkOut);
        calendar1.add(Calendar.DAY_OF_MONTH, 7);
        Date newCheckOut = calendar1.getTime();

        Collection<IRoom> availableRooms = hotelResource.findARoom(newCheckIn, newCheckOut);
        if(availableRooms.isEmpty()) {
            System.out.println("There are no rooms available the following week also");
            printMainMenu();
            return;
        }
        System.out.println("Rooms available on alternative dates:" +
                "\nCheck-In Date:" + newCheckIn +
                "\nCheck-Out Date:" + newCheckOut);
        availableRooms.forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        boolean validInput = true;
        while(validInput) {
            try {
                System.out.println("Would you like to book a room? y/n");
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("y")) {
                    validInput = false;
                }else if (input.equalsIgnoreCase("n")) {
                    validInput = false;
                    printMainMenu();
                    return;
                }
            }catch (Exception ex) {
                System.out.println("Error: Invalid input");
            }
        }

        validInput = true;
        while (validInput) {
            try {
                System.out.println("Do you have an account with us? y/n");
                String input = scanner.nextLine();
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
                email = scanner.nextLine();
                hotelResource.getCustomer(email);
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: Invalid email");
            }
        }

        String roomNumber;
        validInput = true;
        while (validInput) {
            try {
                System.out.println("What room would you like to reserve?");
                roomNumber = scanner.nextLine();
                Reservation reservation = hotelResource.bookARoom(email, hotelResource.getRoom(roomNumber), newCheckIn, newCheckOut);
                System.out.println(reservation);
                mainMenu();
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: invalid input");
            }
        }
    }
    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        Date checkIn = null;
        Date checkOut = null;
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


        validInput = true;
        while(validInput) {
            try {
                System.out.println("Enter Check-Out Date mm/dd/yyyy example 02/01/2020");
                String checkOutDate = scanner.nextLine();
                checkOut = dateFormat.parse(checkOutDate);
                if(checkOut.before(checkIn)) {
                    System.out.println("Checkout date must be after check in date");
                    throw new IllegalArgumentException();
                }
                validInput = false;
            } catch (Exception ex) {
                System.out.println("Error: invalid input");
            }
        }

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
        if(availableRooms.isEmpty()) {
            findAlternativeRooms(checkIn, checkOut);
            printMainMenu();
            return;
        }else {
            availableRooms.forEach(System.out::println);
        }

        validInput = true;
        while(validInput) {
            try {
                System.out.println("Would you like to book a room? y/n");
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("y")) {
                    validInput = false;
                }else if (input.equalsIgnoreCase("n")) {
                    validInput = false;
                    printMainMenu();
                    return;
                }else {
                    System.out.println("Error: Invalid input");
                }
            }catch (Exception ex) {
                System.out.println("Error: invalid input");
            }
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
                email = scanner.nextLine();
                hotelResource.getCustomer(email);
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: Invalid email");
            }
        }

        String roomNumber;
        validInput = true;
        while (validInput) {
            try {
                System.out.println("What room would you like to reserve?");
                roomNumber = String.valueOf(scanner.nextInt());
                Reservation reservation = hotelResource.bookARoom(email, hotelResource.getRoom(roomNumber), checkIn, checkOut);//bug
                System.out.println(reservation);
                printMainMenu();
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: invalid input");
            }
        }
    }
}

