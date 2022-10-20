

import controllers.PedidoDAOMySQL;
import java.sql.Connection;

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
           
           daoSQL.get_mejorC();
    }
}

