package edu.um.ar.programacion2.ventas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.service.ClienteService;
import edu.um.ar.programacion2.ventas.service.VentasService;
import org.springframework.http.HttpHeaders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/cliente")
public class ClienteController {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/")
	public ResponseEntity<List<Cliente>> getAllVentas(Pageable pageable) {
        return new ResponseEntity<List<Cliente>>(clienteService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cliente>> getCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }
    
    @PostMapping("")
	public ResponseEntity<ResponseEntity> createCliente(@RequestBody Cliente cliente) {
    	return ResponseEntity.ok(clienteService.createCliente(cliente));
	}
    
    @GetMapping("/token/{nombre}/{apellido}")
	public ResponseEntity<ResponseEntity> getTarjetaCreditoId(@PathVariable String nombre, @PathVariable String apellido) {
		return ResponseEntity.ok(clienteService.getClienteByNombreApellido(nombre,apellido));
	}

    @DeleteMapping(value = "{idToDelete}")
	public ResponseEntity<Cliente> deleteCliente(@PathVariable("idToDelete") Long id) {
    	return ResponseEntity.ok(clienteService.deleteCliente(id));
	}
    
    @PutMapping
	public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) {
    	return new ResponseEntity(clienteService.updateCliente(cliente), HttpStatus.OK);
	}
}
 