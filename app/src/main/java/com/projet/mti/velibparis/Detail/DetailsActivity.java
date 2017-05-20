package com.projet.mti.velibparis.Detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.projet.mti.velibparis.API.RecordsAPI;
import com.projet.mti.velibparis.API.WebServiceReturn;
import com.projet.mti.velibparis.R;
import com.projet.mti.velibparis.StationItem;
import com.projet.mti.velibparis.WebService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailsActivity extends AppCompatActivity {

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
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
                        Date update = null;
                        try {
                            update = df.parse(recordsAPIs.get(i).getFields().getLast_update());
                        }
                        catch (java.text.ParseException e)
                        {}
                        stations.add(new StationItem(recordsAPIs.get(i).getFields().getStatus(),
                                                     recordsAPIs.get(i).getFields().getName(),
                                                     recordsAPIs.get(i).getFields().getBike_stands(),
                                                     recordsAPIs.get(i).getFields().getAvailable_bike_stands(),
                                                     recordsAPIs.get(i).getFields().getAddress(),
                                                     update));
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
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitle(getString(R.string.detail));
        setSupportActionBar(toolbar);
        collectData();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }
}
