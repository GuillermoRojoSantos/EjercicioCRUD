/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package controllers;

import models.Pedido;

public interface PedidoDAO {


    void nuevo();

    void eliminar();

    void marcar();

    Pedido get_pendiente();

    Pedido get_pedidoA();

    Pedido get_ganancias();

    Pedido get_totalC();

    Pedido get_mejorC();

    Pedido get_pedidoS();

    Pedido get_masV();


}
