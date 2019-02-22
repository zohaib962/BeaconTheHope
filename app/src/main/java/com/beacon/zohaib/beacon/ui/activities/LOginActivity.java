package com.beacon.zohaib.beacon.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class LOginActivity extends AppCompatActivity {


    private EditText userNamET;
    private EditText userPassET;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBeaconReference;

    private ChildEventListener mChaldEventListener;

    List<UserDataModel> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userNamET=(EditText)findViewById(R.id.editusername);
        userPassET=(EditText)findViewById(R.id.editpassword);

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





    public void openDashboard(View view)
    {
        boolean flag=false;

        Log.d("hello",String.valueOf(mList.size()));

        String username=userNamET.getText().toString();
        String userpass=userPassET.getText().toString();
        for (int i=0;i<mList.size();i++)
        {
            UserDataModel mUserDatamodel=mList.get(i);

            if(mUserDatamodel.getUsername().equals(username) && mUserDatamodel.getUserPassword().equals(userpass))
            {
                flag=true;
                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show();

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("username",username);
                editor.putString("password",userpass);
                editor.putString("phone",mUserDatamodel.getPhone());
                editor.commit();

                Intent intent= new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }

        if(flag==true) {
          //  Toast.makeText(this, "Login UnSuccessful", Toast.LENGTH_SHORT).show();
        }

    }
    public void openSignUp(View view)
    {


        Intent intent= new Intent(this, SIgnUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
