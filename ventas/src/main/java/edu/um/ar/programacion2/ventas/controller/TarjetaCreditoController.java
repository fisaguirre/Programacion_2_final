package edu.um.ar.programacion2.ventas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.objeto.TarjetaCreditoObjeto;
import edu.um.ar.programacion2.ventas.repository.TarjetaCreditoRepository;
import edu.um.ar.programacion2.ventas.service.TarjetaCreditoService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/tarjetacredito")
public class TarjetaCreditoController {
	@Autowired
	private TarjetaCreditoService tarjetacreditoService;

	@GetMapping("")
	public ResponseEntity<TarjetaCreditoObjeto[]> getAllTarjetaCredito() {
		ResponseEntity<TarjetaCreditoObjeto[]> allTarjetas = tarjetacreditoService.findAll();
		return new ResponseEntity<TarjetaCreditoObjeto[]>(allTarjetas.getBody(),allTarjetas.getStatusCode());
	}
	
/*
	@GetMapping("/{id}")
	public ResponseEntity<TarjetaCreditoObjeto> getTarjetaCredito(@PathVariable Long id) {
		TarjetaCreditoObjeto tarjetaObj = tarjetacreditoService.fById(id);
		return ResponseEntity.ok(tarjetaObj);
	}
	*/

	@GetMapping("/{numero}")
	public ResponseEntity<ResponseEntity> getTokenIdByNumero(@PathVariable Integer numero) {
		return ResponseEntity.ok(tarjetacreditoService.getTokenIdByNumero(numero));
	}

	@PostMapping("")
	public ResponseEntity<ResponseEntity> post(@RequestBody TarjetaCreditoObjeto tarjetaObj) {
		return ResponseEntity.ok(tarjetacreditoService.createTarjetaCredito(tarjetaObj));
	}

	//@DeleteMapping(value = "{idToDelete}")
	@DeleteMapping("/{idToDelete}")
	public ResponseEntity<TarjetaCredito> deleteTarjetaCredito(@PathVariable("idToDelete") Long id) {
		return ResponseEntity.ok(tarjetacreditoService.deleteTarjetaCredito(id));
	}
	
	@DeleteMapping(value = "{idToDelete}")
	public ResponseEntity<String> inactivarTarjeta(@PathVariable("idToDelete") Long id) {
		ResponseEntity<String> inactivarCliente = tarjetacreditoService.inactivarTarjeta(id);
		return new ResponseEntity<String>(inactivarCliente.getBody(), inactivarCliente.getStatusCode());
	}
/*
	@PutMapping
	public ResponseEntity<TarjetaCredito> updateTarjetaCredito(@RequestBody TarjetaCredito tarjetacredito) {
		return new ResponseEntity(tarjetacreditoService.updateTarjetaCredito(tarjetacredito), HttpStatus.OK);
	}
	*/
}