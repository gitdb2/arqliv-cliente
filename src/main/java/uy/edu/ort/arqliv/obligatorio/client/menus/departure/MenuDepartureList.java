package uy.edu.ort.arqliv.obligatorio.client.menus.departure;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.menus.pdf.PDFRenderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.DepartureServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

public class MenuDepartureList implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	boolean toSysOut = true;

	public MenuDepartureList(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	@Override
	public void render() {
		try {
			DepartureServiceClient client = (DepartureServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientsConstants.DepartureClient);

			List<Departure> departures = client.list();

			String titles = String.format

			("%10s  " // ID
					+ "%-15s  " // "Fecha de partida",
					+ "%15s  " // "Id de barco",
					+ "%-30s  " // "Pais de destino",
					+ "%-30s  " // "Ids contenedores"
					+ "%-30s " // "Desc. Contenedores"
			, "Id", "Fecha de partida", "Id de barco", "Pais de destino",
					"Ids contenedores", "Desc. Contenedores");

			List<String> lines = new ArrayList<>();
			for (int i = 0; i < departures.size(); i++) {

				Departure departure = departures.get(i);
				lines.add(String.format("%10d  " // ID
						+ "%-15s  " // "Fecha de partida",
						+ "%15d  " // "Id de barco",
						+ "%-30s  " // "Pais de destino",
						+ "%-30s  " // "Ids contenedores"
						+ "%-30s" // "Desc. Contenedores"
				, departure.getId(), DepartureMenuUtils.sdfOut.format(departure.getDepartureDate())
				, departure.getShip().getId(), departure.getShipDestination()
				, DepartureMenuUtils.generateContainerList(departure.getContainers()).toString() 
				, departure.getContainersDescriptions()));
			}

			if (toSysOut) {
				System.out.println(titles);
				for (String line : lines) {
					System.out.println(line);
				}
				System.out.println("-------------o-------------");
			} else {
				try {
					String file = "C:/ORT/pdfs/departures_" + System.currentTimeMillis() + ".pdf";
					PDFRenderer renderer = new PDFRenderer(file, "Listado de Partidas", titles, lines, "");
					renderer.render();
					System.out.println("Archivo generado en " + file);
				} catch (Exception e) {
					log.error("no se pudo generar el pdf");
					System.out.println("Error: No se pudo generar el pdf: " + e.getMessage());
				}
			}

		} catch (CustomServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Keyin.inChar("presione enter tecla para volver...");
	}

}
