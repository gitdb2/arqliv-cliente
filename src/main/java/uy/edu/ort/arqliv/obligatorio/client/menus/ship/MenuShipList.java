package uy.edu.ort.arqliv.obligatorio.client.menus.ship;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.menus.pdf.PDFRenderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

public class MenuShipList implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	boolean toSysOut = true;

	public MenuShipList(boolean toSysOut) {
		this.toSysOut = toSysOut;
	}

	@Override
	public void render() {

		boolean exit = false;

		try {
			ShipServiceClient client = (ShipServiceClient) ContextSingleton
					.getInstance().getBean(RemoteClientsConstants.ShipClient);

			List<Ship> ships = client.list();

			String titles = String.format

					 ("%10s  " // ID
					+ "%-30s " // "Nombre",
					+ "%-15s " // "Bandera",
					+ "%15s " // "Codigo",
					+ "%20s " // "Año Manufactura",
					+ "%20s " // "Cant. Tripulacion",
					+ "%15s" // "Capacidad"
			, "Id", "Nombre", "Bandera", "Codigo", "Año Manufactura",
					"Cant. Tripulacion", "Capacidad");

			List<String> lines = new ArrayList<>();
			for (int i = 0; i < ships.size(); i++) {

				Ship ship = ships.get(i);
				lines.add(String.format

						 ("%10d  " // ID
						+ "%-30s " // "Nombre",
						+ "%-15s " // "Bandera",
						+ "%15d " // "Codigo",
						+ "%20d " // "Año Manufactura",
						+ "%20d " // "Cant. Tripulacion",
						+ "%15.2f" // "Capacidad"
				,
				ship.getId(), ship.getName(), ship.getFlag(), ship.getCode(),
						ship.getManufactoringYear(), ship.getCrewQuantity(),
						ship.getCapacity())
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
					String file = "C:/ORT/pdfs/ships_"+ System.currentTimeMillis() + ".pdf";
					PDFRenderer renderer = new PDFRenderer(file,
							"Listado de Barcos", titles, lines, "");
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
