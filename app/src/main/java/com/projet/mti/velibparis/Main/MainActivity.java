package com.projet.mti.velibparis.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.projet.mti.velibparis.API.RecordsAPI;
import com.projet.mti.velibparis.API.WebServiceReturn;
import com.projet.mti.velibparis.GroupDetails.GroupDetailsActivity;
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

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<StationItem> stations = new ArrayList<>();

    public void collectData()
    {
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
        setContentView(R.layout.activity_main);



        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(stations);
        recyclerView.setAdapter(adapter);
        collectData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);

        MenuItem groupButton = menu.findItem(R.id.member_list);
        groupButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getBaseContext(), GroupDetailsActivity.class);
                startActivity(intent);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        FilterStations(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        FilterStations(newText);
        return true;
    }

    private void FilterStations(String text)
    {
        List<StationItem> temp = new ArrayList<>();
        for (StationItem station : stations)
        {
            if (station.getName().toLowerCase().contains(text.toLowerCase()))
            {
                temp.add(station);
            }
        }
        adapter.setData(temp);
    }
}
