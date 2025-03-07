package Dao;

import Conexion.Conexion;
import Interfaces.CRUD;
import Models.TOrdenesReparacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrdenesReparacionesDAO implements CRUD<TOrdenesReparacion,String>{
    
    private PreparedStatement ps;

    @Override
    public void registrar(TOrdenesReparacion tor) {
        String sql = "INSERT INTO OrdenesReparacion "
                + "(orden_trabajo, fecha_recepcion, id_usuario, id_cliente, id_estado, tipo_dispositivo, marca, "
                + "modelo, password, nro_serie,valor_diagnostico, valor_reparacion, problema_reportado, fecha_entrega, observacion, comentarios) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);

            ps.setString(1, tor.getOrdenTrabajo());
            ps.setTimestamp(2, tor.getFechaRecepcion());
            ps.setInt(3, tor.getIdUsuario());
            ps.setInt(4, tor.getIdCliente());
            ps.setInt(5, tor.getIdEstado());
            ps.setString(6, tor.getTipoDispositivo());
            ps.setString(7, tor.getMarca());
            ps.setString(8, tor.getModelo());
            ps.setString(9, tor.getPassword());
            ps.setString(10, tor.getNroSerie());
            ps.setDouble(11, tor.getValorDiagnostico());
            ps.setDouble(12, tor.getValorReparacion());
            ps.setString(13, tor.getProblemaReportado());
            ps.setTimestamp(14, tor.getFechaEntrega());
            ps.setString(15, tor.getObservaciones());
            ps.setString(16, tor.getComentarios());

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("orden reparacion registrado exitosamente: " + tor);
            } else {
                System.out.println("Error al registrar orden reparacion .");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar orden reparacion : " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String idOrdenReparacion) {
        String sql = "delete from ordenesreparacion where orden_trabajo=?";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setString(1, idOrdenReparacion);

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("orden reparacion eliminado exitosamente: " + idOrdenReparacion);
            } else {
                System.out.println("Error al eliminado orden reparacion.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar orden reparacion: " + e.getMessage());

        }          
    }

    @Override
    public void actualizar(TOrdenesReparacion tor) {
        String sql = "UPDATE ordenesreparacion SET fecha_recepcion=?, id_usuario=?, id_cliente=?, id_estado=?, tipo_dispositivo=?, marca=?, "
                + "modelo=?, password=?, nro_serie=?,valor_diagnostico=?, valor_reparacion=?, problema_reportado=?, fecha_entrega=?, observacion=?, comentarios=? WHERE orden_trabajo = ?";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);

            // Asignar los valores del cliente al PreparedStatement
            ps.setTimestamp(1, tor.getFechaRecepcion());
            ps.setInt(2, tor.getIdUsuario());
            ps.setInt(3, tor.getIdCliente());
            ps.setInt(4, tor.getIdEstado());
            ps.setString(5, tor.getTipoDispositivo());
            ps.setString(6, tor.getMarca());
            ps.setString(7, tor.getModelo());
            ps.setString(8, tor.getPassword());
            ps.setString(9, tor.getNroSerie());
            ps.setDouble(10, tor.getValorDiagnostico());
            ps.setDouble(11, tor.getValorReparacion());
            ps.setString(12, tor.getProblemaReportado());
            ps.setTimestamp(13, tor.getFechaEntrega());
            ps.setString(14, tor.getObservaciones());
            ps.setString(15, tor.getComentarios());
            ps.setString(16, tor.getOrdenTrabajo());

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("Cliente Actualido exitosamente: " + tor.getOrdenTrabajo());
            } else {
                System.out.println("Error al Actualizar el cliente.");
            }
        } catch (Exception e) {
            System.out.println("Error al Actualizar el cliente: " + e.getMessage()+" ::"+tor.getOrdenTrabajo());
        }
    }

    @Override
    public TOrdenesReparacion consultarPorId(String id) {
        TOrdenesReparacion o = new TOrdenesReparacion();
        String sql = "SELECT * FROM ordenesreparacion WHERE orden_trabajo=?";
        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                o.setOrdenTrabajo(rs.getString(1));
                o.setFechaRecepcion(rs.getTimestamp(2));
                o.setIdUsuario(rs.getInt(3));
                o.setIdCliente(rs.getInt(4));
                o.setIdEstado(rs.getInt(5));
                o.setTipoDispositivo(rs.getString(6));
                o.setMarca(rs.getString(7));
                o.setModelo(rs.getString(8));
                o.setPassword(rs.getString(9));
                o.setNroSerie(rs.getString(10));
                o.setValorDiagnostico(rs.getDouble(11));
                o.setValorReparacion(rs.getDouble(12));
                o.setProblemaReportado(rs.getString(13));
                o.setFechaEntrega(rs.getTimestamp(14));;
                o.setObservaciones(rs.getString(15));
                o.setComentarios(rs.getString(16));
                
                System.out.println("codigo : " + rs.getString(1));
            } else {
                System.out.println("No se encontró una orden con ID: " + id);
            }

        } catch (Exception e) {
            System.out.println("error en consulta orden reparacion" + e);
        }

        return o;
    }

    @Override
    public List<TOrdenesReparacion> listar() {
        List<TOrdenesReparacion> datos = new ArrayList<>();
        String sql = "SELECT * FROM ordenesreparacion";

        try {
            ps = Conexion.getInstancia().getConnection().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TOrdenesReparacion o = new TOrdenesReparacion();
                o.setOrdenTrabajo(rs.getString(1));
                o.setFechaRecepcion(rs.getTimestamp(2));
                o.setIdUsuario(rs.getInt(3));
                o.setIdCliente(rs.getInt(4));
                o.setIdEstado(rs.getInt(5));
                o.setTipoDispositivo(rs.getString(6));
                o.setMarca(rs.getString(7));
                o.setModelo(rs.getString(8));
                o.setPassword(rs.getString(9));
                o.setNroSerie(rs.getString(10));
                o.setValorDiagnostico(rs.getDouble(11));
                o.setValorReparacion(rs.getDouble(12));
                o.setProblemaReportado(rs.getString(13));
                o.setFechaEntrega(rs.getTimestamp(14));
                o.setObservaciones(rs.getString(15));
                o.setComentarios(rs.getString(16));
                datos.add(o);
            }
        } catch (Exception e) {
            System.out.println("error consulta " + e);
        }
        
        return datos;
    }
}
