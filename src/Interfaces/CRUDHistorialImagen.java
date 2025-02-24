package Interfaces;

import java.util.List;

public interface CRUDHistorialImagen<O,T> {
    public void registrar(O data);

    public List<O> listar();

    public List<O> consultarPorId(T id);

    public void actualizar(O data);

    public void eliminar(T id);
}
