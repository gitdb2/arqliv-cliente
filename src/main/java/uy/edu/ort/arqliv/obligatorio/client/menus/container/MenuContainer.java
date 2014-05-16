package uy.edu.ort.arqliv.obligatorio.client.menus.container;

import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;

public class MenuContainer implements Renderer{

	@Override
	public void render() {
		int swValue;

		boolean exit = false;
		while (!exit) {

			System.out.println("============================");
			System.out.println("|     MENU CONTENEDORES    |");
			System.out.println("============================");
			System.out.println("| Optiones:                |");
			System.out.println("|        1. Alta           |");
			System.out.println("|        2. Baja           |");
			System.out.println("|        3. Modificar      |");
			System.out.println("|        4. Listar         |");
			System.out.println("|        5. Listar (pdf)   |");
			System.out.println("|        0. Salir          |");
			System.out.println("============================");

			swValue = Keyin.inInt(" Seleccione una opcion: ");

			switch (swValue) {
			case 0:
				System.out.println("volviendo...");
				exit = true;
				break;
			case 1:
				System.out.println("abriendo menu Alta...");
				new MenuContainerCreate().render();

				break;
			case 2:
				System.out.println("abriendo menu Baja...");
				new MenuContainerDelete().render();
				
				break;
			case 3:
				System.out.println("abriendo menu Modificar...");
				new MenuContainerUpdate().render();
				
			
				break;
			case 4:
				System.out
				.println("Listando...");
				new MenuContainerList(true).render();
				break;
			case 5:
				System.out
				.println("Generando PDF...");
				new MenuContainerList(false).render();
				break;

			
			default:
				System.out.println("Valor invalido");
				break; // This break is not really necessary
			}
		}
	}

}
