package com.civip.csyy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/12
 */
public class CsyyVehicleLocDTO {
    @JsonProperty("id")
    private String deviceId;
    private double longitude;
    private double latitude;
    private int speed;
    private int direction;
    private int typeId;
    private long collectTime;
    private long updateTime;
    private int onJob;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getOnJob() {
        return onJob;
    }

    public void setOnJob(int onJob) {
        this.onJob = onJob;
    }
}
