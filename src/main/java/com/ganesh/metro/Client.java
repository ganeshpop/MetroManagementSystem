package com.ganesh.metro;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        MetroPresentationInterface metroPresentation = new MetroPresentation();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            metroPresentation.showMenu();
            System.out.println("Enter choice : ");
            String choice = scanner.nextLine();
            metroPresentation.performMenu(MetroPresentationHelper.isInt(choice));

        }
    }
}
