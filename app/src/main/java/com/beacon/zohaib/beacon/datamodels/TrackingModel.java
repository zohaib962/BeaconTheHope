package com.beacon.zohaib.beacon.datamodels;

/**
 * Created by Zohaib on 4/19/2018.
 */

public class TrackingModel {
    private String heartRate;

    public TrackingModel(String heartrate) {
        this.heartRate = heartrate;
    }

    public TrackingModel() {
    }

    public String getHeartrate() {
        return heartRate;
    }

    public void setHeartrate(String heartrate) {
        this.heartRate = heartrate;
    }
}
