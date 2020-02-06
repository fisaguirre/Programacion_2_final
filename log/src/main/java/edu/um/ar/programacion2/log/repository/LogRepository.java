package edu.um.ar.programacion2.log.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.um.ar.programacion2.log.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
	
	Optional<Log> findByVenta(Long venta);
	
}
