package com.julina.agri.resource;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.common.ResponseJson;
import com.julina.agri.dao.LocationCropDao;
import com.julina.agri.pojo.LocationsCropPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by julina on 10/7/14.
 */
@Path("ws/locationCrop")
public class LocationCrop {
    @Path("pull")
    @POST
    public Response getLocation(String jsonString){
        JSONObject responseJson = ResponseJson.getResponse();
        try {
            JSONObject requestJson = new JSONObject(jsonString);
            int locationId = requestJson.getInt("locationId");

            LocationCropDao locationCropDao = new LocationCropDao();
            ArrayList<LocationsCropPojo> results = locationCropDao.getCropsForGivenLocation(locationId);
            JSONArray jsonArray = new JSONArray();
            for(LocationsCropPojo locationPojo : results){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("locationId", locationPojo.getLocationId());
                jsonObject.put("cropId", locationPojo.getCropId().getCropId());
                jsonObject.put("cropName", locationPojo.getCropId().getCropName());
                jsonObject.put("tag", locationPojo.getTag());
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
