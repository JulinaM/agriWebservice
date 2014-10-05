package com.julina.agri.common;

import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.connection.MySqlConnection;
import com.tektak.iloop.rmodel.driver.MySql;

import java.net.URL;

/**
 * Created by julina on 7/4/14.
 */
public class DBConnection {

    public static MySql Connect() throws RmodelException.SqlException, RmodelException.CommonException {

        MySqlConnection mySqlConnection = new MySqlConnection();
        mySqlConnection.setDatabaseName("agri");
        mySqlConnection.setUsername("root");
        mySqlConnection.setPassword("julina12");
        mySqlConnection.setUrl("jdbc:mysql://localhost:3306/");
        mySqlConnection.setDriver("com.mysql.jdbc.Driver");

        MySql mySql = new MySql();
        mySql.setConnection(mySqlConnection);
        mySql.InitConnection();
        return mySql;
    }
}
