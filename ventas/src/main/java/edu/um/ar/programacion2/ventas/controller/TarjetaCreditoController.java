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
import edu.um.ar.programacion2.ventas.dto.TarjetaCreditoDto;
import edu.um.ar.programacion2.ventas.repository.TarjetaCreditoRepository;
import edu.um.ar.programacion2.ventas.service.TarjetaCreditoService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/tarjetacredito")
public class TarjetaCreditoController {
	@Autowired
	private TarjetaCreditoService tarjetacreditoService;

	@GetMapping("")
	public ResponseEntity<TarjetaCreditoDto[]> getAllTarjetaCredito(@RequestHeader("Authorization") String token) {
		System.out.println("el JWT es: "+token);
		/*
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);

		HttpEntity<RestRequest> entityReq = new HttpEntity<RestRequest>(request, headers);
		
		template.exchange("RestSvcUrl", HttpMethod.POST, entityReq, SomeResponse.class);

		*/
		ResponseEntity<TarjetaCreditoDto[]> allTarjetas = tarjetacreditoService.findAll(token);
		return new ResponseEntity<TarjetaCreditoDto[]>(allTarjetas.getBody(), allTarjetas.getStatusCode());
	}

	@GetMapping("/buscaruno")
	public ResponseEntity<String> buscarUno(@RequestHeader("Authorization") String token) {
		System.out.println("el JWT es: " + token);
		ResponseEntity<String> allTarjetas = tarjetacreditoService.buscarUno(token);
		return new ResponseEntity<String>(allTarjetas.getBody(), allTarjetas.getStatusCode());
	}

	/*
	 * @GetMapping("/{id}") public ResponseEntity<TarjetaCreditoObjeto>
	 * getTarjetaCredito(@PathVariable Long id) { TarjetaCreditoObjeto tarjetaObj =
	 * tarjetacreditoService.fById(id); return ResponseEntity.ok(tarjetaObj); }
	 */

	@GetMapping("/{numero}")
	public ResponseEntity<ResponseEntity> getTokenIdByNumero(@PathVariable Long numero) {
		return ResponseEntity.ok(tarjetacreditoService.getTokenIdByNumero(numero));
	}

	@PostMapping("")
	public ResponseEntity<ResponseEntity> createTarjetaCredito(@RequestBody TarjetaCreditoDto tarjetaDto) {
		return ResponseEntity.ok(tarjetacreditoService.createTarjetaCredito(tarjetaDto));
	}

	@DeleteMapping("/{tokenToDelete}")
	public ResponseEntity<ResponseEntity> deleteTarjetaCredito(@PathVariable("tokenToDelete") String token) {
		return ResponseEntity.ok(tarjetacreditoService.deleteTarjetaCredito(token));
	}

	@PutMapping("{token}")
	public ResponseEntity<ResponseEntity> updateTarjetaCredito(@PathVariable("token") String token) {
		return ResponseEntity.ok(tarjetacreditoService.updateTarjetaCredito(token));
	}
}