package Models;

public class TClientes extends Persona {

    private int id_cliente;
    private int telefono;
    private String direccion;

    public TClientes() {
    }

    public TClientes(int id_cliente, String nombre, int telefono, String correo, String direccion, int dni) {
        super(nombre, correo, dni);
        this.id_cliente = id_cliente;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return id_cliente + " | " + nombre;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre+ " Dni: " + dni);
    }

}
