package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.Date;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Pair;

/**
 * 
 * @author rodrigo
 *
 */
public class ProfilingServiceClient {

	private final String SERVER			= MainSingleton
											.getInstance()
											.getProperty("rest.server", "http://localhost:8080/arqliv-web/rest");
	private final String ENTITY 		= "/profiling";
	private final String BASE_URL 		= SERVER + ENTITY;
	private final String AVG_SERV_TIME	= "/avgServiceTime?user={user}&forDate={forDate}";
	private final String MIN_SERV_TIME 	= "/minServiceTime?user={user}&forDate={forDate}";
	private final String MAX_SERV_TIME	= "/maxServiceTime?user={user}&forDate={forDate}";

	private RestRequester<List<Pair<String, Long>>> listLongRequester;
	private RestRequester<List<Pair<String, Double>>> listDoubleRequester;

	public ProfilingServiceClient(){
		listLongRequester = new RestRequester<>();
		listDoubleRequester = new RestRequester<>();
	}

	/**
	 * Consulta de tiempo promedio de ejecucion para una fecha determinada
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Pair<String, Double>> avgServiceTime(Date forDate) throws CustomServiceException {
		return listDoubleRequester.request(
				BASE_URL+AVG_SERV_TIME, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Pair<String, Double>>>() {}, 
				MainSingleton.getInstance().getUser(), 
				RestRequester.formatDate(forDate)
				);
	}
	
	/**
	 *  Consulta de tiempo minimo de ejecucion para una fecha determinada
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Pair<String, Long>> minServiceTime(Date forDate) throws CustomServiceException {
		
		return listLongRequester.request(
				BASE_URL+MIN_SERV_TIME, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Pair<String, Long>>>() {}, 
				MainSingleton.getInstance().getUser(), 
				RestRequester.formatDate(forDate)
				);
	}
	
	/**
	 * Consulta de tiempo maximo de ejecucion para una fecha determinada
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Pair<String, Long>> maxServiceTime(Date forDate) throws CustomServiceException {
		return listLongRequester.request(
				BASE_URL+MAX_SERV_TIME, 
				HttpMethod.GET, 
				new ParameterizedTypeReference<List<Pair<String, Long>>>() {}, 
				MainSingleton.getInstance().getUser(), 
				RestRequester.formatDate(forDate)
				);
	}
	
}
