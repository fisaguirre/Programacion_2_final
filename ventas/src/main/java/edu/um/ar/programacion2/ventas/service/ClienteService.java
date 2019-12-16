package edu.um.ar.programacion2.ventas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.repository.ClienteRepository;
import edu.um.ar.programacion2.ventas.repository.VentasRepository;

@Service
public class ClienteService {
 
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
   
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        //log.debug("Request to get Ventas : {}", id);
        return clienteRepository.findById(id);
    }
    
    public ResponseEntity createCliente(Cliente cliente) {
    	Cliente cliente_creado = clienteRepository.save(cliente);
		return new ResponseEntity<>("Cliente registrado --- Su ID es: "+cliente_creado.getId(), HttpStatus.OK);
    }
    
    public ResponseEntity getClienteByNombreApellido(String nombre, String apellido) {
		
    	if(!verify_nombre_apellido(nombre, apellido)) {
			return new ResponseEntity<>("0", HttpStatus.OK);
		}
		
		Cliente cliente_encontrado = clienteRepository.findByNombreAndApellido(nombre,apellido);
		return new ResponseEntity<>("ID: "+cliente_encontrado.getId(), HttpStatus.OK);
	}
	
	public boolean verify_nombre_apellido(String nombre, String apellido) {
		boolean verificar_cliente = clienteRepository.existsByNombreAndApellido(nombre,apellido);
		return verificar_cliente;
	}

    public boolean exist(Long id) {
    	return clienteRepository.existsById(id);
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