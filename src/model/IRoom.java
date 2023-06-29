package model;

public interface IRoom {
    public String getRoomNumber();

    public Double getRoomPrice();

    public RoomType getRoomType();

    public boolean isFree();

    @Override
    public int hashCode();

    @Override
    public boolean equals(Object obj);

}
