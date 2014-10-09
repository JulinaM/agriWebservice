package com.julina.agri.dao;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.DBConnection;
import com.julina.agri.common.ErrorMessages;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by julina on 10/9/14.
 */
public class InfoClientDao {

    private String tableName= "infoClient";

    private MySql mySql = null;
    private MySqlQuery mySqlQuery = null;


    public InfoClientDao() throws
            RmodelException.SqlException,
            RmodelException.CommonException {

        this.mySql = DBConnection.Connect();
        mySqlQuery = new MySqlQuery();
        mySqlQuery.setSql(this.mySql);
    }

    public int insert(int infoId, String tag) throws SQLException,
            RmodelException.SqlException,
            AgriException.NullPointerException {

        if(infoId == 0 || tag == null)
            throw new AgriException.NullPointerException(ErrorMessages.NULL_USER_ID.toString());
        String query = "INSERT INTO %s (infoId, tag) value (?,?)";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setInt(1, infoId);
            preparedStatement.setString(2, tag);
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
