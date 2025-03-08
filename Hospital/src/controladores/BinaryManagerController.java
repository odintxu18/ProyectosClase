package controladores;

import java.util.HashMap;

import modelo.Hospital;
import java.io.*;
import java.util.*;


public class BinaryManagerController implements Interfaz {
	Scanner sc = new Scanner(System.in);
	File binario = new File("Ficheros/Hospital.bin");

	@Override
	public HashMap<Integer, Hospital> leerTodos() {
	    HashMap<Integer, Hospital> aux = new HashMap<Integer, Hospital>();
	    FileInputStream FIS = null;
	    DataInputStream dis = null;

	    try {
	    	FIS = new FileInputStream(binario);
	        dis = new DataInputStream(FIS);

	        while (dis.available() > 0) {
	            int id = dis.readInt();
	            String paciente = dis.readUTF();
	            int sala = dis.readInt();
	            String enfermedad = dis.readUTF();

	            Hospital h1 = new Hospital(id, paciente, sala, enfermedad);
	            aux.put(id, h1);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (dis != null) {
	                dis.close();
	            }
	            if (FIS != null) {
	            	FIS.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
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

	            // Llama a una función para escribir el nuevo paciente en el archivo binario
	           

	            System.out.println("Paciente creado");
	            return h2;
	        }
	    }
	}


	@Override
	public void insertarPaciente(Hospital paciente) {
		 FileOutputStream fos = null;
		    DataOutputStream dos = null;

		    try {
		        fos = new FileOutputStream(binario, true);
		        dos = new DataOutputStream(fos);

		        dos.writeInt(paciente.getId());
		        dos.writeUTF(paciente.getPaciente());
		        dos.writeInt(paciente.getSala());
		        dos.writeUTF(paciente.getEnfermedad());
		        
		        System.out.println("Paciente escrito en el archivo binario");
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (dos != null) {
		                dos.close();
		            }
		            if (fos != null) {
		                fos.close();
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
	

	@Override
	public void leerPaciente(int id) {
	    Hospital pacienteBuscado = null;
	    FileInputStream fis = null;
	    DataInputStream dis = null;

	    try {
	        fis = new FileInputStream(binario);
	        dis = new DataInputStream(fis);

	        while (dis.available() > 0) {
	            int pacienteId = dis.readInt();
	            String pacienteNombre = dis.readUTF();
	            int pacienteSala = dis.readInt();
	            String pacienteEnfermedad = dis.readUTF();

	            if (pacienteId == id) {
	                pacienteBuscado = new Hospital(pacienteId, pacienteNombre, pacienteSala, pacienteEnfermedad);
	                break; // Se encontró el paciente, salimos del bucle
	            }
	        }

	        if (pacienteBuscado != null) {
	            System.out.println("Encontrado el paciente con ID " + id);
	            System.out.println("ID: " + pacienteBuscado.getId());
	            System.out.println("Nombre: " + pacienteBuscado.getPaciente());
	            System.out.println("Sala: " + pacienteBuscado.getSala());
	            System.out.println("Enfermedad: " + pacienteBuscado.getEnfermedad());
	        } else {
	            System.out.println("No existe el paciente con ID " + id);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (dis != null) {
	                dis.close();
	            }
	            if (fis != null) {
	                fis.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
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

	        // Llama a una función para escribir todos los pacientes de nuevo en el archivo binario
	        escribirTodosEnBinario(pacientes);

	        System.out.println("Paciente modificado");
	    } else {
	        System.out.println("No se encontró un paciente con el ID proporcionado.");
	    }
	}

	public void escribirTodosEnBinario(HashMap<Integer, Hospital> pacientes) {
	    FileOutputStream fos = null;
	    DataOutputStream dos = null;

	    try {
	        fos = new FileOutputStream(binario);
	        dos = new DataOutputStream(fos);

	        for (Hospital paciente : pacientes.values()) {
	            dos.writeInt(paciente.getId());
	            dos.writeUTF(paciente.getPaciente());
	            dos.writeInt(paciente.getSala());
	            dos.writeUTF(paciente.getEnfermedad());
	        }

	        System.out.println("Pacientes actualizados en el archivo binario");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (dos != null) {
	                dos.close();
	            }
	            if (fos != null) {
	                fos.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	@Override
	public void eliminarPaciente(int id) {
	    HashMap<Integer, Hospital> pacientes = this.leerTodos();

	    if (pacientes.containsKey(id)) {
	        pacientes.remove(id);

	        // Llama a una función para escribir todos los pacientes restantes en el archivo binario
	        escribirTodosEnBinario(pacientes);

	        System.out.println("Paciente con ID " + id + " eliminado");
	    } else {
	        System.out.println("No existe el paciente con ID " + id);
	    }
	}
	
	public void borrarDatos() {
	    FileOutputStream fos = null;

	    try {
	        fos = new FileOutputStream(binario, false);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (fos != null) {
	                fos.close();
	             
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


	@Override
	public void buscarPaciente(String busqueda) {
		// TODO Auto-generated method stub
		
	}

}
