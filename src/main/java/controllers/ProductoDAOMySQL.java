package controllers;

import lombok.NoArgsConstructor;
import models.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Alejandro Marín Bermúd
 * @author Guillermo Rojo Santos
 */


@NoArgsConstructor
public class ProductoDAOMySQL implements ProductoDAO {

    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos";

    private static final String GETALL_QUERY="SELECT * FROM pedidos.bocadillos";
    public static final String QUERY_GET_PRECIO = "SELECT Precio FROM bocadillos WHERE id=?;";
    public static final String QUERY_GET_NOMBRE = "SELECT Nombre FROM bocadillos WHERE id=?;";

    private static Connection conexion;

    static {
        try {
            conexion= DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ArrayList<Producto> getAll() {
        var result = new ArrayList<Producto>();
        try(var pst = conexion.prepareStatement(GETALL_QUERY)){
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                var Producto = new Producto();
                Producto.setP_id(resultSet.getInt("id"));
                Producto.setNombre(resultSet.getString("Nombre"));
                Producto.setPrecio(resultSet.getFloat("Precio"));
                result.add(Producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public float getPrecio(int id) {
        float precio = 0.00F;
        try(var pst = conexion.prepareStatement(QUERY_GET_PRECIO)) {
            pst.setInt(1, id);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()){
                Producto prod = new Producto();
                precio=resultado.getFloat("Precio");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return precio;
    }

    @Override
    public String getNombre(int id) {
        String nombre = "";
        try(var pst = conexion.prepareStatement(QUERY_GET_NOMBRE)) {
            pst.setInt(1, id);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()){
                Producto prod = new Producto();
                nombre=resultado.getString("Nombre");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nombre;
    }


}
