package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.DepartureService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

/**
 * 
 * @author rodrigo
 * 
 */
public class DepartureServiceClient {

	private DepartureService departureService;

	/**
	 * Setter para inyeccion del servicio spring
	 * 
	 * @param departureService
	 */
	public void setArrivalService(DepartureService departureService) {
		this.departureService = departureService;
	}

	/**
	 * Crea un departure en el sistema indicandole el id del barco y 
	 * la lista de ids de contenedores.
	 * En caso de error se tira excepcion
	 * @param departure
	 * @param shipId
	 * @param containers
	 * @return el id Del arrival creado
	 * @throws CustomServiceException
	 */
	public Long create(Departure departure, Long shipId, List<Long> containers) throws CustomServiceException {
		return departureService.store(MainSingleton.getInstance().getUser(), departure, shipId, containers);
	}

	/**
	 *	Listado de todas las partidas del sistema
	 * 
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Departure> list() throws CustomServiceException {
		return departureService.list(MainSingleton.getInstance().getUser());
	}

	/**
	 * Modifica el departure recibido, se le pasa el id del nuevo barco o 
	 * el mismo id que tenia si no se modifica, y la nueva lista de ids de contenedores,
	 * se deber enviar haya cambiado o no (con los que ya tenia)
	 * En caso de error se tira excepcion
	 * @param departure
	 * @param shipId
	 * @param containers
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(Departure departure, Long shipId, List<Long> containers) throws CustomServiceException {
		return departureService.update(MainSingleton.getInstance().getUser(), departure, shipId, containers);
	}

	/**
	 * Obtiene un departure por id (carga eager de atributos)
	 * 
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Departure find(long id) throws CustomServiceException {
		return  departureService.find(MainSingleton.getInstance().getUser(), id);
	}

	/**
	 * elimina un departure por id
	 * 
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(long id) throws CustomServiceException {
		departureService.delete(MainSingleton.getInstance().getUser(), id);
	}
	
}
