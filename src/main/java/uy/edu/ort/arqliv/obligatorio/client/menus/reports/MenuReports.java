package uy.edu.ort.arqliv.obligatorio.client.menus.reports;

import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;

public class MenuReports implements Renderer {
	
	public MenuReports() {
		super();
	}

	@Override
	public void render() {
		int swValue;

		boolean exit = false;
		while (!exit) {

			System.out.println("============================================");
			System.out.println("|              MENU  REPORTES              |");
			System.out.println("============================================");
			System.out.println("| Optiones:                                |");
			System.out.println("|        1. Arribos por Mes                |");
			System.out.println("|        2. Arribos por Mes y Barco        |");
			System.out.println("|        3. Arribos por Mes (pdf)          |");
			System.out.println("|        4. Arribos por Mes y Barco (pdf)  |");
			System.out.println("|        5. Partidas por Mes               |");
			System.out.println("|        6. Partidas por Mes y Barco       |");
			System.out.println("|        7. Partidas por Mes (pdf)         |");
			System.out.println("|        8. Partidas por Mes y Barco (pdf) |");
			System.out.println("|        0. Salir                          |");
			System.out.println("===========================================");

			swValue = Keyin.inInt(" Seleccione una opcion: ");

			switch (swValue) {
			case 0:
				System.out.println("volviendo...");
				exit = true;
				break;
			case 1:
				new MenuReportArrivalsByMonth(true).render();
				break;
			case 2:
				new MenuReportArrivalsByMonthByShip(true).render();
				break;
			case 3:
				new MenuReportArrivalsByMonth(false).render();
				break;
			case 4:
				new MenuReportArrivalsByMonthByShip(false).render();
				break;
			case 5:
				new MenuReportDeparturesByMonth(true).render();
				break;
			case 6:
				new MenuReportDeparturesByMonthByShip(true).render();
				break;
			case 7:
				new MenuReportDeparturesByMonth(false).render();
				break;
			case 8:
				new MenuReportDeparturesByMonthByShip(false).render();
				break;
			default:
				System.out.println("Valor invalido");
				break;
			}
		}
	}
	
}
