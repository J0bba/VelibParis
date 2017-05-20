package com.projet.mti.velibparis.Detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projet.mti.velibparis.R;
import com.projet.mti.velibparis.StationItem;

import org.w3c.dom.Text;

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
    private TextView statusLabel;
    private TextView bike_stands;
    private TextView available_bike_stands;
    private TextView addres;
    private TextView last_update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.details_fragment, container, false);

        StationItem item = (StationItem) getArguments().getSerializable("item");

        textTitle = (TextView) rootView.findViewById(R.id.details_title);
        textTitle.setText(item.getName());

        statusLabel = (TextView)rootView.findViewById(R.id.details_status);
        statusLabel.setText(statusLabel.getText() + " " + item.getStatusString());

        bike_stands = (TextView)rootView.findViewById(R.id.bike_stands_details);
        bike_stands.setText(bike_stands.getText() + " " + item.getRooms());

        available_bike_stands = (TextView)rootView.findViewById(R.id.available_bike_stands);
        available_bike_stands.setText(available_bike_stands.getText() + " " + item.getAvailableRooms());

        addres = (TextView)rootView.findViewById(R.id.address_details);
        addres.setText(addres.getText() + " " + item.getAddress());

        last_update = (TextView)rootView.findViewById(R.id.last_update_details);
        last_update.setText(last_update.getText() + " " + item.getUpdateDate().toString());


        return rootView;
    }

    public void setDetails(StationItem item)
    {
        textTitle.setText(item.getName());
    }
}