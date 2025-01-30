package com.nilesh.kafkastreams.main;

public class Manager
{
  public static void main(String[] args)
  {
    RoomManagement roomManagement = new RoomManagement();
    int roomNumber = 420;
    String roomType = "Single";
    System.out.println(roomManagement.addRoom(roomNumber, roomType));
  }
}

class RoomManagement {

  public String addRoom(int roomNumber, String roomType)
  {
    return "Room Successfully booked";
  }
}
