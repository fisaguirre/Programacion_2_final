package edu.um.ar.programacion2.tarjetacredito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.um.ar.programacion2.tarjetacredito.model.Cliente;

@Repository

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
