package edu.um.ar.programacion2.ventas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.um.ar.programacion2.ventas.model.Log;
import edu.um.ar.programacion2.ventas.service.LogService;
@CrossOrigin
@RestController
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;
	

	@GetMapping("")
	public ResponseEntity<Log[]> getAllLog(@RequestHeader("Authorization") String jwToken) {
		ResponseEntity<Log[]> allLog = logService.findAll(jwToken);
		return new ResponseEntity<Log[]>(allLog.getBody(), allLog.getStatusCode());
	}

	@GetMapping("/{ventaId}")
	public ResponseEntity<ResponseEntity> getLogByVentaId(@PathVariable Long ventaId, @RequestHeader("Authorization") String jwToken) {
		return ResponseEntity.ok(logService.getLogByVentaId(ventaId,jwToken));
		// ResponseEntity<Log> log = logService.findByVentaId(ventaId);
		// return new ResponseEntity<Log>(log);
	}
	
/*
	@PostMapping("")
	public ResponseEntity<ResponseEntity> createLog(@RequestBody Log log,
			@RequestHeader("Authorization") String jwToken) {
		return ResponseEntity.ok(logService.createLog(log, jwToken));
	}
	*/
}