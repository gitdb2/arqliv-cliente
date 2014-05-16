package uy.edu.ort.arqliv.obligatorio.client.menus.profiling;

import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;

public class MenuProfiling implements Renderer{

	@Override
	public void render() {
		int swValue;

		boolean exit = false;
		while (!exit) {

			System.out.println("============================");
			System.out.println("|      MENU  PROFILING     |");
			System.out.println("============================");
			System.out.println("| Optiones:                |");
			System.out.println("|        1. Promedio       |");
			System.out.println("|        2. Minimo         |");
			System.out.println("|        3. Maximo         |");
			System.out.println("|        4. Promedio (pdf) |");
			System.out.println("|        5. Minimo (pdf)   |");
			System.out.println("|        6. Maximo (pdf)   |");
			System.out.println("|        0. Salir          |");
			System.out.println("============================");

			swValue = Keyin.inInt(" Seleccione una opcion: ");

			switch (swValue) {
			case 0:
				System.out.println("volviendo...");
				exit = true;
				break;
			case 1:
				System.out.println("abriendo menu Promedio...");
				new MenuProfilingPromedio().render();
				break;
			case 2:
				System.out.println("abriendo menu Minimo...");
				new MenuProfilingMinimo().render();
				break;
			case 3:
				System.out.println("abriendo menu Maximo...");
				new MenuProfilingMaximo().render();
				break;
			case 4:
				//TODO
				System.out.println("Generando PDF Promedio...");
				break;
			case 5:
				//TODO
				System.out.println("Generando PDF Minimo...");
				break;
			case 6:
				//TODO
				System.out.println("Generando PDF Maximo...");
				break;
			default:
				System.out.println("Valor invalido");
				break; // This break is not really necessary
			}
		}
	}

}
