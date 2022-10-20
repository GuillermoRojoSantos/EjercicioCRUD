
import controllers.ProductoDAOMySQL;
import models.Producto;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author AlejandroMarínBermúd
 */
public class Main {

    /*public static Connection conexion;
    public static final String URL = "jdbc:mysql://localhost:3306/pedidos?zeroDateTimeBehavior=CONVERT_TO_NULL ";
    public static final String USER = "root";
    public static final String PASSWORD = "";*/

    public static void main(String[] args) {
        /*PedidoDAOMySQL daoSQL = new  PedidoDAOMySQL();
           
           daoSQL.get_mejorC();*/

        ProductoDAOMySQL productoSQL = new ProductoDAOMySQL();

        var array = new ArrayList<Producto>();
        array=productoSQL.getAll();
        Iterator<Producto> it = array.iterator();
        while(it.hasNext()){
            System.out.println(it.next().toString());
        }
    }
}