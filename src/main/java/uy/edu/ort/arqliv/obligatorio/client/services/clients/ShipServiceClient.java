package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

/**
 * 
 * @author rodrigo
 *
 */
public class ShipServiceClient {

	private ShipService shipService;

	/**
	 * TODO
	 * @param shipService
	 */
	public void setShipService(ShipService shipService) {
		this.shipService = shipService;
	}
	/**
	 * TODO
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Ship ship) throws CustomServiceException {
		return shipService.store(MainSingleton.getInstance().getUser(), ship);
	}
	/**
	 * TODO
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Ship> list() throws CustomServiceException {
		return shipService.list(MainSingleton.getInstance().getUser());
	}
	/**
	 * TODO
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Ship ship) throws CustomServiceException {
		return shipService.store(MainSingleton.getInstance().getUser(), ship);
	}

	/**
	 * TODO
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Ship find(long id) throws CustomServiceException {
		return shipService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * TODO
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		shipService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
