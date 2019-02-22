package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Zohaib on 3/3/2018.
 */
@Dao
public interface TimeDaoo {


    @Insert
    void insert(TimeTable... tData);

    @Query("SELECT * FROM timing")
    List<TimeTable> getAllTime();

    @Query("SELECT tid FROM timing WHERE meddate = :medDate")
    public int getId(String medDate);

    @Query("DELETE FROM timing WHERE tid = :medTime")
    public void delTime(int medTime);

    @Delete
    void delete(TimeTable time);


}
