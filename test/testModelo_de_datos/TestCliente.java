package testModelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestCliente {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorCliente() {
		Cliente cliente = new Cliente("LautaATR","***","Lautaro");
		
		assertEquals("El nombre debería ser LautaATR", "LautaATR", cliente.getNombreUsuario());
		assertEquals("El pass debería ser ***", "***", cliente.getPass());
		assertEquals("El nombre debería ser Lautaro", "Lautaro", cliente.getNombreReal());
	}

}
