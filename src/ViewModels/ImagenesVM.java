package ViewModels;

import Conexion.Conexion;
import Models.TImagenes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ImagenesVM {

    PreparedStatement ps;

    public void RegistrarImagen(TImagenes img) {
        String sql = "INSERT INTO imagenes (orden_trabajo, imagen) VALUES (?,?)";
        try {

            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setString(1, img.getOrden_trabajo());
            ps.setBytes(2, img.getImagen());
            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("imagen registrado exitosamente: ");
            } else {
                System.out.println("Error al registrar imagen de reparacion .");
            }

        } catch (Exception e) {
            System.out.println("Error al registrar imagen de reparacion : " + e.getMessage());

        }
    }

    public List<TImagenes> consultarPorId(String id) {
        String sql = "SELECT * FROM imagenes WHERE orden_trabajo=?";
        List<TImagenes> datos = new ArrayList<>();

        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TImagenes i = new TImagenes();
                i.setIdImagen(rs.getInt(1));
                i.setOrden_trabajo(rs.getString(2));
                i.setImagen(rs.getBytes(3));
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
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
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
