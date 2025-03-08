package controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


import modelo.Hospital;

public class ODBManagerController implements Interfaz{

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("db/pacientes.odb");
	EntityManager em = emf.createEntityManager();
	Scanner sc = new Scanner(System.in);
	@Override
	public HashMap<Integer, Hospital> leerTodos() {
		HashMap<Integer, Hospital> aux = new HashMap<>();
		TypedQuery<Hospital> query = em.createQuery("SELECT b FROM Hospital b", Hospital.class);
		List<Hospital> results = query.getResultList();
		System.out.println("MOSTRAMOS TODOS LOS OBJETOS");
		for(Hospital bb : results) {
			System.out.println(bb);
		}
		return aux;
	}

	@Override
	public Hospital nuevoPaciente() {
		while (true) {
	        System.out.println("Escribe el ID del paciente");
	        int id = sc.nextInt();
	        sc.nextLine();

	        HashMap<Integer, Hospital> aux = leerTodos();

	        if (aux.containsKey(id)) {
	            System.out.println("El ID ya existe.");
	        } else {
	            System.out.println("Escribe el nombre del paciente");
	            String paciente = sc.nextLine();

	            System.out.println("Escribe la sala del paciente");
	            int sala = sc.nextInt();
	            sc.nextLine(); 

	            System.out.println("Escribe la enfermedad del paciente");
	            String enfermedad = sc.nextLine();

	            Hospital nuevoPaciente = new Hospital(id, paciente, sala, enfermedad);

	            em.getTransaction().begin();
	            em.persist(nuevoPaciente);
	            em.getTransaction().commit();

	            System.out.println("Paciente creado");
	            return nuevoPaciente;
            }
		}
	}

	@Override
	public void insertarPaciente(Hospital paciente) {
		em.getTransaction().begin();
		em.persist(paciente);
		em.getTransaction().commit();
		
	}

	@Override
	public void leerPaciente(int id) {
		Hospital buscado = em.find(Hospital.class, id);

		if (buscado != null) {

			System.out.println("Encontrado paciente con id " + id + " " + buscado.toString());

		} else {
			System.out.println("No existe el paciente con id " + id);
		}
		
	}

	@Override
	public void modificarPaciente(int id) {
		Hospital hos = em.find(Hospital.class, id);
		
		Scanner scanner = new Scanner(System.in);

		System.out.print("Nuevo nombre del paciente: ");
		String nuevoNombre = scanner.nextLine();
		hos.setPaciente(nuevoNombre);

		System.out.print("Nueva sala del paciente: ");
		int nuevaSala = scanner.nextInt();
		hos.setSala(nuevaSala);

		System.out.print("Nueva enfermedad del paciente: ");
		scanner.nextLine();
		String nuevaEnfermedad = scanner.nextLine();
		hos.setEnfermedad(nuevaEnfermedad);
		
		em.getTransaction().begin();
		em.merge(hos);
		em.getTransaction().commit();
		
	}

	@Override
	public void eliminarPaciente(int id) {
		Hospital hos = em.find(Hospital.class, id);
		
		em.getTransaction().begin();
		em.remove(hos);
		em.getTransaction().commit();
		System.out.println("paciente eliminado");
	}

	@Override
	public void buscarPaciente(String busqueda) {
		// TODO Auto-generated method stub
		
	}

}
