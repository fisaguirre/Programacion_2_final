package edu.um.ar.programacion2.ventas.dto;

public class VentasDto {

	private Long id;
	private Float monto;
	private Long cliente_id;
	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public VentasDto(Long id, Float monto, Long cliente_id, String token) {
		super();
		this.id = id;
		this.monto = monto;
		this.cliente_id = cliente_id;
		this.token = token;
	}

	public VentasDto() {
		super();
	}

}