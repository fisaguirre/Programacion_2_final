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
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.repository.ClienteRepository;
import edu.um.ar.programacion2.ventas.repository.VentasRepository;

@Service
public class ClienteService {
 
    @Autowired
    private ClienteRepository clienteRepository;
 
    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }
    
    public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
    /**
     * Get one ventas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        //log.debug("Request to get Ventas : {}", id);
        return clienteRepository.findById(id);
    }
    
    public Cliente createCliente(Cliente cliente) {
    	return clienteRepository.save(cliente);
    }
    	
	public Cliente deleteCliente(Long id) {
		clienteRepository.deleteById(id);
		return null;
		//return ventasRepository.deleteById(id);
	}
	
	public ResponseEntity<Cliente> updateCliente(Cliente cliente) {
		Optional<Cliente> optionalCliente = this.findById(cliente.getId());
		if(optionalCliente.isPresent()) {
			Cliente updateCliente = optionalCliente.get();
			updateCliente.setNombre(cliente.getNombre());
			updateCliente.setApellido(cliente.getApellido());
			return ResponseEntity.ok(this.clienteRepository.save(updateCliente));
		}else {
			return ResponseEntity.notFound().build();			
		}
	}
 
}