package com.projet.mti.velibparis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Thiba on 15/05/2017.
 */

public class DetailsFragment extends Fragment {
    public static DetailsFragment newInstance(StationItem item)
    {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        f.setArguments(args);
        return f;
    }
    private TextView textTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.details_fragment, container, false);

        StationItem item = (StationItem) getArguments().getSerializable("item");
        Log.e("FATAL", item.getName());

        textTitle = (TextView) rootView.findViewById(R.id.details_title);
        textTitle.setText(item.getName());

        return rootView;
    }

    public void setDetails(StationItem item)
    {
        textTitle.setText(item.getName());
    }
}
