package com.julina.agri;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.LocationDao;
import com.julina.agri.pojo.LocationPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by julina on 10/6/14.
 */
public class LocationTEst {
    private LocationDao locationDao = null;

    @Test
    public void insertTest() throws SQLException, AgriException.NullPointerException, RmodelException.SqlException, RmodelException.CommonException {
        locationDao = new LocationDao();
        ArrayList<LocationPojo> locationPojos = locationDao.getLocations();
        System.out.println(locationPojos.get(0).getLocationId() +" " +locationPojos.get(0).getLocationName());

    }

}
