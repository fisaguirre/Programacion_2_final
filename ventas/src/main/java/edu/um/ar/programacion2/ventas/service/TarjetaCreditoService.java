package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.repository.TarjetaCreditoRepository;

import org.springframework.transaction.annotation.Transactional;


@Service
public class TarjetaCreditoService {
 
    @Autowired
    private TarjetaCreditoRepository tarjetacreditoRepository;
    
    @Autowired
	private ClienteService clienteService;
 /*
    @Transactional(readOnly = true)
    public Page<TarjetaCredito> findAll(Pageable pageable) {
        //log.debug("Request to get all Ventas");
        return tarjetacreditoRepository.findAll(pageable);
    }
    */
    public List<TarjetaCredito> findAll() {
		return tarjetacreditoRepository.findAll();
    }
    /*
    
    public ResponseEntity<List<TarjetaCredito>> findAll() {
		//return tarjetacreditoRepository.findAll();
    	
    	List<TarjetaCredito> list = tarjetacreditoRepository.findAll();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept","/");
		return ResponseEntity.ok().headers(headers).body(list);

		//return list;
    }
    */
    /**
     * Get one ventas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarjetaCredito> findById(Long id) {
        //log.debug("Request to get Ventas : {}", id);
        return tarjetacreditoRepository.findById(id);
    }
    
    public TarjetaCredito createTarjetaCredito(TarjetaCredito tarjetacredito) {
    	return tarjetacreditoRepository.save(tarjetacredito);
    }
    	/*
    	if(ventasRepository.save(venta) != null) {
    		return true;
    	}
    	return false;
    }
    */
	public TarjetaCredito deleteTarjetaCredito(Long id) {
		tarjetacreditoRepository.deleteById(id);
		return null;
		//return ventasRepository.deleteById(id);
	}
	
	public ResponseEntity<TarjetaCredito> updateTarjetaCredito(TarjetaCredito tarjetacredito) {
		Optional<TarjetaCredito> optionalTarjetaCredito = this.findById(tarjetacredito.getId());
		if(optionalTarjetaCredito.isPresent()) {
			TarjetaCredito updateTarjetaCredito = optionalTarjetaCredito.get();
			updateTarjetaCredito.setTipo(tarjetacredito.getTipo());
			updateTarjetaCredito.setNumero(tarjetacredito.getNumero());
			updateTarjetaCredito.setCodseguridad(tarjetacredito.getCodseguridad());
			updateTarjetaCredito.setVencimiento(tarjetacredito.getVencimiento());
			updateTarjetaCredito.setMontomaximo(tarjetacredito.getMontomaximo());
			updateTarjetaCredito.setToken(tarjetacredito.getToken());
			return ResponseEntity.ok(this.tarjetacreditoRepository.save(updateTarjetaCredito));
		}else {
			return ResponseEntity.notFound().build();			
		}
	}
 
}