package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

/**
 * 
 * @author rodrigo
 * 
 */
public class DepartureServiceClient {
	
	private final String SERVER		= MainSingleton
										.getInstance()
										.getProperty("rest.server", "http://localhost:8080/arqliv-web/rest");
	private final String ENTITY 	= "/departures";
	private final String BASE_URL 	= SERVER + ENTITY;
	private final String LIST   	= "/list?user={user}";
	private final String CREATE 	= "/create?user={user}&shipId={shipId}&arrivalId={arrivalId}&containers={containers}";
	private final String UPDATE		= "/update?user={user}&shipId={shipId}&arrivalId={arrivalId}&containers={containers}";
	private final String FIND   	= "/find/{id}?user={user}";
	private final String DELETE  	= "/delete/{id}?user={user}";

	private RestRequester<List<Departure>> listRequester;
	private RestRequester<Departure> objectRequester;
	private RestRequester<Long> longRequester;

	public DepartureServiceClient(){
		listRequester = new RestRequester<>();
		objectRequester = new RestRequester<>();
		longRequester = new RestRequester<>();
	}

	/**
	 * Crea un departure en el sistema indicandole el id del barco y 
	 * la lista de ids de contenedores.
	 * En caso de error se tira excepcion
	 * @param departure
	 * @param shipId
	 * @param containers
	 * @param arrivalId TODO
	 * @return el id del departure creado
	 * @throws CustomServiceException
	 */
	public Long create(Departure departure, Long shipId, List<Long> containers, Long arrivalId) throws CustomServiceException {
		return longRequester.postObject(
				BASE_URL+CREATE,
				departure, 
				Long.class,
				MainSingleton.getInstance().getUser(), 
				shipId,
				arrivalId,
				RestRequester.parametrizeLongList(containers));
	}

	/**
	 *	Listado de todas las partidas del sistema
	 * 
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Departure> list() throws CustomServiceException {
		return listRequester.request(
				BASE_URL+LIST, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Departure>>() {}, 
				MainSingleton.getInstance().getUser());
	}

	/**
	 * Modifica el departure recibido, se le pasa el id del nuevo barco o 
	 * el mismo id que tenia si no se modifica, y la nueva lista de ids de contenedores,
	 * se deber enviar haya cambiado o no (con los que ya tenia)
	 * En caso de error se tira excepcion
	 * @param departure
	 * @param shipId
	 * @param containers
	 * @param arrivalId TODO
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Departure departure, Long shipId, List<Long> containers, Long arrivalId) throws CustomServiceException {
		return longRequester.postObject(
				BASE_URL+UPDATE,
				departure, 
				Long.class,
				MainSingleton.getInstance().getUser(), 
				shipId,
				arrivalId,
				RestRequester.parametrizeLongList(containers));
	}

	/**
	 * Obtiene un departure por id (carga eager de atributos)
	 * 
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Departure find(long id) throws CustomServiceException {
		return objectRequester.request(
				BASE_URL+FIND, 
				HttpMethod.GET,
				new ParameterizedTypeReference<Departure>() {},
				id,
				MainSingleton.getInstance().getUser()
				);
	}

	/**
	 * elimina un departure por id
	 * 
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		longRequester.delete(BASE_URL+DELETE, 	
				id,
				MainSingleton.getInstance().getUser());	}
	
}
