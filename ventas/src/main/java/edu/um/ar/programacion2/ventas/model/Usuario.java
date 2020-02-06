package edu.um.ar.programacion2.ventas.model;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username", nullable = false, length = 60)
	private String username;

	@Column(name = "password", nullable = false, length = 60)
	private String password;

	@Column(name = "fullname", nullable = false, length = 60)
	private String fullname;

	@Column(name = "activo", length = 60)
	private boolean activo;
	
	@Column(name = "rol")
	private String rol;
	//private String rol;

	public Usuario() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setRoles(String rol) {
		this.rol = rol;
	}

	public Usuario(String username, String password, String fullname, boolean activo, String rol) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.activo = activo;
		this.rol = rol;
	}
	
	public Usuario(Integer id, String username, String password, String fullname, boolean activo, String rol) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.activo = activo;
		this.rol = rol;
	}
	
	

}
