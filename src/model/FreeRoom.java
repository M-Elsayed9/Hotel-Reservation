package model;

import java.util.Objects;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, 0.0 , enumeration);
    }

    @Override
    public String toString() {
        return "Room Number: " + super.roomNumber +
                "\nType: " + super.enumeration + " room" +
                "\nPrice: $" + 0.0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FreeRoom) {
            FreeRoom other = (FreeRoom) obj;
            return this.roomNumber.equals(other.roomNumber);
        }else {
            return false;
        }
    }
}
