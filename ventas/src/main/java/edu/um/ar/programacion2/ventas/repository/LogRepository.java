package edu.um.ar.programacion2.ventas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.um.ar.programacion2.ventas.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
