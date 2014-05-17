package uy.edu.ort.arqliv.obligatorio.client.menus.ship;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomNotArrivedThatDateServiceException;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

/**
 * 
 * @author rodrigo
 * 
 */
public class MenuShipUpdate implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
		
		ShipServiceClient client = (ShipServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientesConstants.ShipClient);

		System.out.println("============= Update barco ==================");

		String fechaArrival = "";
		boolean fechaOk = false;
		Date arrivalDate = null;

		while (!fechaOk) {
			fechaArrival = Keyin
					.inString("Fecha para modificacion (yyyyMMdd) o 0 para salir: ");
			if ("0".equals(fechaArrival.trim())) {
				System.out.println("Cancelado...");
				return;
			}
			try {
				arrivalDate = sdf.parse(fechaArrival);
				fechaOk = true;
			} catch (ParseException e) {
				System.out.println("Formato incorrecto.");
			}
		}

		try {
			
			Ship ship = null;
			int id = -1;
			boolean continueTo = false;
			do{
				id = Keyin.inInt("ID jpa del Barco (-1 o 0 para salir): ");

				if (id < 1) {
					System.out.println("volviendo...");
					return;
				}
				ship = client.find(id);
				
				if(ship ==null){
					System.out.println("Error: No se encuentra el barco id:"+id);
				}else{
					continueTo = true;
				}
			}while(!continueTo);
			
			
		
			System.out.println("==================================");
			System.out.println("========= Modificar id: " + id);
			System.out.println("========= fecha arrivo: " +sdfOut.format(arrivalDate) );
			System.out.println(ship.toStringConsola());
			System.out.println("===================================");

			String name = Keyin.inString("Nombre (" + ship.getName() + "): ");
			String flag = Keyin.inString("Bandera (" + ship.getFlag() + "): ");
			Integer code = Keyin.inIntAllowEmpty("Codigo (" + ship.getCode()
					+ "): ");
			Integer manufactoringYear = Keyin.inIntAllowEmpty("Año Manuf. ("
					+ ship.getManufactoringYear() + "): ");
			Integer crewQuantity = Keyin.inIntAllowEmpty("Cant. tripulantes ("
					+ ship.getCrewQuantity() + "): ");
			Double capacity = Keyin.inDoubleAllowEmpty("Capacidad ("
					+ ship.getCapacity() + "): ");

			boolean hayCambios = false;

			if (name != null && !name.isEmpty()
					&& !ship.getName().trim().equals(name)) {
				ship.setName(name);
				hayCambios = true;
			}
			if (flag != null && !flag.isEmpty()
					&& !ship.getFlag().trim().equals(flag)) {
				ship.setFlag(flag);
				hayCambios = true;
			}
			if (code != null && ship.getCode() != code) {
				ship.setCode(code);
				hayCambios = true;
			}
			if (manufactoringYear != null
					&& ship.getManufactoringYear() != manufactoringYear) {
				ship.setManufactoringYear(manufactoringYear);
				hayCambios = true;
			}
			if (crewQuantity != null && ship.getCrewQuantity() != crewQuantity) {
				ship.setCrewQuantity(crewQuantity);
				hayCambios = true;
			}
			if (capacity != null && ship.getCapacity() != capacity) {
				ship.setCapacity(capacity);
				hayCambios = true;
			}
			if (!hayCambios) {
				System.out
						.println("No hay cambios para realizar. Update Cancelado.");
			} else {

				boolean exit = false;
				while (!exit) {
					char swValue = Keyin.inChar("Modificar? (s/n): ");

					switch (swValue) {
					case 'n':
					case 'N':
						System.out.println("Update Cancelado.");
						exit = true;
						break;

					case 's':
					case 'S':
						try {
							exit = true;
							long idGenerated = client.update(ship, arrivalDate);

							System.out
									.println("Barco Modificado correctamente, id: "
											+ idGenerated);

						}
						catch(CustomNotArrivedThatDateServiceException e){
							log.error("No se puede modificar el barco para dicha fecha", e);
							System.out.println(e.getMessage());
						}catch(CustomServiceException e){
							log.error("Excepcion remota", e);
							System.out.println("Error: Remoto: "+e.getMessage());
						}
						catch (Exception e) {
							//e.printStackTrace();
							log.error("Error inesperado", e);
							System.out.println("Error: inesperado: "+e.getMessage());
						}

						break;

					default:
						System.out.println("Valor invalido.");
						break;
					}
				}
			}
		} catch (CustomServiceException e) {
//			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor: "+e.getMessage());
		}
		Keyin.inChar("presione enter tecla para continuar...");
	}

}
