package com.julina.agri.resource;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.common.ResponseJson;
import com.julina.agri.dao.GCMInfoDao;
import com.tektak.iloop.rmodel.RmodelException;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by julina on 10/8/14.
 */
@Path("ws/GCM")
public class GCMInfo {
    @Path("registration")
    @POST
    public Response GCMRegistration(String jsonString){
        JSONObject responseJson = ResponseJson.getResponse();
        if(jsonString == null) {
            responseJson.put(ResponseJson.MESSAGE, ErrorMessages.NULL_REQUEST.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.NULL_REQUEST.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        JSONObject requestJson = new JSONObject(jsonString);
        if(!requestJson.has("deviceId") || !requestJson.has("regId")){
            responseJson.put(ResponseJson.MESSAGE, ErrorMessages.INVALID_USERNAME.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INVALID_USERNAME.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        String deviceId =  requestJson.getString("deviceId");
        String regId = requestJson.getString("regId");
        try {
            GCMInfoDao gcmInfoDao = new GCMInfoDao();
            gcmInfoDao.registerGCM(deviceId, regId);
            responseJson.put(ResponseJson.ERROR, false);
            responseJson.put(ResponseJson.ERROR_CODE, 200);
            responseJson.put(ResponseJson.MESSAGE, "success");
            return Response.ok().entity(responseJson.toString()).build();
        } catch (SQLException e ) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        } catch (RmodelException.SqlException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        } catch (RmodelException.CommonException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        } catch (AgriException.NullPointerException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        }
    }
}
