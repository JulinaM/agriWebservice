package com.julina.agri.resource;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.common.ResponseJson;
import com.julina.agri.dao.LocationDao;
import com.julina.agri.pojo.LocationPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by julina on 10/6/14.
 */
@Path("ws/location")
public class Location {
    @Path("pull")
    @GET
    public Response getLocation(){
        JSONObject responseJson = ResponseJson.getResponse();
        try {
            LocationDao locationDao = new LocationDao();
            ArrayList<LocationPojo> results = locationDao.getLocations();
            JSONArray jsonArray = new JSONArray();
            for(LocationPojo locationPojo : results){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", locationPojo.getLocationId());
                jsonObject.put("name", locationPojo.getLocationName());
                jsonArray.put(jsonObject);
            }
            responseJson.put(ResponseJson.ERROR, false);
            responseJson.put(ResponseJson.ERROR_CODE, 200);
            responseJson.put(ResponseJson.MESSAGE, "success");
            responseJson.put(ResponseJson.BODY, jsonArray);
            return Response.ok().entity(responseJson.toString()).build();
        } catch (SQLException e) {
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
