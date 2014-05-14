package uy.edu.ort.arqliv.obligatorio.client.menus.ship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

public class MenuShipDelete implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {

		String name = Keyin.inString("Nombre (texto):       ");
		String flag = Keyin.inString("Bandera (texto):      ");
		int code = Keyin.inInt("Codigo (int):            ");
		int manufactoringYear = Keyin.inInt("Año Manuf. (int):        ");
		int crewQuantity = Keyin.inInt("Cant. tripulantes (int): ");
		double capacity = Keyin.inDouble("Capacidad (double):   ");

		Ship ship = new Ship(capacity, code, crewQuantity, flag,
				manufactoringYear, name);

		boolean exit = false;
		while (!exit) {

			System.out.println("========================");
			System.out.println(ship.toStringConsola());
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

					ShipServiceClient client = (ShipServiceClient) ContextSingleton
							.getInstance().getBean(
									RemoteClientesConstants.ShipClient);

					Long idGenerated = client.createShip(ship);
					System.out.println("Barco creado correctamente con id: "
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
