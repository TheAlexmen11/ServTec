package ViewModels;

import Conexion.Conexion;
import Models.TImagenes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ImagenesVM {

    Conexion con1 = new Conexion();
    Connection con = con1.getConnection();
    PreparedStatement ps;

    public void RegistrarImagen(List<TImagenes> img) {
        String sql = "INSERT INTO imagenes (orden_trabajo, ruta_imagen) VALUES (?,?)";
        try {
            for (TImagenes tImagenes : img) {
                ps = con.prepareStatement(sql);
                ps.setString(1, tImagenes.getOrden_trabajo());
                ps.setString(2, tImagenes.getRuta_imagen());
                // Ejecutar la inserción
                int rowsAffected = ps.executeUpdate();

                // Confirmación en consola
                if (rowsAffected > 0) {
                    System.out.println("imagen registrado exitosamente: " + tImagenes.getRuta_imagen());
                } else {
                    System.out.println("Error al registrar imagen de reparacion .");
                }
            }

        } catch (Exception e) {
            System.out.println("Error al registrar imagen de reparacion : " + e.getMessage());

        }
    }

    public List<TImagenes> consultarPorId(String id) {
        String sql = "SELECT * FROM imagenes WHERE orden_trabajo=?";
        List<TImagenes> datos = new ArrayList<>();

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TImagenes i = new TImagenes();
                i.setIdImagen(rs.getInt(1));
                i.setOrden_trabajo(rs.getString(2));
                i.setRuta_imagen(rs.getString(3));
                datos.add(i);
            }
        } catch (Exception e) {
            System.out.println("error consulta imagenes " + e);
        }

        return datos;
    }
    
    public void eliminarImagenReparacion(String idOrdenReparacion) {
        String sql = "DELETE FROM imagenes WHERE orden_trabajo = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setString(1, idOrdenReparacion);

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("imagenes de reparacion eliminado exitosamente: " + idOrdenReparacion);
            } else {
                System.out.println("Error al eliminado imagenes de reparacion.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar imagenes de reparacion: " + e.getMessage());

        }

    }
    
}
