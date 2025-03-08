package controladores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

import modelo.Hospital;

public class BBDDManagerController implements Interfaz {

	Scanner sc;
	Connection conn;

	public BBDDManagerController() {
		sc = new Scanner(System.in);

		// Librer�a de MySQL
		String driver = "com.mysql.cj.jdbc.Driver";

		// Nombre de la base de datos
		String database = "adat_hospital";

		// Host
		String hostname = "localhost";

		// Puerto
		String port = "3306";

		// Ruta de nuestra base de datos (desactivamos el uso de SSL con
		// "?useSSL=false")
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";

		// Nombre de usuario
		String username = "root";

		// Clave de usuario
		String password = "";

		try {
			Class.forName(driver);
			System.out.println(url);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<Integer, Hospital> leerTodos() {
		HashMap<Integer, Hospital> aux = new HashMap<Integer, Hospital>();
		try {
			String query = "SELECT * FROM hospital";

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String paciente = rs.getString("paciente");
				int sala = rs.getInt("sala");
				String enfermedad = rs.getString("enfermedad");

				Hospital hospital = new Hospital(id, paciente, sala, enfermedad);
				aux.put(id, hospital);
			}

			st.close();

		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return aux;
	}

	@Override
	public Hospital nuevoPaciente() {
	    try {
	        System.out.println("Escriba el nombre del paciente");
	        String paciente = sc.nextLine();

	        System.out.println("Escriba la sala del paciente");
	        int sala = sc.nextInt();
	        sc.nextLine(); // Consumir la nueva línea después de nextInt()

	        System.out.println("Escriba la enfermedad del paciente");
	        String enfermedad = sc.nextLine();

	        String query = "INSERT INTO hospital (paciente, sala, enfermedad) VALUES (?, ?, ?)";

	        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

	        ps.setString(1, paciente);
	        ps.setInt(2, sala);
	        ps.setString(3, enfermedad);

	        int affectedRows = ps.executeUpdate();

	        if (affectedRows > 0) {
	            ResultSet generatedKeys = ps.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int id = generatedKeys.getInt(1);
	                Hospital nuevoPaciente = new Hospital(id, paciente, sala, enfermedad);
	                System.out.println("Paciente creado");
	                return nuevoPaciente;
	            } else {
	                System.err.println("No se pudo obtener el ID generado para el nuevo paciente.");
	            }
	        } else {
	            System.err.println("No se pudo insertar el nuevo paciente en la base de datos.");
	        }

	        ps.close();
	    } catch (SQLException e) {
	        System.err.println("Got an exception!");
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
	        HashMap<Integer, Hospital> pacientes = leerTodos();

	        if (pacientes.containsKey(id)) {
	            System.out.println("Escriba el nuevo nombre del paciente:");
	            String nuevoNombre = sc.nextLine();

	            System.out.println("Escriba la nueva sala del paciente:");
	            int nuevaSala = sc.nextInt();
	            sc.nextLine(); 

	            System.out.println("Escriba la nueva enfermedad del paciente:");
	            String nuevaEnfermedad = sc.nextLine();

	            String query = "UPDATE hospital SET paciente = ?, sala = ?, enfermedad = ? WHERE id = ?";
	            PreparedStatement ps = conn.prepareStatement(query);

	            ps.setString(1, nuevoNombre);
	            ps.setInt(2, nuevaSala);
	            ps.setString(3, nuevaEnfermedad);
	            ps.setInt(4, id);

	        } else {
	            System.err.println("Paciente no encontrado con el ID especificado.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Got an exception!");
	        e.printStackTrace();
	    }
	}

	@Override
	public void eliminarPaciente(int id) {
		try {
			String query = "DELETE FROM hospital WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void buscarPaciente(String busqueda) {
		String sql = "SELECT id, sala, enfermedad FROM hospital WHERE paciente = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, busqueda);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("paciente");
                    int sal = resultSet.getInt("sala");
                    String enfermedad = resultSet.getString("enfermedad");
                    
                }
            }
        }
     catch (SQLException e) {
        e.printStackTrace();
    }

    
    System.out.println("No se encontró ningun paciente con el nombre proporcionado.");
   
}

	}


