package uy.edu.ort.arqliv.obligatorio.client.menus.container;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ContainerServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ShipServiceClient;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomNotArrivedThatDateServiceException;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

/**
 * 
 * @author rodrigo
 * 
 */
public class MenuContainerUpdate implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {

		ContainerServiceClient client = (ContainerServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientesConstants.ContainerClient);

		System.out.println("============= Update Contenerdor ==================");

		try {
			Container container = null;
			int id = -1;
			boolean continueTo = false;
			do{
				id = Keyin.inInt("ID jpa del Contenedor (-1 o 0 para salir): ");

				if (id < 1) {
					System.out.println("volviendo...");
					return;
				}
				container = client.find(id);
				
				if(container ==null){
					System.out.println("Error: No se encuentra el contenedor id:"+id);
				}else{
					continueTo = true;
				}
			}while(!continueTo);
			
			System.out.println("==================================");
			System.out.println("========= Modificar id: " + id);
			System.out.println(container.toStringConsola());
			System.out.println("===================================");

			Integer code 	= Keyin.inIntAllowEmpty("Codigo ("+container.getCode()+"): ");
			String brand 	= Keyin.inString("Marca ("+container.getBrand()+"): ");
			String model 	= Keyin.inString("Modelo ("+container.getModel()+"): ");
			Double capacity = Keyin.inDoubleAllowEmpty("Capacidad ("+container.getCapacity()+"): ");
			
			
			boolean hayCambios = false;

			
			if (code != null && container.getCode() != code) {
				container.setCode(code);
				hayCambios = true;
			}
			if (brand != null && !brand.isEmpty()
					&& !container.getBrand().trim().equals(brand)) {
				container.setBrand(brand);
				hayCambios = true;
			}
			if (model != null && !model.isEmpty()
					&& !container.getModel().trim().equals(model)) {
				container.setModel(model);
				hayCambios = true;
			}			
			
			if (capacity != null && container.getCapacity() != capacity) {
				container.setCapacity(capacity);
				hayCambios = true;
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
							long idGenerated = client.update(container);

							System.out
									.println("Contenedor Modificado correctamente, id: "
											+ idGenerated);

						}
						catch(CustomServiceException e){
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
			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor");
		}
		Keyin.inChar("presione enter tecla para continuar...");
	}

}
