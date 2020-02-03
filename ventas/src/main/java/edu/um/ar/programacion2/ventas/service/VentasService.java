package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.ventas.dto.VentasDto;
import edu.um.ar.programacion2.ventas.model.Cliente;
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
		// log.debug("Request to get Ventas : {}", id);
		return ventasRepository.findById(id);
	}

	/*
	public ResponseEntity<String> chequear_registros(VentasObjeto ventasObj) {
		ResponseEntity<String> existsTarjeta, verificarMontoTarjeta;
		boolean exist_cliente = clienteService.exist(ventasObj.getCliente_id());
		if (exist_cliente) {
			try {
				existsTarjeta = new RestTemplate()
						.getForEntity("http://localhost:8200/tarjetacredito/" + ventasObj.getToken(), String.class);
			} catch (HttpClientErrorException error1) {
				return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
			} catch (RestClientException error2) {
				return new ResponseEntity<String>(error2.getMessage(), HttpStatus.FORBIDDEN);
			}
			try {
				verificarMontoTarjeta = new RestTemplate().getForEntity(
						"http://localhost:8200/tarjetacredito/" + ventasObj.getMonto() + "/" + ventasObj.getToken(),
						String.class);
			} catch (HttpClientErrorException error1) {
				return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
			} catch (RestClientException error2) {
				return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
			}
			if (verificarMontoTarjeta.getStatusCodeValue() == 200) {
				System.out.println("Cliente y tarjetas salieron bien");
			}
		} else {
			return new ResponseEntity<String>("El cliente no existe", HttpStatus.BAD_REQUEST);
		}
		return this.createVenta(ventasObj);
	}
	public ResponseEntity<String> createVenta(VentasObjeto ventasObj) {
		Optional<Cliente> optionalCliente = clienteService.findById(ventasObj.getCliente_id());
		if (optionalCliente.isPresent()) {
			Cliente cliente_encontrado = optionalCliente.get();
			Ventas nueva_venta = new Ventas(ventasObj.getMonto(), cliente_encontrado, ventasObj.getToken());
			ventasRepository.save(nueva_venta);
			return new ResponseEntity<String>("La venta ha sido exitosa", HttpStatus.OK);
		}
		return new ResponseEntity<String>("La venta no ha sido exitosa", HttpStatus.BAD_REQUEST);
	}
	*/

	public ResponseEntity<String> createVenta(VentasDto ventasDto) {
		Optional<Cliente> optionalCliente = clienteService.findById(ventasDto.getCliente_id());
		if (optionalCliente.isPresent()) {
			Cliente cliente_encontrado = optionalCliente.get();
			ResponseEntity<String> verificacionTarjeta = verificarTarjeta(ventasDto.getToken());
			ResponseEntity<String> verificarMontoTarjeta = verificarMontoTarjeta(ventasDto.getMonto(),
					ventasDto.getToken());
			if ((verificacionTarjeta.getStatusCode()) == HttpStatus.OK) {
				if (verificarMontoTarjeta.getStatusCode() == HttpStatus.OK) {
					ResponseEntity<TarjetaCredito> findTarjetaByToken = findTarjetaByToken(ventasDto.getToken());
					if (findTarjetaByToken.getBody().getActivo()) {
						if (findTarjetaByToken.getBody().getCliente_id().getId().equals(ventasDto.getCliente_id())) {
							ResponseEntity<String> verificacionVenta = ventaFinal(ventasDto, cliente_encontrado);
							if (verificacionVenta.getStatusCode() == HttpStatus.OK) {
								return new ResponseEntity<String>(verificacionVenta.getBody(),
										verificacionVenta.getStatusCode());
							} else {
								return new ResponseEntity<String>(verificacionVenta.getBody(),
										verificacionVenta.getStatusCode());
							}
						} else {
							return new ResponseEntity<String>("La tarjeta no le pertenece al cliente",
									HttpStatus.BAD_REQUEST);
						}
					} else {
						return new ResponseEntity<String>("La tarjeta no esta habilitada", HttpStatus.BAD_REQUEST);
					}
				} else {
					return new ResponseEntity<String>(verificarMontoTarjeta.getBody(),
							verificarMontoTarjeta.getStatusCode());
				}
			} else {
				return new ResponseEntity<String>(verificacionTarjeta.getBody(), verificacionTarjeta.getStatusCode());
			}
		} else {
			return new ResponseEntity<String>("El cliente no se encuentra registrado", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<String> ventaFinal(VentasDto ventasDto, Cliente cliente) {
		Ventas nueva_venta = new Ventas(ventasDto.getMonto(), cliente, ventasDto.getToken());
		if (ventasRepository.save(nueva_venta) != null) {
			return new ResponseEntity<String>("La venta se realizo correctamente", HttpStatus.OK);
		}
		return new ResponseEntity<String>("No se pudo concretar la venta", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<TarjetaCredito> findTarjetaByToken(String token) {
		try {
			ResponseEntity<TarjetaCredito> existsTarjeta = new RestTemplate()
					.getForEntity("http://localhost:8200/tarjetacredito/find/" + token, TarjetaCredito.class);
			return new ResponseEntity<TarjetaCredito>(existsTarjeta.getBody(), existsTarjeta.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<TarjetaCredito>(error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<TarjetaCredito>(HttpStatus.FORBIDDEN);
		}
	}

	public ResponseEntity<String> verificarTarjeta(String token) {

		try {
			ResponseEntity<String> existsTarjeta = new RestTemplate()
					.getForEntity("http://localhost:8200/tarjetacredito/" + token, String.class);
			return new ResponseEntity<String>(existsTarjeta.getBody(), existsTarjeta.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.FORBIDDEN);
		}
	}

	public ResponseEntity<String> verificarMontoTarjeta(Float monto, String token) {
		try {
			ResponseEntity<String> verificarMontoTarjeta = new RestTemplate()
					.getForEntity("http://localhost:8200/tarjetacredito/" + monto + "/" + token, String.class);
			return new ResponseEntity<String>(verificarMontoTarjeta.getBody(), verificarMontoTarjeta.getStatusCode());
		} catch (HttpClientErrorException error1) {
			return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
		} catch (RestClientException error2) {
			return new ResponseEntity<String>(error2.getMessage(), HttpStatus.FORBIDDEN);
		}
	}

}