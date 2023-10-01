package com.alura.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.alura.classes.Huesped;
import com.alura.classes.Reserva;
import com.alura.controller.BDConnection;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Busqueda() {
		BDConnection dataBase = new BDConnection();
		ArrayList<Huesped> listaHuespedes = new ArrayList<>();
		ArrayList<Reserva> listaReservas = new ArrayList<>();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		//	Buscar - textFiel
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		//	TITULO - label
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		//	Panel
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		
		//	==============================================> Reservas - Table <==========================
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		
		try { listaReservas = dataBase.readReserva(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		for(Reserva aux: listaReservas) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(aux.getFechaEntrada());
			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			int mes = calendar.get(Calendar.MONTH);
			int anio = calendar.get(Calendar.YEAR);
			
			String entrada = String.format("%d-%d-%d", anio,mes,dia);
			
			calendar.setTime(aux.getFechaSalida());
			dia = calendar.get(Calendar.DAY_OF_MONTH);
			mes = calendar.get(Calendar.MONTH);
			anio = calendar.get(Calendar.YEAR);
			
			String salida = String.format("%d-%d-%d", anio,mes,dia);
			
			modelo.addRow(new Object[] {String.valueOf(aux.getId()), entrada, salida, aux.getValor(), aux.getFormaPago()});
		}
		
		
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		//	==============================================> Huespedes - Table <==========================
		
		try { listaHuespedes = dataBase.readHuesped(); } 
		catch (SQLException e) { e.printStackTrace(); }		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		
		// Recorrido de la lista huespedes
		for(Huesped aux: listaHuespedes) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(aux.getFechaNacimiento());
			int dia = calendar.get(Calendar.DAY_OF_MONTH);
			int mes = calendar.get(Calendar.MONTH);
			int anio = calendar.get(Calendar.YEAR);
			
			String fecha = String.format("%d-%d-%d", anio,mes,dia);
			
			modeloHuesped.addRow(new Object[] {String.valueOf(aux.getId()), aux.getNombre(), aux.getApellido(), fecha, aux.getNacionalidad(), aux.getTelefono(), String.valueOf(aux.getIdReserva())});
		}
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		//	Atras - Boton
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		//	Atras - Label
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		//	Exit - boton
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		//	Exit - label
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		//	Separators
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		//	Buscar - panel
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() { //	=============================================> Buscar
			@Override
			public void mouseClicked(MouseEvent e) {
				String busqueda = txtBuscar.getText();
				boolean soloNumeros = busqueda.matches("[0-9]+");
				ArrayList<Reserva> reservaBusqueda = new ArrayList<>();
				ArrayList<Huesped> huespedBusqueda = new ArrayList<>();
				
				
				try { 
					if(soloNumeros) {	//	Se busca reserva
						long idBusqueda = Long.valueOf(busqueda);
						reservaBusqueda = dataBase.readReserva(idBusqueda);
						//	================================================================== Agregar mensaje error
						modelo.setRowCount(0);
						
						for(Reserva aux: reservaBusqueda) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(aux.getFechaEntrada());
							int dia = calendar.get(Calendar.DAY_OF_MONTH);
							int mes = calendar.get(Calendar.MONTH);
							int anio = calendar.get(Calendar.YEAR);
							
							String entrada = String.format("%d/%d/%d", dia,mes,anio);
							
							calendar.setTime(aux.getFechaSalida());
							dia = calendar.get(Calendar.DAY_OF_MONTH);
							mes = calendar.get(Calendar.MONTH);
							anio = calendar.get(Calendar.YEAR);
							
							String salida = String.format("%d/%d/%d", dia,mes,anio);
							
							modelo.addRow(new Object[] {String.valueOf(aux.getId()), entrada, salida, aux.getValor(), aux.getFormaPago()});
						}
					}else {				//	Se busca huesped
						String nombreBusqueda = busqueda;
						huespedBusqueda = dataBase.readHuesped(nombreBusqueda);
						//	================================================================== Agregar mensaje error
						modeloHuesped.setRowCount(0);
						
						for(Huesped aux: huespedBusqueda) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(aux.getFechaNacimiento());
							int dia = calendar.get(Calendar.DAY_OF_MONTH);
							int mes = calendar.get(Calendar.MONTH);
							int anio = calendar.get(Calendar.YEAR);
							
							String fecha = String.format("%d/%d/%d", dia,mes,anio);
							
							modeloHuesped.addRow(new Object[] {String.valueOf(aux.getId()), aux.getNombre(), aux.getApellido(), fecha,
												 aux.getNacionalidad(), aux.getTelefono(), String.valueOf(aux.getIdReserva())});
						}
					}
				
				} catch (SQLException e1) { e1.printStackTrace(); }
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		//	Buscar - label
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		//	Editar - boton
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		btnEditar.addMouseListener( new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// Obtenemos el primer dato del renglon seleccionado
			        if (tbReservas.getSelectedRow() != -1) {
			        	Reserva nuevaReserva = new Reserva();
			        	
			            String idStr = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 0);
			            String entrada = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 1);
			            String salida = (String) modelo.getValueAt(tbReservas.getSelectedRow(),2);
			            String valor = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 3);
			            String pago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
			            //tbReservas.clearSelection();
			            
			            // Casteo
			            long id = Long.valueOf(idStr);
			            // Formato de fechas
			            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
			            
			            
			            Date utilDateEntrada = null;
			            Date utilDateSalida = null;
						try { 
							utilDateEntrada = dateFormat.parse(entrada);
							utilDateSalida = dateFormat.parse(salida);
						}catch (ParseException e1) { e1.printStackTrace(); }
			            
			            EditarReservasView editarReservas = new EditarReservasView(new Reserva(id,utilDateEntrada,utilDateSalida,valor,pago));
			            editarReservas.setVisible(true);
			            dispose();			            
			        } else if(tbHuespedes.getSelectedRow() != -1) {
			        	Huesped nuevoHuesped = new Huesped();
			        	
			        	String idStr = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),0);
			        	String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
			        	String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
			        	String fechaNacimiento = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3);
			        	String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
			        	String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
			        	String idReserva = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),6);
			        	//tbHuespedes.clearSelection();
			        	
			        	// Casteo
			            long id = Long.valueOf(idStr);
			            
			            // Formato de fechas
			            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
			        	
			            Date utilDateNacimiento = null;
			            try { utilDateNacimiento = dateFormat.parse(fechaNacimiento);	}
			            catch (ParseException e1) { e1.printStackTrace(); }
			            
			            //int id, String nombre, String apellido, String nacionalidad,Date fechaNacimiento, String telefono, long idReserva
			            EditarRegistroHuesped editarReservas = new EditarRegistroHuesped(new Huesped((int)id,nombre,apellido,nacionalidad,utilDateNacimiento,telefono,Long.parseLong(idReserva)));
			            editarReservas.setVisible(true);
			            dispose();			        	
			        } else {
			        	JOptionPane.showMessageDialog(null, "Selecciona un renglon.");
			        }
			        
			        
				}
		});
		
		contentPane.add(btnEditar);
		
		//	Editar - label
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		//	Eliminar boton - label
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		
		btnEliminar.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				BDConnection dataBase = new BDConnection();
				
				// Obtenemos el primer dato del renglon seleccionado
		        if (tbReservas.getSelectedRow() != -1) {		        	
		            String idStr = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 0);
		            
		            // Casteo
		            long id = Long.valueOf(idStr);
		            
		            //	Consulta
		            
		            try {
						dataBase.deleteRegistro(id);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		            Busqueda busqueda = new Busqueda();
		            busqueda.setVisible(true);
		            dispose();				            
		        } else if(tbHuespedes.getSelectedRow() != -1) {		        	
		        	String idStr = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),0);
		        	
		        	// Casteo
		            long id = Long.valueOf(idStr);
		            
		            //	Consulta
		            
		            try {
						dataBase.deleteRegistro(id);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            
		            Busqueda busqueda = new Busqueda();
		            busqueda.setVisible(true);
		            dispose();
		            } else {
		        	JOptionPane.showMessageDialog(null, "Selecciona un renglon.");
		        }
		        
		        
			}
	});
		
		contentPane.add(btnEliminar);
		
		//	Eliminar - label
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
		
		//	Eventos agregados
		this.tbHuespedes.getSelectedRow() ;
		
		//	Limpiar listas
		listaHuespedes.clear();
		listaReservas.clear();
	}
	
	/*
	 *for(Huesped auxiliar: listaHuespedes) {
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
		}
	 *
	 * 	 
	 */
	
	//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
	   xMouse = evt.getX();
	   yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	   int x = evt.getXOnScreen();
	   int y = evt.getYOnScreen();
	   this.setLocation(x - xMouse, y - yMouse);
	}
}
