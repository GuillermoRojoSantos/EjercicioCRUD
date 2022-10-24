package models;

/**
 * @author Alejandro Marín Bermúd
 * @author Guillermo Rojo Santos
 */

public class Pedido {

    private int id;

    private String producto;
    private String alumno;
    private String fecha;
    private Float precio;
    private String estado;


    public Pedido() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", pedido=" + producto + ", alumno=" + alumno + ", fecha=" + fecha + ", precio=" + precio + ", estado=" + estado + '}';
    }


}
