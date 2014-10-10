package com.julina.agri.dao;

import com.julina.agri.common.AgriException;
import com.julina.agri.common.DBConnection;
import com.julina.agri.common.ErrorMessages;
import com.julina.agri.pojo.CropPojo;
import com.julina.agri.pojo.InfoPojo;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by julina on 10/9/14.
 */
public class InfoDao {
    private String tableName= "info";

    private MySql mySql = null;
    private MySqlQuery mySqlQuery = null;


    public InfoDao() throws
            RmodelException.SqlException,
            RmodelException.CommonException {

        this.mySql = DBConnection.Connect();
        mySqlQuery = new MySqlQuery();
        mySqlQuery.setSql(this.mySql);
    }

    public int insert(InfoPojo infoPojo) throws SQLException,
            RmodelException.SqlException,
            AgriException.NullPointerException {

        if(infoPojo == null)
            throw new AgriException.NullPointerException(ErrorMessages.NULL_USER_ID.toString());
        String query = "INSERT INTO %s (infoId, infoTitle, infoFrom, infoData) value (?,?,?,?)";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setInt(1, infoPojo.getInfoId());
            preparedStatement.setString(2, infoPojo.getInfoTitle());
            preparedStatement.setString(3, infoPojo.getInfoFrom());
            preparedStatement.setString(4, infoPojo.getInfoData());
            //preparedStatement.setLong(5, infoPojo.getTimestamp());
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

    public String[] getRegId(String tag) throws AgriException.DataModelException, SQLException, RmodelException.SqlException {
        if(tag == null)
            throw new AgriException.DataModelException(ErrorMessages.NULL_REQUEST.toString());
        String query = "SELECT GI.regId from subscriber as S JOIN GCMInfo as GI on S.deviceId = GI.deviceId WHERE S.tag = ?";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try{
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setString(1, tag);
            ResultSet resultSet = mySqlQuery.Drl();
            resultSet.last();
            int length = resultSet.getRow();
            String[] regIds = new String[length];
            resultSet.beforeFirst();
            int i = 0;
            while (resultSet.next()) {
                regIds[i++] = resultSet.getString("regId");
            }
            return regIds;
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

    public ArrayList<InfoPojo> getCropDetail(int cropId) throws SQLException,
            RmodelException.SqlException,
            AgriException.NullPointerException {


        String query = " select distinct i.infoTitle, i.infoData, i.infoFrom, i.infoId, UNIX_TIMESTAMP(timestamp) as t from info as i join infoClient as ic join locationsCrop as lc on lc.tag=ic.tag AND ic.infoId=i.infoId WHERE lc.cropId = ?";
        query = String.format(query, tableName);
        mySqlQuery.setQuery(query);

        PreparedStatement preparedStatement = null;
        try {
            mySqlQuery.InitPreparedStatement();
            preparedStatement = mySqlQuery.getPreparedStatement();
            preparedStatement.setInt(1, cropId);
            ResultSet resultSet = mySqlQuery.Drl();
            resultSet.beforeFirst();
            ArrayList<InfoPojo> infos = new ArrayList<>(resultSet.getRow());
            while (resultSet.next()){
               InfoPojo infoPojo = new InfoPojo();
                infoPojo.setInfoTitle(resultSet.getString("infoTitle"));
                infoPojo.setInfoData(resultSet.getString("infoData"));
                infoPojo.setInfoFrom(resultSet.getString("infoFrom"));
                infoPojo.setTimestamp(resultSet.getLong("t"));
                infoPojo.setInfoId(resultSet.getInt("infoId"));
                infos.add(infoPojo);
            }
            return infos;
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
