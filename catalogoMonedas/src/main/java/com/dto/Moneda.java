package com.dto;

import java.math.BigDecimal;
import java.util.Date;

public class Moneda {

	private Integer monedaID;
	private String nombre;
	private Date anoCreacion;
	private Pais pais;
	private BigDecimal valor;
	private String material;
	private String descripcion;
	private byte[] imagen;
	private Usuario usuario;

	private String codigo;

	public Moneda() {
	}

	public Moneda(Integer monedaID) {
		this.monedaID = monedaID;
	}

	public Moneda(Integer monedaID, String nombre, Date anoCreacion, Pais pais, BigDecimal valor, String material,
			String descripcion, byte[] imagen, Usuario usuario) {
		this.monedaID = monedaID;
		this.nombre = nombre;
		this.anoCreacion = anoCreacion;
		this.pais = pais;
		this.valor = valor;
		this.material = material;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.usuario = usuario;
	}

	public Integer getMonedaID() {
		return monedaID;
	}

	public void setMonedaID(Integer monedaID) {
		this.monedaID = monedaID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getAnoCreacion() {
		return anoCreacion;
	}

	public void setAnoCreacion(Date anoCreacion) {
		this.anoCreacion = anoCreacion;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return (codigo != null ? "[" + codigo + "] " : "") + (nombre != null ? nombre : "");
	}

}
