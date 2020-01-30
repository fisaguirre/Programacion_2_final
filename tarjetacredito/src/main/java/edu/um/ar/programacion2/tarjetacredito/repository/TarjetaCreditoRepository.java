package edu.um.ar.programacion2.tarjetacredito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.um.ar.programacion2.tarjetacredito.model.TarjetaCredito;

public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {
	Optional<TarjetaCredito> findByNumero(Integer numero);
	Boolean existsByNumero(Integer numero);
}