package ViewModels;

import Conexion.Conexion;
import Models.Reparaciones;
import Models.TOrdenesReparacion;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class ReparacionesVM {

    PreparedStatement ps;
    OrdenesReparacionesVM orVM = new OrdenesReparacionesVM();

    public List<Reparaciones> listarReparaciones() {
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
                r.setFechaEntrega(rs.getString(6));
                r.setTecnico(rs.getString(7));
                r.setEstado(rs.getString(8));
                datos.add(r);
            }
        } catch (Exception e) {
            System.out.println("error en consulta");

        }
        return datos;

    }

   

    public void cargarDatosReparacion(DefaultTableModel tablemodel) {
        List<Reparaciones> reparaciones = listarReparaciones();

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
    List<TOrdenesReparacion> ordenes = orVM.listarOrdenReparaciones();
    int contador = ordenes.size() + 1; // Incrementar el número de orden

    // Formar el código de orden único
    return "OT-" + fecha + "-" + String.format("%02d", contador);
}


}
