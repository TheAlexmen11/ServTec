package Dao;

import Conexion.Conexion;
import Interfaces.CRUD;
import Models.TDatosEmpresa;
import java.sql.*;
import java.util.List;

public class DatosEmpresaDAO implements CRUD<TDatosEmpresa, Integer> {

    private PreparedStatement ps;

    @Override
    public void registrar(TDatosEmpresa data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TDatosEmpresa> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public TDatosEmpresa consultarPorId(Integer id) {
        TDatosEmpresa d = new TDatosEmpresa();
        String sql = "select * from datosempresa  where id_empresa=?";
        try {
            ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                d.setIdEmpresa(rs.getInt(1));
                d.setNombre(rs.getString(2));
                d.setDireccion(rs.getString(3));
                d.setCorreo(rs.getString(4));
                d.setTelefono(rs.getString(5));
                d.setRazonSocial(rs.getString(6));
                d.setHorario(rs.getString(7));
                d.setDiasHorario(rs.getString(8));
                d.setHorario1(rs.getString(9));
                d.setDiasHorario1(rs.getString(10));
                d.setTerminoCondiciones(rs.getString(11));
                d.setLogoUrl(rs.getString(12));
            } else {
                System.out.println("No se encontró una empresa con ID: " + id);
            }

        } catch (Exception e) {
            System.out.println("Error al consultarid del empresa: " + e.getMessage());
        }
        return d;
    }

    @Override
    public void actualizar(TDatosEmpresa data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
