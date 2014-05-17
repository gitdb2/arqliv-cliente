package uy.edu.ort.arqliv.obligatorio.client.menus.arrival;

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
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ArrivalServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

public class MenuArrivalCreate implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void render() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		

		System.out.println("============= Alta Arribo ==================");
		
	
		String fechaArrival = "";
		boolean fechaOk = false;
		Date arrivalDate = null;

		while (!fechaOk) {
			fechaArrival = Keyin
					.inString("Fecha de Arribo (yyyyMMdd), ENTER para HOY o 0 para salir: ");
			if ("0".equals(fechaArrival.trim())) {
				System.out.println("Cancelado...");
				return;
			}else{
				if(fechaArrival.trim().isEmpty()){
					arrivalDate = new Date();
					fechaOk = true;
				}else{
					try {
						arrivalDate = sdf.parse(fechaArrival);
						fechaOk = true;
					} catch (ParseException e) {
						System.out.println("Formato incorrecto.");
					}
				}
			}
		}
		
		String shipOrigin =  Keyin.inString("Pais de origen (string): ");
		
		boolean forceShipId = true;
		long ship = 0;
		do{
			ship					= Keyin.inInt   ("ID barco (int): ");
			if(ship > 0){
				forceShipId = false;
			}else{
				System.out.println("Error el id de barco debe ser > 0. Intente de nuevo");
			}
		}while(forceShipId);
	
		
		
		String containersDescriptions 	= Keyin.inString("Descipcion de Contenedores (texto): ");
		List<Long> containers 		= new ArrayList<>();
		System.out.println("Agregar contenedores:");
		boolean noMore = false;
		do{
			long idCont 				= Keyin.inInt("Id contenedor (int) o 0 o negativo para terminar de agregar: ");
			if(idCont > 0){
				if(!containers.contains(idCont)){
					containers.add(idCont);
				}else{
					System.out.println(idCont + " Ya fue agregado");
				}
			}else{
				noMore = true;
			}
		}while(!noMore);
		
		Arrival arrival = new Arrival();
		arrival.setArrivalDate(arrivalDate);
		arrival.setShipOrigin(shipOrigin);	
		arrival.setContainersDescriptions(containersDescriptions);

		boolean exit = false;
		while (!exit) {

			printArrival(arrival, containers, ship);

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

					ArrivalServiceClient client = (ArrivalServiceClient) ContextSingleton
							.getInstance().getBean(RemoteClientsConstants.ArrivalClient);

					Long idGenerated = client.create(arrival, ship, containers);
					System.out.println("Arribo creado correctamente con id: "+ idGenerated);
					exit = true;

				}
				catch (CustomServiceException e){
					log.error("Problema al dar de alta", e);
					System.out.println("Error: "+ e.getMessage());
					
				}
				catch (Exception e) {
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

	private void printArrival(Arrival arrival, List<Long> contenedores,
			long shipId) {
		System.out.println("========================");
		System.out.println("Fecha de arribo:    "+ sdfOut.format(arrival.getArrivalDate()));
		System.out.println("Id de barco:        " + shipId);
		System.out.println("Pais de Origen:     " + arrival.getShipOrigin());
		System.out.println("Ids contenedores:   " + contenedores);
		System.out.println("Desc. Contenedores: "
				+ arrival.getContainersDescriptions());
		System.out.println("========================");
	}

}
