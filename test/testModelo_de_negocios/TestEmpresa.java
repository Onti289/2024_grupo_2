package testModelo_de_negocios;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import util.Constantes;

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


	@Test
	public void testAgregarChofer(){
		Chofer chofer = new ChoferPermanente("12345","Jorge",2000,0); // Se que funciona por el test de la capa de datos
		
		try {
			empresa.agregarChofer(chofer);
		} catch (ChoferRepetidoException e) {
		}
		
		
		HashMap<String, Chofer> Choferes = empresa.getChoferes();
		
		assertTrue("La lista debería contener el chofer agregado", Choferes.containsKey(chofer.getDni()));
				
		
		Empresa empresa3 = null;
		
		try {
			empresa3.agregarChofer(chofer);
		} catch (ChoferRepetidoException e) {
		}
		
		assertNull("Se añadió el chofer correctamente (no debería pasar)", Choferes.containsKey(chofer.getDni()));
		
		try {
			empresa.agregarChofer(chofer);
			fail("Se esperaba la excepcion ChoferRepetidoException.");
		} catch (ChoferRepetidoException e) {
		}
		
		
		
	}
	
	@Test
	public void testAgregarCliente()
	{
		try {
			empresa.agregarCliente("agus", "1234", "Agustin");
		} catch (UsuarioYaExisteException e) {
		}
		
		HashMap<String, Cliente> Clientes = empresa.getClientes();
		
		assertTrue("La lista debería contener el cliente agregado", Clientes.containsKey("agus"));
		
		Empresa empresa3 = null;
		
		try {
			empresa3.agregarCliente("agus", "1234", "Agustin");
		} catch (UsuarioYaExisteException e) {
		}
		
		HashMap<String, Cliente> Clientes3 = empresa3.getClientes();
		
		assertNull("Se añadió el cliente correctamente (no debería pasar)", Clientes3.containsKey("agus"));
		
		try {
			empresa.agregarCliente("agus", "1234", "Agustin");
			fail("Se esperaba la exception UsuarioYaExisteException.");
		} catch (UsuarioYaExisteException e) {
		}
	}
	
	@Test
	public void testAgregarVehiculo(){
		Vehiculo v = new Auto("ABC123",4,true);
		
		try {
			empresa.agregarVehiculo(v);
		} catch (VehiculoRepetidoException e) {
		}
		
        HashMap<String, Vehiculo> Vehiculos = empresa.getVehiculos();
		
		assertTrue("La lista debería contener el vehiculo agregado", Vehiculos.containsKey("ABC123"));
		
		Empresa empresa2 = null;
		
		Vehiculo v2 = new Auto("DEF456",2,false);
		HashMap<String, Vehiculo> Vehiculos2 = empresa.getVehiculos();
		
		try {
			empresa2.agregarVehiculo(v2);
		} catch (VehiculoRepetidoException e) {
		}
		
		assertNull("Se añadió el vehiculo correctamente (no debería pasar)", Vehiculos2.containsKey("DEF123"));
		
		
		try {
			empresa.agregarVehiculo(v);
			fail("Se esperaba la expección VehiculoRepetidoException.");
		} catch (VehiculoRepetidoException e) {
		}
		
		
	}
	
	
	
	
	@Test
	public void testAgregarPedido()
	{
		Vehiculo v = new Auto("ABC123",4,true); // Testeado previamente.
		Vehiculo v2 = new Auto("DEF456",2,false);
		Vehiculo v3 = new Auto("GHI789",4,false);
		
		try {
			empresa.agregarVehiculo(v);
			empresa.agregarVehiculo(v2);
		} catch (VehiculoRepetidoException e) {
		}
		
		// CASO 1
		
		Cliente cliente = new Cliente("agus","1234","agustin");

		try {
			empresa.agregarCliente("agus", "1234", "agustin");
		} catch (UsuarioYaExisteException e) {
		}
		
		Pedido pedido = new Pedido(cliente, 3,false,true,10,Constantes.ZONA_PELIGROSA);
		
		try {
			empresa.agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
		}
		
        HashMap<Cliente, Pedido> Pedidos = empresa.getPedidos();
		
		assertTrue("La lista debería contener el pedido agregado", Pedidos.containsKey(cliente));
		
		// CASO 2
		
		Cliente cliente2 = new Cliente("Pedrito","1234","pedro");
		
        Pedido pedido2 = new Pedido(cliente2, 3,false,true,5,Constantes.ZONA_SIN_ASFALTAR);
		
		try {
			empresa.agregarPedido(pedido2);
			fail("Se esperaba la expección ClienteNoExisteException.");
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			assertTrue("La excepción capturada no es ChoferRepetidoException", e instanceof ClienteNoExisteException);
		}
		
		
		
		// CASO 3
		
		Cliente cliente3 = new Cliente("Manu","1234","Manuel");

		try {
			empresa.agregarCliente("Manu", "1234", "Manuel");
		} catch (UsuarioYaExisteException e) {
		}
		
		Pedido pedido3 = new Pedido(cliente3, 4,true,true,6,Constantes.ZONA_STANDARD);
		try {
			empresa.agregarPedido(pedido3);
			fail("Se esperaba la excepción SinVehiculoParaPedido.");
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			assertTrue("La excepción capturada no es SinVehiculoParaPedidoException", e instanceof SinVehiculoParaPedidoException);
		}
		
		
		// CASO 4
		Empresa empresa2 = null;
		
		try {
			empresa2.agregarVehiculo(v);
		} catch (VehiculoRepetidoException e) {
		}
		
		try {
			empresa2.agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
		}
		
		HashMap<Cliente, Pedido> Pedidos2 = empresa2.getPedidos();
		
		assertNull("Se agrego el pedido correctamente (no debería pasar)", Pedidos2.containsKey(cliente));
		
		
	}
}
	
