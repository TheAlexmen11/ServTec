package Dao;

import Conexion.Conexion;
import Interfaces.CRUD;
import Interfaces.CargadorDeComboBox;
import Models.TRoles;
import java.sql.*;
import java.util.List;
import javax.swing.JComboBox;

public class RolesDAO implements CRUD<TRoles,Integer>,CargadorDeComboBox<TRoles>{

    private  PreparedStatement ps;

    @Override
    public TRoles consultarPorId(Integer id) {
        TRoles r = new TRoles();
        String sql = "SELECT * FROM roles WHERE id_roles = ?";
        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r.setIdRol(rs.getInt(1));
                r.setNombre(rs.getString(2));
            } else {
                System.out.println("No se encontró el Rol con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar id del rol: " + e.getMessage());
        }
        return r;

    }

    @Override
    public void llenarComboBox(JComboBox<TRoles> comboRoles) {
        String sql = "SELECT * FROM roles";
        try  {
            ps = Conexion.getInstancia().getConnection().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            comboRoles.removeAll();
            while (rs.next()) {
                comboRoles.addItem(new TRoles(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar roles para combobox: " + e.getMessage());
        }

    }

    @Override
    public void registrar(TRoles data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TRoles> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizar(TRoles data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
