package uy.edu.ort.arqliv.obligatorio.client.menus.arrival;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ArrivalServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;
/**
 * 
 * @author rodrigo
 *
 */
public class MenuArrivalDelete implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void render() {

		ArrivalServiceClient client = (ArrivalServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientesConstants.ArrivalClient);

		System.out.println("============= BAJA Arribo ==================");

		try {
			
			Arrival arrival = null;
			int id = -1;
			boolean continueTo = false;
			do{
				id = Keyin.inInt("ID jpa del Arribo (-1 o 0 para salir): ");

				if (id < 1) {
					System.out.println("volviendo...");
					return;
				}
				arrival = client.find(id);
				
				if(arrival == null){
					System.out.println("Error: No se encuentra el Arribo id:"+id);
				}else{
					continueTo = true;
				}
			}while(!continueTo);
			
			
			printArrival(arrival);
		

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

						System.out.println("Arribo Eliminado correctamente, id: " + id);
					} 
					
					catch (Exception e) {
						e.printStackTrace();
						log.error("Problema al borrar Arribo", e);
						System.out.println("Error: "+e.getMessage());
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
	
	private void printArrival(Arrival arrival){
		System.out.println("===============================");
		System.out.println("========= Eliminar id: " + arrival.getId());
		System.out.println("===============================");
		System.out.println("Fecha de arribo:    " + sdfOut.format(arrival.getArrivalDate()));
		System.out.println("Id de barco:        " + arrival.getShip().getId());
		System.out.println("Pais de Origen:     " + arrival.getShipOrigin());
		System.out.println("Ids contenedores:   " + generateContainerList(arrival.getContainers()));
		System.out.println("Desc. Contenedores: " + arrival.getContainersDescriptions());
		System.out.println("===============================");
	}
	
	private List<Long> generateContainerList(List<Container> containers) {
		List<Long> ret = new ArrayList<>();
		for (Container container : containers) {
			ret.add(container.getId());
		}
		return ret;
	}
}
