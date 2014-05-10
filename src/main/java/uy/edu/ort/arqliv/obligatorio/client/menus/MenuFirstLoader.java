/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client.menus;

import java.util.HashMap;
import java.util.Map;

import uy.edu.ort.arqliv.obligatorio.client.Keyin;

/**
 *
 * @author rodrigo
 */
public class MenuFirstLoader implements Renderer {

    Map<String, Renderer> options = new HashMap<>();

    public void addOptions(String option, Renderer renderer) {
        this.options.put(option, renderer);
    }

    @Override
    public void render() {
        int swValue;

        boolean exit = false;
        // Switch construct
        while (!exit) {
//            System.out.print("\u001b[2J");
            
            // Display menu graphics
            System.out.println("============================");
            System.out.println("|   MENU SELECTION DEMO    |");
            System.out.println("============================");
            System.out.println("| Optiones:                |");
            System.out.println("|        1. Login          |");
            System.out.println("|        2. Salir          |");
            System.out.println("============================");
            
            swValue = Keyin.inInt(" Select option: ");

            switch (swValue) {
                case 1:
                    System.out.println("Option 1 selected");
                    options.get("login").render();
                    break;
                case 2:
                    System.out.println("Saliendo");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid selection");
                    break; // This break is not really necessary
            }
        }
    }

}
