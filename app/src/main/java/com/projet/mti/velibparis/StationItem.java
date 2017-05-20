package com.projet.mti.velibparis;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Thiba on 15/05/2017.
 */

public class StationItem  implements Serializable{
    private boolean open;
    private String name;
    private Integer rooms;
    private Integer availableRooms;
    private String address;
    private Date updateDate;
    private String statusString;

    public StationItem(String status, String name, Integer rooms, Integer availableRooms, String address, Date updateDate)
    {
        this.statusString = status;
        this.open = !status.equals("CLOSED");
        this.name = name;
        this.rooms = rooms;
        this.availableRooms = availableRooms;
        this.address = address;
        this.updateDate = updateDate;
    }

    public boolean isOpen() {
        return open;
    }

    public String getName() {
        return name;
    }

    public Integer getAvailableRooms()
    {
        return availableRooms;
    }

    public Integer getRooms()
    {
        return rooms;
    }

    public String getAddress()
    {
        return address;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public String getStatusString() {
        return statusString;
    }
}
