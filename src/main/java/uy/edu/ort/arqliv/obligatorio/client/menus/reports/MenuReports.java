package uy.edu.ort.arqliv.obligatorio.client.menus.reports;

import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.menus.profiling.MenuProfilingMax;

public class MenuReports implements Renderer {
	
	public MenuReports() {
		super();
	}

	@Override
	public void render() {
		int swValue;

		boolean exit = false;
		while (!exit) {

			System.out.println("=====================================");
			System.out.println("|           MENU  REPORTES          |");
			System.out.println("=====================================");
			System.out.println("| Optiones:                         |");
			System.out.println("|        1. Arribos por Mes         |");
			System.out.println("|        2. Arribos por Mes y Barco |");
			System.out.println("|        0. Salir                   |");
			System.out.println("=====================================");

			swValue = Keyin.inInt(" Seleccione una opcion: ");

			switch (swValue) {
			case 0:
				System.out.println("volviendo...");
				exit = true;
				break;
			case 1:
				new MenuReportArrivalsByMonth(false).render();
				exit = true;
				break;
			case 2:
				new MenuReportArrivalsByMonthByShip(false).render();
				exit = true;
				break;
			default:
				System.out.println("Valor invalido");
				break;
			}
		}
	}
	
}
