package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return super.toString();
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
