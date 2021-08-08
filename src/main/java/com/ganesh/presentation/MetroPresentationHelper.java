package com.ganesh.presentation;

import com.ganesh.pojos.Color;
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
            else System.out.println(Color.ANSI_RED +"Invalid Name" + Color.ANSI_RESET);
        }
        System.out.println("Welcome! " + name + " \ud83d\ude01");
        while (true) {
            System.out.println("Enter initial Balance: ");
            String initialBalance = scanner.nextLine();
            if (isInt(initialBalance) >= 100) return new Card("Basic", Integer.parseInt(initialBalance));
            else System.out.println(Color.ANSI_RED + "Minimum Card Balance for New Users is 100/- " + Color.ANSI_RESET);
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
                    transactionString.append(Color.ANSI_MAGENTA + "-------------- Trip " + transaction.getTransactionId() + " Details --------------")
                                    .append(Color.ANSI_BLUE + "\nCard ID ").append("\ud83d\udcb3: " + Color.ANSI_RESET )
                                    .append(transaction.getCardId())
                                    .append(Color.ANSI_BLUE + "\nSwipe In Time ").append("\u23f0: " + Color.ANSI_RESET)
                                    .append(transaction.getSwipeInTimeStamp().toString())
                                    .append(Color.ANSI_BLUE + "\n(Source)\n\t " + Color.ANSI_RESET)
                                    .append(transaction.getSourceStation());
                    if(transaction.getDestinationStation() != null) {
                                transactionString.append(Color.ANSI_BLUE + "\n(Destination)\n\t "+ Color.ANSI_RESET)
                                    .append(transaction.getDestinationStation())
                                    .append(Color.ANSI_BLUE + "\nSwipe Out Time ").append("\u23f0: "+ Color.ANSI_RESET)
                                    .append(transaction.getSwipeOutTimeStamp().toString())
                                    .append(Color.ANSI_BLUE + "\nTravel Fare ").append("\ud83d\udcb0: "+ Color.ANSI_RESET)
                                    .append(transaction.getFare() - transaction.getFine())
                                    .append(Color.ANSI_BLUE + "\nFine ").append("\ud83d\udcb0: "+ Color.ANSI_RESET)
                                    .append(transaction.getFine())
                                    .append(Color.ANSI_BLUE + "\nTotal Fare ").append("\ud83d\udcb0: "+ Color.ANSI_RESET)
                                    .append(transaction.getFare())
                                    .append(Color.ANSI_BLUE + "\nTravel Duration ").append("\u23f0: "+ Color.ANSI_RESET)
                                    .append(transaction.getDuration())
                                    .append(Color.ANSI_BLUE + " Minutes [Free Travel Time 90 Minutes ]"+ Color.ANSI_RESET);
                                    if(transaction.getFine() > 0 && transaction.getDuration() > 90) {
                                        transactionString.append(Color.ANSI_RED + "\nYou Have Been Charged a Fine of ").append(transaction.getFine())
                                                .append("/- as You Have Spent an Excess of ").append((transaction.getDuration() - 90))
                                                .append(" Minutes ").append("\ud83d\ude22"+ Color.ANSI_RESET);
                                    } else {
                                        transactionString.append(Color.ANSI_GREEN  +"\nYou Have Completed Your Travel Within 90 Minutes, Fine Not Applicable ").append("\ud83d\ude00"+ Color.ANSI_RESET);
                                    }
                    } else {
                        transactionString.append(Color.ANSI_BLUE +"\n(Destination)\n\t "+ Color.ANSI_RESET)
                                .append(Color.ANSI_BLUE +"[Trip is Not Completed]")
                                .append(Color.ANSI_BLUE +"\nFare, Fine, Duration & Swipe Out Time Not Available").append("\ud83d\ude34"+ Color.ANSI_RESET);

                    }
                    System.out.println(transactionString);
                }
            }
        System.out.println(Color.ANSI_GREEN + "-----------------------------------------------" + Color.ANSI_RESET);
        }

    public static void displayCardDetails(Card card) {

        if (card == null) {
            System.out.println("No Cards Retrieved");
        } else {
            StringBuilder cardString = new StringBuilder();
            cardString.append(Color.ANSI_GREEN + "-------------- Card ").append(card.getCardId()).append(" Details --------------" + Color.ANSI_RESET )
                    .append( Color.ANSI_BLUE + "\nCard ID: " +  Color.ANSI_RESET)
                    .append(card.getCardId())
                    .append( Color.ANSI_BLUE + "\nCard Type: " +  Color.ANSI_RESET)
                    .append(card.getCardType())
                    .append( Color.ANSI_BLUE + "\nCard Balance " +  Color.ANSI_RESET)
                    .append(card.getBalance())
                    .append( Color.ANSI_BLUE + "\nActive Since: " + Color.ANSI_RESET)
                    .append(card.getActiveSince().toString());
            System.out.println(cardString);
            System.out.println("-----------------------------------------------");
        }
    }
    public static void displayStations(Collection<Station> stations){

        if (stations.size() == 0) {
            System.out.println( Color.ANSI_RED + "No Stations Retrieved" + Color.ANSI_RESET );
        } else {
            for (Station station: stations) {

            StringBuilder stationString = new StringBuilder();
            stationString.append( Color.ANSI_GREEN + "-------------- Station ").append(station.getStationId()).append(" Details --------------" +  Color.ANSI_RESET )
                    .append(Color.ANSI_BLUE + "\nStation ID: " + Color.ANSI_RESET)
                    .append(station.getStationId())
                    .append(Color.ANSI_BLUE + "\nStation Name: " + Color.ANSI_RESET)
                    .append(station.getStationName());
            System.out.println(stationString);
            }
        }

        System.out.println(Color.ANSI_GREEN + "-----------------------------------------------" + Color.ANSI_RESET);
    }
    public static void displayStationNames(Collection<Station> stations){
        StringBuilder stationString = new StringBuilder();
        if (stations.size() == 0) {
            System.out.println(Color.ANSI_RED +"No Stations Retrieved" + Color.ANSI_RESET);
        } else {
            stationString.append(Color.ANSI_BLUE + "\n [ Station ID : Station Name ]\n" + Color.ANSI_RESET);
            for (Station station: stations) {
                stationString.append(Color.ANSI_BLUE + "\t    [ " + Color.ANSI_RESET).append(station.getStationId()).append(Color.ANSI_BLUE + " : " + Color.ANSI_RESET).append(station.getStationName()).append(Color.ANSI_BLUE + " ]  \n" + Color.ANSI_RESET)  ;
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

