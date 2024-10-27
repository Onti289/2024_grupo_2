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
import modeloDatos.Moto;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;

public class TestVehiculoNoValidoException {

	private Empresa empresa;
	private Cliente cliente1;
    private Pedido pedido1;
    private Auto auto1;
    private Chofer chofer1;
    private Moto moto;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
        empresa.agregarCliente("Lauta", "***", "Lautaro");
        cliente1 = empresa.getClientes().get("Lauta");
        pedido1 = new Pedido(cliente1, 2, true, true, 12, "ZONA_STANDARD");
        chofer1 = new ChoferTemporario("111", "Messi");
        empresa.agregarChofer(chofer1);
        auto1 = new Auto("123", 4, true);
        empresa.agregarVehiculo(auto1);
        moto = new Moto("DDD");
        empresa.agregarVehiculo(moto);
	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
	@Test
	public void test() throws SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException, PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException {
		try {
	        empresa.agregarPedido(pedido1);	        
	        empresa.crearViaje(pedido1, chofer1, moto);
			fail("Se esperaba que salte la excepcion VehiculoNoValidoException");
		} catch (VehiculoNoValidoException e){}
	}

}
