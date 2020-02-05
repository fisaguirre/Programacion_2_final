package edu.um.ar.programacion2.log.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.um.ar.programacion2.log.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Boolean existsByUsername(String username);

	Usuario findByUsername(String username);

}
