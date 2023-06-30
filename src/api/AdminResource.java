package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;


public class AdminResource {
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

    public Customer getCustomer(String email) throws Exception {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        try {
            for (IRoom room : rooms) {
                reservationService.addRoom(room);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
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
