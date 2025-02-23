package Models;

import java.sql.Timestamp;

public class THistorialDesarrollo {
    private int idHistorial;
    private String orden_trabajo;
    private Timestamp fechaCambio;
    private String desarrolloTecnico;

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getOrden_trabajo() {
        return orden_trabajo;
    }

    public void setOrden_trabajo(String orden_trabajo) {
        this.orden_trabajo = orden_trabajo;
    }

    
    public Timestamp getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Timestamp fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getDesarrolloTecnico() {
        return desarrolloTecnico;
    }

    public void setDesarrolloTecnico(String desarrollo_tecnico) {
        this.desarrolloTecnico = desarrollo_tecnico;
    }

   
}
