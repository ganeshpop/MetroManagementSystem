package com.ganesh.persistence.station;

import com.ganesh.pojos.Station;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface StationDaoInterface {
    Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException;
    boolean isAStation(int stationId) throws SQLException, ClassNotFoundException, IOException;
    String getStation(int stationId) throws SQLException, ClassNotFoundException, IOException;
}
