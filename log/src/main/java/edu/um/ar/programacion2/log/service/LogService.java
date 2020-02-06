package edu.um.ar.programacion2.log.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.um.ar.programacion2.log.model.Log;
import edu.um.ar.programacion2.log.repository.LogRepository;
import edu.um.ar.programacion2.tarjetacredito.dto.TarjetaCreditoDto;
import edu.um.ar.programacion2.tarjetacredito.model.Cliente;
import edu.um.ar.programacion2.tarjetacredito.model.TarjetaCredito;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;

	public ResponseEntity<List<Log>> findAll() {
		return new ResponseEntity<List<Log>>(logRepository.findAll(), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<Log> findByVentaId(Long venta) {
		Optional<Log> buscarLog = logRepository.findByVenta(venta);
		if (buscarLog.isPresent()) {
			Log logEncontrado = buscarLog.get();
			return new ResponseEntity<Log>(logEncontrado, HttpStatus.OK);
		}
		return new ResponseEntity<Log>(HttpStatus.FORBIDDEN);
	}

	public ResponseEntity createLog(Log log) {
		logRepository.save(log);
		return new ResponseEntity<>("Log registrado", HttpStatus.OK);
	}

}
