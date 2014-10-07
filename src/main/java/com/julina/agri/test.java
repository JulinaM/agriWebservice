package com.julina.agri;

import org.json.JSONObject;

/**
 * Created by julina on 9/25/14.
 */
public class test {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "lux");
        jsonObject.put("password", "lux");
        jsonObject.put("firstName", "laxmi");
        jsonObject.put("lastName", "maharjan");
        jsonObject.put("email", "lux|_me3");
        jsonObject.put("deviceId","mamaDevice:P");
        jsonObject.put("userType",1);
        System.out.println(jsonObject.toString());

    }
}
