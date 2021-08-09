package com.ganesh.persistence.station;

import com.ganesh.pojos.Station;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StationDaoHelper {

    public static ArrayList<Station> generateStations(ResultSet resultSet) throws SQLException {
        ArrayList<Station> stations = new ArrayList<>();
        while (resultSet.next()) {
            int stationId = resultSet.getInt("station_id");
            String stationName = resultSet.getString("station_name");
            stations.add(new Station(stationId,stationName));
        }
        return stations;
    }
}
