package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Zohaib on 3/3/2018.
 */
@Dao
public interface MedicationTimeJoinDao {

    @Insert
    void insert(MedicationTimeJoin ...medicationTimeJoin);

    @Query("SELECT * FROM medication_time")
    List<MedicationTimeJoin> getAllMednTime();

    @Query("SELECT medId FROM medication_time WHERE timeId = :mtimeId")
    public List<Integer> getMedId( int mtimeId);

    @Query("SELECT * FROM medication NATURAL JOIN timing NATURAL JOIN medication_time WHERE meddate = :mtime")
    public List<MedicationTable> getCurrMedList(long mtime);

    @Query("SELECT * FROM medication WHERE mid IN " +
            "(SELECT medId FROM medication_time WHERE timeId IN " +
            "(SELECT tid FROM timing WHERE meddate = :mtime))")
    public List<MedicationTable> getMedList(long mtime);

    @Query("DELETE FROM medication_time WHERE medId = :MmEDid ")
    public void deleteid(int MmEDid);
}
