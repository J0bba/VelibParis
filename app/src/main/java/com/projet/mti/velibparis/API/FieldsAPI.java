package com.projet.mti.velibparis.API;

import java.util.ArrayList;

/**
 * Created by Thiba on 15/05/2017.
 */

public class FieldsAPI {
    private final String status;
    private final String contract_name;
    private final String name;
    private final String bonus;
    private final int bike_stands;
    private final int number;
    private final String last_update;
    private final int available_bike_stands;
    private final String banking;
    private final int available_bikes;
    private final String address;
    private final ArrayList<Float> position;

    public FieldsAPI(String status, String contract_name, String name, String bonus, int bike_stands, int number,
                     String last_update, int available_bike_stands, String banking, int available_bikes, String address, ArrayList<Float> position)
    {
        this.status = status;
        this.contract_name = contract_name;
        this.name = name;
        this.bonus = bonus;
        this.bike_stands = bike_stands;
        this.number = number;
        this.last_update = last_update;
        this.available_bike_stands = available_bike_stands;
        this.banking = banking;
        this.available_bikes = available_bikes;
        this.address = address;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getLast_update() { return last_update; }

    public int getBike_stands(){ return bike_stands; }

    public int getAvailable_bike_stands() { return available_bike_stands; }

    public String getAddress() {return address;}
}
