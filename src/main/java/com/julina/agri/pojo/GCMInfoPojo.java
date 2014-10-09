package com.julina.agri.pojo;

/**
 * Created by julina on 10/8/14.
 */
public class GCMInfoPojo {

    private String deviceId;
    private String regId;

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegId() {
        return regId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
