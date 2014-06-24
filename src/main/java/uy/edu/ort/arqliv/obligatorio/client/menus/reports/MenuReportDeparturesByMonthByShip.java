package uy.edu.ort.arqliv.obligatorio.client.menus.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.profiling.UtilsMenuProfiling;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ReportsServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

public class MenuReportDeparturesByMonthByShip {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private boolean toSysOut;
	private SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
	
	public MenuReportDeparturesByMonthByShip(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	public void render() {
		try {
			
			String monthString = Keyin.inString("Ingrese el mes (1..12): ");
			int month = Integer.parseInt(monthString);
			
			String shipString = Keyin.inString("Ingrese el id del Barco: ");
			Long shipId = Long.parseLong(shipString);
			
			ReportsServiceClient client = (ReportsServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientsConstants.ReportsClient);
			
			List<Departure> departures = client.departuresByMonthByShip(month, shipId);
			
			String titles = String.format("%10s  " // ID
					+ "%-15s  " // "Fecha de partida",
					+ "%15s  " // "Id de barco",
					+ "%-15s  " // "Pais de Destino",
					+ "%-20s  " // "Ids contenedores"
					+ "%-20s " // "Desc. Contenedores"
					+ "%-20s " // "Peso Transportado"
					, "Id", "Fecha de partida", "Id de barco"
					, "Pais de Destino", "Ids contenedores", "Desc. Contenedores", "Peso Transportado");
			
			List<String> lines = new ArrayList<>();

			if (departures.isEmpty()) {
				lines.add("No se encontraron resultados para el Mes " + monthString + " y el Barco " + shipId);
			} else {
				for (Departure dep : departures) {
					lines.add(String.format("%10d  " // ID
							+ "%-15s  " // "Fecha de partida",
							+ "%15d  " // "Id de barco",
							+ "%-15s  " // "Pais de Destino",
							+ "%-20s  " // "Ids contenedores"
							+ "%-20s" // "Desc. Contenedores"
							+ "%-20.2f" // "Peso Transportado"
							, dep.getId()
							, sdfOut.format(dep.getDepartureDate())
							, dep.getShip().getId()
							, dep.getShipDestination()
							, generateContainerList(dep.getContainers()).toString()
							, dep.getContainersDescriptions()
							, dep.getShipTransportedWeightThatDay()));
				}
			}
			printData(titles, lines);
		} catch (NumberFormatException p) {
			log.error("Mes o Id de Barco invalido", p);
			System.out.println("Error: Mes o id de Barco invalido");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor");
		}
	}
	
	private void printData(String titles, List<String> lines) {
		if (toSysOut) {
			UtilsMenuProfiling.printToSysOut(titles, lines);
		} else {
			UtilsMenuProfiling.printToPdf(titles, 
					lines, 
					"C:/ORT/pdfs/reports_departures_by_month_by_ship_" + System.currentTimeMillis() + ".pdf", 
					"Partidas por Mes por Barco");
		}
	}
	
	private List<Long> generateContainerList(List<Container> containers) {
		List<Long> ret = new ArrayList<>();
		if (containers != null) {
			for (Container container : containers) {
				ret.add(container.getId());
			}
		}
		return ret;
	}

}
