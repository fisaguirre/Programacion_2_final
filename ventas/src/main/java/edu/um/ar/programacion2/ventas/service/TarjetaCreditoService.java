package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
	private ClienteService clienteService;

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
    public ResponseEntity<List<TarjetaCredito>> findAll() {
		//return tarjetacreditoRepository.findAll();
    	
    	List<TarjetaCredito> list = tarjetacreditoRepository.findAll();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept","/");
		return ResponseEntity.ok().headers(headers).body(list);
		//return list;
    }
    */

	public TarjetaCreditoObjeto findById(Long id) {
		Optional<TarjetaCredito> optionalTarjeta = tarjetacreditoRepository.findById(id);
		// TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto();
		if (optionalTarjeta.isPresent()) {
			TarjetaCredito tarjeta_encontrada = optionalTarjeta.get();
			TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto(tarjeta_encontrada.getId(),
					tarjeta_encontrada.getTipo(), tarjeta_encontrada.getNumero(), tarjeta_encontrada.getCodseguridad(),
					tarjeta_encontrada.getVencimiento(), tarjeta_encontrada.getMontomaximo(),
					tarjeta_encontrada.getToken(), tarjeta_encontrada.getCliente_id().getId());
			return tarjetaObj;
		}
		return null;
	}

/*
	public TarjetaCredito createTarjetaCredito(TarjetaCredito tarjetacredito) {
		TarjetaCreditoObjeto tarjetaObj = new TarjetaCreditoObjeto(tarjetacredito.getId(), tarjetacredito.getTipo(),
				tarjetacredito.getNumero(), tarjetacredito.getCodseguridad(), tarjetacredito.getVencimiento(),
				tarjetacredito.getMontomaximo(), tarjetacredito.getToken(), tarjetacredito.getCliente_id().getId());
		return tarjetacreditoRepository.save(tarjetaObj);
		//return tarjetacreditoRepository.save(tarjetacredito);
	}
	*/

	public ResponseEntity createTarjetaCredito(TarjetaCreditoObjeto tarjetaObj) {
		Optional<Cliente> optionalCliente = clienteService.findById(tarjetaObj.getCliente_id());
		if (optionalCliente.isPresent()) {
			Cliente cliente = optionalCliente.get();
			Cliente cliente_encontrado = new Cliente(cliente.getId(), cliente.getNombre(), cliente.getApellido());

			TarjetaCredito tarjetaCredito = new TarjetaCredito(tarjetaObj.getTipo(), tarjetaObj.getNumero(),
					tarjetaObj.getCodseguridad(), tarjetaObj.getVencimiento(), tarjetaObj.getMontomaximo(),
					cliente_encontrado, tarjetaObj.getToken());
			TarjetaCredito tarjeta_creada = tarjetacreditoRepository.save(tarjetaCredito);

			TarjetaCreditoObjeto tarjetaCredObj = new TarjetaCreditoObjeto(tarjeta_creada.getId(),
					tarjeta_creada.getTipo(), tarjeta_creada.getNumero(), tarjeta_creada.getCodseguridad(),
					tarjeta_creada.getVencimiento(), tarjeta_creada.getMontomaximo(), tarjeta_creada.getToken(),
					tarjeta_creada.getCliente_id().getId());

			//return tarjetaCredObj;
			return new ResponseEntity<>("La tarjeta se creo exitosamente-- Su ID es: "+tarjeta_creada.getId(), HttpStatus.OK);
		}
		return null;
	}
	
/*
	public TarjetaCredito createTarjetaCredito(TarjetaCredito tarjetacredito) {
    	return tarjetacreditoRepository.save(tarjetacredito);
    }
*/
	
	public ResponseEntity getTarjetaCreditoId(Integer numero) {
		if(!verify_numero(numero)) {
			return new ResponseEntity<>("0", HttpStatus.OK);
		}
		TarjetaCredito tarjeta = tarjetacreditoRepository.findByNumero(numero);
		return new ResponseEntity<>("ID: "+tarjeta.getId(), HttpStatus.OK);
	}
	
	public boolean verify_numero(Integer numero) {
		boolean verificar_tarjeta = tarjetacreditoRepository.existsByNumero(numero);
		return verificar_tarjeta;
	}
	
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