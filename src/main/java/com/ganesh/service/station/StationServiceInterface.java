package com.ganesh.service.station;

import com.ganesh.pojos.Station;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public interface StationServiceInterface {
    Collection<Station> getAllStations() throws SQLException, ClassNotFoundException, IOException;
}
