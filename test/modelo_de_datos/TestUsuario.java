package modelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestUsuario {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorUsuario() {
		Usuario usuario = new Cliente("jose","*","jose");
		
		assertEquals("El nombre debería ser jose", "jose", usuario.getNombreUsuario());
		assertEquals("El pass debería ser *", "*", usuario.getPass());
		}

}
