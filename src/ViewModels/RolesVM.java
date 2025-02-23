package ViewModels;

import Conexion.Conexion;
import Models.TRoles;
import java.sql.*;
import javax.swing.JComboBox;

public class RolesVM {

    Conexion con1 = new Conexion();
    Connection con = con1.getConnection();
    PreparedStatement ps;

    public void consultarRol() {

    }

    public TRoles consultarRolId(int id) {
        TRoles r = new TRoles();
        String sql = "SELECT * FROM roles WHERE id_roles = ?";
        try {
            ps = con.prepareStatement(sql);
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

    public void llenarCombobox(JComboBox<TRoles> comboRoles) {
        String sql = "SELECT * FROM roles";
        try  {
            ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            comboRoles.removeAll();
            while (rs.next()) {
                comboRoles.addItem(new TRoles(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar roles para combobox: " + e.getMessage());
        }

    }

}
