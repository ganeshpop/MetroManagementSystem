package com.ganesh.metro;

import java.io.IOException;
import java.sql.*;

public class Test {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

        MetroDaoInterface metroDao = new MetroDao();
        MetroPresentationHelper.displayTransactions(metroDao.getLastTransaction(1001));
        //System.out.println("\ud83d\ude01");

    }
}
