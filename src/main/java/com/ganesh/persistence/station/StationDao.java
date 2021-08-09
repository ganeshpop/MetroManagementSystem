package com.ganesh.persistence.station;

import com.ganesh.persistence.MySQLConnectionHelper;
import com.ganesh.pojos.Station;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class StationDao implements StationDaoInterface{
    @Override
    public Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stations;");
        ResultSet resultSet = preparedStatement.executeQuery();
        return StationDaoHelper.generateStations(resultSet);
    }

    @Override
    public boolean isAStation(int stationId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT station_id FROM stations WHERE station_id = ?;");
        preparedStatement.setInt(1,stationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public String getStation(int stationId) throws SQLException, ClassNotFoundException, IOException {
        Connection connection = MySQLConnectionHelper.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT station_name FROM stations WHERE station_id = ?");
        preparedStatement.setInt(1, stationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String stationName = resultSet.getString("station_name");
            connection.close();
            return stationName;
        }
        return null;

    }
}
