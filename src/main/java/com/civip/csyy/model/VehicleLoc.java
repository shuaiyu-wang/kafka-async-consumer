package com.civip.csyy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author: create by wangshuaiyu
 * @date: 2023/6/15
 */
@Entity
public class VehicleLoc {
    @Id
    @GeneratedValue
    private Long id;
    private String deviceId;
    private Double longitude;
    private Double latitude;
    private Integer speed;
    private Integer direction;
    private Integer typeId;
    private Long collectTime;
    private Long updateTime;
    private Integer onJob;
    private Long recvTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Long collectTime) {
        this.collectTime = collectTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOnJob() {
        return onJob;
    }

    public void setOnJob(Integer onJob) {
        this.onJob = onJob;
    }

    public Long getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Long recvTime) {
        this.recvTime = recvTime;
    }
}
