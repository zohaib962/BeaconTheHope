package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Zohaib on 3/1/2018.
 */
@Entity(tableName = "medication")
public class MedicationTable {


    @PrimaryKey(autoGenerate = true)
    int mid;

    @ColumnInfo(name = "medname")
    String medname;

    @ColumnInfo(name = "dosage")
    int dosage;


    public MedicationTable(String medname,int dosage)
    {
        this.dosage=dosage;
        this.medname=medname;
    }


    public int getId() {
        return mid;
    }

    public String getMedname() {
        return medname;
    }

    public int getDosage() {
        return dosage;
    }


    public void setId(int id) {
        this.mid = id;
    }

    public void setMedname(String medname) {
        this.medname = medname;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

}
