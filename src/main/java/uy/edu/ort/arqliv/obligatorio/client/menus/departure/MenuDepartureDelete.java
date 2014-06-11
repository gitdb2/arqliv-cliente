package uy.edu.ort.arqliv.obligatorio.client.menus.departure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.DepartureServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;
/**
 * 
 * @author rodrigo
 *
 */
public class MenuDepartureDelete implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {

		DepartureServiceClient client = (DepartureServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientsConstants.DepartureClient);

		System.out.println("============= BAJA Partida ==================");

		try {
			
			Departure departure = null;
			int id = -1;
			boolean continueTo = false;
			do{
				id = Keyin.inInt("ID jpa de la Partida (-1 o 0 para salir): ");

				if (id < 1) {
					System.out.println("volviendo...");
					return;
				}
				
				departure = client.find(id);
				
				if(departure == null){
					System.out.println("Error: No se encuentra la Partida id:" + id);
				} else {
					continueTo = true;
				}
			} while (!continueTo);
			
			DepartureMenuUtils.printDeparture(departure, "Eliminar");

			boolean exit = false;
			while (!exit) {
				char swValue = Keyin.inChar("Eliminar? (s/n): ");

				switch (swValue) {
				case 'n':
				case 'N':
					System.out.println("Delete Cancelado.");
					exit = true;
					break;

				case 's':
				case 'S':
					try {
						exit = true;
						client.delete(id);
						System.out.println("Partida eliminada correctamente, id: " + id);
					} 
					catch (Exception e) {
						e.printStackTrace();
						log.error("Problema al borrar Partida", e);
						System.out.println("Error: " + e.getMessage());
					}
					break;
				default:
					System.out.println("Valor invalido.");
					break;
				}
			}
		} catch (CustomServiceException e) {
			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error:" + e.getMessage());
		}
		Keyin.inChar("presione enter tecla para continuar...");
	}
	
}
