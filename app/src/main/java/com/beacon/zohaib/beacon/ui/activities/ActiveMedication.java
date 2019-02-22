package com.beacon.zohaib.beacon.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.adapters.ActiveMedAdapter;
import com.beacon.zohaib.beacon.clickListners.DeleteClickListner;
import com.beacon.zohaib.beacon.db.MeadicationDb;
import com.beacon.zohaib.beacon.db.MedicationTable;

import java.util.List;

public class ActiveMedication extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActiveMedAdapter mAdapter;
    private MedicationTable mMedicationTable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_medication);

//        SharedPreferences pref = getSharedPreferences("MyPref", 0);
//        String phone=pref.getString("phone","");
//        String username=pref.getString("username","");
//        sendSmsMsgFnc(phone,"It's time for "+username+" to take their medicine. Please make sure they take it on time.",getApplicationContext());



    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        recyclerView = (RecyclerView) findViewById(R.id.activeMedicationRV);
        new TimeQuereysAsyncTask().execute();
        SharedPreferences mPref=getSharedPreferences("count",0);
        int count=mPref.getInt("count",0);

        SharedPreferences.Editor edit=mPref.edit();
        edit.clear();
        edit.commit();
        if(count==1)
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Medication Time");
            builder1.setMessage("Take your medicine at 9pm");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else if(count==2)
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Medication Time");
            builder1.setMessage("Take your medicine at 9am and 9pm");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else if(count==3)
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Medication Time");
            builder1.setMessage("Take your medicine at 9am, 2pm and 9pm");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }


//        mAdapter.notifyDataSetChanged();
    }
    public void addMedication(View view)
    {
        Intent intent=new Intent(ActiveMedication.this,Medication.class);
        startActivity(intent);
    }



    private class TimeQuereysAsyncTask extends AsyncTask<Void, Void, List<MedicationTable>> {

        @Override
        protected List<MedicationTable> doInBackground(Void... voids) {

            List<MedicationTable> list1 = MeadicationDb.newInstance(ActiveMedication.this).getMedicationDao().getAllMeds();



            if(list1!=null) {
                Log.d("list2", "Size " + list1.size());

                return list1;

            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<MedicationTable> medicationTables) {
            super.onPostExecute(medicationTables);

            if(medicationTables!=null)
            {
                mAdapter = new ActiveMedAdapter(medicationTables, new DeleteClickListner() {
                    @Override
                    public void deleteMeds(int position) {

                        mMedicationTable=medicationTables.get(position);
                        new DeleteAsyncTask().execute();

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);

            }

        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            MeadicationDb.newInstance
                    (ActiveMedication.this).getMedicationTimeJoin().deleteid(mMedicationTable.getId());

            MeadicationDb.newInstance
                    (ActiveMedication.this).getMedicationDao().deleteMedFromId(mMedicationTable.getId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAdapter.notifyDataSetChanged();

        }
    }



    void sendSmsMsgFnc(String mblNumVar, String smsMsgVar,Context context)
    {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                SmsManager smsMgrVar = SmsManager.getDefault();
                smsMgrVar.sendTextMessage(mblNumVar, null, smsMsgVar, null, null);

            }
            catch (Exception ErrVar)
            {
                ErrVar.printStackTrace();
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                ActivityCompat.requestPermissions((Activity) context,new String[]{android.Manifest.permission.SEND_SMS}, 10);
            }
        }

    }



}
