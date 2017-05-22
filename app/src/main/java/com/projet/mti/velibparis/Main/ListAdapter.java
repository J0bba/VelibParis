package com.projet.mti.velibparis.Main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projet.mti.velibparis.Detail.DetailsActivity;
import com.projet.mti.velibparis.R;
import com.projet.mti.velibparis.StationItem;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<StationItem> mDataset;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView imageView;
        public ViewHolder(View v) {
            super(v);

            mTextView = (TextView) v.findViewById(R.id.list_text_item);
            imageView = (ImageView)v.findViewById(R.id.list_status_image);
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("Station", mTextView.getText());
                    view.getContext().startActivity(intent);
                }
            });
        }

    }
    public ListAdapter(List<StationItem> data) {
        mDataset = data;
    }
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_line, parent, false);
        return new ViewHolder(v);
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

    public void setData(List<StationItem> items)
    {
        mDataset = items;
        notifyDataSetChanged();
    }

}
