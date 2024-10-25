package modelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestChoferTemporario {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorChoferTemporario() {
		Chofer ct = new ChoferTemporario("41370","alejo");
		
        assertEquals("El DNI debería ser 41370", "41370", ct.getDni());
        assertEquals("El nombre debería ser alejo", "alejo", ct.getNombre());
	}

	@Test
	public void testSueldoBrutoChoferTemporario() {
		Chofer ct = new ChoferTemporario("41370","alejo");
		
        ct.setSueldoBasico(100);
        assertEquals("El sueldo bruto debería ser 100", 100, ct.getSueldoBruto(),0.0000001);
	}
	
}
