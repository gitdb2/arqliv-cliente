package pruebas;

import java.io.IOException;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uy.edu.ort.arqliv.obligatorio.common.exceptions.ErrorInfo;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

public class Pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		String url = "http://localhost:8080/arqliv-web/rest/ships/list.json";
		String json = restTemplate.getForObject(url, String.class, 2);
		System.out.println(json);

		{
			ParameterizedTypeReference<List<Ship>> typeRef = new ParameterizedTypeReference<List<Ship>>() {
			};
			ResponseEntity<List<Ship>> response = restTemplate.exchange(
					"http://localhost:8080/arqliv-web/rest/ships/list.json",
					HttpMethod.GET, null, typeRef);
			List<Ship> ships = response.getBody();
			System.out.println(ships);
		}
		{
			ParameterizedTypeReference<List<Ship>> typeRef = new ParameterizedTypeReference<List<Ship>>() {};

			try {
				ResponseEntity<List<Ship>> response = restTemplate
						.exchange(
								"http://localhost:8080/arqliv-web/rest/ships/error.json",
								HttpMethod.GET, null, typeRef);
				List<Ship> ships = response.getBody();
				System.out.println(ships);
			} catch (HttpClientErrorException e) {
				System.out.println(e.getStatusCode());
				String errorpayload = e.getResponseBodyAsString();
				ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
				   try {
					ErrorInfo errorInfo = mapper.readValue(errorpayload, ErrorInfo.class);
					System.out.println(errorInfo);
				} catch (JsonParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

}
