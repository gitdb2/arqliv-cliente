package uy.edu.ort.arqliv.obligatorio.client.menus.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.ContainerServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomInUseServiceException;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;
/**
 * 
 * @author rodrigo
 *
 */
public class MenuContainerDelete implements Renderer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void render() {

		ContainerServiceClient client = (ContainerServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientesConstants.ContainerClient);

		System.out.println("============= BAJA Contenerdor ==================");

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
			
			
			System.out.println("===============================");
			System.out.println("========= Eliminar id: " + id);
			System.out.println(container.toStringConsola());
			System.out.println("================================");

			boolean exit = false;
			while (!exit) {
				char swValue = Keyin.inChar(" Eliminar? (s/n): ");

				switch (swValue) {
				case 'n':
				case 'N':
					System.out.println("Delete Cancelado.");
					exit = true;
					break;

				case 's':
				case 'S':
					try {
						exit = true;
						client.delete(id);

						System.out
								.println("Contenedor Eliminado correctamente, id: " + id);

					} 
					catch(CustomInUseServiceException inUseEx){
						log.error("No se puede eliminar el Contenedor porque esta en uso", inUseEx);
						System.out.println("Error: No se puede eliminar el Contenedor porque esta en uso.");
					}
					catch (Exception e) {
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

		} catch (CustomServiceException e) {
			e.printStackTrace();
			log.error("Problema al contactar al server", e);
			System.out.println("Error: Al contactar al servidor");
		}
		Keyin.inChar("presione enter tecla para continuar...");
	}

}
