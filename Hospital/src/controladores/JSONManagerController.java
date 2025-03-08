package controladores;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import modelo.Hospital;

public class JSONManagerController implements Interfaz {

	ApiRequests encargadoPeticiones;
	private String SERVER_PATH, GET_PACIENTE, SET_PACIENTE, MOD_PACIENTE, DEL_PACIENTE; // Datos de la conexion

	public JSONManagerController() {

		encargadoPeticiones = new ApiRequests();
		SERVER_PATH = "http://localhost/HospitalJSONServer/HospitalJSONServer/";
		GET_PACIENTE = "leePacientes.php";
		SET_PACIENTE = "escribirPaciente.php";
		MOD_PACIENTE = "pacienteModificar.php";
		DEL_PACIENTE = "eliminarPaciente.php";

	}

	@Override
	public HashMap<Integer, Hospital> leerTodos() {
		HashMap<Integer, Hospital> auxhm = new HashMap<Integer, Hospital>();

		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para pacientes");

			String url = SERVER_PATH + GET_PACIENTE; // Sacadas de configuracion

			System.out.println("La url a la que lanzamos la petici�n es " + url); // Traza para pruebas

			String response = encargadoPeticiones.getRequest(url);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) {

				System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
				System.exit(-1);
			} else {
				String estado = (String) respuesta.get("estado");
				// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
				if (estado.equals("ok")) {
					JSONArray array = (JSONArray) respuesta.get("pacientes");

					if (array.size() > 0) {

						// Declaramos variables
						Hospital nuevoHosp;
						int id;
						String paciente;
						int sala;
						String enfermedad;

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);

							id = Integer.parseInt(row.get("id").toString());
							paciente = row.get("paciente").toString();
							sala = Integer.parseInt(row.get("sala").toString());
							enfermedad = row.get("enfermedad").toString();

							nuevoHosp = new Hospital(id, paciente, sala, enfermedad);

							auxhm.put(id, nuevoHosp);
						}

						System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
						System.out.println();

					} else { // El array de jugadores est� vac�o
						System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido alg�n error

					System.out.println("Ha ocurrido un error en la busqueda de datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

					System.exit(-1);

				}
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");

			e.printStackTrace();

			System.exit(-1);
		}

		return auxhm;
	}

	@Override
	public Hospital nuevoPaciente() {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("Escriba el nombre del paciente");
			String paciente = sc.nextLine();

			System.out.println("Escriba la sala del paciente");
			int sala = sc.nextInt();
			sc.nextLine();

			System.out.println("Escriba la enfermedad del paciente");
			String enfermedad = sc.nextLine();

			JSONObject objPaciente = new JSONObject();
			objPaciente.put("paciente", paciente);
			objPaciente.put("sala", sala);
			objPaciente.put("enfermedad", enfermedad);

			JSONObject objPeticion = new JSONObject();
			objPeticion.put("peticion", "add");
			objPeticion.put("pacienteAnnadir", objPaciente);

			String json = objPeticion.toJSONString();

			System.out.println("Lanzamos petición JSON para almacenar un paciente");

			String url = SERVER_PATH + SET_PACIENTE;

			System.out.println("La URL a la que lanzamos la petición es " + url);
			System.out.println("El JSON que enviamos es: ");
			System.out.println(json);

			String response = encargadoPeticiones.postRequest(url, json);

			System.out.println("El JSON que recibimos es: ");
			System.out.println(response);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response);

			if (respuesta == null) {
				System.out.println("El JSON recibido no es correcto. Finaliza la ejecución");
				System.exit(-1);
			} else {
				String estado = (String) respuesta.get("estado");
				if (estado.equals("ok")) {
					System.out.println("Almacenado paciente enviado por JSON Remoto");
				} else {
					System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
					System.out.println("Error: " + respuesta.get("error"));
					System.out.println("Consulta: " + respuesta.get("query"));
					System.exit(-1);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insertarPaciente(Hospital paciente) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leerPaciente(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarPaciente(int id) {
		try {
			Scanner sc = new Scanner(System.in);

			System.out.println("Escribe el nuevo nombre del paciente:");
			String nuevoNombre = sc.nextLine();

			System.out.println("Escribe la nueva sala del paciente:");
			int nuevaSala = sc.nextInt();
			sc.nextLine();

			System.out.println("Escribe la nueva enfermedad del paciente:");
			String nuevaEnfermedad = sc.nextLine();

			JSONObject objPaciente = new JSONObject();
			objPaciente.put("id", id);
			objPaciente.put("paciente", nuevoNombre);
			objPaciente.put("sala", nuevaSala);
			objPaciente.put("enfermedad", nuevaEnfermedad);

			JSONObject objPeticion = new JSONObject();
			objPeticion.put("peticion", "modificar");
			objPeticion.put("pacienteModificar", objPaciente);

			String json = objPeticion.toJSONString();

			System.out.println("Lanzamos petición JSON para modificar un paciente");

			String url = SERVER_PATH + MOD_PACIENTE;

			System.out.println("La URL a la que lanzamos la petición es " + url);
			System.out.println("El JSON que enviamos es: ");
			System.out.println(json);

			String response = encargadoPeticiones.postRequest(url, json);

			System.out.println("El JSON que recibimos es: ");
			System.out.println(response);

			JSONObject respuesta = (JSONObject) JSONValue.parse(response);

			if (respuesta == null) {
				System.out.println("El JSON recibido no es correcto. Finaliza la ejecución");
				System.exit(-1);
			} else {
				String estado = (String) respuesta.get("estado");
				if (estado.equals("ok")) {
					System.out.println("Modificado paciente enviado por JSON Remoto");
				} else {
					System.out.println("Acceso JSON REMOTO - Error al modificar los datos");
					System.out.println("Error: " + respuesta.get("error"));
					System.out.println("Consulta: " + respuesta.get("query"));
					System.exit(-1);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarPaciente(int id) {
	    try {
	        JSONObject objPaciente = new JSONObject();
	        objPaciente.put("id", id);

	        JSONArray pacientesEliminar = new JSONArray();
	        pacientesEliminar.add(objPaciente);

	        JSONObject objPeticion = new JSONObject();
	        objPeticion.put("peticion", "eliminar");
	        objPeticion.put("pacienteEliminar", pacientesEliminar);

	        String json = objPeticion.toJSONString();

	        String url = SERVER_PATH + DEL_PACIENTE;

	        System.out.println("Lanzamos petición JSON para eliminar un paciente");

	        System.out.println("La URL a la que lanzamos la petición es " + url);

	        String response = encargadoPeticiones.postRequest(url, json);

	        System.out.println("El JSON que recibimos es: ");
	        System.out.println(response);

	        JSONObject respuesta = (JSONObject) JSONValue.parse(response);

	        if (respuesta == null) {
	            System.out.println("El JSON recibido no es correcto. Finaliza la ejecución");
	            System.exit(-1);
	        } else {
	            String estado = (String) respuesta.get("estado");
	            if (estado.equals("ok")) {
	                System.out.println("Eliminado paciente enviado por JSON Remoto");
	            } else {
	                System.out.println("Acceso JSON REMOTO - Error al eliminar los datos");
	                System.out.println("Error: " + respuesta.get("error"));
	                System.out.println("Consulta: " + respuesta.get("query"));
	                System.exit(-1);
	            }
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}




	@Override
	public void buscarPaciente(String busqueda) {
		// TODO Auto-generated method stub

	}

}
