package edu.um.ar.programacion2.ventas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import edu.um.ar.programacion2.ventas.model.Log;

public class LogService {
	
	public ResponseEntity<Log[]> findAll(String jwToken) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8100/log";
		MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", jwToken);
		//
		HttpEntity request = new HttpEntity("", headers);
		//
		final ResponseEntity<Log[]> exchange = restTemplate.exchange(url, HttpMethod.GET, request,
				Log[].class);
		return new ResponseEntity<Log[]>(exchange.getBody(), exchange.getStatusCode());
	}
	
	public ResponseEntity getLogByVentaId(Long ventaId, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/log/" + ventaId;
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			System.out.println("esto es: "+exchange.getBody());
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
