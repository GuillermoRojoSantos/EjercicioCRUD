package controllers;

import models.Pedido;
import java.util.ArrayList;

/**
 * @author Alejandro Marín Bermúd
 * @author Guillermo Rojo Santos
 */

public interface PedidoDAO {


    void nuevo();

    void eliminar();

    void marcar(int id);

    ArrayList<Pedido> get_AllPedidos();

    ArrayList<String> get_AllAlumnos();

    ArrayList<Pedido> get_pendiente();

    ArrayList<Pedido> get_pedidoA(String alumno);

    Pedido get_ganancias();

    Pedido get_totalC();

    Pedido get_mejorC();

    Pedido get_pedidoS();

    Pedido get_masV();


}
