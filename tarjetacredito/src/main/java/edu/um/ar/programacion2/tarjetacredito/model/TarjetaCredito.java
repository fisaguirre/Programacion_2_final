package edu.um.ar.programacion2.tarjetacredito.model;

import java.sql.Date;

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
	private Long numero;

	@Column(name = "codseguridad")
	private Integer codseguridad;

	@Column(name = "vencimiento")
	private Date vencimiento;

	@Column(name = "montomaximo")
	private Float montomaximo;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente_id;

	@Column(name = "token")
	private String token;

	@Column(name = "activo", length = 60)
	private boolean activo;

	
	public TarjetaCredito(Long id, String tipo, Long numero, Integer codseguridad, Date vencimiento,
			Float montomaximo, Cliente cliente_id, String token, boolean activo) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.numero = numero;
		this.codseguridad = codseguridad;
		this.vencimiento = vencimiento;
		this.montomaximo = montomaximo;
		this.cliente_id = cliente_id;
		this.token = token;
		this.activo = activo;
	}

	public TarjetaCredito(String tipo, Long numero, Integer codseguridad, Date vencimiento, Float montomaximo,
			Cliente cliente_id, String token, boolean activo) {
		super();
		this.tipo = tipo;
		this.numero = numero;
		this.codseguridad = codseguridad;
		this.vencimiento = vencimiento;
		this.montomaximo = montomaximo;
		this.cliente_id = cliente_id;
		this.token = token;
		this.activo = activo;
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

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getCodseguridad() {
		return codseguridad;
	}

	public void setCodseguridad(Integer codseguridad) {
		this.codseguridad = codseguridad;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
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

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public TarjetaCredito() {
		super();
	}

}
