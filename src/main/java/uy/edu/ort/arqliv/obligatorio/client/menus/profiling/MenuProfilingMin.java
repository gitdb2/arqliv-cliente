package uy.edu.ort.arqliv.obligatorio.client.menus.profiling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ProfilingServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.dominio.Pair;

public class MenuProfilingMin {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private boolean toSysOut;
	
	public MenuProfilingMin(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	public void render() {
		try {
			
			ProfilingServiceClient client = (ProfilingServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientesConstants.ProfilingClient);
			
			List<Pair<String, Long>> mins = client.minServiceTime(new Date());
			
			String titles = String.format("%-40s %-20s", "Servicio", "Tiempo minimo");
			
			List<String> lines = new ArrayList<>();

			for (Pair<String, Long> pair : mins) {
				lines.add(String.format("%-40s %-20d", pair.getKey(), pair.getValue()));
			}
			
			printData(titles, lines);

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
					"C:/ORT/pdfs/profiling_min_" + System.currentTimeMillis() + ".pdf", 
					"Minimo de tiempo de servicios");
		}
	}
	
}
