package com.julina.agri.resource;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.UserDao;
import com.julina.agri.pojo.UserPojo;
import com.tektak.iloop.rmodel.RmodelException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by julina on 10/4/14.
 */
@Path("ws")
public class HelloWorld {
    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) throws RmodelException.SqlException, RmodelException.CommonException, AgriException.NullPointerException, SQLException {

        UserPojo userPojo = new UserPojo();
        userPojo.setUsername(msg);
        userPojo.setPassword("rabbit");
        UserDao userDao = new UserDao();
        UserPojo result = userDao.login(userPojo);
        String response = "hello "+msg+"\n"+"your email address is : "+result.getEmail();
        return Response.ok().entity(response).build();
    }
}
