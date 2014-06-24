package uy.edu.ort.arqliv.obligatorio.client.menus.departure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.DepartureServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

public class MenuDepartureCreate implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		System.out.println("============= Alta Partida ==================");
	
		String departureDateString = "";
		boolean dateOk = false;
		Date departureDate = null;

		while (!dateOk) {
			departureDateString = Keyin.inString("Fecha de Arribo (yyyyMMdd), ENTER para HOY o 0 para salir: ");
			if ("0".equals(departureDateString.trim())) {
				System.out.println("Cancelado...");
				return;
			} else {
				if(departureDateString.trim().isEmpty()){
					departureDate = new Date();
					dateOk = true;
				} else {
					try {
						departureDate = sdf.parse(departureDateString);
						dateOk = true;
					} catch (ParseException e) {
						System.out.println("Formato incorrecto.");
					}
				}
			}
		}

		
		
		
		boolean forceArrivalId = true;
		long arrivalId = 0;
		do {
			arrivalId = Keyin.inInt ("ID Arribo asociado (int): ");
			if (arrivalId > 0) {
				forceArrivalId = false;
			} else {
				System.out.println("Error el id de arribo debe ser > 0. Intente de nuevo");
			}
		} while (forceArrivalId);
		
		String shipDestination =  Keyin.inString("Pais de destino (string): ");
		
		boolean forceShipId = true;
		long shipId = 0;
		do {
			shipId = Keyin.inInt ("ID barco (int): ");
			if (shipId > 0) {
				forceShipId = false;
			} else {
				System.out.println("Error el id de barco debe ser > 0. Intente de nuevo");
			}
		} while (forceShipId);
		
		String containersDescriptions = Keyin.inString("Descripcion de Contenedores (texto): ");
		List<Long> containers = new ArrayList<>();
		System.out.println("Agregar contenedores:");
		boolean noMore = false;
		
		do {
			long idCont = Keyin.inInt("Id contenedor (int) o 0 o negativo para terminar de agregar: ");
			if(idCont > 0) {
				if (!containers.contains(idCont)) {
					containers.add(idCont);
				} else {
					System.out.println(idCont + " Ya fue agregado");
				}
			} else {
				noMore = true;
			}
		} while (!noMore);
		
		Departure departure = new Departure();
		departure.setDepartureDate(departureDate);
		departure.setShipDestination(shipDestination);	
		departure.setContainersDescriptions(containersDescriptions);

		boolean exit = false;
		while (!exit) {
			DepartureMenuUtils.printDepartureCreate(departure, containers, shipId);
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
					
					DepartureServiceClient client = (DepartureServiceClient) ContextSingleton
							.getInstance().getBean(RemoteClientsConstants.DepartureClient);

					Long idGenerated = client.create(departure, shipId, containers, arrivalId);
					System.out.println("Partida creado correctamente con id: " + idGenerated);
					exit = true;
					
				} catch (CustomServiceException e){
					log.error("Problema al dar de alta", e);
					System.out.println("Error: "+ e.getMessage());
					
				} catch (Exception e) {
					log.error("Problema al contactar al server", e);
					System.out.println("Error: Al contactar al servidor: "+e.getMessage());
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
