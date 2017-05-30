package com.projet.mti.velibparis.Detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.projet.mti.velibparis.StationItemsList;

/**
 * Created by Thiba on 15/05/2017.
 */

public class DetailsAdapter extends FragmentStatePagerAdapter {
    private StationItemsList dataSet;

    public void setData(StationItemsList items)
    {
        dataSet = items;
        notifyDataSetChanged();
    }

    public DetailsAdapter(FragmentManager fm, StationItemsList dataSet)
    {
        super(fm);
        this.dataSet = dataSet;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailsFragment.newInstance(dataSet.get(position));
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }
}
