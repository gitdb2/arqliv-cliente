package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

/**
 * 
 * @author rodrigo
 *
 */
public class ShipServiceClient {

	
	private final String SERVER 	= MainSingleton
										.getInstance()
										.getProperty("rest.server", "http://localhost:8080/arqliv-web/rest");
	private final String ENTITY 	= "/ships";
	private final String BASE_URL 	= SERVER + ENTITY;
	
	private final String LIST   	= "/list?user={user}";
	private final String CREATE 	= "/create?user={user}";
	private final String UPDATE		= "/update?user={user}&arrivalDate={arrivalDate}";
	private final String FIND   	= "/find/{id}?user={user}";
	private final String DELETE  	= "/delete/{id}?user={user}";
	
	
	private RestRequester<List<Ship>> listRequester;
	private RestRequester<Ship> objectRequester;
	private RestRequester<Long> longRequester;

	public ShipServiceClient(){
		listRequester = new RestRequester<>();
		objectRequester = new RestRequester<>();
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
	}
	/**
	 * lista todos los barcos en el sistema
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Ship> list() throws CustomServiceException {
		return listRequester.request(
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
		return longRequester.postObject(
				BASE_URL+UPDATE,
				ship, 
				Long.class,
				MainSingleton.getInstance().getUser(), 
				RestRequester.formatDate(arrivalDate));
	}

	/**
	 * Retoran un barco por id
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Ship find(long id) throws CustomServiceException {
		return objectRequester.request(
				BASE_URL+FIND, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<Ship>() {},
				id,
				MainSingleton.getInstance().getUser()
				);

		//return shipService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * da de baja un barco por id, si no esta usado en nungun arribo, de lo contrario tira excpcion
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		longRequester.delete(BASE_URL+DELETE, 	
				id,
				MainSingleton.getInstance().getUser());
	}
}
