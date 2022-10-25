package controllers;

import models.Pedido;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * @author Alejandro Marín Bermúd
 * @author Guillermo Rojo Santos
 */
public class PedidoDAOMySQL implements PedidoDAO {
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos?zeroDateTimeBehavior=CONVERT_TO_NULL";


    public static final String nuevo_pedido = "INSERT INTO `comanda` (`Alumno`, `Producto`, `Fecha`, `Precio`, `Estado`) VALUES ( ?, ?, ?, ?, ?);";
    public static final String eliminar_pedido = "DELETE FROM `comanda` WHERE id=?";
    public static final String marcar_pedido = "UPDATE `comanda` SET `Estado` = 'ENTREGADO' WHERE `comanda`.`id` =? ";
    public static final String pendientes = "SELECT * FROM `comanda` WHERE Estado = 'PENDIENTE' OR 'Pendiente';";
    public static final String pedidos_alumno = "SELECT * FROM `comanda` WHERE Alumno = ?;";
    public static final String ganancias_mes = "SELECT SUM(Precio) FROM `comanda` WHERE month(Fecha) = MONTH(CURRENT_DATE());";
    public static final String total_clientes = "SELECT count(DISTINCT(Alumno)) FROM `comanda`;";
    public static final String mejor_cliente = "SELECT Count(Alumno) as Mejor, Alumno FROM `comanda`Group by Alumno ORDER by Mejor DESC LIMIT 1;";
    public static final String pedidos_semana = "SELECT Count(id) FROM `comanda`WHERE week(Fecha) = week(CURRENT_DATE());";
    public static final String mas_vendido = "SELECT Count(Producto) as Mas_vendido, Producto FROM `comanda` GROUP by Producto ORDER by Mas_vendido DESC LIMIT 1;";
    public static final String get_all = "select * from comanda;";
    public static final String get_Alumnos = "select Alumno from comanda group by Alumno;";

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
            Scanner sci = new Scanner(System.in);
            Pedido p = new Pedido();
            ProductoDAOMySQL prod = new ProductoDAOMySQL();

            System.out.println("Introduce un alumno");
            String alumno = sci.nextLine();
            p.setAlumno(alumno);

            System.out.println("Introduce una Fecha");
            String fecha = sci.nextLine();
            p.setFecha(fecha);

            System.out.println("Introduce el Estado (Pendiente/Terminado)");
            String estado = sci.next().toUpperCase();
            p.setEstado(estado);

            System.out.println("Introduce un Producto (el id)");
            int id = sci.nextInt();
            //String nombre = sci.nextLine();
            p.setProducto(prod.getNombre(id));

            System.out.println("Introduce el Precio");
            //Float precio = sci.nextFloat();
            p.setPrecio(prod.getPrecio(id));

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
    public void marcar(int id) {


        try (var pst = conexion.prepareStatement(marcar_pedido)) {
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
    public ArrayList<Pedido> get_AllPedidos() {
        var result = new ArrayList<Pedido>();
        try (var pst = conexion.prepareStatement(get_all)) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                var Pedido = new Pedido();
                Pedido.setId(resultSet.getInt("id"));
                Pedido.setAlumno(resultSet.getString("Alumno"));
                Pedido.setProducto(resultSet.getString("Producto"));
                Pedido.setFecha(resultSet.getString("Fecha"));
                Pedido.setPrecio(resultSet.getFloat("Precio"));
                Pedido.setEstado(resultSet.getString("Estado"));

                result.add(Pedido);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public ArrayList<String> get_AllAlumnos() {
        var result = new ArrayList<String>();
        try (var pst = conexion.prepareStatement(get_Alumnos)) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("Alumno"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ArrayList<Pedido> get_pendiente() {
        var resultado = new ArrayList<Pedido>();
        try (var pst = conexion.prepareStatement(pendientes)) {
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                var pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setAlumno(resultSet.getString("Alumno"));
                pedido.setProducto(resultSet.getString("Producto"));
                pedido.setFecha(resultSet.getString("Fecha"));
                pedido.setPrecio(resultSet.getFloat("Precio"));
                pedido.setEstado(resultSet.getString("Estado"));
                resultado.add(pedido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public ArrayList<Pedido> get_pedidoA(String alumno) {
        var resultado = new ArrayList<Pedido>();

        try (var pst = conexion.prepareStatement(pedidos_alumno)) {
            pst.setString(1, alumno);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                var pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setAlumno(resultSet.getString("Alumno"));
                pedido.setProducto(resultSet.getString("Producto"));
                pedido.setFecha(resultSet.getString("Fecha"));
                pedido.setPrecio(resultSet.getFloat("Precio"));
                pedido.setEstado(resultSet.getString("Estado"));
                resultado.add(pedido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");

        return resultado;

    }

    @Override
    public Pedido get_ganancias() {

        try (var pst = conexion.prepareStatement(ganancias_mes)) {


            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.printf("La ganacia total del mes es de: %d €%n", resultado.getInt("SUM(Precio)"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Pedido get_totalC() {

        try (var pst = conexion.prepareStatement(total_clientes)) {

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.printf("La cantidad de Clientes es de: %d%n", resultado.getInt("count(DISTINCT(Alumno))"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Pedido get_mejorC() {

        try (var pst = conexion.prepareStatement(mejor_cliente)) {

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.printf("El mejor Clientes es: %s%n", resultado.getString("Alumno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Pedido get_pedidoS() {

        try (var pst = conexion.prepareStatement(pedidos_semana)) {

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.printf("Numero de pedidos de esta semana: %s%n", resultado.getString("Count(id)"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Pedido get_masV() {

        try (var pst = conexion.prepareStatement(mas_vendido)) {

            ResultSet resultado = pst.executeQuery();

            while (resultado.next()) {

                Pedido pedido = new Pedido();

                System.out.printf("El producto mas vendido es %d%n", resultado.getInt("Producto"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
