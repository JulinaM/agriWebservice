package com.julina.agri.dao;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.DBConnection;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by julina on 10/8/14.
 */
public class GCMInfoDao {
    private String tableName= "GCMInfo";

    private MySql mySql = null;
    private MySqlQuery mySqlQuery = null;


    public GCMInfoDao() throws
            RmodelException.SqlException,
            RmodelException.CommonException {

        this.mySql = DBConnection.Connect();
        mySqlQuery = new MySqlQuery();
        mySqlQuery.setSql(this.mySql);
    }

    public int registerGCM(String deviceId, String regId) throws
            SQLException, RmodelException.SqlException,
            AgriException.NullPointerException {

        if(deviceId == null)
            throw new AgriException.NullPointerException("deviceId null");
        if(regId == null)
            throw new AgriException.NullPointerException("regId null");


        String query = "INSERT INTO %s (deviceId, regId) VALUE(?,?)";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setString(1, deviceId);
            preparedStatement.setString(2, regId);
            return mySqlQuery.Dml();
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
        return 0;
    }


}
