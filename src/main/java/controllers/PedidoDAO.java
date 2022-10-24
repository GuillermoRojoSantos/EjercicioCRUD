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

    void marcar();

    ArrayList<Pedido> get_AllPedidos();

    ArrayList<String> get_AllAlumnos();

    Pedido get_pendiente();

    Pedido get_pedidoA();

    Pedido get_ganancias();

    Pedido get_totalC();

    Pedido get_mejorC();

    Pedido get_pedidoS();

    Pedido get_masV();


}
