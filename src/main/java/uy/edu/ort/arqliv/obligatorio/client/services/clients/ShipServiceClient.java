package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.system.MainSingleton;
import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

public class ShipServiceClient {

	private ShipService shipService;

	public void setShipService(ShipService shipService) {
		this.shipService = shipService;
	}
	
	public Long createShip(Ship ship) throws CustomServiceException{
	//	ship = new Ship(11.0, 523, 55, "UY", 1978, "delicious");
		return shipService.store(MainSingleton.getInstance().getUser(), ship);
	}
	
	public List<Ship> listShips()throws CustomServiceException{
		return shipService.list(MainSingleton.getInstance().getUser());
	}
	
	public Long updateShip(Ship ship) throws CustomServiceException{
		return shipService.store(MainSingleton.getInstance().getUser(), ship);
	}

}
