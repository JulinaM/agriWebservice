package com.julina.agri;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.SubscriberDao;
import com.tektak.iloop.rmodel.RmodelException;
import org.json.JSONArray;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by julina on 10/7/14.
 */
public class SubscriberTest {
    private SubscriberDao subscriberDao = null;
    @Test
    public void insert() throws AgriException.NullPointerException, SQLException, RmodelException.SqlException, RmodelException.CommonException {
       subscriberDao = new SubscriberDao();
       subscriberDao.insert("testDevice", 0,new JSONArray().put("tag1").put("tag2"));
    }
}
