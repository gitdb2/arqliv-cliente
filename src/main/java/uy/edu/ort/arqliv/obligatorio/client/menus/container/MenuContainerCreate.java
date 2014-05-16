package uy.edu.ort.arqliv.obligatorio.client.menus.container;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ContainerServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

public class MenuContainerCreate implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {

		System.out.println("============= Alta Contenerdor ==================");
		
		int code 		= Keyin.inInt   ("Codigo (int): ");
		String brand 	= Keyin.inString("Marca (texto): ");
		String model 	= Keyin.inString("Modelo (text): ");
		double capacity = Keyin.inDouble("Capacidad (double): ");

		
//		    "Codigo:           " + code  + "\n" 
//			+ "Brand:            " + brand + "\n" 
//			+ "Model:            " + model + "\n"
//			+ "Capacidad:        " + capacity;
		
		Container container = new Container(brand, capacity, code, model);
			

		boolean exit = false;
		while (!exit) {

			System.out.println("========================");
			System.out.println(container.toStringConsola());
			System.out.println("========================");

			char swValue = Keyin.inChar(" Dar de Alta (s/n): ");

			switch (swValue) {
			case 'n':
			case 'N':
				System.out.println("volviendo...");
				exit = true;
				break;

			case 's':
			case 'S':
				try {

					ContainerServiceClient client = (ContainerServiceClient) ContextSingleton
							.getInstance().getBean(RemoteClientesConstants.ContainerClient);

					Long idGenerated = client.create(container);
					System.out.println("Contenedor creado correctamente con id: "
							+ idGenerated);

					exit = true;

				} catch (Exception e) {
					e.printStackTrace();
					log.error("Problema al contactar al server", e);
					System.out.println("Error: Al contactar al servidor");
				}

				Keyin.inChar("presione enter tecla para continuar...");

				break;

			default:
				System.out.println("Valor invalido.");
				break;
			}

		}
	}

}
