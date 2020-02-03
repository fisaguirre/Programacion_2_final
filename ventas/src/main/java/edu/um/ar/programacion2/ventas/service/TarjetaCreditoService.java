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
//import org.springframework.web.client.RestTemplate;

import edu.um.ar.programacion2.ventas.dto.TarjetaCreditoDto;

//import com.thoughtworks.xstream.mapper.Mapper.Null;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.repository.TarjetaCreditoRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class TarjetaCreditoService {

	@Autowired
	private TarjetaCreditoRepository tarjetacreditoRepository;
/*
	@Autowired
	private RestTemplate restTemplate;
*/
	@Autowired
	private ClienteService clienteService;

	public ResponseEntity<TarjetaCreditoDto[]> findAll() {
		ResponseEntity<TarjetaCreditoDto[]> responseEntity;
		responseEntity = new RestTemplate().getForEntity("http://localhost:8200/tarjetacredito",
				TarjetaCreditoDto[].class);
		return new ResponseEntity<TarjetaCreditoDto[]>(responseEntity.getBody(), responseEntity.getStatusCode());
	}

	public TarjetaCreditoDto fById(Long id) {
		ResponseEntity<TarjetaCreditoDto> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8200/tarjetacredito/" + id, TarjetaCreditoDto.class);
		return responseEntity.getBody();
	}

	public ResponseEntity<Object> createTarjetaCredito(TarjetaCreditoDto tarjetaDto) {
		try {
			ResponseEntity<Object> responseEntity = new RestTemplate().postForEntity("http://localhost:8200/tarjetacredito/add", tarjetaDto,
					Object.class);
			// parece que restTemplate serializa el objeto java a objeto JSON para el otro
			// servicio(al menos con peticion
			// tipo post)
			return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

	public ResponseEntity<Object> getTokenIdByNumero(Long numero) {
		ResponseEntity<Object> responseEntity;
		try {
			responseEntity = new RestTemplate().getForEntity("http://localhost:8200/tarjetacredito/token/" + numero,
					Object.class);
			return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	public boolean verify_numero(Integer numero) {
		boolean verificar_tarjeta = tarjetacreditoRepository.existsByNumero(numero);
		return verificar_tarjeta;
	}

	public ResponseEntity<String> deleteTarjetaCredito(String token) {
		ResponseEntity<String> deshabilitarTarjeta;
		try {
			HttpEntity request = new HttpEntity("");
			String url = "http://localhost:8200/tarjetacredito/{tokenToDelete}";
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
			String url = "http://localhost:8200/tarjetacredito/{token}";
			updateTarjetaCredito = new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class, token);
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(updateTarjetaCredito.getBody(), updateTarjetaCredito.getStatusCode());
	}

}