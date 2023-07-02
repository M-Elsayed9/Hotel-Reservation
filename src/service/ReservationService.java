package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final Set<Reservation> reservations = new HashSet<>();
    private static final Map<String, IRoom> rooms = new HashMap<>();
    private static ReservationService reservationService;

    private ReservationService(){}

    public static ReservationService getInstance() {
        if(reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = rooms.values();

        for(Reservation reservation : reservations) {
            if(checkInDate.equals(reservation.getCheckInDate())
                    || checkInDate.before(reservation.getCheckOutDate())
                    && checkInDate.after(reservation.getCheckInDate())) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        return reservations.stream().filter(reservation -> reservation.getCustomer().equals(customer)).toList();
    }

    public void printAllReservation() {
        if(reservations.isEmpty()) {
            System.out.println("No Reservations found!");
        }else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
}
