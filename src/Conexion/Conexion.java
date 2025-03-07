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
    
    public Connection getConnection() {
        return con;
    }
    

    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/repairtechouse", "root", "123456");
            System.out.println("conexion exitosa");
        } catch (Exception e) {
            System.out.println("conexion fallida");
        }
    }
    
     public static void cerrarConexion() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexión cerrada");
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
     
}
