package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;

public class TestVehiculoNoDisponibleException {

	private Empresa empresa;
	private Cliente cliente1, cliente2;
    private Pedido pedido1, pedido2;
    private Auto auto1;
    private Chofer chofer1, chofer2;
    
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	VehiculoNoDisponibleException
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
