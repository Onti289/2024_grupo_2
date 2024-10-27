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

public class TestVehiculoNoDisponibleException {

	private Empresa empresa;
	private Cliente cliente1, cliente2;
    private Pedido pedido1, pedido2;
    private Auto auto1, auto2;
    private Chofer chofer1, chofer2;
    
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
        empresa.agregarCliente("Lauta", "***", "Lautaro");
        cliente1 = empresa.getClientes().get("Lauta");
        empresa.agregarCliente("Pepe", "***", "Pepe");
        cliente2 = empresa.getClientes().get("Pepe");
        pedido1 = new Pedido(cliente1, 2, true, true, 12, "ZONA_STANDARD");
        pedido2 = new Pedido(cliente2, 2, true, true, 12, "ZONA_STANDARD");
        chofer1 = new ChoferTemporario("111", "Messi");
        empresa.agregarChofer(chofer1);
        chofer2 = new ChoferTemporario("777", "Adolfo");
        empresa.agregarChofer(chofer1);
        auto1 = new Auto("123", 4, true);
        empresa.agregarVehiculo(auto1);
        auto2 = new Auto("321", 4, true);
        empresa.agregarVehiculo(auto1);
	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
	@Test
	public void test() throws SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoValidoException {
		try {
	        empresa.agregarPedido(pedido1);
	        empresa.agregarPedido(pedido2);
	        empresa.crearViaje(pedido1, chofer1, auto1);      
	        empresa.crearViaje(pedido2, chofer2, auto1);
	        
			fail("Se esperaba que salte la excepcion VehiculoNoDisponibleException");
		} catch (VehiculoNoDisponibleException e){}
	}

}
