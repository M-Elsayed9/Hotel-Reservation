package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = Objects.requireNonNull(customer);
        this.room = Objects.requireNonNull(room);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }



    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return customer.getFullName() +
                "\n" + room +
                "Check In Date: " + checkInDate + "\n" +
                "Check Out Date: " + checkOutDate + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Reservation) {
            Reservation other = (Reservation) obj;
            return this.customer.equals(other.customer) &&
                    this.room.equals(other.room) &&
                    this.checkOutDate.equals(other.checkOutDate) &&
                    this.checkInDate.equals(other.checkInDate);
        }else {
            return false;
        }
    }
}
