package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.ContainerService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

/**
 * 
 * @author rodrigo
 *
 */
public class ContainerServiceClient {

	private ContainerService containerService;

	/**
	 * setter apra inyeccion de spring
	 * @param containerService
	 */
	public void setcontainerService(ContainerService containerService) {
		this.containerService = containerService;
	}
	/**
	 * Crea un Contenedor en el sistema, se retorna el id
	 * En caso de error se tira excepcion
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Container container) throws CustomServiceException {
		return containerService.store(MainSingleton.getInstance().getUser(), container);
	}
	/**
	 * LIsta todos los contenedores en el sistema
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Container> list() throws CustomServiceException {
		return containerService.list(MainSingleton.getInstance().getUser());
	}
	/**
	 * Actualiza la informacion del contenedor (el campo id debe estar cargado)
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Container container) throws CustomServiceException {
		return containerService.update(MainSingleton.getInstance().getUser(), container);
	}

	/**
	 * Se obtiene un contenedor por id
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Container find(long id) throws CustomServiceException {
		return containerService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * Elimina un contenedor por id, tira expcion en caso de estar en uso o no poderse realizar la operacion
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		containerService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
