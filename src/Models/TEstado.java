package Models;
public class TEstado {
    private int idEstado;
    private String descripcion;

    public TEstado() {
    }

    public TEstado(int idEstado, String descripcion) {
        this.idEstado = idEstado;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return idEstado + " | " + descripcion;
    }

    
    // Getters y Setters
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
