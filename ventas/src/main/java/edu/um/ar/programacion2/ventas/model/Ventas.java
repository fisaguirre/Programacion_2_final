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
/*
	@Column(name = "descripcion")
	private String descripcion;
*/
	@Column(name = "monto")
	private Float monto;
/*
	@Column(name = "fecha")
	private Instant fecha;
*/
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "valido", length = 60)
	private boolean valido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
/*
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
*/
	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}
/*
	public Instant getFecha() {
		return fecha;
	}

	public void setFecha(Instant fecha) {
		this.fecha = fecha;
	}
*/
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean getValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public Ventas(Long id, Float monto, Cliente cliente,
			String token) {
		super();
		this.id = id;
		//this.descripcion = descripcion;
		this.monto = monto;
		//this.fecha = fecha;
		this.cliente = cliente;
		this.token = token;
	}

	public Ventas(Float monto, Cliente cliente, String token, boolean valido) {
		super();
		this.monto = monto;
		this.cliente = cliente;
		this.token = token;
		this.valido = valido;
	}

	public Ventas() {
		super();
	}



}