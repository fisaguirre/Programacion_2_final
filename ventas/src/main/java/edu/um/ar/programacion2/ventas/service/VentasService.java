package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
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
	public VentasObjeto createVentas(VentasObjeto ventasObj) {
		ResponseEntity<TarjetaCreditoObjeto> responseEntity = new RestTemplate().getForEntity("http://localhost:8200/tarjetacredito/"+ventasObj.getCliente_id(), TarjetaCreditoObjeto.class);
		System.out.println("el numero de la tarjeta extraida es: "+responseEntity.getBody().getNumero());
		//TarjetaCreditoObjeto tar = (TarjetaCreditoObjeto) restTemplate.getForObject("http://localhost:8200/tarjetacredito/" + ventasObj.getTokenTarjeta(), List.class);
		//System.out.println("el numero es: " + tar.getNumero());
		//System.out.println("lo extraido de tarjeta es: "+tar.getTipo());
		Optional<Cliente> optionalCliente = clienteService.findById(ventasObj.getCliente_id());
		VentasObjeto ventaReturn = new VentasObjeto();
		if (optionalCliente.isPresent()) {
			Cliente cliente_encontrado = optionalCliente.get();
			Ventas nueva_venta = new Ventas(ventasObj.getMonto(), cliente_encontrado,
					ventasObj.getTokenTarjeta());
			Ventas venta_creada = ventasRepository.save(nueva_venta);
			ventaReturn.setId(venta_creada.getId());
			ventaReturn.setCliente_id(venta_creada.getCliente().getId());
			ventaReturn.setMonto(venta_creada.getMonto());
			ventaReturn.setTokenTarjeta(venta_creada.getTokentarjeta());
		}
		//return ventasRepository.save(ventasObj);
		return ventaReturn;
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