package edu.um.ar.programacion2.ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.um.ar.programacion2.ventas.model.Cliente;
import edu.um.ar.programacion2.ventas.model.Ventas;

@Repository

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	//List<Cliente> findByNombreAndApellido(List<String> nombre, List<String> apellido);
	Cliente findByNombreAndApellido(String nombre, String apellido);
	boolean existsByNombreAndApellido(String nombre, String apellido);
}
