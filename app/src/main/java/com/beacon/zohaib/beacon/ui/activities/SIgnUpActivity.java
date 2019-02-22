package com.beacon.zohaib.beacon.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.datamodels.UserDataModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zohaib on 3/27/2018.
 */

public class SIgnUpActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBeaconReference;
    private EditText userFNameET;
    private EditText userLNameET;
    private EditText phoneEt;
    private EditText phonecodeET;
    private RadioButton male;
    private RadioButton female;
    private EditText userNameET;
    private EditText userPassET;
    private EditText cnfrmUserPassET;

    private ChildEventListener mChaldEventListener;

    List<UserDataModel> mList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userFNameET=(EditText)findViewById(R.id.firstNameET);
        userLNameET=(EditText)findViewById(R.id.lastNameET);
        phoneEt=(EditText)findViewById(R.id.phoneET);
        male=(RadioButton)findViewById(R.id.maleRb);
        female=(RadioButton)findViewById(R.id.femaleRb);
        userNameET=(EditText)findViewById(R.id.signupUsernameET);
        userPassET=(EditText)findViewById(R.id.signupPasswordET);
        cnfrmUserPassET=(EditText)findViewById(R.id.confirmPassET);

        setMaServer();
    }

    public void openLogIn(View iew)
    {

        String fname=userFNameET.getText().toString();
        String lname=userLNameET.getText().toString();
        String phone=phoneEt.getText().toString();
        String usname=userNameET.getText().toString();
        String upass=userPassET.getText().toString();
        String cupass=cnfrmUserPassET.getText().toString();

        if(fname.isEmpty() || lname.isEmpty() || phone.isEmpty() || usname.isEmpty() ||
                upass.isEmpty() || cupass.isEmpty())
        {
            Toast.makeText(this,"Fill the empty fields first",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!upass.equals(cupass))
        {
            Toast.makeText(this,"Password didn't match",Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i=0;i<mList.size();i++)
        {
            UserDataModel mUserDatamodel=mList.get(i);

            if(mUserDatamodel.getUsername().equals(usname) && mUserDatamodel.getUserPassword().equals(upass))
            {
                Toast.makeText(this,"User already exists.",Toast.LENGTH_SHORT).show();
                return;
            }

        }
        if(male.isChecked())
        {

            UserDataModel mModel=new UserDataModel(fname,lname,phone.trim(),male.getText().toString(),usname,upass);

            mBeaconReference.push().setValue(mModel);
        }
        else
        {
            UserDataModel mModel=new UserDataModel(fname,lname,phone.trim(),female.getText().toString(),usname,upass);

            mBeaconReference.push().setValue(mModel);
        }

        Toast.makeText(this,"SignUp successful",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(this, LOginActivity.class);
        startActivity(intent);
    }
    public void setMaServer()
    {
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mBeaconReference=mFirebaseDatabase.getReference().child("users");

        mList=new ArrayList<>();

        mChaldEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mList.add(dataSnapshot.getValue(UserDataModel.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mBeaconReference.addChildEventListener(mChaldEventListener);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
