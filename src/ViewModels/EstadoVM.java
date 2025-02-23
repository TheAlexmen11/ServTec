package ViewModels;

import Conexion.Conexion;
import Models.TEstado;
import Models.TRoles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class EstadoVM {

    Conexion con1 = new Conexion();
    Connection con = con1.getConnection();
    PreparedStatement ps;

    public List<TEstado> Listar() {

        List<TEstado> datos = new ArrayList<>();
        String sql = "SELECT * FROM estado";

        try {
            ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TEstado e = new TEstado();
                e.setIdEstado(rs.getInt(1));
                e.setDescripcion(rs.getString(2));
                datos.add(e);
            }
        } catch (Exception e) {
            System.out.println("error en consulta " + e);
        }
        return datos;
    }

    public void llenarCombobox(JComboBox<TEstado> comboEstado) {

        List<TEstado> estados = Listar();

        // Limpiar la combobox
        comboEstado.removeAll();

        // Agregar los datos de los clientes al modelo
        for (TEstado est : estados) {
            comboEstado.addItem(new TEstado(
                    est.getIdEstado(),
                    est.getDescripcion()
            ));
        }
    }

    public TEstado consultarPorId(int id) {
        TEstado est = new TEstado();
        String sql = "select * from estado where id_estado=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                est.setIdEstado(rs.getInt(1));
                est.setDescripcion(rs.getString(2));
            } else {
                System.out.println("No se encontró un estado con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error al consultarid del estado: " + e.getMessage());

        }
        return est;
    }
    
}
