package edu.um.ar.programacion2.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.um.ar.programacion2.ventas.dto.VentasDto;
import edu.um.ar.programacion2.ventas.model.Ventas;

@Repository

public interface VentasRepository extends JpaRepository<Ventas, Long> {
	Ventas save(VentasDto ventasDto);
}
