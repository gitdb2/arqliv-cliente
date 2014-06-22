package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
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

	private String BASE_URL = "http://localhost:8080/arqliv-web/rest"+"/ships";
	private String LIST = "/list?user={user}";
	private String CREATE = "/create?user={user}";
	
	private RestRequester<List<Ship>> shipListRequester;
	private RestRequester<Ship> shipRequester;
	private RestRequester<Long> longRequester;

	public ShipServiceClient(){
		shipListRequester = new RestRequester<>();
		shipRequester = new RestRequester<>();
		longRequester = new RestRequester<>();
	}
	
	/**
	 * Crea un barco en la DB y retorna su id
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Ship ship) throws CustomServiceException {

		return longRequester.postObject(
				BASE_URL+CREATE,
				ship, 
				Long.class,
				MainSingleton.getInstance().getUser());

		
//		return shipService.store(MainSingleton.getInstance().getUser(), ship);
	}
	/**
	 * lista todos los barcos en el sistema
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Ship> list() throws CustomServiceException {
		return shipListRequester.request(
					BASE_URL+LIST, 
					HttpMethod.GET, 
					new ParameterizedTypeReference<List<Ship>>() {}, 
					MainSingleton.getInstance().getUser());
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
		return 0L;
		//return shipService.update(MainSingleton.getInstance().getUser(), ship, arrivalDate);
	}

	/**
	 * Retoran un barco por id
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Ship find(long id) throws CustomServiceException {
		return null;
		//return shipService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * da de baja un barco por id, si no esta usado en nungun arribo, de lo contrario tira excpcion
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		//shipService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
