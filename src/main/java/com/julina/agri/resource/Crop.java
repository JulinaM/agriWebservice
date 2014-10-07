package com.julina.agri.resource;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.common.ResponseJson;
import com.julina.agri.dao.CropDao;
import com.julina.agri.dao.LocationDao;
import com.julina.agri.pojo.CropPojo;
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
 * Created by julina on 10/7/14.
 */
@Path("crop")
public class Crop {
    @Path("pull")
    @GET
    public Response getLocation(){
        JSONObject responseJson = ResponseJson.getResponse();
        try{
            CropDao cropDao = new CropDao();
            ArrayList<CropPojo> results = cropDao.getCrops();
            JSONArray jsonArray = new JSONArray();
            for(CropPojo crop : results){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", crop.getCropId());
                jsonObject.put("name", crop.getCropName());
                jsonArray.put(jsonObject);
            }
            responseJson.put(ResponseJson.ERROR, false);
            responseJson.put(ResponseJson.ERROR_CODE, 200);
            responseJson.put(ResponseJson.ERROR_MESSAGE, "success");
            responseJson.put(ResponseJson.BODY, jsonArray);
            return Response.ok().entity(responseJson.toString()).build();
        } catch (SQLException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.ERROR_MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        } catch (RmodelException.SqlException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.ERROR_MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        } catch (RmodelException.CommonException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.ERROR_MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        } catch (AgriException.NullPointerException e) {
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INTERNAL_SERVER_ERROR.getValue());
            responseJson.put(ResponseJson.ERROR_MESSAGE, e.getMessage());
            return Response.ok().entity(responseJson.toString()).build();
        }
    }
}
