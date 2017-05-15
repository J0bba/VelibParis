package com.projet.mti.velibparis.API;

import java.util.ArrayList;

/**
 * Created by Thiba on 15/05/2017.
 */

public class GeometryAPI {
    private final String type;
    private final ArrayList<Float> coordinates;

    public GeometryAPI(String type, ArrayList<Float> coordinates)
    {
        this.type = type;
        this.coordinates = coordinates;
    }
}
