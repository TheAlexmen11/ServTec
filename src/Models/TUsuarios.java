package Models;

public class TUsuarios extends Persona {

    private int idUsuario;
    private int rol;
    private String usuario;
    private String password;

    public TUsuarios(int idUsuario, int rol, String nombre, String usuario, String password, String correo, int dni) {
        super(nombre, correo, dni);
        this.idUsuario = idUsuario;
        this.rol = rol;
        this.usuario = usuario;
        this.password = password;
    }

    public TUsuarios() {

    }

    @Override
    public String toString() {
        return idUsuario + " | " + rol + " | " + nombre;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre + " Dni: " + dni);
    }

}
