package servtec;

import Conexion.Conexion;
import Models.Reparaciones;
import Controllers.ClienteDAO;
import Controllers.OrdenesReparacionesDAO;
import Controllers.ReparacionesDAO;
import java.sql.Connection;
import java.util.List;

public class ServTec {

    public static void main(String[] args) {

        ReparacionesDAO rep = new ReparacionesDAO();
        
        
        System.out.println(rep.generarOrden());
    }
}
