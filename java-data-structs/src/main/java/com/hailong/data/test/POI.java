package com.hailong.data.test;

/**
 * Created by Administrator on 2020/9/2.
 */
public class POI {
    public String poiId;
    public String poiName;
    public double latitude;
    public double lonqitude;
    public String address;



    public POI(String poiId, String poiName, double latitude, double lonqitude, String address) {
        this.poiId = poiId;
        this.poiName = poiName;
        this.latitude = latitude;
        this.lonqitude = lonqitude;
        this.address = address;
    }
}
