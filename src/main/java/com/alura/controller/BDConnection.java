package com.alura.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.alura.classes.Huesped;
import com.alura.classes.Reserva;

public class BDConnection {
	private Connection con;
	
	private void openConnection() throws SQLException{ con = DriverManager.getConnection("jdbc:mysql://localhost:3306/alurahotel?useTimezone=true&serverTimezone=UTC","root","12345"); }
	private void closeConnection() throws SQLException { con.close(); }
	
	//	Querys - Huesped

	public String createHuesped(Huesped huesped) throws SQLException {
		// INSERT INTO huespedes (Nombre,Apellido,FechaNacimiento,Nacionalidad,Telefono,IdReserva) VALUES ('Luis David','Peralta Gonzalez','1997-08-05',"Mexico","5549975195");
		String respuesta = "";
		String query = "INSERT INTO huespedes (Nombre,Apellido,FechaNacimiento,Nacionalidad,Telefono,IdReserva) VALUES (?,?,?,?,?,?);";
		
		openConnection();
		PreparedStatement statement = con.prepareStatement(query);
		
		//	Casteo de Date
		java.sql.Date sqlFechaNacimiento = new java.sql.Date(huesped.getFechaNacimiento().getTime());
		
		statement.setString(1, huesped.getNombre());
		statement.setString(2, huesped.getApellido());
		statement.setDate(3, sqlFechaNacimiento);
		statement.setString(4, huesped.getNacionalidad());
		statement.setString(5, huesped.getTelefono());
		statement.setLong(6, huesped.getIdReserva());
		
		int filasAfectadas = statement.executeUpdate();
		if(filasAfectadas > 0) {
			respuesta = "si";
		}else {
			respuesta = "no";
		}
		
		closeConnection();
		return respuesta;
	}
	
	public ArrayList<Huesped> readHuesped() throws SQLException {
		ArrayList<Huesped> listaHuespedes = new ArrayList<>();
		
		openConnection();
		String query = "Select * from huespedes;";
		PreparedStatement statement = con.prepareStatement(query);
		ResultSet resulSet = statement.executeQuery();
		
		while (resulSet.next()) {
			Huesped huesped = new Huesped();
			huesped.setId(resulSet.getInt("Id"));
			huesped.setNombre(resulSet.getString("Nombre"));
			huesped.setApellido(resulSet.getString("Apellido"));
			
			java.util.Date utilDate = resulSet.getDate("FechaNacimiento");
			huesped.setFechaNacimiento(new Date(utilDate.getTime()));
			
			huesped.setNacionalidad(resulSet.getString("Nacionalidad"));
			huesped.setTelefono(resulSet.getString("Telefono"));
			huesped.setIdReserva(resulSet.getLong("IdReserva"));
			
			listaHuespedes.add(huesped);
		}
		
		closeConnection();
		return listaHuespedes;
	}

	public boolean updateHuesped(Huesped huesped) throws SQLException {
		boolean actualizado = false;
		openConnection();
		
		
		huesped.toString();
		
		java.sql.Date sqlDate1 = new java.sql.Date(huesped.getFechaNacimiento().getTime());
		
		String query = "UPDATE huespedes SET Nombre = ?, Apellido = ?, FechaNacimiento = ?, Nacionalidad = ?, Telefono = ? where Id = ?;";
		PreparedStatement statement = con.prepareStatement(query);
		
		statement.setString(1, huesped.getNombre());
		statement.setString(2, huesped.getApellido());		
		statement.setDate(3, sqlDate1);
		statement.setString(4, huesped.getNacionalidad());
		statement.setString(5, huesped.getTelefono());
		statement.setLong(6, huesped.getId());
		
		int filasAfectadas = statement.executeUpdate();
		if (filasAfectadas > 0) { actualizado = true; System.out.println("Actualizao");}
		else { actualizado = false; System.out.println("No Actualizao");}
		
		closeConnection();
		
		return actualizado;
	}
	
	public ArrayList<Huesped> readHuesped(String nombre) throws SQLException {
		ArrayList<Huesped> listaHuespedes = new ArrayList<>();
		openConnection();
		
		String query = "Select * from huespedes where Nombre = ?;";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1, nombre);
		
		ResultSet resulSet = statement.executeQuery();
		
		while (resulSet.next()) {
			Huesped huesped = new Huesped();
			
			huesped.setId(resulSet.getInt("Id"));			
			huesped.setNombre(resulSet.getString("Nombre"));
			huesped.setApellido(resulSet.getString("Apellido"));
			
			java.util.Date utilNacimiento = resulSet.getDate("FechaNacimiento");
			huesped.setFechaNacimiento(new Date(utilNacimiento.getTime()));
						
			huesped.setNacionalidad(resulSet.getString("Nacionalidad"));
			huesped.setTelefono(resulSet.getString("Telefono"));
			huesped.setIdReserva(resulSet.getLong("IdReserva"));
			
			listaHuespedes.add(huesped);
		}
		
