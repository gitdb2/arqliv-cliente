package uy.edu.ort.arqliv.obligatorio.client.rest.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
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

	private static final Logger logger = LoggerFactory
			.getLogger(RestRequester.class);

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
	public T request(String url, HttpMethod method,
			ParameterizedTypeReference<T> typeRef, Object... uriVariables)
			throws CustomServiceException {
		return request(url, method, null, typeRef, uriVariables);
	}

	/**
	 * 
	 * @param url
	 * @param method
	 * @param requestEntity
	 * @param uriVariables
	 * @return
	 */
	public T request(String url, HttpMethod method,
			HttpEntity<?> requestEntity, ParameterizedTypeReference<T> typeRef,
			Object... uriVariables) throws CustomServiceException {
		T result = null;
		try {
			ResponseEntity<T> response = this.restTemplate.exchange(url,
					method, requestEntity, typeRef, uriVariables);
			result = response.getBody();

		} catch (HttpServerErrorException | HttpClientErrorException e) {
			String errorpayload = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share
			ErrorInfo errorInfo = null; // globally
			try {
				errorInfo = mapper.readValue(errorpayload, ErrorInfo.class);

				if (errorInfo.getExceptionClassName() != null) {

					Class<?> c = Class.forName(errorInfo
							.getExceptionClassName());
					CustomServiceException ex = (CustomServiceException) c
							.getConstructor(String.class).newInstance(
									errorInfo.getMessage());
					throw ex;

				}

			} catch (JsonParseException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (JsonMappingException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (ClassNotFoundException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InstantiationException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalAccessException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalArgumentException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InvocationTargetException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (NoSuchMethodException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (SecurityException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			}
		} catch (ResourceAccessException e1) {
			throw new CustomServiceException(e1.getMessage(), e1);
		}
		return result;

	}

	/**
	 * 
	 * @param url
	 * @param request
	 * @param clasz
	 * @param uriVariables
	 * @return
	 */
	public T postObject(String url, Object request, Class<T> clasz, Object... uriVariables) {
		T result = null;
		try {

			result = restTemplate.postForObject(url, request, clasz, uriVariables);

		} catch (HttpServerErrorException | HttpClientErrorException e) {
			String errorpayload = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share
			ErrorInfo errorInfo = null; // globally
			try {
				errorInfo = mapper.readValue(errorpayload, ErrorInfo.class);

				if (errorInfo.getExceptionClassName() != null) {

					Class<?> c = Class.forName(errorInfo
							.getExceptionClassName());
					CustomServiceException ex = (CustomServiceException) c
							.getConstructor(String.class).newInstance(
									errorInfo.getMessage());
					throw ex;

				}

			} catch (JsonParseException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (JsonMappingException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (ClassNotFoundException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InstantiationException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalAccessException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalArgumentException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InvocationTargetException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (NoSuchMethodException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (SecurityException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			}
		} catch (ResourceAccessException e1) {
			throw new CustomServiceException(e1.getMessage(), e1);
		}
		return result;

	}

	
	public void delete(String url, Object... uriVariables) {
		T result = null;
		try {
			restTemplate.delete(url, uriVariables);

		} catch (HttpServerErrorException | HttpClientErrorException e) {
			String errorpayload = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper(); // can reuse, share
			ErrorInfo errorInfo = null; // globally
			try {
				errorInfo = mapper.readValue(errorpayload, ErrorInfo.class);

				if (errorInfo.getExceptionClassName() != null) {

					Class<?> c = Class.forName(errorInfo
							.getExceptionClassName());
					CustomServiceException ex = (CustomServiceException) c
							.getConstructor(String.class).newInstance(
									errorInfo.getMessage());
					throw ex;

				}

			} catch (JsonParseException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (JsonMappingException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (ClassNotFoundException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InstantiationException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalAccessException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (IllegalArgumentException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (InvocationTargetException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (NoSuchMethodException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			} catch (SecurityException e1) {
				logger.error(e1.getMessage(), e1);
				throw new CustomServiceException(e1.getMessage(), e1);
			}
		} catch (ResourceAccessException e1) {
			throw new CustomServiceException(e1.getMessage(), e1);
		}
	}
	
}
