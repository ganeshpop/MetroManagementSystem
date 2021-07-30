package com.ganesh.metro;

import com.ganesh.helper.MetroPresentationHelper;
import com.ganesh.interfaces.MetroDaoInterface;

import java.io.IOException;
import java.sql.*;

public class Test {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        MetroDaoInterface metroDao = new MetroDao();
        System.out.println();
        //MetroPresentationHelper.displayCardDetails(metroDao.getCardDetails(1001));
        MetroPresentationHelper.displayStationNames(metroDao.getAllStations());
        //System.out.println("\ud83d\ude01");

    }
}
