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
	 * TODO
	 * @param containerService
	 */
	public void setcontainerService(ContainerService containerService) {
		this.containerService = containerService;
	}
	/**
	 * TODO
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Container container) throws CustomServiceException {
		return containerService.store(MainSingleton.getInstance().getUser(), container);
	}
	/**
	 * TODO
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Container> list() throws CustomServiceException {
		return containerService.list(MainSingleton.getInstance().getUser());
	}
	/**
	 * TODO
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Container container) throws CustomServiceException {
		return containerService.update(MainSingleton.getInstance().getUser(), container);
	}

	/**
	 * TODO
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Container find(long id) throws CustomServiceException {
		return containerService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * TODO
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		containerService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
