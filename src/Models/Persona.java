package Models;

abstract public class Persona {

    protected String nombre;
    protected String correo;
    protected int dni;

    public Persona(String nombre, String correo, int dni) {
        this.nombre = nombre;
        this.correo = correo;
        this.dni = dni;
    }

    public Persona() {
    }

    public abstract void mostrarInformacion();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    
    
}
