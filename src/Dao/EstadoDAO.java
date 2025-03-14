package Dao;

import Conexion.Conexion;
import Models.TEstado;
import Interfaces.CRUD;
import Interfaces.CargadorDeComboBox;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;


public class EstadoDAO implements CRUD<TEstado,Integer>, CargadorDeComboBox<TEstado> {

    PreparedStatement ps;

    @Override
    public List<TEstado> listar() {

        List<TEstado> datos = new ArrayList<>();
        String sql = "SELECT * FROM estado";

        try {
            ps = Conexion.getInstancia().getConnection().prepareCall(sql);
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

    @Override
    public void llenarComboBox(JComboBox<TEstado> comboEstado) {

        List<TEstado> estados = listar();

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

    @Override
    public TEstado consultarPorId(Integer id) {
        TEstado est = new TEstado();
        String sql = "select * from estado where id_estado=?";

        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
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

    @Override
    public void registrar(TEstado data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(TEstado data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
