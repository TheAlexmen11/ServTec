package Models;
public class TImagenes {
    private int idImagen;
    private String orden_trabajo;
    private byte[] imagen;

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getOrden_trabajo() {
        return orden_trabajo;
    }

    public void setOrden_trabajo(String orden_trabajo) {
        this.orden_trabajo = orden_trabajo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}
