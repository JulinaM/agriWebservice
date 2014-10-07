package com.julina.agri.pojo;

/**
 * Created by julina on 10/7/14.
 */
public class LocationsCropPojo {
    private CropPojo cropId;
    private LocationPojo locationId;
    private String tag;

    public CropPojo getCropId() {
        return cropId;
    }

    public void setCropId(CropPojo cropId) {
        this.cropId = cropId;
    }

    public LocationPojo getLocationId() {
        return locationId;
    }

    public void setLocationId(LocationPojo locationId) {
        this.locationId = locationId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
