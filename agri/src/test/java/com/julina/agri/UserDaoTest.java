package com.julina.agri;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.UserDao;
import com.julina.agri.pojo.UserPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by julina on 10/4/14.
 */
public class UserDaoTest {
    private UserDao userDao = null;

    //@Test
    public void insertTest() throws RmodelException.SqlException, RmodelException.CommonException, SQLException, AgriException.NullPointerException {
        UserPojo userPojo = new UserPojo();
        userPojo.setUserType(1);
        userPojo.setUsername("mels");
        userPojo.setFirstName("melina");
        userPojo.setLastName("maharjan");
        userPojo.setPassword("owl");
        userPojo.setEmail("smileymels@gmail.com");
        userPojo.setDeviceId("deviceLava");
        userPojo.setOldPassword("");
        userDao = new UserDao();
        String uid = userDao.insert(userPojo);
        Assert.assertNotNull(uid);
    }

    @Test
    public void getTest() throws RmodelException.SqlException, SQLException, RmodelException.CommonException, AgriException.NullPointerException {
        UserPojo userPojo = new UserPojo();
        userPojo.setUsername("sujs");
        userPojo.setPassword("rabbit");
        userDao = new UserDao();
        UserPojo result = userDao.login(userPojo);
        Assert.assertTrue(result.getUid() != null);
       // Assert.assertTrue(result.getEmail().equals("julinamaharjan@gmail.com"));
        System.out.println(result.getUid()+" "+ result.getEmail()+" "+ result.getUserType()+"\n");

    }
}
