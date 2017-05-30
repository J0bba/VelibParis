package com.projet.mti.velibparis.Detail;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.projet.mti.velibparis.GroupDetails.GroupDetailsActivity;
import com.projet.mti.velibparis.R;
import com.projet.mti.velibparis.StationItemsList;


public class DetailsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private DetailsAdapter adapter;
    private StationItemsList stations = new StationItemsList();

    private int getIndexByName(String name)
    {

        for (int i = 0; i < stations.size(); i++)
        {
            if (stations.get(i).getName().equals(name))
                return i;
        }
        return 0;
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
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Intent intent = getIntent();
        if (intent.hasExtra("StationItemsList"))
        {
            stations = intent.getParcelableExtra("StationItemsList");
            adapter.setData(stations);
            viewPager.setCurrentItem(getIndexByName(intent.getStringExtra("Station")), false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.share_detail:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String text = stations.get(viewPager.getCurrentItem()).toString();
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
            case R.id.member_list:
                Intent intent = new Intent(getBaseContext(), GroupDetailsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
