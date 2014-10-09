package com.julina.agri.dao;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.DBConnection;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.pojo.CropPojo;
import com.julina.agri.pojo.LocationPojo;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by julina on 10/7/14.
 */
public class CropDao {
    private String tableName= "crop";

    private MySql mySql = null;
    private MySqlQuery mySqlQuery = null;


    public CropDao() throws
            RmodelException.SqlException,
            RmodelException.CommonException {

        this.mySql = DBConnection.Connect();
        mySqlQuery = new MySqlQuery();
        mySqlQuery.setSql(this.mySql);
    }

    @Deprecated
    public String insert(LocationPojo locationPojo) throws
            AgriException.NullPointerException, SQLException, RmodelException.SqlException {

        if(locationPojo == null)
            throw new AgriException.NullPointerException(ErrorMessages.NULL_USER_ID.toString());

        String userId = UUID.randomUUID().toString().substring(0,10);

        String query = "INSERT INTO %s (locationId, locationName) VALUE(?,?)";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setInt(1, locationPojo.getLocationId());
            preparedStatement.setString(2, locationPojo.getLocationName());
            mySqlQuery.Dml();
            return userId;
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

    public ArrayList<CropPojo> getCrops() throws SQLException,
            RmodelException.SqlException,
            AgriException.NullPointerException {


        String query = "SELECT  cropId, cropName FROM %s ";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            ResultSet resultSet = mySqlQuery.Drl();
            resultSet.beforeFirst();
            ArrayList<CropPojo> cropPojos = new ArrayList<>(resultSet.getRow());
            while (resultSet.next()){
                CropPojo cropPojo = new CropPojo();
                cropPojo.setCropId(resultSet.getInt("cropId"));
                cropPojo.setCropName(resultSet.getString("cropName"));
                cropPojos.add(cropPojo);
            }
            return cropPojos;
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
