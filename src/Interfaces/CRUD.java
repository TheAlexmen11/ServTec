package Interfaces;

import java.util.List;

public interface CRUD<O ,T > {

    public void registrar(O data);

    public List<O> listar();

    public O consultarPorId(T id);

    public void actualizar(O data);

    public void eliminar(T id);
    
}
