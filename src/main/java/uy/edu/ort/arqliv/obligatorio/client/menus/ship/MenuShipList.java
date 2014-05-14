package uy.edu.ort.arqliv.obligatorio.client.menus.ship;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

public class MenuShipList implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {

//		String name = Keyin.inString("Nombre (texto):       ");
//		String flag = Keyin.inString("Bandera (texto):      ");
//		int code = Keyin.inInt("Codigo (int):            ");
//		int manufactoringYear = Keyin.inInt("Año Manuf. (int):        ");
//		int crewQuantity = Keyin.inInt("Cant. tripulantes (int): ");
//		double capacity = Keyin.inDouble("Capacidad (double):   ");

//		Ship ship = new Ship(capacity, code, crewQuantity, flag,
//				manufactoringYear, name);

		boolean exit = false;
		
		try {
			ShipServiceClient client = (ShipServiceClient) ContextSingleton
					.getInstance().getBean(
							RemoteClientesConstants.ShipClient);
			
			List<Ship> ships = client.list();
			
			 System.out.printf(
					  "%-15s"						//  ID
					+ "%-30s"     					//	"Nombre",
			 		+ "%-15s"     				 	//	"Bandera",
			 		+ "%-15s"     				 	//	"Codigo",
			 		+ "%-20s"     				 	//	"Año Manufactura",
			 		+ "%-20s"     				 	//	"Cant. Tripulacion",
			 		+ "%-15s"        				//	"Capacidad"
			 		+ "\n",
			 		"Id",
			 		"Nombre",
			 		"Bandera",
			 		"Codigo",
			 		"Año Manufactura",
			 		"Cant. Tripulacion",
			 		"Capacidad");
			
			
			for (int i = 0; i < ships.size(); i++) {
				
				Ship ship = ships.get(i);
				 System.out.printf(
						  "%-15d"						// ID
						+ "%-30s"     					//	"Nombre",
				 		+ "%-15s"     				 	//	"Bandera",
				 		+ "%-15d"     				 	//	"Codigo",
				 		+ "%-20d"     				 	//	"Año Manufactura",
				 		+ "%-20d"     				 	//	"Cant. Tripulacion",
				 		+ "%-15.2f"        				//	"Capacidad"
				 		+ "\n",
				 		
				 		ship.getId(),
				 		ship.getName(),
				 		ship.getFlag(),
				 		ship.getCode(),
				 		ship.getManufactoringYear(),
				 		ship.getCrewQuantity(),
				 		ship.getCapacity());
			}
			
			System.out.println("-------------o-------------");
			
		} catch (CustomServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Keyin.inChar("presione enter tecla para volver...");
	}

}
