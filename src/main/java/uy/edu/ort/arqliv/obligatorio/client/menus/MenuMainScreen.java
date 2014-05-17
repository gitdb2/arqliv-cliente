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
import uy.edu.ort.arqliv.obligatorio.client.menus.profiling.MenuProfiling;
import uy.edu.ort.arqliv.obligatorio.client.menus.arrival.MenuArrival;
import uy.edu.ort.arqliv.obligatorio.client.menus.container.MenuContainer;
import uy.edu.ort.arqliv.obligatorio.client.menus.reports.MenuReports;
import uy.edu.ort.arqliv.obligatorio.client.menus.ship.MenuShip;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;

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
			System.out.println("|        2. Contenedores   |");
			System.out.println("|        3. Arribos        |");
			System.out.println("|        4. Reportes       |");
			System.out.println("|        5. Profiling      |");
			System.out.println("|        0. Salir          |");
			System.out.println("============================");

			swValue = Keyin.inInt(" Seleccione una opcion: ");

			switch (swValue) {
			case 0:
				System.out.println("volviendo...");
				exit = true;
				break;
			case 1:
				System.out.println("abriendo menu Barcos...");
				new MenuShip().render();
				break;
			case 2:
				System.out.println("abriendo menu Contenedores...");
				new MenuContainer().render();
				break;
			case 3:
				System.out.println("abriendo menu Arribos...");
				new MenuArrival().render();
				break;
			case 4:
				System.out.println("abriendo menu Reportes...");
				new MenuReports().render();
				break;
			case 5:
				System.out.println("abriendo menu Profiling...");
				new MenuProfiling().render();
				break;
			default:
				System.out.println("Invalid selection");
				break; // This break is not really necessary
			}
		}
	}


}
