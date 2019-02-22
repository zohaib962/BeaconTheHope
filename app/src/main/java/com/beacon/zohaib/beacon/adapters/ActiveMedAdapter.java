package com.beacon.zohaib.beacon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.clickListners.DeleteClickListner;
import com.beacon.zohaib.beacon.db.MedicationTable;
import com.beacon.zohaib.beacon.ui.activities.ActiveMedication;

import java.util.List;

/**
 * Created by Zohaib on 3/26/2018.
 */

public class ActiveMedAdapter extends RecyclerView.Adapter<ActiveMedAdapter.MyViewHolder> {

    List<MedicationTable> mlist;

    DeleteClickListner mClickListener;

    public ActiveMedAdapter(List<MedicationTable> list,DeleteClickListner mClickListener) {
     mlist=list;
     this.mClickListener=mClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_active_med, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        MedicationTable mMedicationTable=mlist.get(position);
        holder.title.setText(mMedicationTable.getMedname());
        holder.dosage.setText(String.valueOf(mMedicationTable.getDosage()));

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, dosage;

        public Button mBtn;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.activeMedNameTV);
            dosage=(TextView)view.findViewById(R.id.activeMedDosageTV);

            mBtn=(Button)view.findViewById(R.id.delMedBtn);

            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.deleteMeds(getAdapterPosition());
                }
            });

        }
    }

}
