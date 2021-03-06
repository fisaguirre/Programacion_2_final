package edu.um.ar.programacion2.ventas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.um.ar.programacion2.ventas.model.Ventas;
import edu.um.ar.programacion2.ventas.service.VentasService;
import edu.um.ar.programacion2.ventas.dto.VentasDto;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import org.springframework.http.HttpHeaders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/venta")
public class VentasController {

	@Autowired
	private VentasService ventasService;

	@GetMapping("")
	public ResponseEntity<List<Ventas>> getAllVentas(Pageable pageable) {
		ResponseEntity<List<Ventas>> registrosVentas;
		registrosVentas = ventasService.findAll();
		return new ResponseEntity<List<Ventas>>(registrosVentas.getBody(), registrosVentas.getStatusCode());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Ventas>> getVentas(@PathVariable Long id) {
		// log.debug("REST request to get Ventas : {}", id);
		Optional<Ventas> ventas = ventasService.findById(id);
		// return ResponseUtil.wrapOrNotFound(ventas);
		return ResponseEntity.ok(ventas);
	}

	@PostMapping("")
	public ResponseEntity<ResponseEntity> createVenta(@RequestHeader("Authorization") String jwToken, @RequestBody VentasDto ventasDto) {
		return ResponseEntity.ok(ventasService.createVenta(ventasDto,jwToken));
	}

}
