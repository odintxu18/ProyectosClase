package controladores;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.basex.core.*;
import org.basex.core.cmd.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import modelo.Hospital;

public class BaseXManagerController implements Interfaz {

	Context context;
	Scanner sc = new Scanner(System.in);

	public BaseXManagerController() throws BaseXException {
		try {
			context = new Context();

			System.out.println("Abriendo bd desde constructor");
			abrirDB("BD_Hospital_XML");

		} catch (Exception ex) {
			System.out.println("Salta excepción");
			System.out.println("Intentamos crear bd en el catch");
			crearDB("BD_Hospital_XML", "Ficheros/Hospital.xml");
			System.out.println("Una vez creada, abro DB");
			abrirDB("BD_Hospital_XML");
		}

	}

	public void crearDB(String nombre, String rutaFicheroXML) throws BaseXException {
		System.out.println("=== Creando BD ===");
		// Crear la base de datos con el nombre y la ruta del archivo XML
		new CreateDB(nombre, rutaFicheroXML).execute(context);
	}

	public void abrirDB(String nombre) throws BaseXException {
		System.out.println("=== Abrir BD ===");
		// Abrir la base de datos con el nombre especificado
		new Open(nombre).execute(context);
	}

	public void cerrarDB() throws BaseXException {
		System.out.println("=== Cerrando BD ===");
		try {
			if (context.data() != null) {
				new Close().execute(context);
			}
		} catch (BaseXException ex) {
			if (!ex.getMessage().contains("Database not opened:")) {
				throw ex;
			}
		}
	}

	@Override
	public HashMap<Integer, Hospital> leerTodos() {
		try {
			System.out.println("=== LEER TODOS ===");
			String query = "/Hospital";
			System.out.println("QUERY QUE SE VA A LANZAR:\n" + query);

			HashMap<Integer, Hospital> resultado = new HashMap<Integer, Hospital>();

			String datosConsulta = new XQuery(query).execute(context);

			SAXBuilder saxBuilder = new SAXBuilder();
			InputStream fichero = new ByteArrayInputStream(datosConsulta.getBytes());
			Document document = saxBuilder.build(fichero);

			System.out.println(datosConsulta);
			Element classElement = document.getRootElement();

			List<Element> hospList = classElement.getChildren();
			System.out.println("----------------------------");

			int cont = 1;

			System.out.println("hay " + hospList.size());
			for (int temp = 0; temp < hospList.size(); temp++) {
				Element elemento = hospList.get(temp);

				System.out.println(elemento);

				int id = Integer.parseInt(elemento.getAttributeValue("id"));
				String paciente = "";
				int sala = 0;
				String enfermedad = "";

				if ("paciente".equalsIgnoreCase(elemento.getName())) {
					Element nombreElement = elemento.getChild("nombre");
					paciente = (nombreElement != null) ? nombreElement.getText() : "";

					Element salaElement = elemento.getChild("sala");
					sala = (salaElement != null) ? Integer.parseInt(salaElement.getText()) : 0;

					Element enfermedadElement = elemento.getChild("enfermedad");
					enfermedad = (enfermedadElement != null) ? enfermedadElement.getText() : "";
				}

				Hospital hosp = new Hospital(id, paciente, sala, enfermedad);

				resultado.put(cont, hosp);
				cont++;
			}

			return resultado;

		} catch (Exception ex) {
			System.err.println("EXCEPCION!!");
			ex.printStackTrace();
			System.exit(-1);
		}

		return null;
	}

	private int contadorPacientes = 0;

	@Override
	public Hospital nuevoPaciente() {
	    System.out.println("=== NUEVO PACIENTE ===");
	    try {
	        System.out.println("Ingrese el nombre del paciente:");
	        String nombre = sc.nextLine();

	        System.out.println("Ingrese el número de sala:");
	        int sala = 0;
	        while (true) {
	            try {
	                sala = Integer.parseInt(sc.nextLine());
	                break;
	            } catch (NumberFormatException e) {
	                System.out.println("Error: Ingrese un número válido para la sala.");
	            }
	        }

	        System.out.println("Ingrese la enfermedad del paciente:");
	        String enfermedad = sc.nextLine();

	        contadorPacientes++;
	        Hospital nuevoPaciente = new Hospital(contadorPacientes, nombre, sala, enfermedad);

	        return nuevoPaciente;

	    } catch (Exception ex) {
	        System.err.println("EXCEPCION!!");
	        ex.printStackTrace();
	    }

	    return null;
	}


