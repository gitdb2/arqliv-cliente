package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.ArrivalService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

/**
 * 
 * @author rodrigo
 * 
 */
public class ArrivalServiceClient {

	private String BASE_URL = "http://localhost:8080/arqliv-web/rest"+"/arrivals";
	private String LIST   	= "/list?user={user}";
	private String CREATE 	= "/create?user={user}&shipId={shipId}&containers={containers}";
	private String UPDATE	= "/update?user={user}&shipId={shipId}&containers={containers}";
	private String FIND   	= "/find/{id}?user={user}";
	private String DELETE  	= "/delete/{id}?user={user}";
	
	
	private RestRequester<List<Arrival>> listRequester;
	private RestRequester<Arrival> objectRequester;
	private RestRequester<Long> longRequester;

	public ArrivalServiceClient(){
		listRequester = new RestRequester<>();
		objectRequester = new RestRequester<>();
		longRequester = new RestRequester<>();
	}

	
	private String parametrizeContainers(List<Long> containers){
		
		return StringUtils.arrayToCommaDelimitedString(containers.toArray());
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
				parametrizeContainers(containers));
//		return arrivalService.store(MainSingleton.getInstance().getUser(),
//				arrival, shipId, containers);
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
		
		//return arrivalService.list(MainSingleton.getInstance().getUser());
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
				parametrizeContainers(containers));
//		return arrivalService.update(MainSingleton.getInstance().getUser(),
//				arrival, shipId, containers);
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
//		return  arrivalService.find(MainSingleton.getInstance().getUser(), id);
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
//		arrivalService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
