package com.ganesh.presentation;

import com.ganesh.exceptions.InsufficientBalanceException;
import com.ganesh.exceptions.InvalidStationException;
import com.ganesh.exceptions.InvalidSwipeInException;
import com.ganesh.exceptions.InvalidSwipeOutException;
import com.ganesh.pojos.Color;
import com.ganesh.service.MetroService;
import com.ganesh.service.MetroServiceInterface;
import com.ganesh.pojos.Transaction;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MetroPresentation implements MetroPresentationInterface {
    MetroServiceInterface metroService = new MetroService();
    Scanner scanner = new Scanner(System.in);


    @Override
    public int authenticateUser() {
        System.out.println(Color.ANSI_GREEN + "--------------Welcome To City Metro--------------");
        System.out.println(Color.ANSI_BLUE +"\t\t1.Login  (For Existing User)");
        System.out.println("\t\t2.SignUp (If You are New User)");
        System.out.println("\t\t3.Exit " + Color.ANSI_RESET);
        System.out.println("Enter Your Choice: ");
        String userInput = scanner.nextLine();
        int choice = MetroPresentationHelper.isInt(userInput);
        int intCardId = -1;
        if (choice > 0) {
            switch (choice) {
                case 1: {
                    System.out.println(Color.ANSI_RESET + "Enter Your Metro Card Id: ");
                    String cardId = scanner.nextLine();
                    if (cardId.matches("[0-9]+")) {
                        try {
                            intCardId = Integer.parseInt(cardId);
                            if (metroService.isACard(intCardId)) {
                                System.out.println("Enter Your Password: ");
                                String password = scanner.nextLine();
                                if (metroService.validatePassword(intCardId, password)) {
                                    System.out.println("Login Successful!" + Color.ANSI_RESET);
                                    return intCardId;
                                } else {
                                    System.out.println(Color.ANSI_RED + "Invalid Password, Try Again" + Color.ANSI_RESET);
                                    return -1;
                                }
                            } else {
                                System.out.println(Color.ANSI_RED + "Invalid Card " + Color.ANSI_RESET);
                                return -1;
                            }
                        } catch (SQLException | ClassNotFoundException | IOException e) {
                            e.printStackTrace();
                        }
                    } else System.out.println(Color.ANSI_RED + "Card ID Only Contains Integers" + Color.ANSI_RESET);
                    break;
                }
                case 2: {
                    try {
                        intCardId = metroService.addCard(MetroPresentationHelper.createCard());
                        if (intCardId > 0) {
                            System.out.println(Color.ANSI_GREEN + "Card Created Successfully, Your Card ID is " + intCardId + Color.ANSI_RESET);
                            System.out.println(Color.ANSI_RESET + "Create a Password for Your New Card");
                            while (true) {
                                System.out.println("Enter a Password: ");
                                String passwordOne = scanner.nextLine();
                                System.out.println("Conform Your Password: ");
                                String passwordTwo = scanner.nextLine();
                                if (passwordOne.equals(passwordTwo)) {
                                    if (metroService.setPassword(intCardId, passwordOne)) {
                                        System.out.println("Password Set Successfully" + Color.ANSI_RESET);
                                        break;
                                    } else
                                        System.out.println(Color.ANSI_RED + "Passwords Didnt Match, Try Again" + Color.ANSI_RESET);
                                } else
                                    System.out.println(Color.ANSI_RED + "Setting Password Failed" + Color.ANSI_RESET);
                            }
                        }

                    } catch (SQLException | ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 3: {
                    System.out.println(Color.ANSI_PURPLE + "Thanks for using City Metro Services!");
                    System.exit(0);
                }
                default:
                    System.out.println("Invalid Choice");
            }

        }
        return intCardId;
    }

    @Override
    public void showMenu(int cardId) {
        System.out.println(Color.ANSI_RESET + Color.ANSI_GREEN + "-------------Welcome User " + cardId + "-------------" + Color.ANSI_RESET);
        System.out.println(Color.ANSI_BLUE + "\t\t1.View Travel History");
        System.out.println("\t\t2.View Card Details");
        System.out.println("\t\t3.Recharge Card");
        System.out.println("\t\t4.List Stations");
        System.out.println("\t\t5.Swipe In");
        System.out.println("\t\t6.Swipe Out");
        System.out.println("\t\t7.Change Password");
        System.out.println("\t\t8.Log Out");
        System.out.println("\t\t9.Exit" + Color.ANSI_RESET);

    }

    @Override
    public int performMenu(int choice, int cardId) {
        switch (choice) {

            case 1: {
                try {
                    MetroPresentationHelper.displayTransactions(metroService.getAllTransactions(cardId), false);
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    MetroPresentationHelper.displayCardDetails(metroService.getCardDetails(cardId));
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                try {
                    System.out.print(Color.ANSI_GREEN + "Enter the amount: ");
                    String amount = scanner.nextLine();
                    if(MetroPresentationHelper.isInt(amount) > 0) {
                        if (metroService.rechargeCard(cardId, Integer.parseInt(amount))) {
                            System.out.println("Recharged of amount " + amount + " Successful " + "Current Balance is " + metroService.getBalance(cardId) + Color.ANSI_RESET);
                        } else System.out.println(Color.ANSI_RED + "Recharge Failed" + Color.ANSI_RESET);
                    } else System.out.println(Color.ANSI_RED + "Only Positive Integers Allowed" + Color.ANSI_RESET);
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4: {
                try {
                    MetroPresentationHelper.displayStations(metroService.getAllStations());
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5: {
                try {
                    int balance = metroService.getBalance(cardId);
                    if (balance < 20) {
                        System.out.println(Color.ANSI_BLUE + "Your Current Balance is: " + balance);
                        throw new InsufficientBalanceException();
                    }
                    System.out.print(Color.ANSI_BLUE + "\nSelect Stations to Swipe In : ");
                    MetroPresentationHelper.displayStationNames(metroService.getAllStations());
                    System.out.println("Enter Station ID: ");
                    String stationId = scanner.nextLine();
                    if (stationId.matches("[0-9]+")) {
                        System.out.println(Color.ANSI_GREEN + "Swipe In at Station " + Color.ANSI_BLUE + " [ " + Color.ANSI_RESET  + stationId +  Color.ANSI_BLUE + " : " + Color.ANSI_RESET  + metroService.swipeIn(cardId, Integer.parseInt(stationId)) + Color.ANSI_BLUE +  " ] " + Color.ANSI_GREEN + " Successful!" + Color.ANSI_RESET);
                    } else System.out.println(Color.ANSI_RED + "Only Integers Allowed" + Color.ANSI_RESET);

                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                } catch (InvalidStationException | InvalidSwipeInException | InsufficientBalanceException customException) {
                    System.out.println(customException.getMessage());
                }
                break;
            }
            case 6: {
                System.out.println(Color.ANSI_BLUE + "Select Stations to Swipe Out");
                try {
                    MetroPresentationHelper.displayStationNames(metroService.getAllStations());
                    String stationId = scanner.nextLine();
                    if (stationId.matches("[0-9]+")) {
                        ArrayList<Transaction> transactions = new ArrayList<>();
                        transactions.add(metroService.swipeOut(cardId, Integer.parseInt(stationId)));
                        MetroPresentationHelper.displayTransactions(transactions, true);
                        int balance = metroService.getBalance(cardId);
                        if (balance > 0) System.out.println("Your Current Balance is: " + balance + Color.ANSI_RESET);

                    }
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                } catch (InvalidStationException | InvalidSwipeInException | InsufficientBalanceException | InvalidSwipeOutException customException) {
                    System.out.println(customException.getMessage());

                }
                break;
            }
            case 7: {
                int attempts = 3;
                while (attempts > 0)
                    try {
                        System.out.print(Color.ANSI_RESET + "Enter Your Current Password:");
                        String password = scanner.nextLine();
                        if (metroService.validatePassword(cardId, password)) {
                            System.out.println("Enter New Password: ");
                            String passwordOne = scanner.nextLine();
                            System.out.println("Conform Your New Password: ");
                            String passwordTwo = scanner.nextLine();
                            if (passwordOne.equals(passwordTwo)) {
                                if (metroService.setPassword(cardId, passwordOne))
                                    System.out.println(Color.ANSI_GREEN + "Password Updated Successfully" + Color.ANSI_RESET);
                                break;
                            } else System.out.println(Color.ANSI_RED + "Passwords Didn't Match, Try Again" +  Color.ANSI_RESET);
                        } else {
                            System.out.println(Color.ANSI_RED + "You Have Entered a Wrong Password, Attempts left " + attempts + Color.ANSI_RESET);
                            attempts--;
                        }
                    } catch (SQLException | IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
            }
            case 8: {
                cardId = -1;
                break;
            }
            case 9: {
                System.out.println(Color.ANSI_PURPLE + "Thanks for using City Metro Services!");
                System.exit(0);
            }

            default:
                System.out.println(Color.ANSI_RED + "Invalid Option, Try Again" + Color.ANSI_RESET);
        }
        return cardId;
    }
}