package agenda;

import accesoBD.Conexion;
import accesoBD.ConstantesBD;

public class MainApp {

	public static void main(String[] args) {
		
		Menu.menu();
		Conexion.desconexion(Conexion.conexion(ConstantesBD.URL, ConstantesBD.USR, ConstantesBD.PWD));

	}

}
