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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.thoughtworks.xstream.mapper.Mapper.Null;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.objeto.TarjetaCreditoObjeto;
import edu.um.ar.programacion2.ventas.repository.TarjetaCreditoRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TarjetaCreditoService {

	@Autowired
	private TarjetaCreditoRepository tarjetacreditoRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ClienteService clienteService;

	public ResponseEntity<TarjetaCreditoObjeto[]> findAll() {
		ResponseEntity<TarjetaCreditoObjeto[]> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8200/tarjetacredito/", TarjetaCreditoObjeto[].class);
		return new ResponseEntity<TarjetaCreditoObjeto[]>(responseEntity.getBody(), responseEntity.getStatusCode());
	}

	public TarjetaCreditoObjeto fById(Long id) {
		ResponseEntity<TarjetaCreditoObjeto> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8200/tarjetacredito/" + id, TarjetaCreditoObjeto.class);
		return responseEntity.getBody();
	}

	public ResponseEntity<String> createTarjetaCredito(TarjetaCreditoObjeto tarjetaObj) {
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new RestTemplate().postForEntity("http://localhost:8200/tarjetacredito/add", tarjetaObj,
					String.class);
			// parece que restTemplate serializa el objeto java a objeto JSON para el otro
			// servicio(al menos con peticion
			// tipo post)
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
	}

	public ResponseEntity<String> getTokenIdByNumero(Integer numero) {
		ResponseEntity<String> responseEntity;
		try {
			responseEntity = new RestTemplate().getForEntity("http://localhost:8200/tarjetacredito/token/" + numero,
					String.class);
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(responseEntity.getBody(), responseEntity.getStatusCode());
	}

	public boolean verify_numero(Integer numero) {
		boolean verificar_tarjeta = tarjetacreditoRepository.existsByNumero(numero);
		return verificar_tarjeta;
	}

	public ResponseEntity<String> deleteTarjetaCredito(String token) {
		ResponseEntity<String> deshabilitarTarjeta;
		try {
			HttpEntity request = new HttpEntity("");
			String url = "http://localhost:8200/tarjetacredito/delete/{tokenToDelete}";
			deshabilitarTarjeta = new RestTemplate().exchange(url, HttpMethod.DELETE, request, String.class, token);
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(deshabilitarTarjeta.getBody(), deshabilitarTarjeta.getStatusCode());
	}

	public ResponseEntity<String> updateTarjetaCredito(String token) {
		ResponseEntity<String> updateTarjetaCredito;
		try {
			HttpEntity request = new HttpEntity("");
			String url = "http://localhost:8200/tarjetacredito";
			updateTarjetaCredito = new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class, token);
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(updateTarjetaCredito.getBody(), updateTarjetaCredito.getStatusCode());
	}

}