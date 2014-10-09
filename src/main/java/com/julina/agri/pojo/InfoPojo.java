package com.julina.agri.pojo;

/**
 * Created by julina on 10/9/14.
 */
public class InfoPojo {
    private int infoId;
    private  String infoTitle;
    private String infoFrom;
    private String infoData;
    private long timestamp;

    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoFrom() {
        return infoFrom;
    }

    public void setInfoFrom(String infoFrom) {
        this.infoFrom = infoFrom;
    }

    public String getInfoData() {
        return infoData;
    }

    public void setInfoData(String infoData) {
        this.infoData = infoData;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
