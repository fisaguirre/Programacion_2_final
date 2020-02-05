package edu.um.ar.programacion2.ventas.service;

import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;

import edu.um.ar.programacion2.ventas.dto.TarjetaCreditoDto;

//import com.thoughtworks.xstream.mapper.Mapper.Null;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.repository.TarjetaCreditoRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class TarjetaCreditoService {

	@Autowired
	private TarjetaCreditoRepository tarjetacreditoRepository;
	/*
	 * @Autowired private RestTemplate restTemplate;
	 */
	@Autowired
	private ClienteService clienteService;

	public ResponseEntity<TarjetaCreditoDto[]> findAll(String jwToken) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8200/tarjetacredito";
		MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", jwToken);
		//
		HttpEntity request = new HttpEntity("", headers);
		//
		final ResponseEntity<TarjetaCreditoDto[]> exchange = restTemplate.exchange(url, HttpMethod.GET, request,
				TarjetaCreditoDto[].class);
		return new ResponseEntity<TarjetaCreditoDto[]>(exchange.getBody(), exchange.getStatusCode());
	}

	public ResponseEntity getTokenIdByNumero(Long numero, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito/token/" + numero;
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			System.out.println("esto es: "+exchange.getBody());
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
			/*
			 * responseEntity = new
			 * RestTemplate().getForEntity("http://localhost:8200/tarjetacredito/token/" +
			 * numero, Object.class); return new ResponseEntity<>(responseEntity.getBody(),
			 * responseEntity.getStatusCode());
			 */
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	public TarjetaCreditoDto fById(Long id) {
		ResponseEntity<TarjetaCreditoDto> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8200/tarjetacredito/" + id, TarjetaCreditoDto.class);
		return responseEntity.getBody();
	}

	public ResponseEntity createTarjetaCredito(TarjetaCreditoDto tarjetaDto, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito";
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity(tarjetaDto, headers);
			//
			final ResponseEntity exchange = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

			return new ResponseEntity<>(exchange.getBody(), exchange.getStatusCode());
			// parece que restTemplate serializa el objeto java a objeto JSON para el otro
			// servicio(al menos con peticion
			// tipo post)
			// return new ResponseEntity<>(responseEntity.getBody(),
			// responseEntity.getStatusCode());

		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity deleteTarjetaCredito(String token, String jwToken) {
		// ResponseEntity<String> deshabilitarTarjeta;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito/" + token;
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.DELETE, request,
					String.class);
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
			/*
			 * HttpEntity request = new HttpEntity(""); String url =
			 * "http://localhost:8200/tarjetacredito/{tokenToDelete}"; deshabilitarTarjeta =
			 * new RestTemplate().exchange(url, HttpMethod.DELETE, request, String.class,
			 * token);
			 */
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// return new ResponseEntity<Object>(deshabilitarTarjeta.getBody(),
		// deshabilitarTarjeta.getStatusCode());
	}

	public ResponseEntity updateTarjetaCredito(String token, String jwToken) {
		// ResponseEntity<String> updateTarjetaCredito;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito/" + token;
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
			/*
			 * HttpEntity request = new HttpEntity(""); String url =
			 * "http://localhost:8200/tarjetacredito/{token}"; updateTarjetaCredito = new
			 * RestTemplate().exchange(url, HttpMethod.PUT, request, String.class, token);
			 */
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// return new ResponseEntity<String>(updateTarjetaCredito.getBody(),
		// updateTarjetaCredito.getStatusCode());
	}

}