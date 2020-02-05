package edu.um.ar.programacion2.log.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.um.ar.programacion2.log.model.Log;
import edu.um.ar.programacion2.log.service.LogService;
import edu.um.ar.programacion2.ventas.model.Ventas;

@RestController
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;

	@GetMapping("")
	public ResponseEntity<List<Log>> getAllVentas(Pageable pageable) {
		ResponseEntity<List<Log>> allLog;
		allLog = logService.findAll();
		return new ResponseEntity<List<Log>>(allLog.getBody(), allLog.getStatusCode());
	}

	@GetMapping("/{ventaId}")
	public ResponseEntity<ResponseEntity> getLog(@PathVariable Long ventaId) {
		return ResponseEntity.ok(logService.findByVentaId(ventaId));
		// ResponseEntity<Log> log = logService.findByVentaId(ventaId);
		// return new ResponseEntity<Log>(log);
	}

}