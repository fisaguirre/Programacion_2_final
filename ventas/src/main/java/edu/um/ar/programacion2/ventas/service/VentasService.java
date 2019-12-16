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
 
    @Transactional(readOnly = true)
    public Page<Ventas> findAll(Pageable pageable) {
        //log.debug("Request to get all Ventas");
        return ventasRepository.findAll(pageable);
    }
    
    public List<Ventas> findAll() {
		return ventasRepository.findAll();
	}
    
    @Transactional(readOnly = true)
    public Optional<Ventas> findById(Long id) {
        //log.debug("Request to get Ventas : {}", id);
        return ventasRepository.findById(id);
    }
    /*
    public Ventas createVentas(Ventas venta) {
    	return ventasRepository.save(venta);
    }
    */
	public ResponseEntity createVentas(VentasObjeto ventasObj) {
		Optional<Cliente> optionalCliente = clienteService.findById(ventasObj.getCliente_id());
		//VentasObjeto ventaReturn = new VentasObjeto();
		if (optionalCliente.isPresent()) {
			Cliente cliente_encontrado = optionalCliente.get();
			Ventas nueva_venta = new Ventas(ventasObj.getMonto(), cliente_encontrado, ventasObj.getTokenTarjeta());
			ventasRepository.save(nueva_venta);
			/*
			Ventas venta_creada = ventasRepository.save(nueva_venta);
			ventaReturn.setId(venta_creada.getId());
			ventaReturn.setCliente_id(venta_creada.getCliente().getId());
			ventaReturn.setMonto(venta_creada.getMonto());
			ventaReturn.setTokenTarjeta(venta_creada.getTokentarjeta());
			*/
		}
		//return ventaReturn;
		return new ResponseEntity<>("Se creo la venta exitosamente!", HttpStatus.OK);

	}

	public ResponseEntity chequear_registros(VentasObjeto ventasObj) {
    	boolean exist_cliente = clienteService.exist(ventasObj.getCliente_id());
		ResponseEntity<TarjetaCreditoObjeto> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8200/tarjetacredito/" + ventasObj.getTokenTarjeta(), TarjetaCreditoObjeto.class);
		//HttpHeaders header = restTemplate.headForHeaders("http://localhost:8200/tarjetacredito/" + ventasObj.getTokenTarjeta());
		
		if(responseEntity.getBody().getMontomaximo()==null) {
			return new ResponseEntity<>("La tarjeta no esta registrada!", HttpStatus.BAD_REQUEST);
		}
		if (!exist_cliente) {
			return new ResponseEntity<>("El cliente no existe!", HttpStatus.BAD_REQUEST);
		}
		if (responseEntity.getBody() == null) {
			return new ResponseEntity<>("la tarjeta no existe!", HttpStatus.BAD_REQUEST);
		}
		if (responseEntity.getBody().getCliente_id() != ventasObj.getCliente_id()) {
			return new ResponseEntity<>("La tarjeta no le pertenece al cliente!", HttpStatus.BAD_REQUEST);
		}
		if (ventasObj.getMonto() > 5000) {
			if (responseEntity.getBody().getMontomaximo() < 5000) {
				return new ResponseEntity<>("monto insuficiente!", HttpStatus.BAD_REQUEST);
			}
		}
		return this.createVentas(ventasObj);
		//return responseEntity;
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