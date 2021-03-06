package edu.um.ar.programacion2.tarjetacredito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.um.ar.programacion2.tarjetacredito.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Boolean existsByUsername(String username);

	Usuario findByUsername(String username);

}
