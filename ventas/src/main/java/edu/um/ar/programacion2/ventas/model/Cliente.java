package edu.um.ar.programacion2.ventas.model;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.websocket.Decoder.Binary;

import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;
	/*
	 * @OneToMany(mappedBy = "cliente_id", cascade = CascadeType.ALL) private
	 * List<TarjetaCredito> tarjetacredito = new ArrayList<TarjetaCredito>();
	 */
	// @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	// private List<TarjetaCredito> cards = new ArrayList<TarjetaCredito>();

	@Column(name = "activo", length = 60)
	private boolean activo;

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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Cliente(Long id, String nombre, String apellido, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.activo = activo;
	}

	public Cliente(String nombre, String apellido, boolean activo) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.activo = activo;
	}

	public Cliente() {
		super();
	}

}