/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client.menus;

import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;

/**
 *
 * @author rodrigo
 */
public class MenuFirstLoader implements Renderer {

    @Override
    public void render() {
        int swValue;

        boolean exit = false;
        while (!exit) {
            
            System.out.println("============================");
            System.out.println("|       MENU INICIAL       |");
            System.out.println("============================");
            System.out.println("| Opciones:                |");
            System.out.println("|        1. Login          |");
            System.out.println("|        0. Salir          |");
            System.out.println("============================");
            
            swValue = Keyin.inInt(" Seleccione una opcion: ");

            switch (swValue) {
                case 1:
            		String usuario = Keyin.inString(" Usuario: ");
            		if (!usuario.trim().isEmpty()) {
            			MainSingleton.getInstance().setUser(usuario);
            			new MenuMainScreen().render();
        				System.out.println("Llendo al menu Principal...");
        			} else {
        				System.out.println("Error: usuario no puede estar en blanco");
        			}
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    exit = true;
                    break;
                default:
                    System.out.println("Seleccion invalida");
                    break;
            }
        }
    }

}
