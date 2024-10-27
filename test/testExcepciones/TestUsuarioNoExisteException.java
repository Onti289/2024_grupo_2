package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.UsuarioNoExisteException;
import modeloNegocio.Empresa;

public class TestUsuarioNoExisteException {
   
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
    public void test() throws PasswordErroneaException {
        try {
            empresa.login("111", "111");

            // Si no se lanza la excepción, la prueba falla
            fail("Se esperaba excepción UsuarioNoExisteException");
        } catch (UsuarioNoExisteException e) {
            // Excepción esperada, la prueba pasa
        }
    }

}
