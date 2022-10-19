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
 */
public class PedidoDAOMySQL implements PedidoDAO {
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos?zeroDateTimeBehavior=CONVERT_TO_NULL";


    public static final String nuevo_pedido = "INSERT INTO `comanda` (`Alumno`, `Producto`, `Fecha`, `Precio`, `Estado`) VALUES ( ?, ?, ?, ?, ?);";
    public static final String eliminar_pedido = "DELETE FROM `comanda` WHERE id=?";
    public static final String marcar_pedido = "UPDATE `comanda` SET `Estado` = 'Entregado' WHERE `comanda`.`id` =? ";
    public static final String pendientes = "SELECT * FROM `comanda` WHERE Estado = 'Pendiente';";
    public static final String pedidos_alumno = "SELECT Count(Producto) FROM `comanda` WHERE Alumno = ?;";
    public static final String ganancias_mes = "SELECT SUM(Precio) FROM `comanda` WHERE month(Fecha) = MONTH(CURRENT_DATE());";
    public static final String total_clientes = "SELECT count(DISTINCT(Alumno)) FROM `comanda`;";
    public static final String mejor_cliente = "SELECT Count(Alumno) as Mejor, Alumno FROM `comanda`Group by Alumno ORDER by Mejor DESC LIMIT 1;";
    public static final String pedidos_semana = "SELECT Count(id) FROM `comanda`WHERE week(Fecha) = week(CURRENT_DATE());";
    public static final String mas_vendido = "SELECT Count(Producto) as Mas_vendido, Producto FROM `comanda` GROUP by Producto ORDER by Mas_vendido DESC LIMIT 1;";

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

        try (var pst = conexion.prepareStatement(eliminar_pedido)) {

            Scanner sc = new Scanner(System.in);
            Pedido p = new Pedido();

            System.out.println("Introduce un Id");
            int id = sc.nextInt();


            pst.setInt(1, id);

            if (pst.executeUpdate() == 0) {
                Logger.getLogger(PedidoDAOMySQL.class.getName()).warning("Pedido no existe");
            } else {
                System.out.println("Eliminado con exito");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void marcar() {

        Scanner sc = new Scanner(System.in);


        try (var pst = conexion.prepareStatement(marcar_pedido)) {

            System.out.println("Introduce un Id");
            int id = sc.nextInt();


            pst.setInt(1, id);


            if (pst.executeUpdate() == 0) {
                Logger.getLogger(PedidoDAOMySQL.class.getName()).severe("Pedido no existe.");
            } else {
                System.out.println("Actualizado con exito");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Pedido get_pendiente() {
        try (var pst = conexion.prepareStatement(pendientes)) {

            Scanner sc = new Scanner(System.in);
            System.out.println("Pedidos Pendientes de entrega: ");

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.println(resultado.getInt("id") + " " +
                        resultado.getString("Alumno") + " " +
                        resultado.getString("Producto") + " " +
                        resultado.getString("Fecha") + " " + resultado.getInt("Precio")
                        + " " + resultado.getString("Estado"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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

        try (var pst = conexion.prepareStatement(ganancias_mes)) {

            Scanner sc = new Scanner(System.in);
            System.out.print("La ganacia total del mes es de: ");

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.print(resultado.getInt("SUM(Precio)"));
                System.out.println("euros");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Pedido get_totalC() {

        try (var pst = conexion.prepareStatement(total_clientes)) {

            Scanner sc = new Scanner(System.in);
            System.out.println("La cantidad de Clientes es de: ");

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.print(resultado.getInt("count(DISTINCT(Alumno))"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Pedido get_mejorC() {

        try (var pst = conexion.prepareStatement(mejor_cliente)) {

            Scanner sc = new Scanner(System.in);
            System.out.println("El mejor Clientes es: ");

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.print(resultado.getString("Alumno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Pedido get_pedidoS() {

        try (var pst = conexion.prepareStatement(pedidos_semana)) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Numero de pedidos de esta semana: ");

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.print(resultado.getString("Count(id)"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Pedido get_masV() {

        try (var pst = conexion.prepareStatement(mas_vendido)) {

            Scanner sc = new Scanner(System.in);
            System.out.print("El producto mas vendido es ");

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.print(resultado.getInt("Producto"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
