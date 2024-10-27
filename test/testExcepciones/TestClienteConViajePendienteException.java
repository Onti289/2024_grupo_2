package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;

public class TestClienteConViajePendienteException {

	private Empresa empresa;
	private Cliente cliente;
    private Pedido pedido1, pedido2;
    private Auto auto1, auto2;
    private Chofer chofer1,chofer2;
    
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
        empresa.agregarCliente("Lauta", "***", "Lautaro");
        cliente = empresa.getClientes().get("Lauta");
        pedido1 = new Pedido(cliente, 2, true, true, 12, "ZONA_STANDARD");
        pedido2 = new Pedido(cliente, 2, true, true, 12, "ZONA_STANDARD");
        chofer1 = new ChoferTemporario("111", "Messi");
        empresa.agregarChofer(chofer1);
        chofer2 = new ChoferTemporario("222", "De Paul");
        empresa.agregarChofer(chofer2);
        auto1 = new Auto("123", 4, true);
        auto2 = new Auto("321", 4, true);
        empresa.agregarVehiculo(auto1);
        empresa.agregarVehiculo(auto2);
	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
	@Test
	public void test() throws SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConPedidoPendienteException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException {
		try {
	        empresa.agregarPedido(pedido1);
	        empresa.crearViaje(pedido1, chofer1, auto1);
	        empresa.agregarPedido(pedido2);
			fail("Se esperaba que salte la excepcion ClienteConViajePendienteException");
		} catch (ClienteConViajePendienteException e){}
	}

}
