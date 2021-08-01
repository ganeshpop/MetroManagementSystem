package com.ganesh.helper;

import com.ganesh.pojos.Card;
import com.ganesh.pojos.Station;
import com.ganesh.pojos.Transaction;

import java.util.Collection;
import java.util.Scanner;

public class MetroPresentationHelper {
    static Scanner scanner = new Scanner(System.in);

    public static Card createCard(){
        String name;
        while (true) {
            System.out.println("Enter Your Name: ");
            name = scanner.nextLine();
            if(name.length() > 0) break;
            else System.out.println("Invalid Name");
        }
        System.out.println("Welcome! " + name + " \ud83d\ude01");
        while (true) {
            System.out.println("Enter initial Balance: ");
            String initialBalance = scanner.nextLine();
            if (isInt(initialBalance) >= 100) return new Card("Basic", Integer.parseInt(initialBalance));
            else System.out.println("Minimum Card Balance for New Users is 100/- ");
        }
    }

    public static void displayTransactions(Collection<Transaction> transactions, boolean isSwipeOut){
            if(isSwipeOut){
                System.out.println(("--------------- Trip Completed ---------------"));
            }
            if (transactions.size() == 0) {
                System.out.println("No Transactions Retrieved");
            } else {
                for (Transaction transaction : transactions) {
                    if (transaction.getTransactionId() == 0) {
                        System.out.println("No Travel History Found");
                        break;
                    }
                    StringBuilder transactionString = new StringBuilder();
                    transactionString.append("-------------- Trip " + transaction.getTransactionId() + " Details --------------")
                                    .append("\nCard ID ").append("\ud83d\udcb3: ")
                                    .append(transaction.getCardId())
                                    .append("\nSwipe In Time ").append("\u23f0: ")
                                    .append(transaction.getSwipeInTimeStamp().toString())
                                    .append("\n(Source)\n\t ")
                                    .append(transaction.getSourceStation());
                    if(transaction.getDestinationStation() != null) {
                                transactionString.append("\n(Destination)\n\t ")
                                    .append(transaction.getDestinationStation())
                                    .append("\nSwipe Out Time ").append("\u23f0: ")
                                    .append(transaction.getSwipeOutTimeStamp().toString())
                                    .append("\nTravel Fare ").append("\ud83d\udcb0: ")
                                    .append(transaction.getFare() - transaction.getFine())
                                    .append("\nFine ").append("\ud83d\udcb0: ")
                                    .append(transaction.getFine())
                                    .append("\nTotal Fare ").append("\ud83d\udcb0: ")
                                    .append(transaction.getFare())
                                    .append("\nTravel Duration ").append("\u23f0: ")
                                    .append(transaction.getDuration())
                                    .append(" Minutes [Free Travel Time 90 Minutes ]");
                                    if(transaction.getFine() > 0 && transaction.getDuration() > 90) {
                                        transactionString.append("\nYou Have Been Charged a Fine of ").append(transaction.getFine())
                                                .append("/- as You Have Spent an Excess of ").append((transaction.getDuration() - 90))
                                                .append(" Minutes ").append("\ud83d\ude22");
                                    } else {
                                        transactionString.append("\nYou Have Completed Your Travel Within 90 Minutes, Fine Not Applicable ").append("\ud83d\ude00");
                                    }
                    } else {
                        transactionString.append("\n(Destination)\n\t ")
                                .append("[Trip is Not Completed]")
                                .append("\nFare, Fine, Duration & Swipe Out Time Not Available").append("\ud83d\ude34");

                    }
                    System.out.println(transactionString);
                }
            }
        System.out.println("-----------------------------------------------");
        }

    public static void displayCardDetails(Card card) {

        if (card == null) {
            System.out.println("No Cards Retrieved");
        } else {
            StringBuilder cardString = new StringBuilder();
            cardString.append("-------------- Card ").append(card.getCardId()).append(" Details --------------")
                    .append("\nCard ID: ")
                    .append(card.getCardId())
                    .append("\nCard Type: ")
                    .append(card.getCardType())
                    .append("\nCard Balance ")
                    .append(card.getBalance())
                    .append("\nActive Since: ")
                    .append(card.getActiveSince().toString());
            System.out.println(cardString);
            System.out.println("-----------------------------------------------");
        }
    }
    public static void displayStations(Collection<Station> stations){

        if (stations.size() == 0) {
            System.out.println("No Stations Retrieved");
        } else {
            for (Station station: stations) {

            StringBuilder stationString = new StringBuilder();
            stationString.append("-------------- Station ").append(station.getStationId()).append(" Details --------------")
                    .append("\nStation ID: ")
                    .append(station.getStationId())
                    .append("\nStation Name: ")
                    .append(station.getStationName());
            System.out.println(stationString);
            }
        }

        System.out.println("-----------------------------------------------");
    }
    public static void displayStationNames(Collection<Station> stations){
        StringBuilder stationString = new StringBuilder();
        if (stations.size() == 0) {
            System.out.println("No Stations Retrieved");
        } else {
            stationString.append("\n [ Station ID : Station Name ]\n");
            for (Station station: stations) {
                stationString.append("\t    [ ").append(station.getStationId()).append(" : ").append(station.getStationName()).append( " ]  \n")  ;
            }
        }
        System.out.print(stationString);
    }
    public static int isInt(String input){
        if(input.matches("[0-9]+")){
            return Integer.parseInt(input);
        }
        return -1;
    }
}

