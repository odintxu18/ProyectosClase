package modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hospital {

	@Id
	@GeneratedValue
	private int id;
	private String paciente;
	private int sala;
	private String enfermedad;

	public Hospital() {

	}

	public Hospital(int id, String paciente, int sala, String enfermedad) {
		super();
		this.id = id;
		this.paciente = paciente;
		this.sala = sala;
		this.enfermedad = enfermedad;
	}

	public Hospital(int currentId) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public String getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", paciente=" + paciente + ", sala=" + sala + ", enferdad=" + enfermedad + "]";
	}

}
