package com.projet.mti.velibparis.API;

import java.util.ArrayList;

/**
 * Created by Thiba on 15/05/2017.
 */

public class WebServiceReturn {
    private final int nhits;
    private final ParametersAPI parameters;
    private final ArrayList<RecordsAPI> records;
    //private final ArrayList<FacetGroupsAPI> facet_groups;

    public WebServiceReturn(int nhits, ParametersAPI parameters, ArrayList<RecordsAPI> records/*, ArrayList<FacetGroupsAPI> facet_groups*/)
    {
        this.nhits = nhits;
        this.parameters = parameters;
        this.records = records;
        //this.facet_groups = facet_groups;
    }

    public ArrayList<RecordsAPI> getRecords() {
        return records;
    }
}
