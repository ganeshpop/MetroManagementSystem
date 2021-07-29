package com.ganesh.metro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MetroPresentation implements MetroPresentationInterface {
    MetroServiceInterface metroService = new MetroService();
    Scanner scanner = new Scanner(System.in);


//    @Override
//    public int authenticateUser() {
//        System.out.println("--------------Welcome To City Metro--------------");
//        System.out.println("\t\t1.Login  (For Existing User)");
//        System.out.println("\t\t2.SignUp (If You are New User)");
//        int choice = scanner.nextInt();
//        switch (choice) {
//            case 1:
//            System.out.print("Enter Your Metro Card Id: ");
//            String cardId = scanner.nextLine();
//            if (cardId.matches("[0-9]+")) {
//                try {
//                    int intCardId = Integer.parseInt(cardId);
//                    return metroService.isACard(intCardId)? intCardId: -1;
//                } catch (SQLException | ClassNotFoundException | IOException e) {
//                    e.printStackTrace();
//                }
//            } System.out.println();
//        }
//        return -1;
//    }

    @Override
    public void showMenu(int cardId) {
        System.out.println("--------------Welcome To City Metro--------------");
        System.out.println("\t\t1.Add Card");
        System.out.println("\t\t2.View Travel History");
        System.out.println("\t\t3.View Card Details");
        System.out.println("\t\t4.Recharge Card");
        System.out.println("\t\t5.List Stations");
        System.out.println("\t\t6.List Stations");
        System.out.println("\t\t7.List Stations");
        System.out.println("\t\t8.Swipe In");
        System.out.println("\t\t9.Swipe Out");
        System.out.println("\t\t9.Exit");

    }

    @Override
    public void performMenu(int choice) {
        switch (choice) {
            case 1: {
                try {
                    metroService.addCard(MetroPresentationHelper.createCard());
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
            case 2: {
                System.out.print("Enter Your Card Id: ");
                System.out.print("Enter Your Metro Card Id: ");
                String cardId = scanner.nextLine();
                if (cardId.matches("[0-9]+")) {
                    try {
                        for (Transaction transaction :metroService.getAllTransactions(Integer.parseInt(cardId))) {

                        }
                    } catch (SQLException | ClassNotFoundException | IOException throwables) {
                        throwables.printStackTrace();
                    }

                } else System.out.println("Only Integers are Allowed");

            }
        }
    }
}