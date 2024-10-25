package modelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestMoto {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorMoto() {
		Moto moto = new Moto("DDD");
		
		assertEquals("La patente debería ser DDD", "DDD", moto.getPatente());
	}

	@Test
    public void testgetPuntajePedido() {
		Moto moto = new Moto("DDD");
        
        Cliente cliente = new Cliente("lau","atr","lautaro");
        
        Pedido pedido1 = new Pedido(cliente, 2, true, true, 10, "ZONA_PELIGROSA");
        Pedido pedido2 = new Pedido(cliente, 2, false, false, 10, "ZONA_PELIGROSA");
        
        Integer prueba1 = moto.getPuntajePedido(pedido1);
        assertNull("El objeto 1 deberia ser null", prueba1);
        Integer prueba2 = moto.getPuntajePedido(pedido2);
        assertNull("El objeto 2 deberia ser null", prueba2);
    }
	
}
