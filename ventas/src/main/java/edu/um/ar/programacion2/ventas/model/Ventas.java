package edu.um.ar.programacion2.ventas.model;

import java.sql.Date;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "ventas")
public class Ventas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "monto")
	private Float monto;

	@Column(name = "fecha")
	private Instant fecha;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cliente_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Cliente cliente;
	
	/*
	@ManyToOne
	@JoinColumn(name = "owner")
	private Cliente owner;
	*/
	@Column(name = "tokentarjeta")
	private String tokentarjeta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public Instant getFecha() {
		return fecha;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getTokentarjeta() {
		return tokentarjeta;
	}

	public void setTokentarjeta(String tokentarjeta) {
		this.tokentarjeta = tokentarjeta;
	}

	public Ventas(Long id, String nombre, String descripcion, Float monto, Instant fecha, Cliente cliente,
			String tokentarjeta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.monto = monto;
		this.fecha = fecha;
		this.cliente = cliente;
		this.tokentarjeta = tokentarjeta;
	}

	public Ventas() {
		super();
	}



}