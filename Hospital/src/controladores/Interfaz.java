package controladores;

import java.util.HashMap;

import modelo.Hospital;

public interface Interfaz {

	HashMap<Integer, Hospital> leerTodos();
	public Hospital nuevoPaciente();
	void insertarPaciente(Hospital paciente);
	void leerPaciente(int id);
	void  modificarPaciente(int id);
	void eliminarPaciente(int id);
	void buscarPaciente(String busqueda);
}
