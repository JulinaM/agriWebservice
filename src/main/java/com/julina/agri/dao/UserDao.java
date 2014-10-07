package com.julina.agri.dao;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.DBConnection;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.pojo.UserPojo;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by julina on 9/25/14.
 */
public class UserDao {
    private String tableName= "testuser";

    private MySql mySql = null;
    private MySqlQuery mySqlQuery = null;


    public UserDao() throws
            RmodelException.SqlException,
            RmodelException.CommonException {

        this.mySql = DBConnection.Connect();
        mySqlQuery = new MySqlQuery();
        mySqlQuery.setSql(this.mySql);
    }

    public String insert(UserPojo userPojo) throws
            SQLException, RmodelException.SqlException,
            AgriException.NullPointerException {

        if(userPojo == null)
            throw new AgriException.NullPointerException(ErrorMessages.NULL_USER_ID.toString());

        String userId = UUID.randomUUID().toString().substring(0,10);

        String query = "INSERT INTO %s (username, password, email, firstName," +
                " lastName, userType, uid, deviceId) VALUE(?,?,?,?,?,?,?,?)";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setString(1, userPojo.getUsername());
            preparedStatement.setString(2, userPojo.getPassword());
            preparedStatement.setString(3, userPojo.getEmail());
            preparedStatement.setString(4, userPojo.getFirstName());
            preparedStatement.setString(5, userPojo.getLastName());
            preparedStatement.setInt(6, userPojo.getUserType());
            preparedStatement.setString(7, userId);
            preparedStatement.setString(8, userPojo.getDeviceId());
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

    public UserPojo login(UserPojo userPojo) throws SQLException,
            RmodelException.SqlException,
            AgriException.NullPointerException {

        if(userPojo == null)
            throw new AgriException.NullPointerException(ErrorMessages.NULL_USER_ID.toString());
        String query = "SELECT  uid, userType, email FROM %s WHERE username = ? AND password = ?";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        UserPojo userPojo1 = new UserPojo();
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setString(1, userPojo.getUsername());
            preparedStatement.setString(2, userPojo.getPassword());
            ResultSet resultSet = mySqlQuery.Drl();
            resultSet.first();
            if(resultSet.getRow() == 1) {
                userPojo1.setUid(resultSet.getString("uid"));
                userPojo1.setEmail(resultSet.getString("email"));
                userPojo1.setUserType(resultSet.getInt("userType"));
            }
            return userPojo1;
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
