package edu.um.ar.programacion2.ventas.model;

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
@Table(name = "tarjetacredito")
public class TarjetaCredito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "tipo", nullable = false)
	private String tipo;

	@Column(name = "numero")
	private Integer numero;

	@Column(name = "codseguridad")
	private Integer codseguridad;

	@Column(name = "vencimiento")
	private Integer vencimiento;

	@Column(name = "montomaximo")
	private Float montomaximo;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	//@OnDelete(action = OnDeleteAction.CASCADE)
	//@JsonIgnore
	private Cliente cliente_id;
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "owner") private Cliente owner;
	 */
	@Column(name = "token")
	private String token;


	public TarjetaCredito(Long id, String tipo, Integer numero, Integer codseguridad, Integer vencimiento,
			Float montomaximo, Cliente cliente_id, String token) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.numero = numero;
		this.codseguridad = codseguridad;
		this.vencimiento = vencimiento;
		this.montomaximo = montomaximo;
		this.cliente_id = cliente_id;
		this.token = token;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Integer getNumero() {
		return numero;
	}


	public void setNumero(Integer numero) {
		this.numero = numero;
	}


	public Integer getCodseguridad() {
		return codseguridad;
	}


	public void setCodseguridad(Integer codseguridad) {
		this.codseguridad = codseguridad;
	}


	public Integer getVencimiento() {
		return vencimiento;
	}


	public void setVencimiento(Integer vencimiento) {
		this.vencimiento = vencimiento;
	}


	public Float getMontomaximo() {
		return montomaximo;
	}


	public void setMontomaximo(Float montomaximo) {
		this.montomaximo = montomaximo;
	}


	public Cliente getCliente_id() {
		return cliente_id;
	}


	public void setCliente_id(Cliente cliente_id) {
		this.cliente_id = cliente_id;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public TarjetaCredito() {
		super();
	}

}
