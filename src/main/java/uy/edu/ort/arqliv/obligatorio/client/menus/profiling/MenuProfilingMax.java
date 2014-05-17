package uy.edu.ort.arqliv.obligatorio.client.menus.profiling;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ProfilingServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.dominio.Pair;

public class MenuProfilingMax {

private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private boolean toSysOut;

	public MenuProfilingMax(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	public void render() {
		try {
			String forDateString = Keyin.inString(UtilsMenuProfiling.DATE_PARAMETER_MSG);
			
			Date forDate = UtilsMenuProfiling.parseDate(forDateString);
			
			ProfilingServiceClient client = (ProfilingServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientesConstants.ProfilingClient);
			
			List<Pair<String, Long>> maxs = client.maxServiceTime(forDate);
			
			String titles = String.format("%-40s %-20s", "Servicio", "Tiempo maximo");
			
			List<String> lines = new ArrayList<>();

			if (maxs.isEmpty()) {
				lines.add(UtilsMenuProfiling.NO_RECORDS_FOUND_MSG + forDateString);
			} else {
				for (Pair<String, Long> pair : maxs) {
					lines.add(String.format("%-40s %-20d", pair.getKey(), pair.getValue()));
				}
			}
			
			printData(titles, lines);
			
		} catch (ParseException p) {
			log.error("Fecha invalida", p);
			System.out.println("Error: Fecha invalida");
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
					"C:/ORT/pdfs/profiling_avg_" + System.currentTimeMillis() + ".pdf", 
					"Maximo de tiempo de servicios");
		}
	}
	
}
