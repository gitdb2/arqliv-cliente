import java.util.List;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import uy.edu.ort.arqliv.obligatorio.dominio.Ship;


public class RestClientTest {

	
	public void clientRestMain(){
		RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

	    String url = "http://localhost:8080/arqliv-web/rest/ships/list.json";
	    String json = restTemplate.getForObject(url, String.class, 2);
	    System.out.println(json);
	    
	    
	    ParameterizedTypeReference<List<Ship>> typeRef = new ParameterizedTypeReference<List<Ship>>() {};
	    ResponseEntity<List<Ship>> response = restTemplate.exchange("http://example.com", HttpMethod.GET, null, typeRef);
	    
	    List<Ship> ships = response.getBody();
	    
	    System.out.println(ships);
	    
//	    List<Ship> user = restTemplate.getForObject(url, List<Ship>.class, 2);
//	    System.out.println("code: " +user.getCode());
//	    System.out.println("name: " + user.getName());
	}
	

}