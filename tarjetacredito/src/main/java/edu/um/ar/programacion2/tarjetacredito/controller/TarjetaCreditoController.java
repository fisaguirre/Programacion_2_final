package edu.um.ar.programacion2.tarjetacredito.controller;

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

import com.fasterxml.jackson.databind.util.JSONPObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.um.ar.programacion2.tarjetacredito.model.TarjetaCredito;
import edu.um.ar.programacion2.tarjetacredito.objeto.TarjetaCreditoObjeto;
import edu.um.ar.programacion2.tarjetacredito.service.TarjetaCreditoService;

import org.springframework.http.HttpHeaders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController @CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/tarjetacredito")
public class TarjetaCreditoController {
	@Autowired
    private TarjetaCreditoService tarjetacreditoService;

	@GetMapping("")
	public ResponseEntity<List<TarjetaCreditoObjeto>> getAllTarjetaCredito() {
		return new ResponseEntity<List<TarjetaCreditoObjeto>>(tarjetacreditoService.findAll(), HttpStatus.OK);
		// return tarjetacreditoService.findAll(); }
	}
    
    @GetMapping("/token/{numero}")
	public ResponseEntity<String> findTokenByNumero(@PathVariable Integer numero) {
    	ResponseEntity<String> token_tarjeta = tarjetacreditoService.findTokenByNumero(numero);
    	return new ResponseEntity<String>(token_tarjeta.getBody(),token_tarjeta.getStatusCode());
	}
    
    @GetMapping("/{token}")
	public ResponseEntity<String> verificarTarjeta(@PathVariable String token) {
    	System.out.println("este token es: "+token);
    	ResponseEntity<String> token_tarjeta = tarjetacreditoService.verificarTarjeta(token);
    	return new ResponseEntity<String>(token_tarjeta.getBody(),token_tarjeta.getStatusCode());
	}
    
    @GetMapping("/{monto}/{token}")
	public ResponseEntity<String> verificarMontoTarjeta(@PathVariable Float monto,@PathVariable String token) {
    	ResponseEntity<String> token_tarjeta = tarjetacreditoService.verificarMontoTarjeta(monto,token);
    	return new ResponseEntity<String>(token_tarjeta.getBody(),token_tarjeta.getStatusCode());
	}

    @PostMapping("/add")
	public ResponseEntity<String> createTarjetaCredito(@RequestBody TarjetaCreditoObjeto tarjetaCreditoObjeto) {
    	ResponseEntity<String> token = tarjetacreditoService.createTarjetaCredito(tarjetaCreditoObjeto);    	
    	return new ResponseEntity<String>(token.getBody(), token.getStatusCode());
	}
    
    
    @DeleteMapping("/{tokenToDelete}")
	public ResponseEntity<String> deleteTarjetaCredito(@PathVariable("tokenToDelete") String token) {
    	ResponseEntity<String> deshabilitarTarjeta = tarjetacreditoService.deleteTarjetaCredito(token);
    	return new ResponseEntity<String>(deshabilitarTarjeta.getBody(),deshabilitarTarjeta.getStatusCode());
	}
	
    
    @PutMapping("/{token}")
	public ResponseEntity<String> updateTarjetaCredito(@PathVariable("token") String token) {
    	ResponseEntity<String> updateTarjetaCredito = tarjetacreditoService.updateTarjetaCredito(token);
    	return new ResponseEntity<String>(updateTarjetaCredito.getBody(),updateTarjetaCredito.getStatusCode());
	}
	
}