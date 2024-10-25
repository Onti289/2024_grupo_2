package modelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestCombi {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorCombi() {
		Combi combi = new Combi("ABC", 8, true);
		
        assertEquals("La patente debería ser ABC", "ABC", combi.getPatente());
        assertEquals("La cantidad de plazas debería ser 8", 8, combi.getCantidadPlazas());
        assertTrue("Debería permitir mascota", combi.isMascota());
	}

	@Test
    public void testgetPuntajePedido() {
		Combi combi = new Combi("ABC", 10, true);

        
        Cliente cliente = new Cliente("lau","atr","lautaro");
        
        Pedido pedido1 = new Pedido(cliente, 9, true, true, 10, "ZONA_PELIGROSA");
        Pedido pedido2 = new Pedido(cliente, 4, false, false, 10, "ZONA_PELIGROSA");
        //Pedido pedido3 = new Pedido(cliente, 199, false, false, 10, "ZONA_PELIGROSA");
        
        int prueba1 = combi.getPuntajePedido(pedido1);
        assertEquals("El puntaje debería ser 190", 190, prueba1);
        Integer prueba2 = combi.getPuntajePedido(pedido2);
        assertNull("El objeto 2 deberia ser null", prueba2);
        Integer prueba3 = combi.getPuntajePedido(pedido1);
        assertNull("El objeto 3 deberia ser null", prueba3);
	}
}
