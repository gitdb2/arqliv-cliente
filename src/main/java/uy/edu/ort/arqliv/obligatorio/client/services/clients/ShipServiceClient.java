package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.Date;
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
	 * Spring injection
	 * @param shipService
	 */
	public void setShipService(ShipService shipService) {
		this.shipService = shipService;
	}
	/**
	 * Crea un barco en la DB y retorna su id
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Ship ship) throws CustomServiceException {
		return shipService.store(MainSingleton.getInstance().getUser(), ship);
	}
	/**
	 * lista todos los barcos en el sistema
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Ship> list() throws CustomServiceException {
		return shipService.list(MainSingleton.getInstance().getUser());
	}
	/**
	 * Actualiza la informacion de un barco para una determinada 
	 * fecha (regla de negocio: no se puede modificar la capacidad de 
	 * un barco si no arribo al puerto en essa fecha)
	 * @param ship
	 * @param arrivalDate
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Ship ship, Date arrivalDate) throws CustomServiceException {
		return shipService.update(MainSingleton.getInstance().getUser(), ship, arrivalDate);
	}

	/**
	 * Retoran un barco por id
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Ship find(long id) throws CustomServiceException {
		return shipService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * da de baja un barco por id, si no esta usado en nungun arribo, de lo contrario tira excpcion
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		shipService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
