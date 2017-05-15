package com.projet.mti.velibparis.API;

import java.util.ArrayList;

/**
 * Created by Thiba on 15/05/2017.
 */

public class FacetGroupsAPI {
    private final String name;
    private final ArrayList<FacetAPI> facets;

    public FacetGroupsAPI(String name, ArrayList<FacetAPI> facets)
    {
        this.name = name;
        this.facets = facets;
    }
}
