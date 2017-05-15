package com.projet.mti.velibparis;

/**
 * Created by Thiba on 15/05/2017.
 */

public class StationItem {
    private boolean open;
    private String name;

    public StationItem(String status, String name)
    {
        if (status.equals("CLOSED"))
            this.open = false;
        else
            this.open = true;
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public String getName() {
        return name;
    }
}
