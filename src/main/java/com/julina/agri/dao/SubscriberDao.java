package com.julina.agri.dao;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.DBConnection;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.pojo.LocationPojo;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import org.json.JSONArray;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by julina on 10/7/14.
 */
public class SubscriberDao {

    private String tableName= "subsriber";

    private MySql mySql = null;
    private MySqlQuery mySqlQuery = null;


    public SubscriberDao() throws
            RmodelException.SqlException,
            RmodelException.CommonException {

        this.mySql = DBConnection.Connect();
        mySqlQuery = new MySqlQuery();
        mySqlQuery.setSql(this.mySql);
    }


    public int[] insert(String deviceId,int locationId, JSONArray tags) throws
            AgriException.NullPointerException, SQLException, RmodelException.SqlException {

        if(deviceId == null)
            throw new AgriException.NullPointerException(ErrorMessages.NULL_USER_ID.toString());

        if(tags == null || tags.length() == 0)
            throw new AgriException.NullPointerException(ErrorMessages.NULL_USER_ID.toString());

        String query = "INSERT INTO %s (deviceId, tag) VALUE(?,?)";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            int length = tags.length();
            for (int i = 0; i < length; i++) {
                preparedStatement.setString(1, deviceId);
                preparedStatement.setString(2, tags.getString(i));
                preparedStatement.addBatch();
            }

            return preparedStatement.executeBatch();
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
