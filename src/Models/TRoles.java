package Models;

public class TRoles {

    private int idRol;
    private String nombre;

    public TRoles(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public TRoles(){
    }
    
    @Override
    public String toString() {
        return nombre;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
