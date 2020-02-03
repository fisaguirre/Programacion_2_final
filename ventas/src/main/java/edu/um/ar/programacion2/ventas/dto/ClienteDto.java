package edu.um.ar.programacion2.ventas.dto;

public class ClienteDto {

	private Long id;
	private String nombre;
	private String apellido;
	private Boolean activo;

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

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public ClienteDto(Long id, String nombre, String apellido, Boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.activo = activo;
	}

}