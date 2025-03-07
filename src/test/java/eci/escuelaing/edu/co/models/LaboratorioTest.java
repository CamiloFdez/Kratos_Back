package eci.escuelaing.edu.co.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LaboratorioTest {

    private Laboratorio laboratorio;

    @BeforeEach
    public void setUp() {

        laboratorio = new Laboratorio("lab1", "Labinfo", "Edificio A", 30);
    }

    @Test
    public void testConstructorAndGetters() {
        // Verifica que el constructor y los getters funcionen correctamente
        assertThat(laboratorio.getId()).isEqualTo("lab1");
        assertThat(laboratorio.getNombre()).isEqualTo("Labinfo");
        assertThat(laboratorio.getUbicacion()).isEqualTo("Edificio A");
        assertThat(laboratorio.getCapacidad()).isEqualTo(30);
    }

    @Test
    public void testSetters() {

        laboratorio.setId("lab2");
        laboratorio.setNombre("Laboratorio de Redes");
        laboratorio.setUbicacion("Edificio B");
        laboratorio.setCapacidad(40);

        assertThat(laboratorio.getId()).isEqualTo("lab2");
        assertThat(laboratorio.getNombre()).isEqualTo("Laboratorio de Redes");
        assertThat(laboratorio.getUbicacion()).isEqualTo("Edificio B");
        assertThat(laboratorio.getCapacidad()).isEqualTo(40);
    }

    //@Test
    //public void testEqualsAndHashCode() {

    //    Laboratorio laboratorio1 = new Laboratorio("lab1", "Labinfo", "Edificio A", 30);
    //    Laboratorio laboratorio2 = new Laboratorio("lab1", "Labinfo", "Edificio A", 30);

    //    assertThat(laboratorio1).isEqualTo(laboratorio2);
    //    assertThat(laboratorio1.hashCode()).isEqualTo(laboratorio2.hashCode());
    //}

    @Test
    public void testNotEquals() {

        Laboratorio laboratorio1 = new Laboratorio("lab1", "Labinfo", "Edificio A", 30);
        Laboratorio laboratorio2 = new Laboratorio("lab2", "Labinfo", "Edificio A", 30);

        assertThat(laboratorio1).isNotEqualTo(laboratorio2);
    }
}