package testModelo_de_negocios;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloNegocio.Empresa;

public class TestEmpresa {
    Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		
		Empresa empresa2 = Empresa.getInstance();
		
		assertNotNull("La instancia debería ser creada en la primera llamada a getInstance()",empresa);
		
		assertSame("Las instancias deberían ser las mismas",empresa,empresa2);
	}

	public void testAgregarChofer(){
		Chofer chofer = new ChoferPermanente("12345","Jorge",2000,0); // Se que funciona por el test de la capa de datos
		
		try {
			empresa.agregarChofer(chofer);
		} catch (ChoferRepetidoException e) {
		}
		
		HashMap<String, Chofer> Choferes = empresa.getChoferes();
		
		assertTrue("La lista debería contener el chofer agregado", Choferes.containsKey(chofer.getDni()));
				
				
		
		
	}
}
