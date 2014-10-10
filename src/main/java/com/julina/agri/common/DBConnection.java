package com.julina.agri.common;

import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.connection.MySqlConnection;
import com.tektak.iloop.rmodel.driver.MySql;

/**
 * Created by julina on 7/4/14.
 */
public class DBConnection {
    /*
    * Root User: adminM4LRDeE
   Root Password: qBb3vU9dFdzB
   Database Name: krishighar
host: 127.2.43.130
port:3306*/

    public static MySql Connect() throws RmodelException.SqlException, RmodelException.CommonException {

        MySqlConnection mySqlConnection = new MySqlConnection();
        mySqlConnection.setDatabaseName("krishighar");
        mySqlConnection.setUsername("adminM4LRDeE");
        mySqlConnection.setPassword("qBb3vU9dFdzB");
        mySqlConnection.setUrl("jdbc:mysql://127.2.43.130:3306/");
        mySqlConnection.setDriver("com.mysql.jdbc.Driver");

       /* mySqlConnection.setDatabaseName("agri");
        mySqlConnection.setUsername("root");
        mySqlConnection.setPassword("julina12");
        mySqlConnection.setUrl("jdbc:mysql://localhost:3306/");
        mySqlConnection.setDriver("com.mysql.jdbc.Driver");*/

        MySql mySql = new MySql();
        mySql.setConnection(mySqlConnection);
        mySql.InitConnection();
        return mySql;
    }
}
