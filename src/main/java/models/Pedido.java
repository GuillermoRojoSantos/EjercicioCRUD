/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package models;


/**
 * @author AlejandroMarínBermúd
 * @author GuillermoRojoSantos
 */

public class Pedido {

    private Integer id;
    
    private String producto;
    private String alumno;
    private String fecha;
    private Float precio;
    private String estado;


    public Pedido() {
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
