package com.ganesh.metro;

import com.ganesh.exceptions.InsufficientBalanceException;
import com.ganesh.exceptions.InvalidStationException;
import com.ganesh.exceptions.InvalidSwipeInException;
import com.ganesh.exceptions.InvalidSwipeOutException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public void showMenu() {
        System.out.println("-------------Welcome To City Metro-------------");
        System.out.println("\t\t1.Add Card");
        System.out.println("\t\t2.View Travel History");
        System.out.println("\t\t3.View Card Details");
        System.out.println("\t\t4.Recharge Card");
        System.out.println("\t\t5.List Stations");
        System.out.println("\t\t6.Swipe In");
        System.out.println("\t\t7.Swipe Out");
        System.out.println("\t\t8.Exit");

    }

    @Override
    public void performMenu(int choice) {
        switch (choice) {
            case 1: {
                try {
                    int cardId = metroService.addCard(MetroPresentationHelper.createCard());
                    if (cardId > 0) System.out.println("Card Created Successfully, Your Card ID is " + cardId);
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                System.out.print("Enter Your Metro Card Id: ");
                String cardId = scanner.nextLine();
                if (cardId.matches("[0-9]+")) {
                    try {
                        MetroPresentationHelper.displayTransactions(metroService.getAllTransactions(Integer.parseInt(cardId)),false);
                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                } else System.out.println("Only Integers are Allowed");
                break;
            }
            case 3: {
                System.out.print("Enter Your Metro Card Id: ");
                String cardId = scanner.nextLine();
                if (cardId.matches("[0-9]+")) {
                    try {
                        MetroPresentationHelper.displayCardDetails(metroService.getCardDetails(Integer.parseInt(cardId)));
                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                } else System.out.println("Only Integers are Allowed");
                break;
            }
            case 4: {
                System.out.print("Enter Your Metro Card Id: ");
                String cardId = scanner.nextLine();
                if (cardId.matches("[0-9]+")) {
                    try {
                        System.out.print("Enter the amount: ");
                        String amount = scanner.nextLine();
                        if (cardId.matches("[0-9]+")) {
                            if (metroService.rechargeCard(Integer.parseInt(cardId), Integer.parseInt(amount))) {
                                System.out.println("Recharged of amount " + amount + " Successful");
                            } else System.out.println("Recharge Failed");
                        } else System.out.println("Only Integers are Allowed");
                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                } else System.out.println("Only Integers are Allowed");
                break;
            }
            case 5: {
                try {
                    MetroPresentationHelper.displayStations(metroService.getAllStations());
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 6: {
                System.out.print("Enter Your Metro Card Id: ");
                String cardId = scanner.nextLine();
                try {
                if (cardId.matches("[0-9]+")) {
                    System.out.print("Your Current Balance is: " + metroService.getBalance(Integer.parseInt(cardId)));
                    System.out.print("Select Stations to Swipe In : ");

                        MetroPresentationHelper.displayStationNames(metroService.getAllStations());
                        String stationId = scanner.nextLine();
                        if (stationId.matches("[0-9]+")) {
                            System.out.println("Swipe In at Station [ "+ stationId + " : " + metroService.swipeIn(Integer.parseInt(cardId), Integer.parseInt(stationId)) + " ] Successful!");
                        }
                    }
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                } catch (InvalidStationException | InvalidSwipeInException | InsufficientBalanceException customException) {
                    customException.getMessage();
                }
                break;
            }
            case 7: {
                System.out.print("Enter Your Metro Card Id: ");
                String cardId = scanner.nextLine();
                if (cardId.matches("[0-9]+")) {
                    System.out.println("Select Stations to Swipe Out");
                    try {
                        MetroPresentationHelper.displayStationNames(metroService.getAllStations());
                        String stationId = scanner.nextLine();
                        if (stationId.matches("[0-9]+")) {
                            ArrayList<Transaction> transactions = new ArrayList<>();
                            transactions.add( metroService.swipeOut(Integer.parseInt(cardId), Integer.parseInt(stationId)));
                            MetroPresentationHelper.displayTransactions(transactions, true);
                        }
                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    } catch (InvalidStationException | InvalidSwipeInException | InsufficientBalanceException | InvalidSwipeOutException customException) {
                        System.out.println(customException.getMessage());

                    }
                } break;
            }
            case 8: {
                System.out.println("Thanks for using City Metro Services!");
                System.exit(0);
            }
            default:
                System.out.println("Invalid Option, Try Again");
        }
    }
}