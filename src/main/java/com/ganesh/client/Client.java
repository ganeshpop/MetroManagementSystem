package com.ganesh.client;

import com.ganesh.pojos.Color;
import com.ganesh.presentation.MetroPresentationHelper;
import com.ganesh.presentation.MetroPresentationInterface;
import com.ganesh.presentation.MetroPresentation;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        MetroPresentationInterface metroPresentation = new MetroPresentation();
        Scanner scanner = new Scanner(System.in);
        while(true){
                int cardId = metroPresentation.authenticateUser();
                if(cardId < 0) continue;
            while (true) {
                metroPresentation.showMenu(cardId);
                System.out.println(Color.ANSI_RESET + "Enter choice : ");
                String choice = scanner.nextLine();
                if(metroPresentation.performMenu(MetroPresentationHelper.isInt(choice), cardId) < 0)
                    break;

            }
        }
    }
}
