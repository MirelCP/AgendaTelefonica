package accesoBD;
import agenda.*;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AccesoDatosBD {

	public static void crearBD(Connection con, String bd) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD);
		}

		try (PreparedStatement ps = con.prepareStatement( "\nCREATE DATABASE IF NOT EXISTS " + bd)) {
			ps.execute("DROP DATABASE IF EXISTS "+ bd);
			ps.execute();
			JOptionPane.showMessageDialog(null, "Bases de datos creada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void crearTabla(Connection con, String bd, String tabla) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD);
		}
		try (PreparedStatement ps = con.prepareStatement("Create table " + tabla
				+ "(nombre varchar(60), apellidos varchar(60), " + "telefono int, email varchar(60) )")) {
			ps.execute("use " + bd);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void borrarTabla(Connection con, String nomTabla) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD);
		}
		try (PreparedStatement ps = con.prepareStatement("DROP TABLE " + nomTabla)){
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertarDatosDesdeFichero(Connection con,String fichero, String bd, String tabla) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD);
		}
		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))){
			while(ois !=null) {
				contactos.add((Contacto) ois.readObject());
			}
		} catch(EOFException e) {}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (PreparedStatement ps = con.prepareStatement("Insert into " +tabla + " values(?,?,?,?)")){
			ps.execute("USE "+bd);
			for(Contacto c: contactos) {
				ps.setString(1, c.getNombre());
				ps.setString(2, c.getApellido());
				ps.setInt(3, c.getTelefono());
				ps.setString(4, c.getEmail());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertarContactoNuevo(Connection con, Contacto c, String bd, String tabla) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD);
		}
		try (PreparedStatement ps = con.prepareStatement("Insert into "+ tabla + " values(?,?,?,?)")){
			ps.execute("Use "+bd);
			ps.setString(1, c.getNombre());
			ps.setString(2, c.getApellido());
			ps.setInt(3, c.getTelefono());
			ps.setString(4, c.getEmail());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void modificarDatos(Connection con, String bd, String tabla) {
		if (con == null) {
			con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD);
		}
		int opc = Integer.parseInt(JOptionPane.showInputDialog("Elige opcion\n"
				+ "\t1. Modificar telefono\n"
				+ "\t2. Modificar email\n"
				+ "\t3. Modificar ambos"));
		
		String nombre = JOptionPane.showInputDialog("Introduce el nombre");
		String apellidos = JOptionPane.showInputDialog("Introduce los apellidos");
		
		if(opc ==1) {
			int telefono = Integer.parseInt(JOptionPane.showInputDialog("Introduce el nuevo numero de telefono"));
			try (PreparedStatement ps = con.prepareStatement("Update "+tabla + " set telefono = "+"'"+telefono+"'"+" where name = "+"'"+nombre+"'"+ " and "
						+ "apellidos ="+"'"+apellidos+"'"+")")){
				ps.execute("Use "+bd);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(opc ==2) {
			String email = JOptionPane.showInputDialog("Introduce el nuevo email");
			try (PreparedStatement ps = con.prepareStatement("Update "+tabla + " set email = "+"'"+email+"'"+" where name = "+"'"+nombre+"'"+ " and "
						+ "apellidos ="+"'"+apellidos+"'"+")")){
				ps.execute("Use "+bd);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
