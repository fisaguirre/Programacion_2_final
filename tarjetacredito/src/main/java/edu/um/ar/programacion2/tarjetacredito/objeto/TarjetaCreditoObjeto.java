package edu.um.ar.programacion2.tarjetacredito.objeto;

public class TarjetaCreditoObjeto {

	private Long id;
	private String tipo;
	private Integer numero;
	private Integer codseguridad;
	private Integer vencimiento;
	private Float montomaximo;
	private String token;
	private Long cliente_id;

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

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	public TarjetaCreditoObjeto(Long id, String tipo, Integer numero, Integer codseguridad, Integer vencimiento,
			Float montomaximo, String token, Long cliente_id) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.numero = numero;
		this.codseguridad = codseguridad;
		this.vencimiento = vencimiento;
		this.montomaximo = montomaximo;
		this.token = token;
		this.cliente_id = cliente_id;
	}

	public TarjetaCreditoObjeto() {
		super();
	}

}