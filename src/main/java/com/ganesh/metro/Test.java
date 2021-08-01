package com.ganesh.metro;

import com.ganesh.helper.MetroPresentationHelper;
import com.ganesh.interfaces.MetroDaoInterface;
import com.ganesh.interfaces.MetroServiceInterface;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        while(true) {
//            Scanner s = new Scanner(System.in);
//            System.out.println("ID: ");
//            int id = s.nextInt();
            MetroDao metroDao = new MetroDao();
            MetroService metroService = new MetroService();
//            System.out.println("duration " + metroDao.getTransactionDuration(id));
//            System.out.println("fine " + metroService.getFine(id));
//            System.out.println(metroService.isAStation(100));
        }
    }
}
