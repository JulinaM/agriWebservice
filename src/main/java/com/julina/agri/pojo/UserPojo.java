package com.julina.agri.pojo;

/**
 * Created by julina on 9/25/14.
 */
public class UserPojo {
    public static final String UID = "uid";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String EMAIL = "email";
    public static final String USER_TYPE = "userType";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String DEVICE_ID = "deviceId";

    private String uid;
    private String username;
    private String password;
    private String email;
    private int userType;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String deviceId;

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
