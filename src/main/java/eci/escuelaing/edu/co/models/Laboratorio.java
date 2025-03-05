package eci.escuelaing.edu.co.models;

import org.springframework.data.annotation.Id;  // Para usar la anotación @Id en el campo de identificación
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;  // Para mapear la clase a una colección de MongoDB

@Document(collection = "laboratorios")
public class Laboratorio {

    @Id
    private String id;
    private String nombre;  // Nombre del laboratorio
    private String ubicacion;  // Ubicación del laboratorio
    private int capacidad;  // Capacidad del laboratorio

    public Laboratorio(String id , String nombre, String ubicacion, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
