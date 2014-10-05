package com.julina.agri.common;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by julina on 9/25/14.
 */
public class ResponseJson {
    public final static String ERROR_CODE = "errorCode";
    public final static String ERROR_MESSAGE = "errorMessage";
    public final static String ERROR = "error";
    public final static String TIMESTAMP = "timestamp";
    public final static String BODY = "body";

    public static JSONObject getResponse(){
        JSONObject responseJson = new JSONObject();
        responseJson.put(ResponseJson.ERROR, true);
        responseJson.put(ResponseJson.ERROR_CODE, 0);
        responseJson.put(ResponseJson.TIMESTAMP, new Date().getTime());
        responseJson.put(ResponseJson.ERROR_MESSAGE, JSONObject.NULL);
        responseJson.put(ResponseJson.BODY, JSONObject.NULL);
        return responseJson;
    }

}
