package com.ganesh.metro;

import java.util.Collection;
import java.util.Scanner;

public class MetroPresentationHelper {
    static Scanner scanner = new Scanner(System.in);

    public static Card createCard(){
        String name;
        while (true) {
            System.out.println("Enter Student Name: ");
            name = scanner.nextLine();
            if(name.length() > 0) break;
            else System.out.println("Invalid Name");
        }
        System.out.println("Welcome! " + name + " \ud83d\ude01");
        while (true) {
            System.out.println("Enter initial Balance: ");
            int initialBalance = scanner.nextInt();
            if(initialBalance >= 100) return new Card("Basic", initialBalance);
            else System.out.println("Minimum Card Balance for New Users is 100/- ");
        }
    }

    public static void displayTransactions(Collection<Transaction> transactions){

            if (transactions.size() == 0) {
                System.out.println("No Transactions Retrieved");
            } else {
                for (Transaction transaction : transactions) {
                    StringBuilder transactionString = new StringBuilder();
                    transactionString.append("-------------- Trip " + transaction.getTransactionId() + " Details --------------")
                                    .append("\nCard ID: ")
                                    .append(transaction.getCardId())
                                    .append("\nSwipe In Time: ")
                                    .append(transaction.getSwipeInTimeStamp().toString())
                                    .append("\n(Source)\n\t ")
                                    .append(transaction.getSourceStation());
                    if(transaction.getDestinationStation() != null) {
                                transactionString.append("\n(Destination)\n\t ")
                                    .append(transaction.getDestinationStation())
                                    .append("\nSwipe Out Time: ")
                                    .append(transaction.getSwipeOutTimeStamp().toString())
                                    .append("\nFare").append("\ud83d\udcb0: ")
                                    .append(transaction.getFare());
                    } else {
                        transactionString.append("\n(Destination)\n\t ")
                                .append("[Trip is Not Completed]")
                                .append("\nFare & Swipe Out Time Not Available");

                    }


                    System.out.println(transactionString);
                }
            }
        System.out.println("-----------------------------------------------");
        }
}

