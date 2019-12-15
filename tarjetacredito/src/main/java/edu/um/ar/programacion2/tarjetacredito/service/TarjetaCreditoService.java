package edu.um.ar.programacion2.tarjetacredito.service;

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
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.tarjetacredito.model.TarjetaCredito;
import edu.um.ar.programacion2.tarjetacredito.objeto.TarjetaCreditoObjeto;
import edu.um.ar.programacion2.tarjetacredito.repository.TarjetaCreditoRepository;

@Service
public class TarjetaCreditoService {
 
    @Autowired
    private TarjetaCreditoRepository tarjetacreditoRepository;
 /*
    @Transactional(readOnly = true)
    public Page<TarjetaCredito> findAll(Pageable pageable) {
        //log.debug("Request to get all Ventas");
        return tarjetacreditoRepository.findAll(pageable);
    }
    */
    public List<TarjetaCreditoObjeto> findAll() {
		List<TarjetaCredito> list = tarjetacreditoRepository.findAll();
		List<TarjetaCreditoObjeto> tarjetaList = new ArrayList<TarjetaCreditoObjeto>();
		for (TarjetaCredito tarjeta : list) {
			TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto(tarjeta.getId(), tarjeta.getTipo(),
					tarjeta.getNumero(), tarjeta.getCodseguridad(), tarjeta.getVencimiento(), tarjeta.getMontomaximo(),
					tarjeta.getToken(), tarjeta.getCliente_id().getId());
			tarjetaList.add(tarjetaObj);
		}
		return tarjetaList;
	}
    /*
    @Transactional(readOnly = true)
    public Optional<TarjetaCredito> findById(Long id) {
        //log.debug("Request to get Ventas : {}", id);
        return tarjetacreditoRepository.findById(id);
    }
*/
	public TarjetaCreditoObjeto findById(Long id) {
		Optional<TarjetaCredito> optionalTarjeta = tarjetacreditoRepository.findById(id);
		TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto();
		if (optionalTarjeta.isPresent()) {
			TarjetaCredito tar = optionalTarjeta.get();
			tarjetaObj.setId(tar.getId());
			tarjetaObj.setTipo(tar.getTipo());
			tarjetaObj.setNumero(tar.getNumero());
			tarjetaObj.setCodseguridad(tar.getCodseguridad());
			tarjetaObj.setVencimiento(tar.getVencimiento());
			tarjetaObj.setMontomaximo(tar.getMontomaximo());
			tarjetaObj.setToken(tar.getToken());
			tarjetaObj.setCliente_id(tar.getCliente_id().getId());
		}
		return tarjetaObj;
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
/*
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
*/
 
}