		closeConnection();
		return listaHuespedes;
	}
	
	//	Querys - Reserva
	public String createReserva(Reserva reserva) throws SQLException {		
		String respuesta = "";
		String query = "INSERT INTO reservas (FechaEntrada,FechaSalida,Valor,FormaPago) VALUES (?,?,?,?);";
		BigDecimal decimal = new BigDecimal(reserva.getValor());
		
		openConnection();
		PreparedStatement statement = con.prepareStatement(query);
		
		//	Casteo de Dates
		java.sql.Date sqlDateEntrada = new java.sql.Date(reserva.getFechaEntrada().getTime());
		java.sql.Date sqlDateSalida = new java.sql.Date(reserva.getFechaSalida().getTime());
		
		
		
		statement.setDate(1, sqlDateEntrada);
		statement.setDate(2, sqlDateSalida);
		statement.setBigDecimal(3, decimal);
		statement.setString(4, reserva.getFormaPago());
		
		int filasAfectadas = statement.executeUpdate();
		if(filasAfectadas > 0) {
			respuesta = "si";
		}else {
			respuesta = "no";
		}
		
		closeConnection();
		return respuesta;
	}
	
	public long getNumeroReserva() throws SQLException {
		openConnection();
		long reservaID = 0;
		String query = "SELECT Id FROM reservas ORDER BY id DESC LIMIT 1;";
		
		PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) { reservaID = Long.valueOf(resultSet.getString("Id")); }
		
		closeConnection();
		return reservaID;
	}

	public boolean updateReserva(Reserva reserva) throws SQLException {
		boolean actualizado = false;
		openConnection();
		
		java.sql.Date sqlDate1 = new java.sql.Date(reserva.getFechaEntrada().getTime());
		java.sql.Date sqlDate2 = new java.sql.Date(reserva.getFechaSalida().getTime());
		
		String query = "UPDATE reservas SET FechaEntrada = ?, FechaSalida = ?, Valor = ?, FormaPago = ? WHERE id = ?;";
		PreparedStatement statement = con.prepareStatement(query);
		
		statement.setDate(1, sqlDate1);
		statement.setDate(2, sqlDate2);
		statement.setString(3, reserva.getValor());
		statement.setString(4, reserva.getFormaPago());
		statement.setLong(5, reserva.getId());
		
		int filasAfectadas = statement.executeUpdate();
		if (filasAfectadas > 0) { actualizado = true; }
		else { actualizado = false; }
		
		closeConnection();
		
		return actualizado;
	}
	
	public ArrayList<Reserva> readReserva() throws SQLException {
		ArrayList<Reserva> listaReservas = new ArrayList<>();
		
		openConnection();
		String query = "Select * from reservas;";
		PreparedStatement statement = con.prepareStatement(query);
		ResultSet resulSet = statement.executeQuery();
		
		while (resulSet.next()) {
			Reserva reserva = new Reserva();
			
			reserva.setId(resulSet.getInt("Id"));			
			
			java.util.Date utilDate1 = resulSet.getDate("FechaEntrada");
			reserva.setFechaEntrada(new Date(utilDate1.getTime()));
			
			java.util.Date utilDate2 = resulSet.getDate("FechaSalida");
			reserva.setFechaSalida(new Date(utilDate2.getTime()));
			
			reserva.setValor(String.valueOf(resulSet.getBigDecimal("Valor")));
			reserva.setFormaPago(resulSet.getString("FormaPago"));
			
			listaReservas.add(reserva);
		}
		
		closeConnection();
		return listaReservas;
	}
	
	public ArrayList<Reserva> readReserva(long Id) throws SQLException {
		ArrayList<Reserva> listaReservas = new ArrayList<>();
		openConnection();
		
		String query = "Select * from reservas where Id = ?;";
		PreparedStatement statement = con.prepareStatement(query);
		statement.setLong(1, Id);
		
		ResultSet resulSet = statement.executeQuery();
		
		while (resulSet.next()) {
			Reserva reserva = new Reserva();
			
			reserva.setId(resulSet.getInt("Id"));			
			
			java.util.Date utilDate1 = resulSet.getDate("FechaEntrada");
			reserva.setFechaEntrada(new Date(utilDate1.getTime()));
			
			java.util.Date utilDate2 = resulSet.getDate("FechaSalida");
			reserva.setFechaSalida(new Date(utilDate2.getTime()));
			
			reserva.setValor(String.valueOf(resulSet.getBigDecimal("Valor")));
			reserva.setFormaPago(resulSet.getString("FormaPago"));
			
			listaReservas.add(reserva);
		}
		
		closeConnection();
		return listaReservas;
	}

	public void deleteRegistro(long idReserva) throws SQLException {
		openConnection();
		
		try {
            String sql1 = "delete huespedes from Huespedes huespedes inner join Reservas reservas on huespedes.IdReserva = reservas.Id where huespedes.IdReserva = ?;";
            String sql2 = "delete from reservas where Id = ?;";
            
            PreparedStatement statement1 = con.prepareStatement(sql1);
            statement1.setLong(1, idReserva);
            
            PreparedStatement statement2 = con.prepareStatement(sql2);
            statement2.setLong(1, idReserva);

            int filasAfectadas1 = statement1.executeUpdate();
            int filasAfectadas2 = statement2.executeUpdate();
            
            if(filasAfectadas1 > 0 && filasAfectadas2 > 0) {
            	JOptionPane.showMessageDialog(null, "Registro eliminado");
            }else {
            	JOptionPane.showMessageDialog(null, "Registro no eliminado");
            }
            
        } catch (SQLException e) { e.printStackTrace(); }
	}
}
