package com.ganesh.helper;

import com.ganesh.pojos.Station;
import com.ganesh.pojos.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MetroDaoHelper {

    public static ArrayList<Station> generateStations(ResultSet resultSet) throws SQLException {
        ArrayList<Station> stations = new ArrayList<>();
        while (resultSet.next()) {
            int stationId = resultSet.getInt("station_id");
            String stationName = resultSet.getString("station_name");
            stations.add(new Station(stationId,stationName));
        }
        return stations;
    }
    public static ArrayList<Transaction> generateTransactions(ResultSet resultSet) throws SQLException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            int transactionId = resultSet.getInt("transaction_id");
            int cardId = resultSet.getInt("card_id");
            Station sourceStation = (resultSet.getString("source_station_id") != null) ? new Station(resultSet.getInt("source_station_id"), resultSet.getString("source_station_name")) : null;
            Station destinationStation = (resultSet.getString("destination_station_id") != null) ? new Station(resultSet.getInt("destination_station_id"), resultSet.getString("destination_station_name")) : null;
            int fare = resultSet.getInt("fare");
            Timestamp swipeInTimeStamp = resultSet.getTimestamp("swipe_in_time_stamp");
            Timestamp swipeOutTimeStamp = resultSet.getTimestamp("swipe_out_time_stamp");
            transactions.add(new Transaction(cardId, transactionId, sourceStation, destinationStation, fare, swipeInTimeStamp, swipeOutTimeStamp));
        }
        if(transactions.size() == 0) {
            transactions.add(new Transaction());
        }
        return transactions;
    }

}
