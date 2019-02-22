package com.beacon.zohaib.beacon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.datamodels.TrackingModel;
import com.beacon.zohaib.beacon.db.MedicationTable;

import java.util.List;

/**
 * Created by Zohaib on 4/19/2018.
 */

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.MyViewHolder>{
    List<TrackingModel> list1;

    public TrackingAdapter(List<TrackingModel> list1)
    {
        this.list1=list1;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tracking, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TrackingModel mTrackingModel=list1.get(position);
        holder.title.setText(mTrackingModel.getHeartrate());
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.heartRateTV);
        }
    }

}
