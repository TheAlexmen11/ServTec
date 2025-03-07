package Dao;

import Conexion.Conexion;
import Interfaces.CRUD;
import Interfaces.CargadorDeDatosTabla;
import Models.Reparaciones;
import Models.TOrdenesReparacion;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class ReparacionesDAO implements CRUD<Reparaciones, String>,CargadorDeDatosTabla {

    private PreparedStatement ps;
    private OrdenesReparacionesDAO orDAO = new OrdenesReparacionesDAO();

    @Override
    public List<Reparaciones> listar() {
        List<Reparaciones> datos = new ArrayList<>();

        String sql = """
                     SELECT 
                         o.orden_trabajo AS 'N de pedido',
                         c.nombre AS 'Nombre de cliente',
                         o.tipo_dispositivo AS 'Dispositivo',
                         o.marca AS 'Marca',
                         o.modelo AS 'Modelo',
                         o.fecha_entrega AS 'Fecha Entrega',
                         u.nombre AS 'Tecnico',
                         e.descripcion AS 'Estado'
                     FROM OrdenesReparacion o
                     JOIN cliente c ON o.id_cliente = c.id_cliente
                     JOIN usuario u ON o.id_usuario = u.id_usuario
                     JOIN estado e ON o.id_estado = e.id_estado""";

        try {
            ps = Conexion.getInstancia().getConnection().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reparaciones r = new Reparaciones();
                r.setOrdenTrabajo(rs.getString(1));
                r.setNombreCliente(rs.getString(2));
                r.setDispositivo(rs.getString(3));
                r.setMarca(rs.getString(4));
                r.setModelo(rs.getString(5));
                r.setFechaEntrega(rs.getTimestamp(6));
                r.setTecnico(rs.getString(7));
                r.setEstado(rs.getString(8));
                datos.add(r);
            }
        } catch (Exception e) {
            System.out.println("error en consulta");

        }
        return datos;

    }

    @Override
    public void cargarDatosTabla(DefaultTableModel tablemodel) {
        List<Reparaciones> reparaciones = listar();

        tablemodel.setRowCount(0);

        for (Reparaciones repair : reparaciones) {
            tablemodel.addRow(new Object[]{
                repair.getOrdenTrabajo(),
                repair.getNombreCliente(),
                repair.getDispositivo(),
                repair.getMarca(),
                repair.getModelo(),
                repair.getFechaEntrega(),
                repair.getTecnico(),
                repair.getEstado()
            });
        }

    }

    public String generarOrden() {
        // Obtener la fecha actual en formato YYMMDD
        SimpleDateFormat formato = new SimpleDateFormat("yyMMdd");
        String fecha = formato.format(new Date());

        // Obtener la cantidad de órdenes ya registradas hoy
        List<TOrdenesReparacion> ordenes = orDAO.listar();
        int contador = ordenes.size() + 1; // Incrementar el número de orden

        // Formar el código de orden único
        return "OT-" + fecha + "-" + String.format("%02d", contador);
    }

    @Override
    public void registrar(Reparaciones data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Reparaciones consultarPorId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(Reparaciones data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
