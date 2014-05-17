package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.ArrivalService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

/**
 * 
 * @author rodrigo
 * 
 */
public class ArrivalServiceClient {

	private ArrivalService arrivalService;

	/**
	 * TODO
	 * 
	 * @param containerService
	 */
	public void setArrivalService(ArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}

	/**
	 * TODO
	 * 
	 * @param arrival
	 * @param shipId
	 * @param containers
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Arrival arrival, Long shipId, List<Long> containers)
			throws CustomServiceException {
		return arrivalService.store(MainSingleton.getInstance().getUser(),
				arrival, shipId, containers);
	}

	/**
	 * TODO
	 * 
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> list() throws CustomServiceException {
		return arrivalService.list(MainSingleton.getInstance().getUser());
	}

	/**
	 * TODO
	 * @param arrival
	 * @param shipId
	 * @param containers
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Arrival arrival, Long shipId, List<Long> containers) throws CustomServiceException {
		return arrivalService.update(MainSingleton.getInstance().getUser(),
				arrival, shipId, containers);
	}

	/**
	 * TODO
	 * 
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Arrival find(long id) throws CustomServiceException {
		return  arrivalService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * TODO
	 * 
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		arrivalService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
