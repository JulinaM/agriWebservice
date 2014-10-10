package com.julina.agri;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.GCMInfoDao;
import com.tektak.iloop.rmodel.RmodelException;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by julina on 10/8/14.
 */
public class GCMInfoTest {
    private GCMInfoDao gcmInfoDao = null;

    @Test
    public void insertTest() throws AgriException.NullPointerException, SQLException, RmodelException.SqlException, RmodelException.CommonException {
        gcmInfoDao = new GCMInfoDao();
        gcmInfoDao.registerGCM("myDev", "reg12");
    }
}
