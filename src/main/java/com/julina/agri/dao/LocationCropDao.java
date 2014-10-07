package com.julina.agri.dao;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.DBConnection;
import com.julina.agri.pojo.CropPojo;
import com.julina.agri.pojo.LocationPojo;
import com.julina.agri.pojo.LocationsCropPojo;
import com.julina.agri.pojo.UserPojo;
import com.julina.agri.resource.Crop;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by julina on 10/7/14.
 */
public class LocationCropDao {
    private String tableName= "locationCrops";

    private MySql mySql = null;
    private MySqlQuery mySqlQuery = null;


    public LocationCropDao() throws
            RmodelException.SqlException,
            RmodelException.CommonException {

        this.mySql = DBConnection.Connect();
        mySqlQuery = new MySqlQuery();
        mySqlQuery.setSql(this.mySql);
    }

    public  ArrayList<LocationsCropPojo> getCropsForGivenLocation(int locationId) throws SQLException,
            RmodelException.SqlException,
            AgriException.NullPointerException {


        String query = "SELECT  C.cropId, C.cropName, LC.tag FROM crop as C JOIN locationsCrop as LC " +
                "ON C.cropId = LC.cropId WHERE LC.locationId = ? ";
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setInt(1, locationId);
            ResultSet resultSet = mySqlQuery.Drl();
            resultSet.beforeFirst();
            ArrayList<LocationsCropPojo> locationsCrop = new ArrayList<>(resultSet.getRow());
            while (resultSet.next()){
                LocationsCropPojo locationsCropPojo = new LocationsCropPojo();
                CropPojo cropPojo = new CropPojo();
                cropPojo.setCropId(resultSet.getInt("cropId"));
                cropPojo.setCropName(resultSet.getString("cropName"));
                locationsCropPojo.setCropId(cropPojo);
                locationsCropPojo.setTag(resultSet.getString("tag"));
                locationsCrop.add(locationsCropPojo);
            }
            return locationsCrop;
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(preparedStatement != null)
                preparedStatement.close();
            if(mySqlQuery != null)
                mySqlQuery.Close();
        }
        return null;
    }
}
