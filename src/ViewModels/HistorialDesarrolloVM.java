package ViewModels;

import Conexion.Conexion;
import Models.THistorialDesarrollo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class HistorialDesarrolloVM {

    PreparedStatement ps;

    public void registrarHistorialDesarrollo(THistorialDesarrollo htor) {
        String sql = "INSERT INTO historialdesarrollo (orden_trabajo, fecha_cambio, desarrollo_tecnico) VALUES (?, ?, ?)";
        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);

            ps.setString(1, htor.getOrden_trabajo());
            ps.setTimestamp(2, htor.getFechaCambio());
            ps.setString(3, htor.getDesarrolloTecnico());

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("historial de reparacion registrado exitosamente: " + htor);
            } else {
                System.out.println("Error al registrar historial de reparacion .");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar historial de reparacion : " + e.getMessage());

        }

    }

    public void eliminarHistorialReparacion(String idOrdenReparacion) {
        String sql = "DELETE FROM historialdesarrollo WHERE orden_trabajo = ?;";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setString(1, idOrdenReparacion);

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("historial de reparacion eliminado exitosamente: " + idOrdenReparacion);
            } else {
                System.out.println("Error al eliminado historial de reparacion.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar historial de reparacion: " + e.getMessage());

        }

    }

    public void ActualizarHistorial(THistorialDesarrollo hdes) {
        String sql = "";

    }

    public List<THistorialDesarrollo> consultarPorId(String id) {
        String sql = "SELECT * FROM historialdesarrollo WHERE orden_trabajo=?";
        List<THistorialDesarrollo> datos = new ArrayList<>();

        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                THistorialDesarrollo h = new THistorialDesarrollo();
                h.setIdHistorial(rs.getInt(1));
                h.setOrden_trabajo(rs.getString(2));
                h.setFechaCambio(rs.getTimestamp(3));
                h.setDesarrolloTecnico(rs.getString(4));
                datos.add(h);
            }
        } catch (Exception e) {
            System.out.println("error consulta historial " + e);
        }

        return datos;

    }

    public void cargarDatosHistorial(DefaultTableModel tableModel, String orden) {
        List<THistorialDesarrollo> historial = consultarPorId(orden);
        
        tableModel.setRowCount(0);
        
        for (THistorialDesarrollo historic : historial) {
            tableModel.addRow(new Object[]{
                historic.getIdHistorial(),
                historic.getOrden_trabajo(),
                historic.getDesarrolloTecnico(),
                historic.getFechaCambio()
            });
        }

    }

}
