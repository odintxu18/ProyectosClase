package controladores;

import java.util.HashMap;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import modelo.Hospital;
/*cd..
 * cd..
 * cd mongo
 * cd mongodb
 * cd bin
 * mongod
 * 
 * cd..
 * cd..
 * cd mongo
 * cd mongodb
 * cd bin
 * mongo
 * cd..
 * cd..
 * cd mongo
 * cd mongodb
 * cd bin
 * mongoimport --db maquinarefrescos --file auxJSON\dispensadores.json --collection dispensadores --jsonArray --drop*/
public class MongoManagerController implements Interfaz {

	Scanner sc;
	MongoCollection miColeccion;

	public MongoManagerController() {

		MongoClient mongoClient = new MongoClient("localhost", 27017);

		MongoDatabase database = mongoClient.getDatabase("Hospitales");

		miColeccion = database.getCollection("hospital");
	}

	@Override
	public HashMap<Integer, Hospital> leerTodos() {

		HashMap<Integer, Hospital> aux = new HashMap<>();
		MongoCursor<Document> resultado = miColeccion.find().iterator();

		System.out.println("\n VER DATOS \n");

		while (resultado.hasNext()) {
			Document doc = resultado.next();
			Hospital hospital = documentToHospital(doc);

			aux.put(hospital.getId(), hospital);
		}

		return aux;

	}

	@Override
	public Hospital nuevoPaciente() {
		try {
			System.out.println("Ingrese los datos del nuevo paciente:");

			System.out.print("ID: ");
			int id = Integer.parseInt(sc.nextLine());

			System.out.print("Nombre del paciente: ");
			String paciente = sc.nextLine();

			System.out.print("Número de sala: ");
			int sala = Integer.parseInt(sc.nextLine());

			System.out.print("Enfermedad: ");
			String enfermedad = sc.nextLine();

			return new Hospital(id, paciente, sala, enfermedad);
		} catch (Exception e) {
			System.err.println("Error al intentar crear un nuevo paciente: " + e.getMessage());
			return null;
		}
	}

	@Override
	public void insertarPaciente(Hospital paciente) {
	    try {
	        Document nuevoPaciente = new Document("id", paciente.getId())
	                .append("paciente", paciente.getPaciente())
	                .append("sala", paciente.getSala())
	                .append("enfermedad", paciente.getEnfermedad());

	        miColeccion.insertOne(nuevoPaciente);

	        System.out.println("Paciente insertado correctamente.");
	    } catch (Exception e) {
	        System.err.println("Error al intentar insertar un nuevo paciente: " + e.getMessage());
	    }
	}

	@Override
	public void leerPaciente(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarPaciente(int id) {
	    try {
	        Document pacienteExistente = (Document) miColeccion.find(new Document("id", id)).first();
	        if (pacienteExistente == null) {
	            System.out.println("Paciente con ID " + id + " no encontrado.");
	            return;
	        }

	        Hospital pacienteActual = documentToHospital(pacienteExistente);
	        System.out.println("Detalles actuales del paciente:");
	        System.out.println(pacienteActual);

	        System.out.println("Ingrese los nuevos detalles del paciente:");

	        System.out.print("Nombre del paciente: ");
	        String nuevoNombre = sc.nextLine();

	        System.out.print("Número de sala: ");
	        int nuevaSala = Integer.parseInt(sc.nextLine());

	        System.out.print("Enfermedad: ");
	        String nuevaEnfermedad = sc.nextLine();

	        Document nuevosDatos = new Document("paciente", nuevoNombre)
	                .append("sala", nuevaSala)
	                .append("enfermedad", nuevaEnfermedad);

	        miColeccion.updateOne(new Document("id", id), new Document("$set", nuevosDatos));

	        System.out.println("Paciente modificado correctamente.");
	    } catch (Exception e) {
	        System.err.println("Error al intentar modificar el paciente: " + e.getMessage());
	    }
	}


	public void eliminarPaciente(int id) {
	    try {
	        Document pacienteExistente = (Document) miColeccion.find(new Document("id", id)).first();
	        if (pacienteExistente == null) {
	            System.out.println("Paciente con ID " + id + " no encontrado. No se puede eliminar.");
	            return;
	        }

	        Hospital paciente = documentToHospital(pacienteExistente);
	        System.out.println("Detalles del paciente a eliminar:");
	        System.out.println(paciente);

	        // Eliminar el paciente directamente sin solicitar confirmación
	        miColeccion.deleteOne(new Document("id", id));
	        System.out.println("Paciente eliminado correctamente.");
	    } catch (Exception e) {
	        System.err.println("Error al intentar eliminar el paciente: " + e.getMessage());
	    }
	}


	@Override
	public void buscarPaciente(String busqueda) {
	    try {
	        Document filtro = new Document("paciente", new Document("$regex", busqueda));

	        MongoCursor<Document> resultado = miColeccion.find(filtro).iterator();

	        System.out.println("\nResultados de la búsqueda para '" + busqueda + "':\n");

	        while (resultado.hasNext()) {
	            Document doc = resultado.next();
	            Hospital paciente = documentToHospital(doc);
	            System.out.println(paciente);
	        }
	    } catch (Exception e) {
	        System.err.println("Error al intentar buscar pacientes: " + e.getMessage());
	    }
	}


	private Hospital documentToHospital(Document doc) {
		int id = doc.getInteger("id");
		String paciente = doc.getString("paciente");
		int sala = doc.getInteger("sala");
		String enfermedad = doc.getString("enfermedad");

		return new Hospital(id, paciente, sala, enfermedad);
	}

}
