package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.ReportsService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

/**
 * 
 * @author rodrigo
 *
 */
public class ReportsServiceClient {

	private ReportsService reportsService;

	public void setReportsService(ReportsService reportsService) {
		this.reportsService = reportsService;
	}

	/**
	 * 
	 * @param month
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> arrivalsByMonth(int month) throws CustomServiceException {
		return reportsService.arrivalsByMonth(MainSingleton.getInstance().getUser(), month);
	}
	
	public List<Arrival> arrivalsByMonthByShip(int month, Long shipId) throws CustomServiceException {
		return reportsService.arrivalsByMonthByShip(MainSingleton.getInstance().getUser(), month, shipId);
	}
	
}
