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

	public UsuarioDto(Integer id, String username, String fullname) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
	}

}
