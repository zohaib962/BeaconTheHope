package com.beacon.zohaib.beacon.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Zohaib on 3/3/2018.
 */
@Entity(tableName = "medication_time",
        primaryKeys = { "medId", "timeId" },
        foreignKeys = {
                @ForeignKey(entity = MedicationTable.class,
                        parentColumns = "mid",
                        childColumns = "medId"),
                @ForeignKey(entity = TimeTable.class,
                        parentColumns = "tid",
                        childColumns = "timeId")
        })
public class MedicationTimeJoin {
    public final int medId;
    public final int timeId;


    public MedicationTimeJoin(final int medId,final int timeId) {
        this.medId=medId;
        this.timeId=timeId;
    }

}
