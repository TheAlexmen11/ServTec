package ViewModels;

import Conexion.Conexion;
import Models.TClientes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

public class ClienteVM {

    Conexion con1 = new Conexion();
    Connection con = con1.getConnection();
    PreparedStatement ps;
    DefaultTableModel modelo;
    int id;

    public void RegistrarCliente(TClientes cl) {
        String sql = "INSERT INTO cliente (nombre, telefono, correo, direccion) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setString(1, cl.getNombre());
            ps.setInt(2, cl.getTelefono());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getDireccion());

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("Cliente registrado exitosamente: " + cl);
            } else {
                System.out.println("Error al registrar el cliente.");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar el cliente: " + e.getMessage());
        }
    }

    public void EliminarCliente(int idCLiente) {
        String sql = "delete from cliente where id_cliente=?";
        id = idCLiente;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setInt(1, id);

            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("Cliente eliminado exitosamente: " + id);
            } else {
                System.out.println("Error al eliminado el cliente.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }

    }

    public List<TClientes> listar() {
        List<TClientes> datos = new ArrayList<>();
        String sql = "select * from cliente";
        try {
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TClientes c = new TClientes();
                c.setId_cliente(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setTelefono(rs.getInt(3));
                c.setCorreo(rs.getString(4));
                c.setDireccion(rs.getString(5));
                datos.add(c);
            }
        } catch (Exception e) {
            System.out.println("error en consulta");
        }
        return datos;
    }

    public void cargarDatosCliente(DefaultTableModel tableModel) {
        List<TClientes> clientes = listar();

        // Limpiar la tabla
        tableModel.setRowCount(0);

        // Agregar los datos de los clientes al modelo
        for (TClientes cliente : clientes) {
            tableModel.addRow(new Object[]{
                cliente.getId_cliente(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getCorreo()
            });
        }

    }

    public TClientes ConsultarClienteporId(int id) {
        TClientes c = new TClientes();
        String sql = "select * from cliente where id_cliente=?";
        this.id = id;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c.setId_cliente(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setTelefono(rs.getInt(3));
                c.setCorreo(rs.getString(4));
                c.setDireccion(rs.getString(5));
            } else {
                System.out.println("No se encontró un cliente con ID: " + id);
            }

        } catch (Exception e) {
            System.out.println("Error al consultarid del cliente: " + e.getMessage());
        }
        return c;
    }

    public void ActualizarCliente(TClientes Tclt) {
        String sql = "UPDATE cliente SET nombre = ?, telefono = ?, correo = ?, direccion = ? WHERE id_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            // Asignar los valores del cliente al PreparedStatement
            ps.setString(1, Tclt.getNombre());
            ps.setInt(2, Tclt.getTelefono());
            ps.setString(3, Tclt.getCorreo());
            ps.setString(4, Tclt.getDireccion());
            ps.setInt(5, Tclt.getId_cliente());
            // Ejecutar la inserción
            int rowsAffected = ps.executeUpdate();

            // Confirmación en consola
            if (rowsAffected > 0) {
                System.out.println("Cliente Actualido exitosamente: " + Tclt.getId_cliente());
            } else {
                System.out.println("Error al Actualizar el cliente.");
            }
        } catch (Exception e) {
            System.out.println("Error al Actualizar el cliente: " + e.getMessage());
        }
    }

    public void llenarCombobox(JComboBox<TClientes> comboClientes) {

        List<TClientes> clientes = listar();

        // Limpiar la combobox
        comboClientes.removeAll();

        // Agregar los datos de los clientes al modelo
        for (TClientes cliente : clientes) {
            comboClientes.addItem(new TClientes(
                cliente.getId_cliente(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getCorreo(),
                cliente.getDireccion()
            ));
        }
    }
}
