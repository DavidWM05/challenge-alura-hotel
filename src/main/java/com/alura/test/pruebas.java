package com.alura.test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.alura.classes.Huesped;
import com.alura.classes.Reserva;
import com.alura.controller.BDConnection;

public class pruebas {
	public static void main(String[] args) throws SQLException {
		BDConnection dataBase = new BDConnection();
		// long numero = dataBase.getNumeroReserva();
		// System.out.println(numero);
		/*
		ArrayList<Huesped> listaHuespedes = new ArrayList<>();
		ArrayList<Reserva> listaReservas = new ArrayList<>();
		
		listaHuespedes = dataBase.readHuesped("Alberto");
		listaReservas = dataBase.readReserva(1);
		
		System.out.println("======|Huespedes|======");
		for(Huesped auxiliar: listaHuespedes) {
			System.out.println("Id: " + auxiliar.getId());
			System.out.println("Nombre: " + auxiliar.getNombre());
			System.out.println("Apellido: " + auxiliar.getApellido());
			System.out.println("Fecha Nacimiento: " + auxiliar.getFechaNacimiento());
			System.out.println("Nacionalidad: " + auxiliar.getNacionalidad());
			System.out.println("Telefono: " + auxiliar.getTelefono());
			System.out.println("Reserva: " + auxiliar.getIdReserva() + "\n");
		}
		
		System.out.println("======|Reservas|======");
		for(Reserva auxiliar: listaReservas) {
			System.out.println("Id: "+ auxiliar.getId());
			System.out.println("Entrada: "+ auxiliar.getFechaEntrada());
			System.out.println("Salida: "+ auxiliar.getFechaSalida());
			System.out.println("Valor: "+ auxiliar.getValor());
			System.out.println("Pago: "+ auxiliar.getFormaPago() + "\n");
		} */
		Date fecha = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
		try { fecha = dateFormat.parse("2000-05-05"); }
		catch (ParseException e) { e.printStackTrace(); }
		
		
		
		Huesped nuevo = new Huesped(5,"Miguel Angel", "Corona Olvera", "Azteca",fecha, "5512456985",4);
		System.out.println(nuevo.toString());
		
		boolean si = dataBase.updateHuesped(nuevo);
		
	}
}
