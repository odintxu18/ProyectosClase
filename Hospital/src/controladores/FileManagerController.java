package controladores;

import java.util.HashMap;
import java.util.*;
import java.io.*;

import modelo.Hospital;
import principal.Principal;

public class FileManagerController extends Principal implements Interfaz  {
	Scanner sc = new Scanner(System.in);
	File fichero = new File("Ficheros/Hospital.txt");



	public HashMap<Integer, Hospital> leerTodos() {

		HashMap<Integer, Hospital> aux = new HashMap<Integer, Hospital>();

		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(fichero);
			br = new BufferedReader(fr);

			String linea;
			while ((linea = br.readLine()) != null) {
				String[] hosp = linea.split(";");

				if (hosp.length >= 4) {
					Integer id = Integer.valueOf(hosp[0].trim());
					String paciente = hosp[1].trim();
					Integer sala = Integer.valueOf(hosp[2].trim());
					String enfermedad = hosp[3].trim();

					Hospital h1 = new Hospital(id, paciente, sala, enfermedad);

					aux.put(id, h1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aux;

	}

	@Override
	public Hospital nuevoPaciente() {
	    while (true) {
	        System.out.println("Inserta el ID del paciente");
	        int id = sc.nextInt();
	        HashMap<Integer, Hospital> aux = leerTodos();
	        
  	        if (aux.containsKey(id)) {
	            System.out.println("El ID ya está introducido. Por favor, elige otro ID.");
	        } else {
	            sc.nextLine(); // Limpia el búfer del teclado
	            System.out.println("Escriba el nombre del paciente");
	            String paciente = sc.nextLine();

	            System.out.println("Escriba la sala del paciente");
	            int sala = sc.nextInt();
	            sc.nextLine(); // Limpia el búfer del teclado
	            System.out.println("Escriba la enfermedad del paciente");
	            String enfermedad = sc.nextLine();

	            Hospital h2 = new Hospital(id, paciente, sala, enfermedad);
	            System.out.println("Paciente creado");
	            return h2;
	        }
	    }
	}


	@Override
	public void insertarPaciente(Hospital paciente) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("Ficheros/Hospital.txt", true);
			pw = new PrintWriter(fw);

			String linea = "\n";
			linea += paciente.getId() + ";";
			linea += paciente.getPaciente() + ";";
			linea += paciente.getSala() + ";";
			linea += paciente.getEnfermedad() + ";";
			//linea+= "";
			pw.print(linea);
			System.out.println("Paciente escrito");
			fw.close();
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void  leerPaciente(int id) {
		HashMap<Integer, Hospital> aux = this.leerTodos();
		Hospital buscado = aux.get(id);
		if(buscado!=null) {
			System.out.println("Encontrado el paciente con id "+ buscado.toString());
		}else {
			System.out.println("No existe el paciente de id " + id);
		}
		

			}

	@Override
	public void modificarPaciente(int id) {

		HashMap<Integer, Hospital> pacientes = this.leerTodos();

		if (pacientes.containsKey(id)) {
			Hospital pacienteExistente = pacientes.get(id);

			System.out.println("Escriba el nombre del paciente");
			String paciente = sc.next();
			pacienteExistente.setPaciente(paciente);

			System.out.println("Escriba la sala del paciente");
			int sala = sc.nextInt();
			pacienteExistente.setSala(sala);

			System.out.println("Escriba la enfermedad del paciente");
			String enfermedad = sc.next();
			pacienteExistente.setEnfermedad(enfermedad);

			pacientes.put(id, pacienteExistente);

			escribirMuchos(pacientes);

			System.out.println("Paciente modificado");
		} else {
			System.out.println("No se encontró un paciente con el ID proporcionado.");
		}

	}

	public void escribirTodos(HashMap<Integer, Hospital> pacientes) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("Ficheros/Hospital.txt");
			pw = new PrintWriter(fw);

			for (Hospital paciente : pacientes.values()) {
				String linea = paciente.getId() + ";";
				linea += paciente.getPaciente() + ";";
				linea += paciente.getSala() + ";";
				linea += paciente.getEnfermedad() + ";";
				pw.println(linea);
			}

			System.out.println("Pacientes actualizados");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
				if (pw != null) {
					pw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void eliminarPaciente(int id) {
		HashMap<Integer, Hospital> aux = this.leerTodos();
		if (aux.get(id) != null) {
			// if (aux.containsKey(id)) {
			aux.remove(id);
			System.out.println("Paciente con id " + id + " eliminado");
			this.escribirMuchos(aux);
		} else {
			System.out.println("No existe el paciente con id " + id);
		}

	}

	public void escribirMuchos(HashMap<Integer, Hospital> nuevos) {
		borrarDatos();

		for (Map.Entry<Integer, Hospital> entry : nuevos.entrySet()) {

			Hospital objeto = entry.getValue();

			insertarPaciente(objeto);

		}

	}

	public void borrarDatos() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("Ficheros/Hospital.txt", false);
			pw = new PrintWriter(fichero);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void buscarPaciente(String busqueda) {
		// TODO Auto-generated method stub
		
	}

}