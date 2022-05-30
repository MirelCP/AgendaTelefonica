package agenda;
import accesoBD.AccesoDatosBD;
import accesoBD.Conexion;
import accesoBD.ConstantesBD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class Menu {
	static final int MAX_OPC = 7;
	private final static String FICHERO = "agenda.dat", FICHEROTXT="eliminados.txt";

	public static void menu() {
		int opc;

		do {
			opc = Integer.parseInt(JOptionPane.showInputDialog(
					"Elige opcion\n" + "\t1. Crear contacto\n" + "\t2. Buscar contacto\n" + "\t3. Modificar contacto\n"
							+ "\t4. Eliminar contacto\n" + "\t5. Ordenar alfabeticamente\n" + 
							"\t6. Manipular BD\n"+"\t7. Salir"));

			switch (opc) {

			case 1:
				guararEnFichero(FICHERO);
				break;
			case 2:
				buscarContacto(FICHERO);
				break;
			case 3:
				menuParaModificarContacto(FICHERO);
				break;
			case 4: eliminarContacto(FICHERO, FICHEROTXT);
					leerFichero(FICHEROTXT);
			break;
			case 6: menuBd();
			}

		} while (opc < MAX_OPC || opc > MAX_OPC || opc != MAX_OPC);

	}
	//Metodo leer fichero.txt
	public static void leerFichero(String fichero) {
		String eliminados = "Estos son todos los contactos eliminados\n";
		try (BufferedReader br = new BufferedReader(new FileReader(fichero))){
			while(br.ready()==true) {
				eliminados +=br.readLine();
			}
			JOptionPane.showMessageDialog(null, eliminados);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Metodo crear nuevo contacto
	public static Contacto crearContacto() {
		String nombre = JOptionPane.showInputDialog("Introduce el nombre");
		String apellidos = JOptionPane.showInputDialog("Introduce los apellidos");
		int telefono = Integer.parseInt(JOptionPane.showInputDialog("Introduce el numero de telefono"));
		String email = JOptionPane.showInputDialog("Introduce el email");
		return new Contacto(nombre, apellidos, telefono, email);

	}

	// Metodo guardar Contacto en fichero
	public static void guararEnFichero(String fichero) {
		File f = new File(fichero);

		if (f.exists()) {
			try (ObjOutpOverWrite oos = new ObjOutpOverWrite(new FileOutputStream(f, true))) {
				oos.writeObject(crearContacto());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
				oos.writeObject(crearContacto());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void buscarContacto(String fichero) {

		String nombre = JOptionPane.showInputDialog("Introduce el nombre");
		String apellidos = JOptionPane.showInputDialog("Introduce los apellidos");
		int cont = 0;
		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
			while (ois != null) {
				Contacto a = (Contacto) ois.readObject();
				contactos.add(a);
			}

		} catch (EOFException e) {

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Contacto c : contactos) {
			if (c.equals(new Contacto(nombre, apellidos, 0, null)) && cont < 1) {
				JOptionPane.showMessageDialog(null, c);
				cont++;
			}

		}
		if (cont == 0) {
			JOptionPane.showMessageDialog(null, "No se encuentra el contacto con los datos introducidos");
		}
	}

//Menu modificar datos del contacto
	public static void menuParaModificarContacto( String fichero) {
		int opc;
//		int max_opc = 4;
//		do {
			opc = Integer.parseInt(JOptionPane.showInputDialog("Elige opcion \n" + "\t1. Modificar telefono\n"
					+ "\t2. Modificar email\n" + "\t3. Modificar email y telefono\n" + "\t4. Salir"));
			
			modificarDatos(fichero, opc);
				
				
			
//		} while (opc < max_opc || opc > max_opc || opc != max_opc);
		
	}
	// Metodos para modificar los datos del contacto

	public static void modificarDatos(String fichero, int opc) {

		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(fichero))) {
			while (oos != null) {
				contactos.add((Contacto) oos.readObject());
			}
		} catch (EOFException e) {
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String nombre = JOptionPane.showInputDialog("Introduce el nombre");
		String apellidos = JOptionPane.showInputDialog("Introduce los apellidos");
		int cont = 0;
		for (Contacto c : contactos) {
			if (c.equals(new Contacto(nombre, apellidos, 0, null)) && cont < 1 && opc==1) {
				cont++;
				int tel = Integer.parseInt(JOptionPane.showInputDialog("Introduce el nuevo numero de telefono"));
				c.setTelefono(tel);
			}else if(c.equals(new Contacto(nombre, apellidos, 0, null)) && cont < 1 && opc==2) {
				cont++;
				String email = JOptionPane.showInputDialog("Introduce el email nuevo");
				c.setEmail(email);
			}else if(c.equals(new Contacto(nombre, apellidos, 0, null)) && cont < 1 && opc==3){
				cont++;
				int tel = Integer.parseInt(JOptionPane.showInputDialog("Introduce el nuevo numero de telefono"));
				String email = JOptionPane.showInputDialog("Introduce el email nuevo");
				c.setTelefono(tel);
				c.setEmail(email);
			}
				try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
					oos.writeObject(c);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			
		}
		if(cont==0) {
			JOptionPane.showMessageDialog(null, "No se encuentra el contacto con los datos introducidos");
		}
	}
	public static void eliminarContacto(String fichero, String txt) {
		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))){
			while(ois != null) {
				contactos.add((Contacto) ois.readObject());
			}
		}catch(EOFException e) {}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String nombre = JOptionPane.showInputDialog("Introduce el nombre");
		String apellidos = JOptionPane.showInputDialog("Introduce los apellidos");
		Iterator<Contacto> i = contactos.iterator();
		int cont=0;
		while(i.hasNext()) {
			Contacto c = i.next();
			if(c.equals(new Contacto(nombre, apellidos, 0, null))) {
				cont++;
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(txt))){
					bw.write(c.getNombre() + " " + c.getApellido());
				} catch (IOException e) {
					e.printStackTrace();
				}
				i.remove();
			}
		}
		if(cont==0) {
			JOptionPane.showMessageDialog(null, "No se encuentra el contacto con los datos introducidos");
		}
		
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))){
				for(Contacto c : contactos) {
					oos.writeObject(c);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//Metodos para manipular la BD
		public static void menuBd() {
			int max_op =7;
			int opc;
			do{
				opc=Integer.parseInt(JOptionPane.showInputDialog("Elige opcion \n"
						+ "\t1. Crear una base de datos\n"
						+ "\t2. Crear una tabla\n"
						+ "\t3. Insertar datos desde un fichero\n"
						+ "\t4. Insertar contacto nuevo\n"
						+ "\t5. Modificar datos sobre un contacto\n"
						+ "\t7. Salir"));
				switch(opc) {
				case 1: 
					String bd = JOptionPane.showInputDialog("Introduce el nombre de la base de datos");
					AccesoDatosBD.crearBD(Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD), bd);
					break;
					
				case 2: 
						bd = JOptionPane.showInputDialog("Introduce el nombre de la base de datos");
						String tabla = JOptionPane.showInputDialog("Introduce el nombre de la tabla");
						AccesoDatosBD.crearTabla(Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD), bd, tabla);
						break;
				case 4:bd = JOptionPane.showInputDialog("Introduce el nombre de la base de datos");
							tabla = JOptionPane.showInputDialog("Introduce el nombre de la tabla");
					AccesoDatosBD.insertarContactoNuevo(Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD), crearContacto(), bd, tabla);
					break;
				}
				
			}while(opc <max_op || opc> max_op || opc!=max_op);
			
			
		}
		
		
 }
