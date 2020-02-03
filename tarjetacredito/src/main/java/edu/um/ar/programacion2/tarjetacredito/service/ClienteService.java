package edu.um.ar.programacion2.tarjetacredito.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.um.ar.programacion2.tarjetacredito.model.Cliente;
import edu.um.ar.programacion2.tarjetacredito.repository.ClienteRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Transactional(readOnly = true)
	public Optional<Cliente> findById(Long id) {
		// log.debug("Request to get Ventas : {}", id);
		return clienteRepository.findById(id);
	}
}