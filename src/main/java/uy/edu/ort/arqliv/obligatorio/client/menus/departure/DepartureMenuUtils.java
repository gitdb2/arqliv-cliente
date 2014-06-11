package uy.edu.ort.arqliv.obligatorio.client.menus.departure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.dominio.Container;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

public class DepartureMenuUtils {

	public static SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void printDeparture(Departure departure, String operation) {
		System.out.println("===============================");
		System.out.println("========= " + operation + "id: " + departure.getId());
		System.out.println("===============================");
		System.out.println("Fecha de partida:   " + sdfOut.format(departure.getDepartureDate()));
		System.out.println("Id de barco:        " + departure.getShip().getId());
		System.out.println("Pais de Destino     " + departure.getShipDestination());
		System.out.println("Ids contenedores:   " + generateContainerList(departure.getContainers()));
		System.out.println("Desc. Contenedores: " + departure.getContainersDescriptions());
		System.out.println("===============================");
	}
	
	public static List<Long> generateContainerList(List<Container> containers) {
		List<Long> ret = new ArrayList<>();
		if (containers != null) {
			for (Container container : containers) {
				ret.add(container.getId());
			}
		}
		return ret;
	}
	
	public static void printDepartureCreate(Departure departure, List<Long> contenedores,	long shipId) {
		System.out.println("========================");
		System.out.println("Fecha de partida:   " + sdfOut.format(departure.getDepartureDate()));
		System.out.println("Id de barco:        " + shipId);
		System.out.println("Pais de Destino:    " + departure.getShipDestination());
		System.out.println("Ids contenedores:   " + contenedores);
		System.out.println("Desc. Contenedores: " + departure.getContainersDescriptions());
		System.out.println("========================");
	}

	
	
}
