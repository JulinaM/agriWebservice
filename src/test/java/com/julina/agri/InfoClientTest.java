package com.julina.agri;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.InfoClientDao;
import com.tektak.iloop.rmodel.RmodelException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by julina on 10/9/14.
 */
public class InfoClientTest {
    private InfoClientDao infoClientDao = null;
    @Test
    public void insertTest() throws AgriException.NullPointerException, SQLException, RmodelException.SqlException, RmodelException.CommonException {
        infoClientDao = new InfoClientDao();
        int result = infoClientDao.insert(5,"6-1");
        Assert.assertTrue(result == 1);
    }
}
