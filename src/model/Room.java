package model;

import java.util.Objects;

public class Room implements IRoom {
    protected final String roomNumber;
    private final Double price;
    protected static RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = Objects.requireNonNull(price);
        this.enumeration = enumeration;
    }
    @Override
    public final String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public final Double getRoomPrice() {
        return price;
    }

    @Override
    public final RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public final boolean isFree() {
        return price <= 0;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                "\nType: " + enumeration + " room" +
                "\nPrice: $" + price + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Room) {
            Room other = (Room) obj;
            return this.roomNumber.equals(other.roomNumber);
        }else {
            return false;
        }
    }
}
