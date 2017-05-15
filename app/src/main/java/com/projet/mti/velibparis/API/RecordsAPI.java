package com.projet.mti.velibparis.API;

import java.util.ArrayList;

/**
 * Created by Thiba on 15/05/2017.
 */

public class RecordsAPI {
    private final String datasetid;
    private final String recordid;
    private final FieldsAPI fields;
    private final GeometryAPI geometry;

    public RecordsAPI(String datasetid, String recordid, FieldsAPI fields, GeometryAPI geometry)
    {
        this.datasetid = datasetid;
        this.recordid = recordid;
        this.fields = fields;
        this.geometry = geometry;
    }

    public FieldsAPI getFields() {
        return fields;
    }
}
