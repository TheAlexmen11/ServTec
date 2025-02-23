package Models;
public class TDatosEmpresa {
 private int idEmpresa;
    private String nombre;
    private String direccion;
    private String correo;
    private String telefono;
    private String horario;
    private String diasHorario;
    private String horario1;
    private String diasHorario1;
    private String terminoCondiciones;
    private String logoUrl;

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDiasHorario() {
        return diasHorario;
    }

    public void setDiasHorario(String diasHorario) {
        this.diasHorario = diasHorario;
    }

    public String getHorario1() {
        return horario1;
    }

    public void setHorario1(String horario1) {
        this.horario1 = horario1;
    }

    public String getDiasHorario1() {
        return diasHorario1;
    }

    public void setDiasHorario1(String diasHorario1) {
        this.diasHorario1 = diasHorario1;
    }

    public String getTerminoCondiciones() {
        return terminoCondiciones;
    }

    public void setTerminoCondiciones(String terminoCondiciones) {
        this.terminoCondiciones = terminoCondiciones;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}

