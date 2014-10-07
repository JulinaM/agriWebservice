package com.julina.agri;

import com.julina.agri.common.AgriException;
import com.julina.agri.dao.LocationCropDao;
import com.julina.agri.pojo.LocationsCropPojo;
import com.tektak.iloop.rmodel.RmodelException;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by julina on 10/7/14.
 */
public class LocationsCropTest {
    private LocationCropDao locationCropDao = null;
    @Test
    public void getTest() throws SQLException, AgriException.NullPointerException, RmodelException.SqlException, RmodelException.CommonException {
        locationCropDao = new LocationCropDao();
        ArrayList<LocationsCropPojo> locationPojos = locationCropDao.getCropsForGivenLocation(4);
        for (LocationsCropPojo lc : locationPojos){
        System.out.println(lc.getCropId().getCropId()
                +" " +lc.getCropId().getCropName()+" "+ lc.getTag());
        }

    }
}
