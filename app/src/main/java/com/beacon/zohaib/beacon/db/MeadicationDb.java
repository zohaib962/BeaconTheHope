package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/*
  Created by Zohaib on 3/1/2018.
*/

@android.arch.persistence.room.Database(entities = {MedicationTable.class, TimeTable.class, MedicationTimeJoin.class},version = 1)
public abstract class MeadicationDb extends RoomDatabase {


    public abstract MedicationDaoo getMedicationDao();
    public abstract TimeDaoo getTimeDao();
    public abstract MedicationTimeJoinDao getMedicationTimeJoin();

    static MeadicationDb mInstance;


    static final String dbName="MedicineDB";


    public static MeadicationDb newInstance(Context context) {
        if(mInstance==null) {
            mInstance= Room.databaseBuilder(context.getApplicationContext(), MeadicationDb.class,dbName).build();
        }
        return mInstance;
    }

}
