package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloNegocio.Empresa;

public class TestChoferRepetidoException {

	private Empresa empresa;
    private Chofer chofer1,chofer2;
    
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();

        chofer1 = new ChoferTemporario("123", "Agustin Colazo");
        chofer2 = new ChoferTemporario("123", "Elias Torres");

	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}

	@Test
	public void test() {
		try {
        empresa.agregarChofer(chofer1);
        empresa.agregarChofer(chofer2);
		fail("Se esperaba que salte la excepcion ChoferRepetidoException");
		} catch (ChoferRepetidoException e) {}
	}

}
