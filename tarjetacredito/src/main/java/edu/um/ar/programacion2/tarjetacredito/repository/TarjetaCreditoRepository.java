package edu.um.ar.programacion2.tarjetacredito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.um.ar.programacion2.tarjetacredito.model.TarjetaCredito;
import edu.um.ar.programacion2.tarjetacredito.objeto.TarjetaCreditoObjeto;

public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {
	Optional<TarjetaCredito> findByNumero(Integer numero);
	Boolean existsByNumero(Integer numero);
	Boolean existsByToken(String token);
	Optional<TarjetaCredito> findByToken(String token);
	Optional<TarjetaCredito> findById(Long id);
}