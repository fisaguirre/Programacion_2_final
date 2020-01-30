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

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.objeto.TarjetaCreditoObjeto;
import edu.um.ar.programacion2.ventas.objeto.VentasObjeto;
import edu.um.ar.programacion2.ventas.repository.VentasRepository;

@Service
public class VentasService {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private RestTemplate restTemplate;
    
	@Autowired
    private VentasRepository ventasRepository;
 /*
    @Transactional(readOnly = true)
    public Page<Ventas> findAll(Pageable pageable) {
        //log.debug("Request to get all Ventas");
        return ventasRepository.findAll(pageable);
    }
    */
	
    public ResponseEntity<List<Ventas>> findAll() {
		return new ResponseEntity<List<Ventas>>(ventasRepository.findAll(),HttpStatus.OK);
	}
    
    @Transactional(readOnly = true)
    public Optional<Ventas> findById(Long id) {
        //log.debug("Request to get Ventas : {}", id);
        return ventasRepository.findById(id);
    }
    
	public ResponseEntity<String> chequear_registros(VentasObjeto ventasObj) {
		ResponseEntity<String> existsTarjeta, verificarMontoTarjeta;
		boolean exist_cliente = clienteService.exist(ventasObj.getCliente_id());
		if (exist_cliente) {
			try {
				existsTarjeta = new RestTemplate().getForEntity(
						"http://localhost:8200/tarjetacredito/" + ventasObj.getToken(), String.class);
			} catch (HttpClientErrorException error1) {
				return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
			} catch (RestClientException error2) {
				return new ResponseEntity<String>(error2.getMessage(), HttpStatus.FORBIDDEN);
			}
			try {
				verificarMontoTarjeta = new RestTemplate().getForEntity("http://localhost:8200/tarjetacredito/"
						+ ventasObj.getMonto() + "/" + ventasObj.getToken(), String.class);
			} catch (HttpClientErrorException error1) {
				return new ResponseEntity<String>(error1.getResponseBodyAsString(), error1.getStatusCode());
			} catch (RestClientException error2) {
				return new ResponseEntity<String>(error2.getMessage(), HttpStatus.BAD_REQUEST);
			}
			if (verificarMontoTarjeta.getStatusCodeValue() == 200) {
				System.out.println("Cliente y tarjetas salieron bien");
			}
		}else {
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
			return new ResponseEntity<String>("La venta ha sido exitosa",HttpStatus.OK);
		}
		return new ResponseEntity<String>("La venta no ha sido exitosa", HttpStatus.BAD_REQUEST);
	}

    public Ventas deleteVentas(Long id) {
		ventasRepository.deleteById(id);
		return null;
		//return ventasRepository.deleteById(id);
	}
	/*
	public ResponseEntity<Ventas> updateVentas(Ventas venta) {
		Optional<Ventas> optinalVenta = this.findById(venta.getId());
		if(optinalVenta.isPresent()) {
			Ventas updateVentas = optinalVenta.get();
			updateVentas.setDescripcion(venta.getDescripcion());
			updateVentas.setMonto(venta.getMonto());
			updateVentas.setNombre(venta.getNombre());
			updateVentas.setTokentarjeta(venta.getTokentarjeta());
			return ResponseEntity.ok(this.ventasRepository.save(updateVentas));
		}else {
			return ResponseEntity.notFound().build();			
		}
	}
 */
}