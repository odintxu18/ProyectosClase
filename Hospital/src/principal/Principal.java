package principal;

import java.util.HashMap;

import java.util.Map.Entry;
import java.util.Scanner;

import org.basex.core.BaseXException;

import controladores.BBDDManagerController;
import controladores.BaseXManagerController;
import controladores.BinaryManagerController;
import controladores.ExistDBManagerController;
import controladores.FileManagerController;
import controladores.HibernateManagerController;
import controladores.Interfaz;
import controladores.JSONManagerController;
import controladores.MongoManagerController;
import controladores.ODBManagerController;
import controladores.XMLManagerController;
import modelo.Hospital;


public class Principal {

	Scanner sc;
	Interfaz intermediario;

	public static void main(String[] args) {

		try {
			System.out.println("Inicio");

			Principal principal = new Principal();

			principal.ejecucion();

			System.out.println("Fin");
			System.exit(0);
		} catch (Exception e) {

			System.out.println("Se ha producido un error");
		}
	}

	public Principal() {

		sc = new Scanner(System.in);

	}

	public int opcionMenu() {

		int opcion = 0;
		System.out.println("1-Leer todos los pacientes");
		System.out.println("2-Crear paciente");
		System.out.println("3-Buscar paciente");
		System.out.println("4-Modificar paciente");
		System.out.println("5-Eliminar paciente");
		System.out.println("6-Búsqueda plus");
		opcion = this.leerEntero("Selecciona una opción");

		return opcion;

	}

	public int opcionMenu2() {

		int opcion = 0;
		System.out.println("1-Fichero");
		System.out.println("2-Binario");
		System.out.println("3-XML");
		System.out.println("4-BBDD");
		System.out.println("5-Hibernate");
		System.out.println("6-ObjectDB");
		System.out.println("7-JSON");
		System.out.println("8-Mongo");
		System.out.println("9-BaseX");
		System.out.println("10-ExistDB");

		opcion = this.leerEntero("Selecciona una opción");

		return opcion;

	}

	public Interfaz elegirIntermediario(int opcion) throws BaseXException {

		Interfaz aux = null;

		switch (opcion) {
		case 1: // Fichero
			aux = (Interfaz) new FileManagerController();
			break;
		case 2:
			aux =(Interfaz) new BinaryManagerController();
			break;
		case 3: 
			aux = (Interfaz) new XMLManagerController();
			break;
		case 4: 
			aux = (Interfaz) new BBDDManagerController();
			break;
		case 5: 
			aux = (Interfaz) new HibernateManagerController();
			break;
		case 6: 
			aux = (Interfaz) new ODBManagerController();
			break;
		case 7: 
			aux = (Interfaz) new JSONManagerController();
			break;
		case 8: 
			aux = (Interfaz) new MongoManagerController();
			break;
		case 9: 
			aux = (Interfaz) new BaseXManagerController();
			break;
		case 10: 
			//aux = (Interdaz) new ExistDBManagerController();
			break;
		default:
			System.out.println("OPCIÓN NO VÁLIDA O NO IMPLEMENTADA");
			System.exit(-1);
			break;
		}

		return aux;

	}

	public void ejecucion() {
		boolean salir = false;
		int opcion;

		try {
			int op = opcionMenu2();
			intermediario = elegirIntermediario(op);
			while (!salir) {
				opcion = opcionMenu();
				switch (opcion) {
				case 0: // Salir
					salir = true;
					System.out.println("HASTA LA PRÓXIMA!!!");
					break;
				case 1:
					this.leeTodos();
					break;
				case 2:
					this.insertarPaciente();
					break;
				case 3:
					System.out.println("Inserte el id del paciente");
					int Id = sc.nextInt();
					this.leerPaciente(Id);
					break;
				case 4:
					System.out.println("Inserte el id del paciente");
					Id = sc.nextInt();
					this.modificarPaciente(Id);
					break;
				case 5:
					System.out.println("Inserte el id del paciente que desea eliminar");
					Id = sc.nextInt();
					this.eliminarPaciente(Id);
					break;
				case 6:
                    System.out.println("Introduce el término de búsqueda (ID, paciente, sala o enfermedad):");
                    String busqueda = sc.nextLine();
                    intermediario.buscarPaciente(busqueda);
                    break;
				default:
					System.out.println("OPCIÓN NO VÁLIDA O NO IMPLEMENTADA");
					break;
				}

				if (!salir) {
					System.out.println("\n PULSA ENTER PARA CONTINUAR \n");
					System.out.println();
					System.out.println();
					sc.nextLine();
				}

			}
		} catch (Exception e) {
			System.err.println("Se ha producido una excepción!!!");
			e.printStackTrace();
		}
	}

	public int leerEntero(String textoPeticion) {

		int var = 0;

		boolean salir = false;

		while (!salir) {
			try {
				System.out.println(textoPeticion);
				var = Integer.parseInt(sc.nextLine());
				salir = true;
			} catch (Exception e) {
				System.out.println("No es un número entero");
			}

		}

		return var;

	}

	private void leeTodos() {
		HashMap<Integer, Hospital> aux = intermediario.leerTodos();
		this.pintarDatos(aux);
	}

	private void pintarDatos(HashMap<Integer, Hospital> aux) {

		System.out.println("Mostrando pacientes");

		for (Entry<Integer, Hospital> entry : aux.entrySet()) {

			Hospital objeto = entry.getValue();

			System.out.println(objeto.toString());
		}

	}

	private Hospital nuevoPaciente() {
		Hospital hosp = intermediario.nuevoPaciente();
		return hosp;

	}

	private void insertarPaciente() {
		Hospital h = nuevoPaciente();
		intermediario.insertarPaciente(h);

	}

	private void leerPaciente(int id) {
		 intermediario.leerPaciente(id);
		
	}

	private void modificarPaciente(int id) {
		intermediario.modificarPaciente(id);

	}

	private void eliminarPaciente(int id) {
		intermediario.eliminarPaciente(id);

	}

}
