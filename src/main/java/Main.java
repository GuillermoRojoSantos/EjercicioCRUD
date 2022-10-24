import controllers.PedidoDAOMySQL;
import controllers.ProductoDAOMySQL;
import models.Pedido;
import models.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Alejandro Marín Bermúd
 * @author Guillermo Rojo Santos
 */
public class Main {


    private static PedidoDAOMySQL pedidoSQL = new PedidoDAOMySQL();
    private static ProductoDAOMySQL productoSQL = new ProductoDAOMySQL();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (option != 7) {
            //actualizamos los arrays de pedidos nada más abrir el while
            // para que estén actualizados
            var arrayProductos = productoSQL.getAll();
            Iterator<Producto> itProductos = arrayProductos.iterator();

            var arrayPedidos = pedidoSQL.get_AllPedidos();
            Iterator<Pedido> itPedidos = arrayPedidos.iterator();

            System.out.println("Bienvenido a la aplicación de pedidos");
            System.out.println("By Alejandro Marín Bermúdez & Guillermo Rojo Santos");
            System.out.println("----------------------------------------------------");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear pedido");
            System.out.println("2. Eliminar pedido");
            System.out.println("3. Marcar pedido como recogido");
            System.out.println("4. Mostrar pedidos marcados para hoy");
            System.out.println("5. Mostrar pedidos de X alumno");
            System.out.println("6. Menú de indicadores estadísticos");
            System.out.println("7. Salir");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("En la carta tenemos: ");
                    System.out.println("----------------------------------------------------");
                    while (itProductos.hasNext()) {
                        System.out.println(itProductos.next().toString());
                    }
                    System.out.println("----------------------------------------------------");
                    System.out.println("Haga su pedido");
                    pedidoSQL.nuevo();
                    System.out.println("¡Pedido guardado correctamente!");
                    break;

                case 2:
                    System.out.println("Pedidos disponibles");
                    System.out.println("----------------------------------------------------");
                    while (itPedidos.hasNext()){
                        System.out.println(itPedidos.next().toString());
                    }
                    System.out.println("----------------------------------------------------");
                    System.out.println("¿Que pedido desea eliminar?");
                    pedidoSQL.eliminar();
                    System.out.println("¡Pedido eliminado correctamente!");

                    break;

                case 3:
                    System.out.println("Pedidos disponibles");
                    System.out.println("----------------------------------------------------");
                    while (itPedidos.hasNext()){
                        System.out.println(itPedidos.next().toString());
                    }System.out.println("----------------------------------------------------");
                    System.out.println("¿Que pedido desea marcar como Entregado?");
                    pedidoSQL.marcar();
                    break;

                case 4:
                    System.out.println("Pedidos Marcados para hoy :");
                    pedidoSQL.get_pendiente();
                    var array = new ArrayList<Pedido>();
                    Iterator<Pedido> it = array.iterator();
                    while (it.hasNext()){
                        System.out.println(it.next().toString());
                    }
                    break;

                case 5:
                    System.out.println("Seleccione uno de los alumnos siguientes: ");
                    System.out.println(pedidoSQL.get_AllAlumnos());
                    System.out.println("----------------------------------------------------");
                    pedidoSQL.get_pedidoA();
                    break;

                case 6:
                    int opcion=sc.nextInt();
                    while (opcion!=6){
                        switch (opcion){
                            case 1://Ganancias totales de este mes
                                pedidoSQL.get_ganancias();
                                break;
                            case 2://Clientes totales
                                pedidoSQL.get_totalC();
                                break;
                            case 3://Mejor Cliente
                                pedidoSQL.get_mejorC();
                                break;
                            case 4://Pedidos de esta semana
                                pedidoSQL.get_pedidoS();
                                break;
                            case 5://Producto más vendido
                                pedidoSQL.get_masV();
                                break;
                            case 6:
                                System.out.println("Saliendo del menú de estadísticas");
                            default:
                                System.out.println("Opción invalida");
                                break;
                        }
                    }
                break;
                case 7:

                    System.out.println("¡Gracias por usar nuestro programa!");
                    System.out.println("Cerrando...");
                default:
                    System.out.printf("La seleción '%d' no es válida %n", option);
                    break;
            }
        }


    }
}