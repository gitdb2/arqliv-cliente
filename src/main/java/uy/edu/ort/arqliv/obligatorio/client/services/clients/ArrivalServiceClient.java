package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.ArrivalService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

/**
 * 
 * @author rodrigo
 * 
 */
public class ArrivalServiceClient {

	private ArrivalService arrivalService;

	/**
	 * Setter para inyeccion del servicio spring
	 * 
	 * @param containerService
	 */
	public void setArrivalService(ArrivalService arrivalService) {
		this.arrivalService = arrivalService;
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
		return arrivalService.store(MainSingleton.getInstance().getUser(),
				arrival, shipId, containers);
	}

	/**
	 *	Listado de todos los arribos en el sistema
	 * 
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> list() throws CustomServiceException {
		return arrivalService.list(MainSingleton.getInstance().getUser());
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
		return arrivalService.update(MainSingleton.getInstance().getUser(),
				arrival, shipId, containers);
	}

	/**
	 * Obtiene un arrival por id (carga eaguer de atributos)
	 * 
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Arrival find(long id) throws CustomServiceException {
		return  arrivalService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * elimina un arrival por id
	 * 
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		arrivalService.delete(MainSingleton.getInstance().getUser(), id);
	}
}
