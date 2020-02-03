package edu.um.ar.programacion2.tarjetacredito.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.um.ar.programacion2.tarjetacredito.model.Cliente;
import edu.um.ar.programacion2.tarjetacredito.service.ClienteService;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Cliente>> getCliente(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);
		return ResponseEntity.ok(cliente);
	}
}
