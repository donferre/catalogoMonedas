package com.dto;

public class Usuario {

	private Integer usuario_id;
	private String nombre;
	private String email;
	private String contrasena;
	private String rol;

	public Usuario() {
	}

	public Usuario(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Usuario(Integer usuario_id, String nombre, String email, String contrasena, String rol) {
		this.usuario_id = usuario_id;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
		this.rol = rol;
	}

	public Integer getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
