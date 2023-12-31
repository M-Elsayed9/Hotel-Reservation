package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;


public class    AdminResource {
    private static AdminResource adminResource;
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private AdminResource(){}

    public static AdminResource getInstance() {
        if(adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public void addRoom(List<IRoom> rooms) {
            for (IRoom room : rooms) {
                reservationService.addRoom(room);
            }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomer();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
