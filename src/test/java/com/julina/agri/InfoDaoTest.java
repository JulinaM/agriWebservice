package com.julina.agri;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.InfoDao;
import com.julina.agri.pojo.InfoPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by julina on 10/9/14.
 */
public class InfoDaoTest {
    private InfoDao infoDao = null;
    @Test
    public void info() throws RmodelException.SqlException, RmodelException.CommonException, AgriException.DataModelException, SQLException {
        infoDao = new InfoDao();
        String[] regIds= infoDao.getRegId("2-1");

        for (String regId: regIds)
        {
            System.out.println(regId);
        }
    }

    @Test
    public void insert() throws RmodelException.SqlException, RmodelException.CommonException, AgriException.DataModelException, SQLException, AgriException.NullPointerException {
        infoDao = new InfoDao();
        InfoPojo infoPojo = new InfoPojo();
        infoPojo.setInfoId(5);
        infoPojo.setInfoTitle("rice");
        infoPojo.setInfoFrom("test");
        infoPojo.setInfoData("rice plantation should be ...");
        infoPojo.setTimestamp(0);
        //int result = infoDao.insert(infoPojo);
        //Assert.assertTrue(result == 1);

    }
}
