package edu.um.ar.programacion2.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.um.ar.programacion2.ventas.dto.TarjetaCreditoDto;
import edu.um.ar.programacion2.ventas.model.TarjetaCredito;


public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {
	TarjetaCredito save(TarjetaCreditoDto tarjetaDto);
	TarjetaCredito findByNumero(Long numero);
	boolean existsByNumero(Long numero);
}