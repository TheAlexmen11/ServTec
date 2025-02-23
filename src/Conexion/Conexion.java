package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static Connection con = null;

    private static Conexion instancia  = null;

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/repairtechouse", "root", "");
            System.out.println("conexion exitosa");
        } catch (Exception e) {
            System.out.println("conexion fallida");
        }
    }

    public Connection getConnection() {
        return con;
    }
}
/*
package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection con = null;
    private static final String URL = "jdbc:mysql://localhost/repairtechouse";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Constructor privado para evitar instanciación directa
    private Conexion() {}

    // Método para obtener la conexión
    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa");
            } catch (ClassNotFoundException e) {
                System.out.println("Error: No se encontró el driver JDBC.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Error de conexión a la base de datos.");
                e.printStackTrace();
            }
        }
        return con;
    }


}
*/
