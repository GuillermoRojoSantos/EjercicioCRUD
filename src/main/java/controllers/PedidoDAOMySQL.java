/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package controllers;

import models.Pedido;
import models.Producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * @author AlejandroMarínBermúd
 * @author GuillermoRojoSantos
 */

public class PedidoDAOMySQL implements PedidoDAO {
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos?zeroDateTimeBehavior=CONVERT_TO_NULL";


    public static final String nuevo_pedido = "INSERT INTO `comanda` (`Alumno`, `Producto`, `Fecha`, `Precio`, `Estado`) VALUES ( ?, ?, ?, ?, ?);";
    public static final String eliminar_pedido = "DELETE FROM `comanda` WHERE id=?";
    public static final String marcar_pedido = "UPDATE `Estado` SET `Estado` = `Entregado` WHERE `id` = ? ";
    public static final String pendientes = "SELECT * FROM `comanda` WHERE Estado = \"Pendiente\";";
    public static final String pedidos_alumno = "SELECT Count(Producto) FROM `comanda` WHERE Alumno = ?;";
    public static final String ganancias_mes = "SELECT SUM(Precio) FROM `comanda` WHERE month(Fecha) = MONTH(CURRENT_DATE());";
    public static final String total_clientes = "SELECT count(DISTINCT(Alumno)) FROM `comanda`;";
    public static final String mejor_cliente = "SELECT Count(Alumno) as Mejor FROM `comanda`Group by Alumno ORDER by Mejor DESC LIMIT 1;";
    public static final String pedidos_semana = "SELECT Count(id) FROM `comanda`WHERE week(Fecha) = week(CURRENT_DATE());";
    public static final String mas_vendido = "SELECT Count(Producto) as Mas_vendido FROM `comanda` GROUP by Producto ORDER by Mas_vendido DESC LIMIT 1;";

    static {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            Logger.getLogger(PedidoDAOMySQL.class.getName()).info("Conexión establecida con exito");
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private static Connection conexion;

    @Override
    public void nuevo() {
        try (var pst = conexion.prepareStatement(nuevo_pedido, RETURN_GENERATED_KEYS)) {
            Scanner sc = new Scanner(System.in);
            Pedido p = new Pedido();

            System.out.println("Introduce un alumno");
            String alumno = sc.nextLine();
            p.setAlumno(alumno);

            System.out.println("Introduce un Producto");
            String producto = sc.nextLine();
            p.setProducto(producto);

            System.out.println("Introduce una Fecha");
            String fecha = sc.nextLine();
            p.setFecha(fecha);

            System.out.println("Introduce el Precio");
            Float precio = sc.nextFloat();
            p.setPrecio(precio);

            System.out.println("Introduce el Estado");
            String estado = sc.next();
            p.setEstado(estado);

            pst.setString(1, p.getAlumno());
            pst.setString(2, p.getProducto());
            pst.setString(3, p.getFecha());
            pst.setFloat(4, p.getPrecio());
            pst.setString(5, p.getEstado());


            if (pst.executeUpdate() > 0) {

                var keys = pst.getGeneratedKeys();
                keys.next();

                p.setId(keys.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void marcar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido get_pendiente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido get_pedidoA() {
        try (var pst = conexion.prepareStatement(pedidos_alumno)) {

            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce un alumno");
            String alumno = sc.nextLine();

            pst.setString(1, alumno);
            ResultSet resultado = pst.executeQuery();

            if (resultado.next()) {

                Pedido pedido = new Pedido();
                Producto producto = new Producto();

                System.out.println(resultado.getInt("Count(Producto)"));
            } else {
                return null;
            }


        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");

        return null;

    }

    @Override
    public Pedido get_ganancias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido get_totalC() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido get_mejorC() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido get_pedidoS() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pedido get_masV() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
