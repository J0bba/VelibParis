package com.projet.mti.velibparis.Main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projet.mti.velibparis.Detail.DetailsActivity;
import com.projet.mti.velibparis.R;
import com.projet.mti.velibparis.StationItemsList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private StationItemsList mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView imageView;
        public ViewHolder(View v,final StationItemsList items) {
            super(v);

            mTextView = (TextView) v.findViewById(R.id.list_text_item);
            imageView = (ImageView)v.findViewById(R.id.list_status_image);
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("Station", mTextView.getText());
                    intent.putExtra("StationItemsList", (Parcelable) items);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }
    public ListAdapter(StationItemsList data) {
        mDataset = data;
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_line, parent, false);
        return new ViewHolder(v, mDataset);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position).getName());
        if (mDataset.get(position).isOpen())
        {
            holder.imageView.setImageResource(R.drawable.ic_check_black_24dp);
            ColorFilter colorFilter = new LightingColorFilter(Color.GREEN, Color.GREEN);
            holder.imageView.setColorFilter(colorFilter);
        }
        else
        {
            holder.imageView.setImageResource(R.drawable.ic_error_black_24dp);
            ColorFilter colorFilter = new LightingColorFilter(Color.RED, Color.RED);
            holder.imageView.setColorFilter(colorFilter);
        }
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setData(StationItemsList items)
    {
        mDataset = items;
        notifyDataSetChanged();
    }

}
