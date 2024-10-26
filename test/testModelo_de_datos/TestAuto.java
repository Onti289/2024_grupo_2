package testModelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestAuto {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
    public void testConstructorAuto() {
        // Valores de prueba
        String patente = "ABC";
        int cantidadPlazas = 2;
        boolean mascota = false;

        // Crear instancia del auto
        Auto auto = new Auto(patente, cantidadPlazas, mascota);

        // Verificar que los atributos se asignaron correctamente
        assertEquals("La patente debería ser ABC", "ABC", auto.getPatente());
        assertEquals("La cantidad de plazas debería ser 2", 2, auto.getCantidadPlazas());
        assertFalse("No debería permitir mascota", auto.isMascota());
    }
	
	@Test
    public void testgetPuntajePedido() {
        Auto auto1 = new Auto("AAA", 4, true);
        Auto auto2 = new Auto("AAA", 4, false);
        
        Cliente cliente = new Cliente("lau","atr","lautaro");
        
        Pedido pedido1 = new Pedido(cliente, 2, true, true, 10, "ZONA_STANDARD");
        Pedido pedido2 = new Pedido(cliente, 80, false, false, 10, "ZONA_STANDARD");
        
        int prueba1 = auto1.getPuntajePedido(pedido1);
        assertEquals("El puntaje debería ser 80", 80, prueba1);
        Integer prueba2 = auto1.getPuntajePedido(pedido2);
        assertNull("El objeto 2 deberia ser null", prueba2);
        Integer prueba3 = auto2.getPuntajePedido(pedido1);
        assertNull("El objeto 3 deberia ser null", prueba3);
     
    }
	
	

}
