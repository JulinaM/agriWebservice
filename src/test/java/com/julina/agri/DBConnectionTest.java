package com.julina.agri;

import com.julina.agri.common.DBConnection;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by julina on 10/4/14.
 */
public class DBConnectionTest {
    private DBConnection dbConnection = null;

    @Test
    public void init() throws RmodelException.SqlException {
        dbConnection = new DBConnection();
        MySql mySql = null;
        try {
            mySql = dbConnection.Connect();

            String query= "INSERT into test VALUE (?,?)";

            MySqlQuery mySqlQuery = new MySqlQuery(mySql, query);
            mySqlQuery.InitPreparedStatement();
            PreparedStatement preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "sujan");
            mySqlQuery.Dml();

        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(mySql != null)
                mySql.CloseConnection();
        }

    }
}
