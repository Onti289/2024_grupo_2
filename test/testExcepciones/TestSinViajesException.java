package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.SinViajesException;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloNegocio.Empresa;

public class TestSinViajesException {
	private Empresa empresa;
    private Chofer chofer1;

	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
        chofer1 = new ChoferTemporario("111", "Messi");
        empresa.agregarChofer(chofer1);
   	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
    @Test
    public void test() {
        try {
        	double calificacion = empresa.calificacionDeChofer(chofer1);
            // Si no se lanza la excepción, la prueba falla
            fail("Se esperaba excepción SinViajesException");
        } catch (SinViajesException e) {
            // Excepción esperada, la prueba pasa
        }
    }

}
