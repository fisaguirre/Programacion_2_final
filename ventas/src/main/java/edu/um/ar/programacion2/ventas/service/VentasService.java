package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import edu.um.ar.programacion2.ventas.dto.TarjetaCreditoDto;
import edu.um.ar.programacion2.ventas.dto.VentasDto;
import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.Log;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.repository.VentasRepository;

@Service
public class VentasService {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private VentasRepository ventasRepository;

	public ResponseEntity<List<Ventas>> findAll() {
		return new ResponseEntity<List<Ventas>>(ventasRepository.findAll(), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public Optional<Ventas> findById(Long id) {
		return ventasRepository.findById(id);
	}

	public ResponseEntity<String> createVenta(VentasDto ventasDto, String jwToken) {
		Optional<Cliente> optionalCliente = clienteService.findById(ventasDto.getCliente_id());
		if (optionalCliente.isPresent()) {
			Cliente cliente_encontrado = optionalCliente.get();
			ResponseEntity<String> verificacionTarjeta = verificarTarjeta(ventasDto.getToken(), jwToken);
			if ((verificacionTarjeta.getStatusCode()) == HttpStatus.OK) {
				ResponseEntity<String> verificarVencimiento = verificarVencimiento(ventasDto.getToken(), jwToken);
				if (verificarVencimiento.getStatusCode() == HttpStatus.OK) {
					ResponseEntity<String> verificarMontoTarjeta = verificarMontoTarjeta(ventasDto.getMonto(),
							ventasDto.getToken(), jwToken);
					if (verificarMontoTarjeta.getStatusCode() == HttpStatus.OK) {
						ResponseEntity<TarjetaCredito> findTarjetaByToken = findTarjetaByToken(ventasDto.getToken(),
								jwToken);
						if (findTarjetaByToken.getBody().getActivo()) {
							if (findTarjetaByToken.getBody().getCliente_id().getId()
									.equals(ventasDto.getCliente_id())) {
								Ventas verificacionVenta = ventaFinal(ventasDto, cliente_encontrado, false);
								if (verificacionVenta != null) {
									Ventas ventaValida = ventaFinal(ventasDto, cliente_encontrado, false);
									crearLog(ventaValida.getId(), "verificacion de cliente", "OK",
											"El cliente esta registrado", jwToken);
									crearLog(ventaValida.getId(), "verificacion de tarjeta", "OK",
											verificacionTarjeta.getBody(), jwToken);
									crearLog(ventaValida.getId(), "verificacion de monto de tarjeta", "OK",
											verificacionTarjeta.getBody(), jwToken);
									crearLog(ventaValida.getId(), "verificacion de tarjeta habilitada", "OK",
											verificacionTarjeta.getBody(), jwToken);
									crearLog(ventaValida.getId(), "verificacion de pertenencia de tarjeta a cliente",
											"OK", verificacionTarjeta.getBody(), jwToken);
									return new ResponseEntity<String>("Venta realizada con exito)", HttpStatus.OK);
								} else {
									return new ResponseEntity<String>("La venta no se concreto",
											HttpStatus.BAD_REQUEST);
								}
							} else {
								Ventas ventaInvalida = ventaFinal(ventasDto, cliente_encontrado, false);
								crearLog(ventaInvalida.getId(), "verificacion de cliente", "OK",
										"El cliente esta registrado", jwToken);
								crearLog(ventaInvalida.getId(), "verificacion de tarjeta", "OK",
										verificacionTarjeta.getBody(), jwToken);
								crearLog(ventaInvalida.getId(), "verificacion de monto de tarjeta", "OK",
										verificacionTarjeta.getBody(), jwToken);
								crearLog(ventaInvalida.getId(), "verificacion de tarjeta habilitada", "OK",
										verificacionTarjeta.getBody(), jwToken);
								crearLog(ventaInvalida.getId(), "verificacion de pertenencia de tarjeta a cliente",
										"FALLO", verificacionTarjeta.getBody(), jwToken);
								return new ResponseEntity<String>("La tarjeta no le pertenece al cliente",
										HttpStatus.BAD_REQUEST);
							}
						} else {
							Ventas ventaInvalida = ventaFinal(ventasDto, cliente_encontrado, false);
							crearLog(ventaInvalida.getId(), "verificacion de cliente", "OK",
									"El cliente esta registrado", jwToken);
							crearLog(ventaInvalida.getId(), "verificacion de tarjeta", "OK",
									verificacionTarjeta.getBody(), jwToken);
							crearLog(ventaInvalida.getId(), "verificacion de monto de tarjeta", "OK",
									verificacionTarjeta.getBody(), jwToken);
							crearLog(ventaInvalida.getId(), "verificacion de tarjeta habilitada", "FALLO",
									verificacionTarjeta.getBody(), jwToken);
							return new ResponseEntity<String>("La tarjeta no esta habilitada", HttpStatus.BAD_REQUEST);
						}
					} else {
						Ventas ventaInvalida = ventaFinal(ventasDto, cliente_encontrado, false);
						crearLog(ventaInvalida.getId(), "verificacion de cliente", "OK", "El cliente esta registrado",
								jwToken);
						crearLog(ventaInvalida.getId(), "verificacion de tarjeta", "OK", verificacionTarjeta.getBody(),
								jwToken);
						crearLog(ventaInvalida.getId(), "verificacion de monto de tarjeta", "FALLO",
								verificacionTarjeta.getBody(), jwToken);
						return new ResponseEntity<String>(verificarMontoTarjeta.getBody(),
								verificarMontoTarjeta.getStatusCode());
					}
				} else {
					Ventas ventaInvalida = ventaFinal(ventasDto, cliente_encontrado, false);
					crearLog(ventaInvalida.getId(), "verificacion de cliente", "OK", "El cliente esta registrado",
							jwToken);
					crearLog(ventaInvalida.getId(), "verificacion de tarjeta", "OK", verificacionTarjeta.getBody(),
							jwToken);
					crearLog(ventaInvalida.getId(), "verificacion de vencimiento de tarjeta", "FALLO",
							verificacionTarjeta.getBody(), jwToken);
					return new ResponseEntity<String>(verificacionTarjeta.getBody(),
							verificacionTarjeta.getStatusCode());
				}
			} else {
				Ventas ventaInvalida = ventaFinal(ventasDto, cliente_encontrado, false);
				crearLog(ventaInvalida.getId(), "verificacion de cliente", "OK", "El cliente esta registrado", jwToken);
				crearLog(ventaInvalida.getId(), "verificacion de tarjeta", "FALLO", verificacionTarjeta.getBody(),
						jwToken);
				return new ResponseEntity<String>(verificacionTarjeta.getBody(), verificacionTarjeta.getStatusCode());
			}

		} else {
			return new ResponseEntity<String>("El cliente no se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
	}

	public Ventas ventaFinal(VentasDto ventasDto, Cliente cliente, boolean valido) {
		Ventas nueva_venta = new Ventas(ventasDto.getMonto(), cliente, ventasDto.getToken(), valido);
		Ventas venta = ventasRepository.save(nueva_venta);
		if (venta != null) {
			return venta;
		}
		return null;
	}

	public ResponseEntity<String> verificarTarjeta(String token, String jwToken) {
		String url = "http://localhost:8200/tarjetacredito/" + token;
		HttpMethod method = HttpMethod.GET;
		ResponseEntity<String> respuestaConsultaTarjeta;
		respuestaConsultaTarjeta = consultaToTarjeta(url, method, jwToken);
		return new ResponseEntity<String>(respuestaConsultaTarjeta.getBody(), respuestaConsultaTarjeta.getStatusCode());
	}

	public ResponseEntity<String> verificarVencimiento(String token, String jwToken) {
		String url = "http://localhost:8200/tarjetacredito/vencimiento/" + token;
		HttpMethod method = HttpMethod.GET;
		ResponseEntity<String> respuestaConsultaTarjeta;
		respuestaConsultaTarjeta = consultaToTarjeta(url, method, jwToken);
		return new ResponseEntity<String>(respuestaConsultaTarjeta.getBody(), respuestaConsultaTarjeta.getStatusCode());

	}

	public ResponseEntity<String> verificarMontoTarjeta(Float monto, String token, String jwToken) {
		String url = "http://localhost:8200/tarjetacredito/" + monto + "/" + token;
		HttpMethod method = HttpMethod.GET;
		ResponseEntity<String> respuestaConsultaTarjeta;
		respuestaConsultaTarjeta = consultaToTarjeta(url, method, jwToken);
		return new ResponseEntity<String>(respuestaConsultaTarjeta.getBody(), respuestaConsultaTarjeta.getStatusCode());

	}

	public ResponseEntity<String> consultaToTarjeta(String url, HttpMethod method, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, method, request, String.class);
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
	}

	public ResponseEntity<TarjetaCredito> findTarjetaByToken(String token, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito/find/" + token;
			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<TarjetaCredito> exchange = restTemplate.exchange(url, HttpMethod.GET, request,
					TarjetaCredito.class);
			return new ResponseEntity<TarjetaCredito>(exchange.getBody(), exchange.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<TarjetaCredito>(error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<TarjetaCredito>(HttpStatus.FORBIDDEN);
		}
	}

	public ResponseEntity<String> crearLog(Long ventaId, String paso, String resultado, String explicacion,
			String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			Log crearLog = new Log(ventaId, paso, resultado, explicacion);
			String url = "http://localhost:8100/log";
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity(crearLog, headers);
			//
			final ResponseEntity exchange = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			return new ResponseEntity<String>(exchange.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<>(error2.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
/*
	public ResponseEntity<String> verificarTarjeta(String token, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito/" + token;
			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.FORBIDDEN);
		}
	}
	
	public ResponseEntity<String> verificarVencimiento(String token, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito/vencimiento/" + token;
			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.FORBIDDEN);
		}
	}

	public ResponseEntity<String> verificarMontoTarjeta(Float monto, String token, String jwToken) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://localhost:8200/tarjetacredito/" + monto + "/" + token;
			MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", jwToken);
			//
			HttpEntity request = new HttpEntity("", headers);
			//
			final ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
			return new ResponseEntity<String>(exchange.getBody(), exchange.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.FORBIDDEN);
		}
	}
	*/

}