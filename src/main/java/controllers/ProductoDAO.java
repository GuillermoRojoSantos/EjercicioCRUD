package controllers;

import models.Producto;
import java.util.ArrayList;

/**
 * @author Alejandro Marín Bermúd
 * @author Guillermo Rojo Santos
 */

public interface ProductoDAO {

    ArrayList<Producto> getAll();

    public float getPrecio(int id);

    public String getNombre(int id);


}
