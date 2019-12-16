package edu.um.ar.programacion2.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.um.ar.programacion2.ventas.model.TarjetaCredito;
import edu.um.ar.programacion2.ventas.objeto.TarjetaCreditoObjeto;


public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {
	TarjetaCredito save(TarjetaCreditoObjeto tarjetaObj);
	TarjetaCredito findByNumero(Integer numero);
	boolean existsByNumero(Integer numero);

}