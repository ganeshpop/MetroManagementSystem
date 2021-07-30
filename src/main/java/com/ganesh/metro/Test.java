package com.ganesh.metro;

import java.io.IOException;
import java.sql.*;

public class Test {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        MetroDaoInterface metroDao = new MetroDao();
        //MetroPresentationHelper.displayCardDetails(metroDao.getCardDetails(1001));
        MetroPresentationHelper.displayStationNames(metroDao.getAllStations());
        //System.out.println("\ud83d\ude01");

    }
}
