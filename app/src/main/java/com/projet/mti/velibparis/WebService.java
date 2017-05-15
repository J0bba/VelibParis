package com.projet.mti.velibparis;

import com.projet.mti.velibparis.API.WebServiceReturn;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Thiba on 15/05/2017.
 */

public interface WebService {
    public static final String ENDPOINT = "https://opendata.paris.fr";

    @GET("/api/records/1.0/search/")
    Call<WebServiceReturn> callService(@Query("dataset") String dataset, @Query("rows") Integer rows);
}
