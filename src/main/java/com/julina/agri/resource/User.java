package com.julina.agri.resource;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.common.ResponseJson;
import com.julina.agri.dao.UserDao;
import com.julina.agri.pojo.UserPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by julina on 9/25/14.
 */

@Path("user")
public class User {

    @Path("sign_up")
    @POST

    public Response registerPost(String jsonString)  {

        JSONObject responseJson = ResponseJson.getResponse();
        if(jsonString == null) {
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.NULL_REQUEST.toString()+"jsonString null");
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.NULL_REQUEST.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        JSONObject requestJson = new JSONObject(jsonString);
        UserPojo userPojo = new UserPojo();
        if(!requestJson.has(UserPojo.USERNAME)){
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.INVALID_USERNAME.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INVALID_USERNAME.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        if(!requestJson.has(UserPojo.PASSWORD)){
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.INVALID_PASSWORD.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INVALID_PASSWORD.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        if(!requestJson.has(UserPojo.EMAIL)){
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.INVALID_EMAIL.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INVALID_EMAIL.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        if(!requestJson.has(UserPojo.FIRST_NAME)){
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.NULL_REQUEST.toString()+"fistnameError");
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.NULL_REQUEST.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        if(!requestJson.has(UserPojo.LAST_NAME)){
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.NULL_REQUEST.toString()+"lastNameError");
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.NULL_REQUEST.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        userPojo.setUsername( requestJson.getString(UserPojo.USERNAME));
        userPojo.setPassword(requestJson.getString(UserPojo.PASSWORD));
        userPojo.setEmail(requestJson.getString(UserPojo.EMAIL));
        userPojo.setFirstName(requestJson.getString(UserPojo.FIRST_NAME));
        userPojo.setLastName(requestJson.getString(UserPojo.LAST_NAME));
        userPojo.setDeviceId(requestJson.getString(UserPojo.DEVICE_ID));
        userPojo.setUserType(requestJson.getInt(UserPojo.USER_TYPE));
        try {
            UserDao userDao = new UserDao();
            String uid = userDao.insert(userPojo);
            responseJson.put(ResponseJson.ERROR, false);
            responseJson.put(ResponseJson.ERROR_CODE, 200);
            responseJson.put(ResponseJson.ERROR_MESSAGE, "success");
            responseJson.put(ResponseJson.BODY, new JSONObject().put(UserPojo.UID, uid));
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

    @POST
    @Path("login")
    public Response loginPost(String jsonString){
        JSONObject responseJson = ResponseJson.getResponse();
        if(jsonString == null) {
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.NULL_REQUEST.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.NULL_REQUEST.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        JSONObject requestJson = new JSONObject(jsonString);
        if(!requestJson.has(UserPojo.USERNAME)){
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.INVALID_USERNAME.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INVALID_USERNAME.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        if(!requestJson.has(UserPojo.PASSWORD)){
            responseJson.put(ResponseJson.ERROR_MESSAGE, ErrorMessages.INVALID_PASSWORD.toString());
            responseJson.put(ResponseJson.ERROR_CODE, ErrorMessages.INVALID_PASSWORD.getValue());
            return Response.ok().entity(responseJson.toString()).build();
        }
        UserPojo userPojo = new UserPojo();
        userPojo.setUsername( requestJson.getString(UserPojo.USERNAME));
        userPojo.setPassword(requestJson.getString(UserPojo.PASSWORD));
        try {
            UserDao userDao = new UserDao();
            UserPojo userPojo1 = userDao.login(userPojo);
            responseJson.put(ResponseJson.ERROR, false);
            responseJson.put(ResponseJson.ERROR_CODE, 200);
            responseJson.put(ResponseJson.ERROR_MESSAGE, "success");
            JSONObject respJson = new JSONObject();
            respJson.put(UserPojo.UID, userPojo1.getUid());
            respJson.put(UserPojo.EMAIL, userPojo1.getEmail());
            respJson.put(UserPojo.USER_TYPE, userPojo1.getUserType());
            responseJson.put(ResponseJson.BODY, respJson);
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
