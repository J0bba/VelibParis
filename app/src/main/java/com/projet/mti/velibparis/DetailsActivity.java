package com.projet.mti.velibparis;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.projet.mti.velibparis.API.RecordsAPI;
import com.projet.mti.velibparis.API.WebServiceReturn;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thiba on 15/05/2017.
 */

public class DetailsActivity extends FragmentActivity{

    private ViewPager viewPager;
    private DetailsAdapter adapter;
    private List<StationItem> stations = new ArrayList<>();

    private int getIndexByName(String name)
    {
        for (int i = 0; i < stations.size(); i++)
        {
            if (stations.get(i).getName().equals(name))
                return i;
        }
        return 0;
    }

    public void collectData()
    {
        stations = new ArrayList<>();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebService webService = retrofit.create(WebService.class);
        Call<WebServiceReturn> webServiceReturnCall = webService.callService("stations-velib-disponibilites-en-temps-reel", 100);
        webServiceReturnCall.enqueue(new Callback<WebServiceReturn>() {
            @Override
            public void onResponse(Call<WebServiceReturn> call, Response<WebServiceReturn> response) {
                if(response.isSuccessful()) {
                    WebServiceReturn returnService = response.body();
                    ArrayList<RecordsAPI> recordsAPIs = returnService.getRecords();
                    for(int i = 0; i < recordsAPIs.size(); i++)
                    {
                        stations.add(new StationItem(recordsAPIs.get(i).getFields().getStatus(),
                                recordsAPIs.get(i).getFields().getName()));
                    }
                    adapter.setData(stations);
                    viewPager.setCurrentItem(getIndexByName(getIntent().getStringExtra("Station")));
                } else {
                    //Error
                }
            }
            @Override
            public void onFailure(Call<WebServiceReturn> call, Throwable t) {
                Log.e("APITEST", t.getMessage());
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        // Instantiate a ViewPager and a PagerAdapter.
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new DetailsAdapter(getSupportFragmentManager(), stations);
        viewPager.setAdapter(adapter);
        collectData();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
