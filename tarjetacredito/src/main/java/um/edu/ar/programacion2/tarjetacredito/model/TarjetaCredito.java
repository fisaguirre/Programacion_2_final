package um.edu.ar.programacion2.tarjetacredito.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ventas")
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

	@Column(name = "token")
	private String token;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TarjetaCredito(Long id, String tipo, Integer numero, Integer codseguridad, Integer vencimiento,
			Float montomaximo, String token) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.numero = numero;
		this.codseguridad = codseguridad;
		this.vencimiento = vencimiento;
		this.montomaximo = montomaximo;
		this.token = token;
	}

}