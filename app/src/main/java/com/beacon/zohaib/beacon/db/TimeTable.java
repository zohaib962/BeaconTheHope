package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Zohaib on 3/3/2018.
 */
@Entity(tableName = "timing")
public class TimeTable {
    @PrimaryKey(autoGenerate = true)
    int tid;

    @ColumnInfo(name = "meddate")
    Long mMedienceDate;

    public TimeTable(Long mMedienceDate)
    {
        this.mMedienceDate=mMedienceDate;
    }



    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setmMedienceDate(Long mMedienceDate) {
        this.mMedienceDate = mMedienceDate;
    }

    public int getTid() {

        return tid;
    }

    public Long getmMedienceDate() {
        return mMedienceDate;
    }
}
