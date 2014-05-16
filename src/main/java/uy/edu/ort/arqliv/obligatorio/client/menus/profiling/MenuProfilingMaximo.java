package uy.edu.ort.arqliv.obligatorio.client.menus.profiling;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ProfilingServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.dominio.Pair;

public class MenuProfilingMaximo {

private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void render() {
		try {
			
			ProfilingServiceClient client = (ProfilingServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientesConstants.ProfilingClient);
			
			List<Pair<String, Long>> max = client.maxServiceTime(new Date());
			
			System.out.println("========================");
			
			for (Pair<String, Long> pair : max) {
				System.out.println(pair.toString());
			}
			
			System.out.println("========================");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor");
		}
	}

}
