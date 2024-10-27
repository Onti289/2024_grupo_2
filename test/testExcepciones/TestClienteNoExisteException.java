package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;

public class TestClienteNoExisteException {

	private Empresa empresa;
	private Cliente cliente;
    private Pedido pedido1;
    private Auto auto1;
    private Chofer chofer1;
    
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
		cliente = new Cliente("juan","**","juan");
        auto1 = new Auto("123", 4, true);
        empresa.agregarVehiculo(auto1);
        chofer1 = new ChoferTemporario("111", "Messi");
        empresa.agregarChofer(chofer1);
        pedido1 = new Pedido(cliente, 2, true, true, 12, "ZONA_STANDARD");
	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
	@Test
	public void test() throws SinVehiculoParaPedidoException, ClienteConViajePendienteException, ClienteConPedidoPendienteException {
		try {
			empresa.agregarPedido(pedido1);
			fail("Se esperaba que salte la excepcion ClienteNoExisteException");
		} catch (ClienteNoExisteException e){}
	}

}
