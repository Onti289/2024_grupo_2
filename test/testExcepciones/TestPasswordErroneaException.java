package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloNegocio.Empresa;

public class TestPasswordErroneaException {

    private Empresa empresa;
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
        empresa.agregarCliente("Lauta", "***", "Lauta");
	}

	@After
	public void tearDown() throws Exception {
	}

    @Test
    public void test() throws UsuarioNoExisteException {
        try {
        	empresa.login("lauta", "**");
            // Si no se lanza la excepción, la prueba falla
    		fail("Se esperaba excepción PasswordErroneaException");
        } catch (PasswordErroneaException e) {
            // Excepción esperada, la prueba pasa
        }
    }

}
