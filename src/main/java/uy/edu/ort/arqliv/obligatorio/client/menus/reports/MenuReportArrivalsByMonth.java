package uy.edu.ort.arqliv.obligatorio.client.menus.reports;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.profiling.UtilsMenuProfiling;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ProfilingServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ReportsServiceClient;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

public class MenuReportArrivalsByMonth {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private boolean toSysOut;
	
	public MenuReportArrivalsByMonth(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	public void render() {
		try {
			String monthString = Keyin.inString("Ingrese el mes (1..12): ");
			
			int month = UtilsMenuReports.parseMonth(monthString);
			
			ReportsServiceClient client = (ReportsServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientesConstants.ReportsClient);
			
			List<Arrival> arrivals = client.arrivalsByMonth(month);
			
			String titles = String.format("%-40s %-20s", "Servicio", "Tiempo promedio");
			
			List<String> lines = new ArrayList<>();

			if (arrivals.isEmpty()) {
				lines.add("No se encontraron resultados para el Mes " + monthString);
			} else {
				for (Arrival arr : arrivals) {
					//lines.add(String.format("%-40s %-20.2f", pair.getKey(), pair.getValue()));
				}
			}
			
			printData(titles, lines);
		} catch (NumberFormatException p) {
			log.error("Mes invalido", p);
			System.out.println("Error: Mes invalido");
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
					"C:/ORT/pdfs/reports_arrival_by_month_" + System.currentTimeMillis() + ".pdf", 
					"Arribos por Mes");
		}
	}


}
