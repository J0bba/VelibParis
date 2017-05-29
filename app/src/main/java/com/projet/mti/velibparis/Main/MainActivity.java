package com.projet.mti.velibparis.Main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.view.View;
import android.widget.TextView;

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
    private SwipeRefreshLayout swipeRefreshLayout;

    public void collectData()
    {
        stations.clear();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected()))
        {
            recyclerView.setVisibility(View.GONE);
            TextView nointernet = (TextView)findViewById(R.id.no_internet_text);
            nointernet.setVisibility(View.VISIBLE);
            return;
        }
        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            TextView nointernet = (TextView)findViewById(R.id.no_internet_text);
            nointernet.setVisibility(View.INVISIBLE);

        }

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebService webService = retrofit.create(WebService.class);
        Call<WebServiceReturn> webServiceReturnCall = webService.callService("stations-velib-disponibilites-en-temps-reel", 1000);
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
                                                     update,
                                                     recordsAPIs.get(i).getFields().getPosition().get(0),
                                                     recordsAPIs.get(i).getFields().getPosition().get(1)));
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

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(stations);
        recyclerView.setAdapter(adapter);

        collectData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                                                    @Override
                                                    public void onRefresh()
                                                    {
                                                        collectData();
                                                        swipeRefreshLayout.setRefreshing(false);
                                                    }
                                                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        FilterStations(query);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.member_list :
                Intent intent = new Intent(getBaseContext(), GroupDetailsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
