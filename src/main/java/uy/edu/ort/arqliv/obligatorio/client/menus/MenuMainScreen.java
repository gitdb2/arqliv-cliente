/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client.menus;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.ship.MenuShip;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;

/**
 * 
 * @author rodrigo
 */
public class MenuMainScreen implements Renderer {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	Map<String, Renderer> options = new HashMap<>();

	public void addOptions(String option, Renderer renderer) {
		this.options.put(option, renderer);
	}

	@Override
	public void render() {
		int swValue;

		boolean exit = false;
		while (!exit) {

			System.out.println("============================");
			System.out.println("|   MENU  PRINCIPAL        |");
			System.out.println("============================");
			System.out.println("| Optiones:                |");
			System.out.println("|        1. Barcos         |");
			System.out.println("|        2. Containers     |");
			System.out.println("|        3. Arribos        |");
			System.out.println("|        4. Reportes       |");
			System.out.println("|        0. Salir          |");
			System.out.println("============================");

			swValue = Keyin.inInt(" Seleccione una opcion: ");

			switch (swValue) {
			case 0:
				System.out.println("volviendo...");
				exit = true;
				break;
			case 1:
				System.out.println("abriendo menu Ship...");
				new MenuShip().render();

				break;
			case 2:
				System.out
				.println("Error: Containers No implememtado aún");
				break;
			case 3:
				System.out
				.println("Error: Arribos No implememtado aún");
				break;
			case 4:
				System.out
				.println("Error: Reportes No implememtado aún");
				break;


			
			default:
				System.out.println("Invalid selection");
				break; // This break is not really necessary
			}
		}
	}

	public void renderss() {

		// System.out.print("\u001b[2J");
		// Display menu graphics
		System.out.println("============================");
		System.out.println("|       Identificacion     |");
		System.out.println("============================");
		// System.out.println("| Optiones:                |");
		// System.out.println("|        1. Login          |");
		// System.out.println("|        2. Salir          |");
		// System.out.println("============================");
		// swValue = Keyin.inInt(" Select option: ");

		// String usuario = Keyin.inString(" Usuario: ");
		// String password = Keyin.inString(" Password: ");

		// if ("1".equals(usuario) && "1".equals(password)) {
		//
		// System.out.println("Llendo al menu Principal");
		// } else {
		// System.out.println("Error: usuario o contrasena incorrecta");
		// }

		try {

			ShipServiceClient client = (ShipServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientesConstants.ShipClient);

			client.createShip(null);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor");
		}

		Keyin.inChar("presione enter tecla para continuar...");

	}

}
