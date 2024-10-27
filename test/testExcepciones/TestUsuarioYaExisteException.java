package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.UsuarioYaExisteException;
import modeloNegocio.Empresa;

public class TestUsuarioYaExisteException {

	private Empresa empresa;
	
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
        	empresa.agregarCliente("Dibu", "penales", "Emiliano Martinez");
        	empresa.agregarCliente("Dibu", "penal", "Emiliano");

            // Si no se lanza la excepción, la prueba falla
            fail("Se esperaba excepción UsuarioYaExisteException");
        } catch (UsuarioYaExisteException e) {
            // Excepción esperada, la prueba pasa
        }
    }
    
}
