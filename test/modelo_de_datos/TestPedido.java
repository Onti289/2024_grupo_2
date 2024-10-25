package modelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Cliente;
import modeloDatos.Pedido;

public class TestPedido {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorPedido() {
		Cliente cliente = new Cliente("LautaATR","***","Lautaro");
		
		Pedido pedido1 = new Pedido(cliente, 10, true, true, 12, "ZONA_STANDARD");
		
		assertEquals("El cliente debería ser LautaATR", cliente, pedido1.getCliente());
		assertEquals("La cantidad de pasajeros debería ser 10", 10, pedido1.getCantidadPasajeros());
		assertTrue("Debería pedir baul",  pedido1.isBaul());
		assertTrue("Debería pedir mascota", pedido1.isMascota());
		assertEquals("La cantidad de KM debería ser 12", 12, pedido1.getKm());
		assertEquals("La zona debería ser standar", "ZONA_STANDARD", pedido1.getZona());
		
		Pedido pedido2 = new Pedido(cliente, 10, true, true, 12, "ZONA_SIN_ASFALTAR");
		Pedido pedido3 = new Pedido(cliente, 10, true, true, 12, "ZONA_PELIGROSA");
		assertEquals("La zona debería ser SIN ASFALTAR", "ZONA_SIN_ASFALTAR", pedido2.getZona());
		assertEquals("La zona debería ser PELIGROSA", "ZONA_PELIGROSA", pedido3.getZona());

	}

}
