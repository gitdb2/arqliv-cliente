package uy.edu.ort.arqliv.obligatorio.client.menus.arrival;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.menus.pdf.PDFRenderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ArrivalServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

public class MenuArrivalList implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	boolean toSysOut = true;
	private SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");

	public MenuArrivalList(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	@Override
	public void render() {

		boolean exit = false;

		try {
			ArrivalServiceClient client = (ArrivalServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientesConstants.ArrivalClient);


			List<Arrival> arrivals = client.list();

			String titles = String.format

					 ("%10s  " // ID
					+ "%-15s  " // "Fecha de arribo",
					+ "%15s  " // "Id de barco",
					+ "%-30s  " // "Pais de Origen",
					+ "%-30s  " // "Ids contenedores"
					+ "%-30s " // "Desc. Contenedores"
			, "Id", "Fecha de arribo", "Id de barco", "Pais de Origen", "Ids contenedores", "Desc. Contenedores");
			
			List<String> lines = new ArrayList<>();
			for (int i = 0; i < arrivals.size(); i++) {

				Arrival arrival = arrivals.get(i);
				lines.add(
					String.format(
						  "%10d  " // ID                   
						+ "%-15s  " // "Fecha de arribo",    
						+ "%15d  " // "Id de barco",        
						+ "%-30s  " // "Pais de Origen",    
						+ "%-30s  " // "Ids contenedores"    
						+ "%-30s" // "Desc. Contenedores"  
						,
						arrival.getId(), 
						sdfOut.format(arrival.getArrivalDate()), 
						arrival.getShip().getId(), 
						arrival.getShipOrigin(),
						generateContainerList(arrival.getContainers()).toString(),
						arrival.getContainersDescriptions()
					)
				);
			}

			if (toSysOut) {
				System.out.println(titles);
				for (String line : lines) {
					System.out.println(line);
				}
				System.out.println("-------------o-------------");
			}else{
				try {
					String file = "C:/ORT/pdfs/arrivals_"+ System.currentTimeMillis() + ".pdf";
					PDFRenderer renderer = new PDFRenderer(file,
							"Listado de Arribos", titles, lines, "");
					renderer.render();
					System.out.println("Archivo generado en "+file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("no se pudo generar el pdf");
					System.out.println("Error: No se pudo generar el pdf: "+e.getMessage());
				}
				
			}

			

		} catch (CustomServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Keyin.inChar("presione enter tecla para volver...");
	}

	private List<Long> generateContainerList(List<Container> containers) {
		List<Long> ret = new ArrayList<>();
		for (Container container : containers) {
			ret.add(container.getId());
		}
		return ret;
	}
}
