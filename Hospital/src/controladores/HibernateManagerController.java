package controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import modelo.Hospital;

public class HibernateManagerController implements Interfaz {

	Session session;

	public HibernateManagerController() {

		HibernateUtil util = new HibernateUtil();

		session = util.getSessionFactory().openSession();

	}

	@Override
	public HashMap<Integer, Hospital> leerTodos() {
		HashMap<Integer, Hospital> aux = new HashMap<>();

		try {
			TypedQuery<Hospital> q = session.createQuery("select h from Hospital h");
			List<Hospital> results = q.getResultList();

			for (Hospital hos : results) {
				System.out.println("Id: " + hos.getId() + " - Nombre: " + hos.getPaciente() + " - Sala: "
						+ hos.getSala() + " - Enfermedad: " + hos.getEnfermedad());
				aux.put(hos.getId(), hos);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aux;
	}

	@Override
	public Hospital nuevoPaciente() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("ID del paciente: ");
		int id = scanner.nextInt();

		scanner.nextLine();

		System.out.print("Nombre del paciente: ");
		String nombre = scanner.nextLine();

		System.out.print("Número de sala: ");
		int sala = scanner.nextInt();

		scanner.nextLine();

		System.out.print("Enfermedad: ");
		String enfermedad = scanner.nextLine();

		return new Hospital(id, nombre, sala, enfermedad);
	}

	@Override
	public void insertarPaciente(Hospital paciente) {
		try {
			session.beginTransaction();
			session.save(paciente);
			session.getTransaction().commit();
			System.out.println("Paciente insertado con éxito.");
		} catch (Exception e) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			System.err.println("Error al insertar paciente en la base de datos.");
		}
	}

	@Override
	public void leerPaciente(int id) {
		try {
			TypedQuery<Hospital> query = session.createQuery("select h from Hospital h where h.id = :id",
					Hospital.class);
			query.setParameter("id", id);
			Hospital paciente = query.getSingleResult();

			if (paciente != null) {
				System.out.println("Encontrado el paciente con id " + paciente.toString());
			} else {
				System.out.println("No existe el paciente de id " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modificarPaciente(int id) {
		try {
			TypedQuery<Hospital> query = session.createQuery("select h from Hospital h where h.id = :id",
					Hospital.class);
			query.setParameter("id", id);
			Hospital paciente = query.getSingleResult();

			if (paciente != null) {
				System.out.println("Paciente actual: " + paciente.toString());

				Scanner scanner = new Scanner(System.in);

				System.out.print("Nuevo nombre del paciente: ");
				String nuevoNombre = scanner.nextLine();
				paciente.setPaciente(nuevoNombre);

				System.out.print("Nueva sala del paciente: ");
				int nuevaSala = scanner.nextInt();
				paciente.setSala(nuevaSala);

				System.out.print("Nueva enfermedad del paciente: ");
				scanner.nextLine();
				String nuevaEnfermedad = scanner.nextLine();
				paciente.setEnfermedad(nuevaEnfermedad);

				session.beginTransaction();
				session.update(paciente);
				session.getTransaction().commit();

				System.out.println("Paciente modificado.");

			} else {
				System.out.println("No existe el paciente de id " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarPaciente(int id) {
		try {
			TypedQuery<Hospital> query = session.createQuery("select h from Hospital h where h.id = :id",
					Hospital.class);
			query.setParameter("id", id);
			Hospital paciente = query.getSingleResult();

			if (paciente != null) {
				System.out.println("Paciente a eliminar: " + paciente.toString());

				System.out.println("Paciente eliminado con éxito.");

				session.beginTransaction();
				session.delete(paciente);
				session.getTransaction().commit();

			} else {
				System.out.println("No existe el paciente de id " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void buscarPaciente(String busqueda) {
		// TODO Auto-generated method stub

	}

}
