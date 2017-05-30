package com.projet.mti.velibparis;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Thiba on 30/05/2017.
 */

public class StationItemsList extends ArrayList<StationItem> implements Parcelable{

    public StationItemsList()
    {
        super();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size());
        for (int i = 0; i < size(); i++)
        {
            StationItem stationItem = get(i);
            stationItem.writeToParcel(dest, flags);
        }
    }

    public static final Parcelable.Creator<StationItemsList> CREATOR = new Parcelable.Creator<StationItemsList>()
    {
        @Override
        public StationItemsList createFromParcel(Parcel source)
        {
            return new StationItemsList(source);
        }
        @Override
        public StationItemsList[] newArray(int size)
        {
            return new StationItemsList[size];
        }
    };

    private StationItemsList(Parcel source)
    {
        this.clear();
        int size = source.readInt();
        for (int i = 0; i < size; i++)
        {
            StationItem item = new StationItem(source);
            add(item);
        }
    }
}
