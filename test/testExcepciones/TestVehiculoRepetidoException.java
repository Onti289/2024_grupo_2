package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.*;
import modeloDatos.*;
import modeloNegocio.Empresa;

public class TestVehiculoRepetidoException {

	private Empresa empresa;
    private Auto auto1,auto2;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
	@Test
	public void test() {
		try {
	        auto1 = new Auto("123", 4, true);
	        empresa.agregarVehiculo(auto1);
	        auto2 = new Auto("123", 3, false);
	        empresa.agregarVehiculo(auto2);
			fail("Se esperaba que salte la excepcion VehiculoRepetidoException");
		} catch (VehiculoRepetidoException e){}
	}

}
