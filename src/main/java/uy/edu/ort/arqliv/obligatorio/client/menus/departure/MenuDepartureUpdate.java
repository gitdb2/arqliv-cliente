package uy.edu.ort.arqliv.obligatorio.client.menus.departure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.DepartureServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

/**
 * 
 * @author rodrigo
 * 
 */
public class MenuDepartureUpdate implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	public void render() {

		DepartureServiceClient client = (DepartureServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientsConstants.DepartureClient);

		System.out.println("============= Update Partida ==================");

		try {

			Departure departure = null;
			int id = -1;
			boolean continueTo = false;
			
			do {
				id = Keyin.inInt("ID jpa de la Partida (-1 o 0 para salir): ");

				if (id < 1) {
					System.out.println("volviendo...");
					return;
				}
				departure = client.find(id);

				if (departure == null) {
					System.out.println("Error: No se encuentra el Arribo id:" + id);
				} else {
					continueTo = true;
				}
			} while (!continueTo);

			DepartureMenuUtils.printDeparture(departure, "Modificar");

			String departureDateString = "";
			boolean fechaOk = false;
			Date departureDate = null;

			while (!fechaOk) {
				departureDateString = Keyin.inString("Fecha de Arribo ("+ sdf.format(departure.getDepartureDate()) + "): ");
				if ("0".equals(departureDateString.trim())) {
					System.out.println("Cancelado...");
					return;
				} else {
					if (departureDateString.trim().isEmpty()) {
						fechaOk = true;
					} else {
						try {
							departureDate = sdf.parse(departureDateString);
							fechaOk = true;
						} catch (ParseException e) {
							System.out.println("Formato incorrecto.");
						}
					}
				}
			}

			String shipDestination = Keyin.inString("Pais de origen ("+ departure.getShipDestination() + "): ");

			boolean forceShipId = true;
			Integer ship = 0;
			do {
				ship = Keyin.inIntAllowEmpty("ID barco ("+ departure.getShip().getId() + "): ");
				if (ship == null || ship > 0) {
					forceShipId = false;
				} else {
					System.out.println("Error el id de barco debe ser > 0. Intente de nuevo");
				}
			} while (forceShipId);

			String containersDescriptions = Keyin
					.inString("Descipcion de Contenedores ("
					+ departure.getContainersDescriptions() + "): ");
			List<Long> containers = new ArrayList<>();
			
			int inCambiaCont = 0;
			{
				boolean exit = false;
				while (!exit) {
					char swValue = Keyin.inChar("Cambiar los contenedores? (s/n): ");
					switch (swValue) {
					case 'n':
					case 'N':
						exit = true;
						inCambiaCont = 0;
						break;
					case 's':
					case 'S':
						inCambiaCont = 1;
						exit = true;
						break;
					default:
						System.out.println("Valor invalido.");
						break;
					}
				}
			}
			
			boolean hayCambios = false;
			
			if (inCambiaCont == 1) {
				System.out.println("Contenedores ("
						+ DepartureMenuUtils.generateContainerList(departure.getContainers())
						+ "), \nse deben ingresar nuevamente:");
				boolean noMore = false;
				do {
					long idCont = Keyin.inInt("Id contenedor (int) o 0 o negativo para terminar de agregar: ");
					if (idCont > 0) {
						if (!containers.contains(idCont)) {
							containers.add(idCont);
						} else {
							System.out.println(idCont + " Ya fue agregado");
						}
					} else {
						noMore = true;
					}
				} while (!noMore);
				hayCambios = true;
			} else {
				containers = DepartureMenuUtils.generateContainerList(departure.getContainers());
			}

			if (departureDate != null) {
				if (!sdf.format(departure.getDepartureDate()).equals(departureDateString)) {
					departure.setDepartureDate(departureDate);
					hayCambios = true;
				}
			}
			if (shipDestination != null
				&& !shipDestination.isEmpty()
				&& !departure.getShipDestination().trim().equals(shipDestination.trim())) {
					departure.setShipDestination(shipDestination);
					hayCambios = true;
			}
			if (containersDescriptions != null
				&& !containersDescriptions.trim().isEmpty()
				&& !departure.getContainersDescriptions().trim().equals(containersDescriptions.trim())) {
					departure.setContainersDescriptions(containersDescriptions);
					hayCambios = true;
			}
			Long shipid = 0L;
			if (ship != null && departure.getShip().getId() != (long) ship) {
				hayCambios = true;
				shipid = (long) ship;
			} else {
				shipid = departure.getShip().getId();
			}

			if (!hayCambios) {
				System.out.println("No hay cambios para realizar. Update Cancelado.");
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
							long idGenerated = client.update(departure, shipid, containers, departure.getArrival().getId());
							System.out.println("Arribo Modificado correctamente, id: " + idGenerated);
						} catch (CustomServiceException e) {
							log.error("Excepcion remota", e);
							System.out.println("Error: Remoto: " + e.getMessage());
						} catch (Exception e) {
							log.error("Error inesperado", e);
							System.out.println("Error: inesperado: " + e.getMessage());
						}
						break;
					default:
						System.out.println("Valor invalido.");
						break;
					}
				}
			}
		} catch (CustomServiceException e) {
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			System.out.println("Error: " + e.getMessage());
		}
		Keyin.inChar("presione enter tecla para continuar...");
	}

}
