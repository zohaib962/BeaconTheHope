package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Zohaib on 3/1/2018.
 * koi haal nahi ha
 */
@Dao
public interface MedicationDaoo {

@Insert
void insertMed(MedicationTable... mData);

@Query("SELECT mid FROM medication WHERE medname = :mname AND dosage = :mdosage")
public int getId(String mname,int mdosage);

@Query("SELECT * FROM medication WHERE mid = :medid")
public List<MedicationTable> getAllMedsFromId(int medid);

@Query("SELECT medname FROM medication WHERE mid = :medid")
public String  getMedsFromId(int medid);

@Query("SELECT * FROM medication")
List<MedicationTable> getAllMeds();

@Query("DELETE FROM medication WHERE mid = :mMedId")
public void deleteMedFromId(int mMedId);

@Delete
void delete(MedicationTable med);

}
