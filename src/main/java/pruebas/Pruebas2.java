package pruebas;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import uy.edu.ort.arqliv.obligatorio.client.rest.utils.RestRequester;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

public class Pruebas2 {

	public static void main(String[] args) {
	
		RestRequester<List<Ship>> requester = new RestRequester<>();
		
		List<Ship> ships = requester.request("http://localhost:8080/arqliv-web/rest/ships/list.json", HttpMethod.GET, new ParameterizedTypeReference<List<Ship>>() {});
		
		List<Ship> ships2 = requester.request("http://localhost:8080/arqliv-web/rest/ships/error.json", HttpMethod.GET, new ParameterizedTypeReference<List<Ship>>() {});
		
	}

}
