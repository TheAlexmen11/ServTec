package Controllers;

import Conexion.Conexion;
import Interfaces.CRUD;
import Interfaces.CargadorDeComboBox;
import Interfaces.CargadorDeDatosTabla;
import Models.TRoles;
import Models.TUsuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class UsuarioDAO implements CRUD<TUsuarios, Integer>, CargadorDeComboBox<TUsuarios>,CargadorDeDatosTabla {
    
    PreparedStatement ps;
    
    @Override
    public void registrar(TUsuarios usr) {
        
        String sql = "INSERT INTO usuario (id_rol, nombre, usuario, password, correo,dni) VALUES (?,?,?,?,?,?);";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setInt(1, usr.getRol());
            ps.setString(2, usr.getNombre());
            ps.setString(3, usr.getUsuario());
            ps.setString(4, usr.getPassword());
            ps.setString(5, usr.getCorreo());
            ps.setInt(6, usr.getDni());

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("Cliente registrado exitosamente: " + usr);
            } else {
                System.out.println("Error al registrar el usuario.");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar el cliente: " + e.getMessage());
        }
    }
    
    @Override
    public List<TUsuarios> listar() {
        List<TUsuarios> datos = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TUsuarios c = new TUsuarios();
                c.setIdUsuario(rs.getInt(1));
                c.setRol(rs.getInt(2));
                c.setNombre(rs.getString(3));
                c.setUsuario(rs.getString(4));
                c.setPassword(rs.getString(5));
                c.setCorreo(rs.getString(6));
                c.setDni(rs.getInt(7));
                datos.add(c);
            }
        } catch (Exception e) {
            System.out.println("error en consulta" + e);
        }
        return datos;
    }
    
    @Override
    public void cargarDatosTabla(DefaultTableModel tableModel) {
        List<TUsuarios> usuarios = listar();
        TRoles rol = new TRoles();
        RolesDAO rolesvm = new RolesDAO();
        //limpiar tabla
        tableModel.setRowCount(0);

        //Agregar datos usuario
        for (TUsuarios user : usuarios) {
            rol = rolesvm.consultarPorId(user.getRol());
            tableModel.addRow(new Object[]{
                user.getIdUsuario(),
                rol,
                user.getNombre(),
                user.getUsuario()
            });
        }
    }
    
    @Override
    public TUsuarios consultarPorId(Integer id) {
        TUsuarios u = new TUsuarios();
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setIdUsuario(rs.getInt(1));
                u.setRol(rs.getInt(2));
                u.setNombre(rs.getString(3));
                u.setUsuario(rs.getString(4));
                u.setPassword(rs.getString(5));
                u.setCorreo(rs.getString(6));
                u.setDni(rs.getInt(7));
            } else {
                System.out.println("No se encontró el Usuario con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("Error al consultarid del Usuario: " + e.getMessage());
        }
        return u;
    }
    
     @Override
    public void eliminar(Integer idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setInt(1, idUsuario);

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("Usuario eliminado exitosamente: " + idUsuario);
            } else {
                System.out.println("Error al eliminar el Usuario.");
            }
        } catch (Exception e) {
            System.out.println("Error al Eliminar el usuario: " + e.getMessage());
        }
    }
    
    @Override
    public void actualizar(TUsuarios tusr) {
        String sql = "UPDATE usuario SET  id_rol=?, nombre=?, usuario=?, password=?, correo=? , dni=?  WHERE id_usuario= ? ";
        try {
            PreparedStatement ps = Conexion.getInstancia().getConnection().prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setInt(1, tusr.getRol());
            ps.setString(2, tusr.getNombre());
            ps.setString(3, tusr.getUsuario());
            ps.setString(4, tusr.getPassword());
            ps.setString(5, tusr.getCorreo());
            ps.setInt(6, tusr.getDni());
            ps.setInt(7, tusr.getIdUsuario());
            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("Usuario Actualido exitosamente: " + tusr.getIdUsuario());
            } else {
                System.out.println("Error al Actualizar el usuario.");
            }
        } catch (Exception e) {
            System.out.println("Error al Actualizar el usuario: " + e.getMessage());
            
        }
        
    }
    
    @Override
    public void llenarComboBox(JComboBox<TUsuarios> comboUsuario) {
        
        List<TUsuarios> usuarios = listar();

        // Limpiar la combobox
        comboUsuario.removeAll();

        // Agregar los datos de los clientes al modelo
        for (TUsuarios usr : usuarios) {
            comboUsuario.addItem(new TUsuarios(
                    usr.getIdUsuario(),
                    usr.getRol(),
                    usr.getNombre(),
                    usr.getUsuario(),
                    usr.getPassword(),
                    usr.getCorreo(),
                    usr.getDni()
            ));
        }
    }
}
