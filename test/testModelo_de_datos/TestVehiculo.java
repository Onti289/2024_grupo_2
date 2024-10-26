package testModelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestVehiculo {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorVehiculo() {
		Vehiculo v = new Auto("ABC",2,true);
		
        assertEquals("La patente debería ser ABC", "ABC", v.getPatente());
        assertEquals("La cantidad de plazas debería ser 2", 2, v.getCantidadPlazas());
        assertTrue("Debería permitir mascota", v.isMascota());
	}

}
