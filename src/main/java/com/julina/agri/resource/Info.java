package com.julina.agri.resource;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.common.ResponseJson;
import com.julina.agri.dao.InfoClientDao;
import com.julina.agri.dao.InfoDao;
import com.julina.agri.dao.SubscriberDao;
import com.julina.agri.pojo.InfoPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by julina on 10/9/14.
 */
@Path("ws/info")
public class Info {

    @Path("update")
    @POST
    public Response getLocation(String jsonString){
        JSONObject responseJson = ResponseJson.getResponse();
        try {
            if(jsonString == null) {
                responseJson.put(ResponseJson.MESSAGE, ErrorMessages.NULL_REQUEST.toString()+"jsonString null");
                responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.NULL_REQUEST.getValue());
                return Response.ok().entity(responseJson.toString()).build();
            }
            JSONObject requestJson = new JSONObject(jsonString);
            InfoPojo info = new InfoPojo();

            info.setInfoId(requestJson.getInt("infoId"));

            info.setInfoTitle(requestJson.getString("infoTitle"));
            info.setInfoFrom(requestJson.getString("infoFrom"));
            info.setInfoData(requestJson.getString("infoData"));
            info.setTimestamp(requestJson.getLong("timestamp"));
            String tag = requestJson.getString("tag");
            InfoDao infoDao = new InfoDao();
            int result = infoDao.insert(info);
            if (result != 1){
                responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
                responseJson.put(ResponseJson.MESSAGE, "internal server error 1");
                return Response.ok().entity(responseJson.toString()).build();
            }
            InfoClientDao infoClientDao = new InfoClientDao();
            int result1= infoClientDao.insert(requestJson.getInt("infoId"), tag);
            if(result1 != 1){
                responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
                responseJson.put(ResponseJson.MESSAGE, "internal server error 2");
                return Response.ok().entity(responseJson.toString()).build();
            }
            infoDao = new InfoDao();
            String[] regId = infoDao.getRegId(tag);

            responseJson.put(ResponseJson.ERROR, false);
            responseJson.put(ResponseJson.ERROR_CODE, 200);
            responseJson.put(ResponseJson.BODY, regId);
            responseJson.put(ResponseJson.MESSAGE, "success");
        } catch (SQLException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        } catch (RmodelException.SqlException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        } catch (RmodelException.CommonException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        } catch (AgriException.NullPointerException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        } catch (AgriException.DataModelException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
        }
        return Response.ok().entity(responseJson.toString()).build();
    }

    @Path("crop")
    @POST
    public Response getCropsDetail(String jsonString){
        JSONObject responseJson = ResponseJson.getResponse();
        try {
            if(jsonString == null) {
                responseJson.put(ResponseJson.MESSAGE, ErrorMessages.NULL_REQUEST.toString()+"jsonString null");
                responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.NULL_REQUEST.getValue());
                return Response.ok().entity(responseJson.toString()).build();
            }

            JSONObject requestJson = new JSONObject(jsonString);
            long timestamp = requestJson.getLong("timestamp");
            int cropId = requestJson.getInt("cropId");
            InfoDao infoDao = new InfoDao();
            ArrayList<InfoPojo> infoPojos = infoDao.getCropDetail(cropId);
            JSONArray jsonArray = new JSONArray();
            for(InfoPojo infoPojo : infoPojos){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", infoPojo.getInfoTitle());
                jsonObject.put("from", infoPojo.getInfoFrom());
                jsonObject.put("body", infoPojo.getInfoData());
                jsonObject.put("timestamp", infoPojo.getTimestamp());
                jsonArray.put(jsonObject);
            }
            responseJson.put(ResponseJson.ERROR, false);
            responseJson.put(ResponseJson.ERROR_CODE, 200);
            responseJson.put(ResponseJson.BODY, jsonArray);
            responseJson.put(ResponseJson.MESSAGE, "success");
        } catch (SQLException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        } catch (RmodelException.SqlException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        } catch (RmodelException.CommonException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        } catch (AgriException.NullPointerException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
        }
        return Response.ok().entity(responseJson.toString()).build();
    }
}
