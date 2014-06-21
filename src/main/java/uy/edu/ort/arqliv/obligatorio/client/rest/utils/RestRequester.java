package uy.edu.ort.arqliv.obligatorio.client.rest.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.ErrorInfo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author rodrigo
 * 
 * @param <T>
 */
public class RestRequester<T> {

	private RestTemplate restTemplate;

	public RestRequester() {
		this.restTemplate = new RestTemplate();
		this.restTemplate.getMessageConverters().add(
				new StringHttpMessageConverter());
		this.restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
	}

	/**
	 * 
	 * @param url
	 * @param method
	 * @return
	 */
	public T send(String url, HttpMethod method,
			ParameterizedTypeReference<T> typeRef)
			throws CustomServiceException {
		return send(url, method, null, typeRef);
	}

	/**
	 * 
	 * @param url
	 * @param method
	 * @param requestEntity
	 * @param uriVariables
	 * @return
	 */
	public T send(String url, HttpMethod method, HttpEntity<?> requestEntity,
			ParameterizedTypeReference<T> typeRef, Object... uriVariables)
			throws CustomServiceException {
		T result = null;
		try {
			ResponseEntity<T> response = this.restTemplate.exchange(url,
					method, requestEntity, typeRef, uriVariables);
			result = response.getBody();
			System.out.println(result);

		} catch (HttpServerErrorException | HttpClientErrorException e) {
			System.out.println(e.getStatusCode());
			String errorpayload = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share
			ErrorInfo errorInfo = null; // globally
			try {
				errorInfo = mapper.readValue(errorpayload, ErrorInfo.class);
				System.out.println(errorInfo);
				// throw new CustomServiceException(errorInfo.getMessage());
//				errorInfo.setExceptionClassName("uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomInUseServiceException");
//

				
				if (errorInfo.getExceptionClassName() != null) {

					Class<?> c = Class.forName(errorInfo
							.getExceptionClassName());
					CustomServiceException ex = (CustomServiceException) c
							.getConstructor(String.class).newInstance(errorInfo.getMessage());
					throw ex;

				}

			} catch (JsonParseException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InstantiationException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (SecurityException e1) {
				e1.printStackTrace();
				throw new CustomServiceException(e1.getMessage(), e1);
			}
		}
		return result;

	}

}
