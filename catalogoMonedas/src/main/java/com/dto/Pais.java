package com.dto;

public class Pais {

	private Integer id;
	private String nombre;
	

	public Pais() {
	}

	public Pais(Integer id) {
		this.id = id;
	}

	public Pais(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return (id != null ? "[" + id + "] " : "") + (nombre != null ? nombre : "");
	}
}
