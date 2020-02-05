package edu.um.ar.programacion2.ventas.dto;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

public class UsuarioDto {

	private Integer id;

	private String username;

	private String fullname;
	
	private String rol;

	private boolean activo;

	public UsuarioDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public UsuarioDto(Integer id, String username, String fullname, boolean activo, String rol) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.activo = activo;
		this.rol = rol;
	}

}
