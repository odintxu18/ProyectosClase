package controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import modelo.Hospital;

public class XMLManagerController implements Interfaz {
	Scanner sc = new Scanner(System.in);
	File xmle = new File ("Ficheros/Hospital.xml");

	@Override
	public HashMap<Integer, Hospital> leerTodos() {
		 HashMap<Integer, Hospital> hospitalMap = new HashMap<>();
	        try {
	            SAXBuilder saxBuilder = new SAXBuilder();
	            org.jdom2.Document document = saxBuilder.build(xmle);
	            Element rootElement = document.getRootElement();
	            List<Element> hospitalElements = rootElement.getChildren("paciente");

	            for (Element hospitalElement : hospitalElements) {
	                int id = Integer.parseInt(hospitalElement.getAttributeValue("id"));
	                String paciente = hospitalElement.getChildText("nombre");
	                int sala = Integer.parseInt(hospitalElement.getChildText("sala"));
	                String enfermedad = hospitalElement.getChildText("enferdad");

	                Hospital hospital = new Hospital(id, paciente, sala, enfermedad);
	                hospitalMap.put(id, hospital);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return hospitalMap;
	    
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
		            sc.nextLine();
		            System.out.println("Escriba el nombre del paciente");
		            String paciente = sc.nextLine();

		            System.out.println("Escriba la sala del paciente");
		            int sala = sc.nextInt();
		            sc.nextLine();
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
		  try {
		        SAXBuilder saxBuilder = new SAXBuilder();
		        org.jdom2.Document document = saxBuilder.build(xmle);
		        Element rootElement = document.getRootElement();

		        Element nuevoHospital = new Element("paciente");
		        nuevoHospital.setAttribute("id", String.valueOf(paciente.getId()));
		        nuevoHospital.addContent(new Element("nombre").setText(paciente.getPaciente()));
		        nuevoHospital.addContent(new Element("sala").setText(String.valueOf(paciente.getSala())));
		        nuevoHospital.addContent(new Element("enferdad").setText(paciente.getEnfermedad()));

		        rootElement.addContent(nuevoHospital);

		        org.jdom2.output.XMLOutputter xmlOutput = new org.jdom2.output.XMLOutputter();
		        xmlOutput.output(document, new FileOutputStream(xmle));

		        System.out.println("Paciente insertado con éxito.");
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
	}

	@Override
	public void leerPaciente(int id) {
		HashMap<Integer, Hospital> aux = this.leerTodos();
		Hospital buscado = aux.get(id);
		if(buscado!=null) {
			System.out.println("Encontrado el paciente con id "+ buscado.toString());
		}else {
			System.out.println("No existe el paciente de id " + id);
		}
		
	}
	
	public void buscarPaciente(String busqueda) {
	    HashMap<Integer, Hospital> pacientes = this.leerTodos();
	    boolean encontrado = false;

	    for (Hospital paciente : pacientes.values()) {
	        boolean isNumeric = busqueda.matches("-?\\d+"); // Comprueba si la cadena es un número
	        if ((isNumeric && String.valueOf(paciente.getId()).equals(busqueda)) ||
	            paciente.getPaciente().equalsIgnoreCase(busqueda) ||
	            (isNumeric && String.valueOf(paciente.getSala()).equals(busqueda)) ||
	            paciente.getEnfermedad().equalsIgnoreCase(busqueda)) {
	            System.out.println("Paciente encontrado: " + paciente.toString());
	            encontrado = true;
	        }
	    }

	    if (!encontrado) {
	        System.out.println("No se encontró ningún paciente con la búsqueda: " + busqueda);
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

		        escribirTodosEnXml(pacientes);

		        System.out.println("Paciente modificado");
		    } else {
		        System.out.println("No se encontró un paciente con el ID proporcionado.");
		    }
		
	}

	@Override
	public void eliminarPaciente(int id) {
		HashMap<Integer, Hospital> pacientes = this.leerTodos();

	    if (pacientes.containsKey(id)) {
	        pacientes.remove(id);
	        
	        Element rootElement = new Element("Hospital");
	        for (Hospital paciente : pacientes.values()) {
	            Element nuevoHospital = new Element("paciente");
	            nuevoHospital.setAttribute("id", String.valueOf(paciente.getId()));
	            nuevoHospital.addContent(new Element("nombre").setText(paciente.getPaciente()));
	            nuevoHospital.addContent(new Element("sala").setText(String.valueOf(paciente.getSala())));
	            nuevoHospital.addContent(new Element("enferdad").setText(paciente.getEnfermedad()));
	            rootElement.addContent(nuevoHospital);
	        }
	        
	        org.jdom2.Document document = new org.jdom2.Document(rootElement);

	        org.jdom2.output.XMLOutputter xmlOutput = new org.jdom2.output.XMLOutputter();
	        try {
	            xmlOutput.output(document, new FileOutputStream(xmle));
	            System.out.println("Paciente con ID " + id + " eliminado.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("No existe el paciente con ID " + id);
	    }
	}
	
	public void escribirTodosEnXml(HashMap<Integer,Hospital> pacientesXML) {
		try {
	        SAXBuilder saxBuilder = new SAXBuilder();
	        org.jdom2.Document document = saxBuilder.build(xmle);
	        Element rootElement = document.getRootElement();

	        Element nuevoHospital = new Element("paciente");
	        for(Hospital paciente : pacientesXML.values()) {
	        nuevoHospital.setAttribute("id", String.valueOf(paciente.getId()));
	        nuevoHospital.addContent(new Element("nombre").setText(paciente.getPaciente()));
	        nuevoHospital.addContent(new Element("sala").setText(String.valueOf(paciente.getSala())));
	        nuevoHospital.addContent(new Element("enferdad").setText(paciente.getEnfermedad()));
	        

	        rootElement.addContent(nuevoHospital);

	        org.jdom2.output.XMLOutputter xmlOutput = new org.jdom2.output.XMLOutputter();
	        
	        xmlOutput.output(document, new FileOutputStream(xmle));
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
		
	}

}
