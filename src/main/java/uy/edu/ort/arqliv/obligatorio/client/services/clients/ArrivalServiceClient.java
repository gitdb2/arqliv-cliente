package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

/**
 * 
 * @author rodrigo
 * 
 */
public class ArrivalServiceClient {
	private final String SERVER		= MainSingleton
										.getInstance()
										.getProperty("rest.server", "http://localhost:8080/arqliv-web/rest");
	private final String ENTITY 	= "/arrivals";
	private final String BASE_URL	= SERVER + ENTITY;
	
	private final String LIST   	= "/list?user={user}";
	private final String CREATE 	= "/create?user={user}&shipId={shipId}&containers={containers}";
	private final String UPDATE		= "/update?user={user}&shipId={shipId}&containers={containers}";
	private final String FIND   	= "/find/{id}?user={user}";
	private final String DELETE  	= "/delete/{id}?user={user}";
	
	
	private RestRequester<List<Arrival>> listRequester;
	private RestRequester<Arrival> objectRequester;
	private RestRequester<Long> longRequester;

	public ArrivalServiceClient(){
		listRequester = new RestRequester<>();
		objectRequester = new RestRequester<>();
		longRequester = new RestRequester<>();
	}

	
	
	/**
	 * Crea un arrival en el sistema indicandole el id del barco y 
	 * la lista de ids de contenedores. retorna el id Del arrival creado
	 * En caso de error se tira excepcion
	 * @param arrival
	 * @param shipId
	 * @param containers
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Arrival arrival, Long shipId, List<Long> containers)
			throws CustomServiceException {
		
		return longRequester.postObject(
				BASE_URL+CREATE,
				arrival, 
				Long.class,
				MainSingleton.getInstance().getUser(), 
				shipId,
				RestRequester.parametrizeLongList(containers));
	}

	/**
	 *	Listado de todos los arribos en el sistema
	 * 
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> list() throws CustomServiceException {
		return listRequester.request(
				BASE_URL+LIST, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Arrival>>() {}, 
				MainSingleton.getInstance().getUser());
	}

	/**
	 * Modifica el arrival recibido, se le pasa el id del nuevo barco o 
	 * el mismo id que tenia si no se modifica, y la nueva lista de ids de contetenedores,
	 * se deber enviar haya cambiado o no (con los que ya tenia)
	 * En caso de error se tira excepcion
	 * @param arrival
	 * @param shipId
	 * @param containers
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Arrival arrival, Long shipId, List<Long> containers) throws CustomServiceException {
		return longRequester.postObject(
				BASE_URL+UPDATE,
				arrival, 
				Long.class,
				MainSingleton.getInstance().getUser(), 
				shipId,
				RestRequester.parametrizeLongList(containers));
	}

	/**
	 * Obtiene un arrival por id (carga eaguer de atributos)
	 * 
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Arrival find(long id) throws CustomServiceException {
		return objectRequester.request(
				BASE_URL+FIND, 
				HttpMethod.GET,
				new ParameterizedTypeReference<Arrival>() {},
				id,
				MainSingleton.getInstance().getUser()
				);
	}

	/**
	 * elimina un arrival por id
	 * 
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		longRequester.delete(BASE_URL+DELETE, 	
				id,
				MainSingleton.getInstance().getUser());
	}
}
