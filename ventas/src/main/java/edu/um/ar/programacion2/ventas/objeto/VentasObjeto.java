package edu.um.ar.programacion2.ventas.objeto;

public class VentasObjeto {

	private Long id;
	private Float monto;
	private Long cliente_id;
	private Long tokenTarjeta;

	
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

	public Long getTokenTarjeta() {
		return tokenTarjeta;
	}

	public void setTokenTarjeta(Long tokenTarjeta) {
		this.tokenTarjeta = tokenTarjeta;
	}

	public VentasObjeto(Long id, Float monto, Long cliente_id, Long tokenTarjeta) {
		super();
		this.id = id;
		this.monto = monto;
		this.cliente_id = cliente_id;
		this.tokenTarjeta = tokenTarjeta;
	}

	public VentasObjeto() {
		super();
	}

}
