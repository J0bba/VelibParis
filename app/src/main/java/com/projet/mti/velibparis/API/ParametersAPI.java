package com.projet.mti.velibparis.API;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Thiba on 15/05/2017.
 */

public class ParametersAPI {
    private final ArrayList<String> dataset;
    private final String timezone;
    private final int rows;
    private final String format;
    private final ArrayList<String> facet;

    public ParametersAPI(ArrayList<String> dataset, String timezone, int rows, String format, ArrayList<String> facet)
    {
        this.dataset = dataset;
        this.timezone = timezone;
        this.rows = rows;
        this.format = format;
        this.facet = facet;
    }
}
