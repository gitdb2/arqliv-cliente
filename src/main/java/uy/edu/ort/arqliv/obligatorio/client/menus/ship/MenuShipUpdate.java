package uy.edu.ort.arqliv.obligatorio.client.menus.ship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;
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

		//
		// Ship ship = new Ship(capacity, code, crewQuantity, flag,
		// manufactoringYear, name);

		ShipServiceClient client = (ShipServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientesConstants.ShipClient);

		int id = Keyin.inInt("ID jpa del barco (-1 para salir): ");

		if (id < 1) {
			System.out.println("volviendo...");
			return;
		}

		try {
			Ship ship = client.find(id);
			System.out.println("========================");
			System.out.println("========= Modificar id: " + id);
			System.out.println(ship.toStringConsola());
			System.out.println("================================");

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
					char swValue = Keyin.inChar(" Modificar? (s/n): ");

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
							long idGenerated = client.updateShip(ship);

							System.out
									.println("Barco Modificado correctamente, id: "
											+ idGenerated);

						} catch (Exception e) {
							e.printStackTrace();
							log.error("Problema al contactar al server", e);
							System.out.println("Error: Al contactar al servidor");
						}

						break;

					default:
						System.out.println("Valor invalido.");
						break;
					}
				}
			}
		} catch (CustomServiceException e) {
			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor");
		}
		Keyin.inChar("presione enter tecla para continuar...");
	}

}
