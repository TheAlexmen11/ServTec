package Models;

public class TUsuarios {

    private int idUsuario;
    private int rol;
    private String nombre;
    private String usuario;
    private String password;
    private String correo;

    public TUsuarios() {
    }

    public TUsuarios(int idUsuario, int rol, String nombre, String usuario, String password, String correo) {
        this.idUsuario = idUsuario;
        this.rol = rol;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.correo = correo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    
}
    