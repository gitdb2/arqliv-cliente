package uy.edu.ort.arqliv.obligatorio.client.menus.container;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.menus.pdf.PDFRenderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ContainerServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

public class MenuContainerList implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	boolean toSysOut = true;

	public MenuContainerList(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	@Override
	public void render() {

		boolean exit = false;

		try {
			ContainerServiceClient client = (ContainerServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientsConstants.ContainerClient);


			List<Container> containers = client.list();

			String titles = String.format

					 ("%10s  " // ID
					+ "%15s  " // "Codigo",
					+ "%-30s " // "Marca",
					+ "%-30s " // "Modelo",
					+ "%15s" // "Capacidad"
			, "Id", "Codigo", "Marca", "Modelo", "Capacidad");

			List<String> lines = new ArrayList<>();
			for (int i = 0; i < containers.size(); i++) {

				Container container = containers.get(i);
				lines.add(String.format

						 ("%10d  " // ID
						+ "%15d  " // "Codigo",
						+ "%-30s " // "Marca",
						+ "%-30s " // "Modelo",
						+ "%15.2f" // "Capacidad"
				,
				container.getId(), container.getCode(), container.getBrand(), container.getModel(), 
						container.getCapacity())
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
					String file = "C:/ORT/pdfs/containers_"+ System.currentTimeMillis() + ".pdf";
					PDFRenderer renderer = new PDFRenderer(file,
							"Listado de Contenedores", titles, lines, "");
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
}
