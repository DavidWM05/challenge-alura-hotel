package com.alura.classes;

import java.util.Date;

public class Huesped {
	private long id;
	private String nombre;
	private String apellido;
	private String nacionalidad;
	private Date fechaNacimiento;
	private String telefono;
	private long idReserva;
	
	public Huesped(){};
	
	public Huesped(String nombre, String apellido, String nacionalidad,Date fechaNacimiento, String telefono, long idReserva) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}
	
	public Huesped(long id, String nombre, String apellido, String nacionalidad,Date fechaNacimiento, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
	}
	
	public Huesped(int id, String nombre, String apellido, String nacionalidad,Date fechaNacimiento, String telefono, long idReserva) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public Date getFechaNacimiento() { return fechaNacimiento; }
	public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

	public long getId() { return id; }
	public void setId(long id) {	this.id = id; }
	
	public String getNombre() {	return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	
	public String getApellido() { return apellido; }
	public void setApellido(String apellido) { this.apellido = apellido; }
	
	public String getNacionalidad() { return nacionalidad; }
	public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
	
	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
	
	public long getIdReserva() { return idReserva; }
	public void setIdReserva(long idReserva) { this.idReserva = idReserva; }
	
	@Override
	public String toString() {
		String cadena = "";
		cadena = String.valueOf(id)+ " " + nombre + " " + apellido + " " + nacionalidad + " " + fechaNacimiento.toString() + " " + telefono + " " + String.valueOf(idReserva);
		
		return cadena;
	}
	
}
