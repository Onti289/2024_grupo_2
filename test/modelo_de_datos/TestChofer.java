package modelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestChofer {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

    
	@Test
    public void testConstructorChofer() {
        Chofer chofer = new ChoferPermanente("12345","Jorge",2000,0); //es abstracta

        assertEquals("El DNI debería ser 12345", "12345", chofer.getDni());
        assertEquals("El nombre debería ser Jorge", "Jorge", chofer.getNombre());
    }
	
	@Test
    public void testSueldoBasico_y_sueldoneto() {
        Chofer chofer = new ChoferPermanente("12345","Jorge",2000,0); //es abstracta

        chofer.setSueldoBasico(100.0);
        assertEquals("El sueldo basico debería ser 100", 100, chofer.getSueldoBasico(),0.0000001);
        
        assertEquals("El sueldo neto debería ser 86", 86, chofer.getSueldoNeto(),0.000001);
    }
}
