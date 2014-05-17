package uy.edu.ort.arqliv.obligatorio.client.menus.arrival;

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
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ArrivalServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ContainerServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

/**
 * 
 * @author rodrigo
 * 
 */
public class MenuArrivalUpdate implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	public void render() {

		ArrivalServiceClient client = (ArrivalServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientesConstants.ArrivalClient);

		System.out.println("============= Update Arribo ==================");

		try {

			Arrival arrival = null;
			int id = -1;
			boolean continueTo = false;
			do {
				id = Keyin.inInt("ID jpa del Arribo (-1 o 0 para salir): ");

				if (id < 1) {
					System.out.println("volviendo...");
					return;
				}
				arrival = client.find(id);

				if (arrival == null) {
					System.out.println("Error: No se encuentra el Arribo id:"
							+ id);
				} else {
					continueTo = true;
				}
			} while (!continueTo);

			printArrival(arrival);

			String fechaArrival = "";
			boolean fechaOk = false;
			Date arrivalDate = null;

			while (!fechaOk) {
				fechaArrival = Keyin.inString("Fecha de Arribo ("
						+ sdf.format(arrival.getArrivalDate()) + "): ");
				if ("0".equals(fechaArrival.trim())) {
					System.out.println("Cancelado...");
					return;
				} else {
					if (fechaArrival.trim().isEmpty()) {
						fechaOk = true;
					} else {
						try {
							arrivalDate = sdf.parse(fechaArrival);
							fechaOk = true;
						} catch (ParseException e) {
							System.out.println("Formato incorrecto.");
						}
					}
				}
			}

			String shipOrigin = Keyin.inString("Pais de origen ("
					+ arrival.getShipOrigin() + "): ");

			boolean forceShipId = true;
			Integer ship = 0;
			do {
				ship = Keyin.inIntAllowEmpty("ID barco ("
						+ arrival.getShip().getId() + "): ");
				if (ship == null || ship > 0) {
					forceShipId = false;
				} else {
					System.out
							.println("Error el id de barco debe ser > 0. Intente de nuevo");
				}
			} while (forceShipId);

			String containersDescriptions = Keyin
					.inString("Descipcion de Contenedores ("
							+ arrival.getContainersDescriptions() + "): ");
			List<Long> containers = new ArrayList<>();

			int incambiaCont = Keyin
					.inInt("Cambiar los contenedores (1 si / 0 no)?");
			if (incambiaCont == 1) {
				System.out.println("Contenedores ("
						+ generateContainerList(arrival.getContainers())
						+ "), \nse deben ingresar nuevamente:");
				boolean noMore = false;
				do {
					long idCont = Keyin
							.inInt("Id contenedor (int) o 0 o negativo para terminar de agregar: ");
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
			} else {
				containers = generateContainerList(arrival.getContainers());
			}

			boolean hayCambios = false;

			if (arrivalDate != null) {
				if (sdf.format(arrival.getArrivalDate()).equals(fechaArrival)) {
					arrival.setArrivalDate(arrivalDate);
					hayCambios = true;
				}
			}
			if (shipOrigin != null
					&& !shipOrigin.isEmpty()
					&& !arrival.getShipOrigin().trim()
							.equals(shipOrigin.trim())) {
				arrival.setShipOrigin(shipOrigin);
				hayCambios = true;
			}
			if (containersDescriptions != null
					&& !containersDescriptions.trim().isEmpty()
					&& !arrival.getContainersDescriptions().trim()
							.equals(containersDescriptions.trim())) {
				arrival.setContainersDescriptions(containersDescriptions);
				hayCambios = true;
			}
			Long shipid = 0L;
			if (ship != null && arrival.getShip().getId() != (long) ship) {
				hayCambios = true;
				shipid = (long) ship;
			} else {
				shipid = arrival.getShip().getId();
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
							long idGenerated = client.update(arrival, shipid,
									containers);

							System.out
									.println("Arribo Modificado correctamente, id: "
											+ idGenerated);

						} catch (CustomServiceException e) {
							log.error("Excepcion remota", e);
							System.out.println("Error: Remoto: "
									+ e.getMessage());
						} catch (Exception e) {
							// e.printStackTrace();
							log.error("Error inesperado", e);
							System.out.println("Error: inesperado: "
									+ e.getMessage());
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

	private void printArrival(Arrival arrival) {
		System.out.println("===============================");
		System.out.println("========= Modificar id: " + arrival.getId());
		System.out.println("===============================");
		System.out.println("Fecha de arribo:    "
				+ sdfOut.format(arrival.getArrivalDate()));
		System.out.println("Id de barco:        " + arrival.getShip().getId());
		System.out.println("Pais de Origen:     " + arrival.getShipOrigin());
		System.out.println("Ids contenedores:   "
				+ generateContainerList(arrival.getContainers()));
		System.out.println("Desc. Contenedores: "
				+ arrival.getContainersDescriptions());
		System.out.println("===============================");
	}

	private List<Long> generateContainerList(List<Container> containers) {
		List<Long> ret = new ArrayList<>();
		if (containers != null) {
			for (Container container : containers) {
				ret.add(container.getId());
			}
		}
		return ret;
	}
}
