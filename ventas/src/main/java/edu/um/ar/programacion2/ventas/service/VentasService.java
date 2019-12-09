package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.repository.VentasRepository;

@Service
public class VentasService {
 
    @Autowired
    private VentasRepository ventasRepository;
 /*
    public Ventas get(long id) {
        return ventasRepository.findOne(id);
    }
    */
    public List<Ventas> findAll() {
		return ventasRepository.findAll();
	}
    
    public Optional<Ventas> findById(Long id) {
    	return ventasRepository.findById(id);
    	/*Optional<Ventas> venta = ventasRepository.findById(id);
    	if(venta.isPresent()) {
			return venta;
		} 
    	//return ventasRepository.findById(id);
		return venta;*/
    }
    
    public Ventas createVentas(Ventas venta) {
    	return ventasRepository.save(venta);
    }
    	/*
    	if(ventasRepository.save(venta) != null) {
    		return true;
    	}
    	return false;
    }
    */
	public Ventas deleteVentas(Long id) {
		ventasRepository.deleteById(id);
		return null;
		//return ventasRepository.deleteById(id);
	}
	
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
 
}
