package com.alura.classes;

import java.util.Date;

public class Reserva {
	private long id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private String valor;
	private String formaPago;
	
	public Reserva(Date fechaEntrada,	Date fechaSalida,	String valor, String formaPago) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}
	
	public Reserva(long id, Date fechaEntrada,	Date fechaSalida,	String valor, String formaPago) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}
	
	public Reserva() {
		
	}

	public long getId() { return id;	}
	public void setId(long id) {	this.id = id; }

	public Date getFechaEntrada() { return fechaEntrada;	}
	public void setFechaEntrada(Date fechaEntrada) { this.fechaEntrada = fechaEntrada; }

	public Date getFechaSalida() { return fechaSalida; }
	public void setFechaSalida(Date fechaSalida) { this.fechaSalida = fechaSalida; }

	public String getValor() { return valor; }
	public void setValor(String valor) { this.valor = valor; }

	public String getFormaPago() { return formaPago; }
	public void setFormaPago(String formaPago) { this.formaPago = formaPago; }
	
	@Override
	public String toString() {
		String cadena="";
		
		cadena = String.valueOf(id) + fechaEntrada.toString() + fechaSalida.toString() + valor + formaPago;
		
		return cadena;
	}
}
