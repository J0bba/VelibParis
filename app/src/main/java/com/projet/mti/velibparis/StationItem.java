package com.projet.mti.velibparis;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Thiba on 15/05/2017.
 */

public class StationItem  implements Parcelable, Serializable{
    private boolean open;
    private String name;
    private Integer rooms;
    private Integer availableRooms;
    private String address;
    private Date updateDate;
    private String statusString;
    private float latitude;
    private float longitude;

    public StationItem(String status, String name, Integer rooms, Integer availableRooms, String address, Date updateDate, float latitude, float longitude)
    {
        this.statusString = status;
        this.open = !status.equals("CLOSED");
        this.name = name.split("-")[1];
        this.rooms = rooms;
        this.availableRooms = availableRooms;
        this.address = address;
        this.updateDate = updateDate;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        String res = "Station de velib : " + name + "\n";
        res += "Adresse : " + address + "\n";
        res += "Nombre de places disponibles : " + availableRooms;
        return res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(statusString);
        dest.writeString(name);
        dest.writeInt(rooms);
        dest.writeInt(availableRooms);
        dest.writeString(address);
        dest.writeSerializable(updateDate);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }

    public static final Parcelable.Creator<StationItem> CREATOR = new Parcelable.Creator<StationItem>()
    {
        @Override
        public StationItem createFromParcel(Parcel source)
        {
            return new StationItem(source);
        }
        @Override
        public StationItem[] newArray(int size)
        {
            return new StationItem[size];
        }
    };

    public StationItem(Parcel source)
    {
        this.statusString = source.readString();
        this.open = !statusString.equals("CLOSED");
        this.name = source.readString();
        this.rooms = source.readInt();
        this.availableRooms = source.readInt();
        this.address = source.readString();
        this.updateDate =  (java.util.Date) source.readSerializable();
        this.latitude = source.readFloat();
        this.longitude = source.readFloat();
    }
}
