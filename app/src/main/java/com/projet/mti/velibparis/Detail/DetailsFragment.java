package com.projet.mti.velibparis.Detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projet.mti.velibparis.R;
import com.projet.mti.velibparis.StationItem;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Thiba on 15/05/2017.
 */

public class DetailsFragment extends Fragment implements OnMapReadyCallback {
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
    private TextView address;
    private TextView last_update;
    private StationItem item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.details_fragment, container, false);

        item = (StationItem) getArguments().getSerializable("item");

        textTitle = (TextView) rootView.findViewById(R.id.details_title);
        textTitle.setText(item.getName());

        statusLabel = (TextView)rootView.findViewById(R.id.details_status);
        statusLabel.setText(statusLabel.getText() + " " + item.getStatusString());
        if (item.isOpen())
            statusLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.colorIsOpen));
        else
            statusLabel.setTextColor(ContextCompat.getColor(getContext(), R.color.colorIsClosed));

        bike_stands = (TextView)rootView.findViewById(R.id.bike_stands_details);
        bike_stands.setText(bike_stands.getText() + " " + item.getRooms());

        available_bike_stands = (TextView)rootView.findViewById(R.id.available_bike_stands);
        available_bike_stands.setText(available_bike_stands.getText() + " " + item.getAvailableRooms());

        address = (TextView)rootView.findViewById(R.id.address_details);
        address.setText(address.getText() + " " + item.getAddress());
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:" + item.getLatitude() + ", " + item.getLongitude() + "?q=" + item.getAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        last_update = (TextView)rootView.findViewById(R.id.last_update_details);
        Date date = item.getUpdateDate();
        PrettyTime p = new PrettyTime(new Locale("fr"));
        last_update.setText(last_update.getText() + " " + p.format(date));

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return rootView;
    }

    public void setDetails(StationItem item)
    {
        textTitle.setText(item.getName());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(item.getLatitude(), item.getLongitude())).title("Marker"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(item.getLatitude(), item.getLongitude()), 15.0f));
    }
}
