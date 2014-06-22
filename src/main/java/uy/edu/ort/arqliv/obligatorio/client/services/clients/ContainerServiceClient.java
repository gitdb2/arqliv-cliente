package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

/**
 * 
 * @author rodrigo
 *
 */
public class ContainerServiceClient {
	private final String SERVER		= MainSingleton
										.getInstance()
										.getProperty("rest.server", "http://localhost:8080/arqliv-web/rest");
	private final String ENTITY 	= "/containers";
	private final String BASE_URL	= SERVER + ENTITY;
	private final String LIST   	= "/list?user={user}";
	private final String CREATE 	= "/create?user={user}";
	private final String UPDATE		= "/update?user={user}";
	private final String FIND   	= "/find/{id}?user={user}";
	private final String DELETE  	= "/delete/{id}?user={user}";
	
	
	private RestRequester<List<Container>> listRequester;
	private RestRequester<Container> objectRequester;
	private RestRequester<Long> longRequester;

	public ContainerServiceClient(){
		listRequester = new RestRequester<>();
		objectRequester = new RestRequester<>();
		longRequester = new RestRequester<>();
	}

	
	/**
	 * Crea un Contenedor en el sistema, se retorna el id
	 * En caso de error se tira excepcion
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(Container container) throws CustomServiceException {
		return longRequester.postObject(
				BASE_URL+CREATE,
				container, 
				Long.class,
				MainSingleton.getInstance().getUser());
//		return containerService.store(MainSingleton.getInstance().getUser(), container);
	}
	/**
	 * LIsta todos los contenedores en el sistema
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Container> list() throws CustomServiceException {
		return listRequester.request(
				BASE_URL+LIST, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Container>>() {}, 
				MainSingleton.getInstance().getUser());
//		return containerService.list(MainSingleton.getInstance().getUser());
	}
	/**
	 * Actualiza la informacion del contenedor (el campo id debe estar cargado)
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Container container) throws CustomServiceException {
		return longRequester.postObject(
				BASE_URL+UPDATE,
				container, 
				Long.class,
				MainSingleton.getInstance().getUser());
	
//		return containerService.update(MainSingleton.getInstance().getUser(), container);
	}

	/**
	 * Se obtiene un contenedor por id
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Container find(long id) throws CustomServiceException {
		return objectRequester.request(
				BASE_URL+FIND, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<Container>() {},
				id,
				MainSingleton.getInstance().getUser()
				);
//		return containerService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * Elimina un contenedor por id, tira expcion en caso de estar en uso o no poderse realizar la operacion
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		longRequester.delete(BASE_URL+DELETE, 	
				id,
				MainSingleton.getInstance().getUser());
//		containerService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
