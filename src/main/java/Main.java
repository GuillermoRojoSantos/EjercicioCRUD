/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import Ejercicio2.controllers.PedidoDAOMySQL;
import Ejercicio2.models.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AlejandroMarínBermúd
 */
public class Main {

    public static Connection conexion;
    public static final String URL = "jdbc:mysql://localhost:3306/pedidos?zeroDateTimeBehavior=CONVERT_TO_NULL ";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    
    
     
    
    public static void main(String[] args) {
        
         PedidoDAOMySQL daoSQL = new  PedidoDAOMySQL();
           
           daoSQL.nuevo();
    }
}

