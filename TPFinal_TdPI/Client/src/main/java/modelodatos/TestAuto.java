package modelodatos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAuto {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorAuto() {	
		Auto auto = new Auto("ABC", 2, false);
		
		assertEquals("La patente debería ser ABC", "ABC", auto.getPatente());
        assertEquals("La cantidad de plazas debería ser 2", 2, auto.getCantidadPlazas());
        assertTrue("No debería permitir mascota", auto.isMascota());
	}

}