package testModelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestChoferPermanente {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorChoferPermanente() {
		Chofer cp = new ChoferPermanente("123","El Diegote",2024,120);
		
        assertEquals("El DNI debería ser 123", "123", cp.getDni());
        assertEquals("El nombre debería ser El Diegote", "El Diegote", cp.getNombre());
        //no se puede verificar el año y cant hijos
	}
	
	@Test
	public void testSueldoBrutoChoferPermanente() {
		Chofer cp1 = new ChoferPermanente("123","El Diegote",2024,0);
		Chofer cp2 = new ChoferPermanente("123","El Diegote",1999,2);
		
        cp1.setSueldoBasico(100.0);
        assertEquals("El sueldo bruto debería ser 100", 100, cp1.getSueldoBruto(),0.0000001);
        
        cp2.setSueldoBasico(100.0);
        assertEquals("El sueldo bruto debería ser 214", 214, cp2.getSueldoBruto(),0.0000001);
	}
}