	@Override
	public void insertarPaciente(Hospital paciente) {
		System.out.println("=== PRUEBA INSERCION ===");
		try {
			if (paciente != null) {
				Element nuevoPac = new Element("Paciente");

				nuevoPac.setAttribute("id", String.valueOf(paciente.getId()));

				Element pac = new Element("nombre");
				pac.setText(String.valueOf(paciente.getPaciente()));

				Element sala = new Element("sala");
				sala.setText(String.valueOf(paciente.getSala()));

				Element enfermedad = new Element("enfermedad");
				enfermedad.setText(String.valueOf(paciente.getEnfermedad()));

				nuevoPac.addContent(pac);
				nuevoPac.addContent(sala);
				nuevoPac.addContent(enfermedad);

				XMLOutputter xmlOut = new XMLOutputter();
				String formateado = xmlOut.outputString(nuevoPac);

				String queryInsert = "insert node " + formateado + " into /Hospital";

				new XQuery(queryInsert).execute(context);

				System.out.println("== PACIENTE INSERTADO CORRECTAMENTE ==");

			} else {
				System.err.println("ERROR: El objeto 'paciente' es nulo.");
			}
		} catch (Exception ex) {
			System.err.println("EXCEPCION!!");
			ex.printStackTrace();
		}
	}

	@Override
	public void leerPaciente(int id) {
	    try {
	        System.out.println("=== BUSCAR PACIENTE ===");

	        // Construir la consulta XQuery para buscar el paciente por su ID
	        String query = "/Hospital/Paciente[@id='" + id + "']";
	        System.out.println("QUERY QUE SE VA A LANZAR:\n" + query);

	        // Ejecutar la consulta
	        String datosConsulta = new XQuery(query).execute(context);
	        System.out.println("RESULTADO DE LA CONSULTA:\n" + datosConsulta);

	        // Si no se encontraron resultados, mostrar un mensaje
	        if (datosConsulta.isEmpty()) {
	            System.out.println("No se encontró ningún paciente con el ID especificado.");
	        } else {
	            // Si se encontraron resultados, procesar y mostrar la información del paciente
	            SAXBuilder saxBuilder = new SAXBuilder();
	            InputStream fichero = new ByteArrayInputStream(datosConsulta.getBytes());
	            Document document = saxBuilder.build(fichero);

	            Element pacienteElement = document.getRootElement();
	            int pacienteId = Integer.parseInt(pacienteElement.getAttributeValue("id"));
	            String pacienteNombre = pacienteElement.getChildText("nombre");
	            int pacienteSala = Integer.parseInt(pacienteElement.getChildText("sala"));
	            String pacienteEnfermedad = pacienteElement.getChildText("enfermedad");

	            Hospital paciente = new Hospital(pacienteId, pacienteNombre, pacienteSala, pacienteEnfermedad);

	            System.out.println("Información del paciente:");
	            System.out.println(paciente);
	        }

	    } catch (Exception ex) {
	        System.err.println("EXCEPCION!!");
	        ex.printStackTrace();
	    }
	}


	@Override
	public void modificarPaciente(int id) {
		System.out.println("=== MODIFICAR PACIENTE ===");
		HashMap<Integer, Hospital> pacientes = this.leerTodos();

		if (pacientes.containsKey(id)) {
			Hospital pacienteAModificar = pacientes.get(id);

			System.out.println("Detalles del paciente a modificar:");
			System.out.println(pacienteAModificar);

			System.out.println("Ingrese el nuevo nombre del paciente:");
			String nuevoNombre = sc.nextLine();

			System.out.println("Ingrese el nuevo número de sala:");
			int nuevaSala = 0;
			while (true) {
				try {
					nuevaSala = Integer.parseInt(sc.nextLine());
					break;
				} catch (NumberFormatException e) {
					System.out.println("Error: Ingrese un número válido para la sala.");
				}
			}

			System.out.println("Ingrese la nueva enfermedad del paciente:");
			String nuevaEnfermedad = sc.nextLine();

			pacienteAModificar.setPaciente(nuevoNombre);
			pacienteAModificar.setSala(nuevaSala);
			pacienteAModificar.setEnfermedad(nuevaEnfermedad);

			System.out.println("Información actualizada del paciente:");
			System.out.println(pacienteAModificar);

			escribirTodosEnXml(pacientes);

		} else {
			System.out.println("Error: El ID especificado no existe en la base de datos.");
		}
	}

	@Override
	public void eliminarPaciente(int id) {
		System.out.println("=== ELIMINAR PACIENTE ===");
		HashMap<Integer, Hospital> pacientes = this.leerTodos();

		if (pacientes.containsKey(id)) {
			pacientes.remove(id);
			escribirTodosEnXml(pacientes);

			System.out.println("== PACIENTE ELIMINADO CORRECTAMENTE ==");
		} else {
			System.out.println("Error: El ID especificado no existe en la base de datos.");
		}
	}

	@Override
	public void buscarPaciente(String busqueda) {
		// TODO Auto-generated method stub

	}

	public void escribirTodosEnXml(HashMap<Integer, Hospital> pacientes) {
		try {
			new XQuery("delete nodes /Hospital/*").execute(context);

			for (Hospital paciente : pacientes.values()) {
				insertarPaciente(paciente);
			}

			System.out.println("== BASE DE DATOS ACTUALIZADA CORRECTAMENTE ==");

			cerrarDB();

		} catch (Exception ex) {
			System.err.println("EXCEPCION AL ACTUALIZAR LA BASE DE DATOS!!");
			ex.printStackTrace();
		}
	}

}
