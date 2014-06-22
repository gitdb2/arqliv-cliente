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
public class ReportsServiceClient {

	private final String SERVER 	= MainSingleton.getInstance()
										.getProperty("rest.server", "http://localhost:8080/arqliv-web/rest");
	private final String ENTITY 	= "/reports";
	private final String BASE_URL 	= SERVER + ENTITY;
	
	private final String ARR_BY_MOTH 		= "/arrivalsByMonth/{id}?user={user}";
	private final String ARR_BY_MOTH_SHIP 	= "/arrivalsByMonthByShip/{id}/{shipId}?user={user}";
	
	private RestRequester<List<Arrival>> listArrivalRequester;

	public ReportsServiceClient() {
		listArrivalRequester = new RestRequester<>();
	}

	/**
	 * Retorna los arrivals que para un mes (1 Enero)
	 * 
	 * @param month
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> arrivalsByMonth(int month) throws CustomServiceException {
		return listArrivalRequester.request(
				BASE_URL+ARR_BY_MOTH, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Arrival>>() {}, 
				month,
				MainSingleton.getInstance().getUser());
	}

	/**
	 * Lista los arrivals filtrando por me y por id barco
	 * 
	 * @param month
	 * @param shipId
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> arrivalsByMonthByShip(int month, Long shipId)
			throws CustomServiceException {
		return listArrivalRequester.request(
				BASE_URL+ARR_BY_MOTH_SHIP, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Arrival>>() {}, 
				month, 
				shipId,
				MainSingleton.getInstance().getUser());
	}

}
