import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    public static void adminMenu() {
        printAdminMenu();
    }

    private static void printAdminMenu() {
        List<String> options = List.of(
                "1. See all Customers",
                "2. See all Rooms",
                "3. See all Reservations",
                "4. Add a Room",
                "5. Back to Main Menu"
        );
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
            case 1 -> seeAllCustomers();
            case 2 -> seeAllRooms();
            case 3 -> seeAllReservations();
            case 4 -> addRoom();
            case 5 -> {
                System.out.println("Exiting Admin Menu");
                MainMenu.printMainMenu();
            }
        }
    }

    private static void seeAllCustomers() {
        adminResource.getAllCustomers().forEach(System.out::println);
        printAdminMenu();
    }

    private static void seeAllRooms() {
        adminResource.getAllRooms().forEach(System.out::println);
        printAdminMenu();
    }

    private static void seeAllReservations() {
        adminResource.displayAllReservations();
        printAdminMenu();
    }

    private static void addRoom() {
        Scanner scanner = new Scanner(System.in);
        List<IRoom> rooms = new ArrayList<>();
        String roomNumber = "";
        Double price = 0.0;
        RoomType roomType;

        boolean validInput = true;
        while(validInput) {
            try {
                System.out.println("Enter room number: ");
                roomNumber = String.valueOf(scanner.nextInt());
                if(!roomNumber.isEmpty()) {
                    validInput = false;
                }
            }catch (Exception ex) {
                scanner.nextLine();
                System.out.println("Error: invalid input");
            }
        }

        validInput = true;
        while (validInput) {
            try {
                System.out.println("Enter price per night:");
                price = Double.parseDouble(scanner.next()) ;
                validInput = false;
            }catch (Exception ex) {
                System.out.println("Error: Invalid Input");
            }
        }

        validInput = true;
        while (validInput) {
            try {
                System.out.println("Room type: \nEnter 1 for a single bed\nEnter 2 for a double bed");
                int type = scanner.nextInt();
                if(type == 1) {
                    roomType = RoomType.SINGLE;
                } else if (type == 2) {
                    roomType = RoomType.DOUBLE;
                }else {
                    throw new IllegalArgumentException();
                }
                final IRoom room = new Room(roomNumber, price, roomType);
                if(adminResource.getAllRooms().contains(room)) {
                    System.out.println("Error: Cant add duplicate rooms");
                    printAdminMenu();
                    return;
                }else {
                    rooms.add(room);
                    validInput = false;
                }
            }catch (Exception ex) {
                System.out.println("Error: invalid input (Enter 1 or 2)");
                scanner.nextLine();
            }
        }

        adminResource.addRoom(rooms);

        validInput = true;
        while (validInput) {
            try {
                System.out.println("Would you like to add another room? y/n");
                String input = scanner.nextLine();
                if(input.equals("y")) {
                    addRoom();
                    return;
                }else if(input.equals("n")){
                    printAdminMenu();
                    validInput = false;
                }else {
                    throw new IllegalArgumentException();
                }
            }catch (Exception ex) {
                System.out.println("Error: Invalid input");
            }
        }
    }
}
