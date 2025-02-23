package servtec;

import Conexion.Conexion;
import Models.Reparaciones;
import ViewModels.ClienteVM;
import ViewModels.OrdenesReparacionesVM;
import ViewModels.ReparacionesVM;
import java.sql.Connection;
import java.util.List;

public class ServTec {

    public static void main(String[] args) {

        ReparacionesVM rep = new ReparacionesVM();
        
        
        System.out.println(rep.generarOrden());
    }
}
