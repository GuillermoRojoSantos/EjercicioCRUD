package controllers;

import lombok.NoArgsConstructor;
import models.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@NoArgsConstructor
public class ProductoDAOMySQL implements ProductoDAO {

    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://localhost:3306/pedidos";

    private String GETALL_QUERY="SELECT * FROM pedidos.bocadillos";

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
}
