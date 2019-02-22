package com.beacon.zohaib.beacon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.db.MedicationTable;

import java.util.List;

/**
 * Created by Zohaib on 3/26/2018.
 */

public class CurrentMedAdapter extends RecyclerView.Adapter<CurrentMedAdapter.MyViewHolder>{

    List<MedicationTable> list1;

    public CurrentMedAdapter(List<MedicationTable> mlist)
    {
        list1=mlist;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_curr_med, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MedicationTable mMedicationTable=list1.get(position);
        holder.title.setText(mMedicationTable.getMedname());
        holder.dosage.setText(String.valueOf(mMedicationTable.getDosage()));
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, dosage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.currMedNameTV);
            dosage = (TextView) view.findViewById(R.id.currMedDosageTV);
        }
    }

}
