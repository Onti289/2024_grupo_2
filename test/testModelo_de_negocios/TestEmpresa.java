package testModelo_de_negocios;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ChoferNoDisponibleException;
import excepciones.ChoferRepetidoException;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.ClienteSinViajePendienteException;
import excepciones.PasswordErroneaException;
import excepciones.PedidoInexistenteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.SinViajesException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioYaExisteException;
import excepciones.VehiculoNoDisponibleException;
import excepciones.VehiculoNoValidoException;
import excepciones.VehiculoRepetidoException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferPermanente;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloDatos.Usuario;
import modeloDatos.Vehiculo;
import modeloDatos.Viaje;
import modeloNegocio.Empresa;
import util.Constantes;

public class TestEmpresa {
    Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
		empresa.getClientes().clear();
		empresa.getChoferes().clear();
		empresa.getVehiculos().clear();
		empresa.getPedidos().clear();
		empresa.getViajesIniciados().clear();
		empresa.getVehiculosDesocupados().clear();
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
				
		
		/*Empresa empresa3 = null;
		
		try {
			empresa3.agregarChofer(chofer);
		} catch (ChoferRepetidoException e) {
		}
		
		assertNull("Se añadió el chofer correctamente (no debería pasar)", Choferes.containsKey(chofer.getDni()));
		*/
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
		
		/*Empresa empresa3 = null;
		
		try {
			empresa3.agregarCliente("agus", "1234", "Agustin");
		} catch (UsuarioYaExisteException e) {
		}
		
		HashMap<String, Cliente> Clientes3 = empresa3.getClientes();
		
		assertNull("Se añadió el cliente correctamente (no debería pasar)", Clientes3.containsKey("agus"));
		*/
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
		
		/*Empresa empresa2 = null;
		
		Vehiculo v2 = new Auto("DEF456",2,false);
		HashMap<String, Vehiculo> Vehiculos2 = empresa.getVehiculos();
		
		try {
			empresa2.agregarVehiculo(v2);
		} catch (VehiculoRepetidoException e) {
		}
		
		assertNull("Se añadió el vehiculo correctamente (no debería pasar)", Vehiculos2.containsKey("DEF123"));
		*/
		
		try {
			empresa.agregarVehiculo(v);
			fail("Se esperaba la excepción VehiculoRepetidoException.");
		} catch (VehiculoRepetidoException e) {
		}
		
		
	}
	
	@Test
	public void testAgregarPedido()
	{
		Vehiculo v = new Auto("ABC123",4,false); // Testeado previamente.
		Vehiculo v2 = new Auto("DEF456",4,false);
		
		try {
			empresa.agregarVehiculo(v);
			empresa.agregarVehiculo(v2);
		} catch (VehiculoRepetidoException e) {
		}
		
		// CASO 1
		try {
			empresa.agregarCliente("agus", "1234", "agustin");
		} catch (UsuarioYaExisteException e) {
		}
		
		Cliente clienteGet = empresa.getClientes().get("agus");
		
		Pedido pedido = new Pedido(clienteGet, 3,false,true,10,Constantes.ZONA_PELIGROSA);
		
		try {
			empresa.agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
		}
		
        HashMap<Cliente, Pedido> Pedidos = empresa.getPedidos();
		
        
		assertTrue("La lista debería contener el pedido agregado", Pedidos.containsKey(clienteGet));
		
		// CASO 2
		
		Cliente cliente2 = new Cliente("Pedrito","1234","pedro");
		
		try {
			empresa.agregarCliente("Pedrito","1234","pedro");
		} catch (UsuarioYaExisteException e) {
		}
		
		
        Pedido pedido2 = new Pedido(cliente2, 3,false,true,5,Constantes.ZONA_SIN_ASFALTAR);
		
		try {
			empresa.agregarPedido(pedido2);
			fail("Se esperaba la excepción ClienteNoExisteException.");
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			assertTrue("La excepción capturada no es ClienteNoExisteException", e instanceof ClienteNoExisteException);

		}
		
		// CASO 3
		try {
			empresa.agregarCliente("Manu", "1234", "Manuel");
		} catch (UsuarioYaExisteException e) {
		}
		
		Cliente clienteGet3 = empresa.getClientes().get("Manu");
		
		Pedido pedido3 = new Pedido(clienteGet3, 4,true,true,6,Constantes.ZONA_STANDARD);
		try {
			empresa.agregarPedido(pedido3);
			fail("Se esperaba la excepción SinVehiculoParaPedido.");
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			assertTrue("La excepción capturada no es SinVehiculoParaPedidoException", e instanceof SinVehiculoParaPedidoException);
		}
		
		
		// CASO 4
		/*Empresa empresa2 = null;
		
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
		
		assertNull("Se agrego el pedido correctamente (no debería pasar)", Pedidos2.containsKey(cliente)); */
		
		
		// CASO 5 - AGREGO EL PEDIDO DEL CASO 1 DE NUEVO
		
		try {
			empresa.agregarPedido(pedido);
			fail("Se esperaba la excepción ClienteConPedidoPendiente.");
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
			assertTrue("La excepción capturada no es ClienteConPedidoPendienteException", e instanceof ClienteConPedidoPendienteException);
		}
		
		// CASO 6 - AGREGO EL PEDIDO DEL CASO 1 DE NUEVO
		// PRIMERO VINCULO UN CHOFER AL PEDIDO QUE ES EL UNICO QUE ESTA EN LA LISTA
		
		Chofer chofer = new ChoferPermanente("12345","Jorge",2000,0); // Se que funciona por el test de la capa de datos
		
		
		try {
			empresa.agregarChofer(chofer);
		} catch (ChoferRepetidoException e) {
		}
		
		try {
			empresa.crearViaje(pedido, chofer, v);
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
		}
		
		Pedido pedido4 = new Pedido(clienteGet, 4,false,true,6,Constantes.ZONA_STANDARD);
		
		try {
			empresa.agregarPedido(pedido4);
			fail("Se esperaba la excepción ClienteConViajePendiente.");
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
						| ClienteConPedidoPendienteException e) {
			assertTrue("La excepción capturada no es ClienteConViajePendienteException", e instanceof ClienteConViajePendienteException);
		}
		
	}

	@Test
	public void testCrearViaje(){

		// ESCENARIO INICIAL
		
		Vehiculo v = new Auto("HUD557",4,false); // Testeado previamente.
		Vehiculo v2 = new Auto("ASD123",4,false);
		Vehiculo v3 = new Auto("LLL589",4,true); //No lo utilizo pero lo pongo para que no lancé la excepcion de sin vehiculo para pedido
		
		try {
			empresa.agregarVehiculo(v);
			empresa.agregarVehiculo(v2);
			empresa.agregarVehiculo(v3);
		} catch (VehiculoRepetidoException e) {
		}
		
		Chofer chofer = new ChoferPermanente("40159003","Anacleto",2000,0); // Se que funciona por el test de la capa de datos
		Chofer chofer2 = new ChoferPermanente("4555386","rodrigo",2000,0);
		
		try {
			empresa.agregarChofer(chofer);
			empresa.agregarChofer(chofer2);
		} catch (ChoferRepetidoException e) {
		}

		
		try {
			empresa.agregarCliente("agus","1234","agustin");
			empresa.agregarCliente("manu","4567","manuel");
			empresa.agregarCliente("lauti","5678","lautaro");
			empresa.agregarCliente("luken","1234","lucas");
		} catch (UsuarioYaExisteException e) {
		}
		
		Cliente clienteGet1 = empresa.getClientes().get("agus");
		Cliente clienteGet2 = empresa.getClientes().get("manu");
		Cliente clienteGet3 = empresa.getClientes().get("lauti");
		Cliente clienteGet4 = empresa.getClientes().get("luken");
		
		Pedido pedido1 = new Pedido(clienteGet1, 2,false,false,3,Constantes.ZONA_PELIGROSA);
		Pedido pedido2 = new Pedido(clienteGet2, 2,true,false,3,Constantes.ZONA_PELIGROSA);
		Pedido pedido3 = new Pedido(clienteGet3, 5,false,true,3,Constantes.ZONA_PELIGROSA);
		Pedido pedido4 = new Pedido(clienteGet2, 3,false,false,3,Constantes.ZONA_SIN_ASFALTAR);
		Pedido pedido5 = new Pedido(clienteGet4, 4,true,false,3,Constantes.ZONA_SIN_ASFALTAR);
		Pedido pedido6 = new Pedido(clienteGet3, 3,false,false,3,Constantes.ZONA_PELIGROSA);
		
		try {
			empresa.agregarPedido(pedido1);
			empresa.agregarPedido(pedido2);
			empresa.agregarPedido(pedido5); //Los pongo antes de pedido 4 para que lance la excepcion más tarde
			empresa.agregarPedido(pedido6);
			empresa.agregarPedido(pedido4);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {

		}

		try {
			empresa.crearViaje(pedido1,chofer,v);
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
		}

		assertTrue("La lista debería contener el pedido agregado", empresa.getViajesIniciados().containsKey(clienteGet1));

		// CASO 2
		
		try {
			empresa.crearViaje(pedido2,chofer2,v2);
			fail("Se esperaba la excepción VehiculoNoValidoException");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			assertTrue("La excepción capturada no es VehiculoNoValidoException", e instanceof VehiculoNoValidoException);
		}
		
		// CASO 3 EL PEDIDO NO FUE AGREGADO A LA LISTA DE PEDIDOS
		
		try {
			empresa.crearViaje(pedido3,chofer2,v2);
			fail("Se esperaba la excepción PedidoInexistenteException");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			assertTrue("La excepción capturada no es PedidoInexistenteException", e instanceof PedidoInexistenteException);
		}
		
		// CASO 4 EL CLIENTE YA TENIA UN VIAJE QUE TODAVIA NO FUE INICIADO
		
		try {
			empresa.crearViaje(pedido4,chofer2,v2);
			fail("Se esperaba la excepción ClienteConViajePendienteException ");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			assertTrue("La excepción capturada no es ClienteConViajePendienteException", e instanceof ClienteConViajePendienteException);
		}
		
		// CASO 5 El chofer ya se encuentra realizando un viaje
		
		try {
			empresa.crearViaje(pedido5,chofer,v2);
			fail("Se esperaba la excepción ChoferNoDisponibleException ");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			assertTrue("La excepción capturada no es ChoferNoDisponibleException", e instanceof ChoferNoDisponibleException);
		}
		
		// CASO 6
		
		try {
			empresa.crearViaje(pedido6,chofer2,v);
			fail("Se esperaba la excepción VehiculoNoDisponibleException");
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
			assertTrue("La excepción capturada no es VehiculoNoDisponibleException", e instanceof VehiculoNoDisponibleException);
		}
		
		
		
	}

	@Test
	public void testVehiculosOrdenadosPorPedido()
	{
		// ESCENARIO INICIAL
	     
		   // CASO 2 (INICIO CON ESE CASO ANTES DE AGREGAR LOS VEHICULOS)
		   
			try {
				empresa.agregarCliente("Manu","1234","Manuel");
			} catch (UsuarioYaExisteException e) {
			}
		
			Cliente clienteGet1 = empresa.getClientes().get("Manu");
		
			Pedido pedido1 = new Pedido(clienteGet1, 3,false,true,10,Constantes.ZONA_PELIGROSA);
		
			try {
				empresa.agregarPedido(pedido1);
			} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
					| ClienteConPedidoPendienteException e) {
			}
		
			ArrayList <Vehiculo> VehiculosOrdenadosCaso1 = empresa.vehiculosOrdenadosPorPedido(pedido1);
		
			assertTrue("La lista debería estar vacía", VehiculosOrdenadosCaso1.isEmpty());
		
		
		
		    // CASO 1 CON VEHICULOS AGREGADOS
			
			Vehiculo v = new Auto("HUD557",4,false); // Testeado previamente.
			Vehiculo v2 = new Auto("ASD123",4,false);
			Vehiculo v3 = new Auto("LLL589",4,true); 
			try {
				empresa.agregarVehiculo(v);
				empresa.agregarVehiculo(v2);
				empresa.agregarVehiculo(v3);
			} catch (VehiculoRepetidoException e) {
			}
				
			try {
				empresa.agregarCliente("agus","1234","Agustin");
			} catch (UsuarioYaExisteException e) {
			}
			
			Cliente clienteGet2 = empresa.getClientes().get("agus");
			
			Pedido pedido2 = new Pedido(clienteGet2, 3,false,true,10,Constantes.ZONA_PELIGROSA);
			
			try {
				empresa.agregarPedido(pedido2);
			} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
					| ClienteConPedidoPendienteException e) {
			}
			
			ArrayList <Vehiculo> VehiculosOrdenadosCaso2 = empresa.vehiculosOrdenadosPorPedido(pedido2);
			
			assertEquals("La lista debería contener exactamente tres vehículos", 3, VehiculosOrdenadosCaso2.size());
		    assertTrue("La lista debería contener el vehículo 1", VehiculosOrdenadosCaso2.contains(v));
		    assertTrue("La lista debería contener el vehículo 2", VehiculosOrdenadosCaso2.contains(v2));
		    assertTrue("La lista debería contener el vehículo 3", VehiculosOrdenadosCaso2.contains(v3));
		    
		    // CASO 3 - NO SE PUEDE BRINDAR VEHICULO PARA EL PEDIDO
		    
		    try {
				empresa.agregarCliente("jorgito","1234","jorge");
			} catch (UsuarioYaExisteException e) {
			}
			
			Cliente clienteGet3 = empresa.getClientes().get("jorgito");
			
		    Pedido pedido3 = new Pedido(clienteGet3, 10,false,true,10,Constantes.ZONA_PELIGROSA);
		    
		    try {
				empresa.agregarPedido(pedido3);
			} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
					| ClienteConPedidoPendienteException e) {
			}
			
			ArrayList <Vehiculo> VehiculosOrdenadosCaso3 = empresa.vehiculosOrdenadosPorPedido(pedido3);   
			
			assertTrue("La lista debería estar vacía", VehiculosOrdenadosCaso3.isEmpty());
		}
		
	@Test
	public void testValidarPedido(){
	
		
		// CASO 2 - COLECCION DE VEHICULOS VACIA
		
		try {
			empresa.agregarCliente("Manu","1234","Manuel");
		} catch (UsuarioYaExisteException e) {
		}
	
		Cliente clienteGet2 = empresa.getClientes().get("Manu");
	
		Pedido pedido2 = new Pedido(clienteGet2, 3,false,true,10,Constantes.ZONA_SIN_ASFALTAR);
	
		try {
			empresa.agregarPedido(pedido2);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
		}
		
		assertFalse("El método validarPedido debería devolver false", empresa.validarPedido(pedido2));
		
		// CASO 1
		
		Vehiculo v = new Auto("HUD557",4,false); // Testeado previamente.
		try {
			empresa.agregarVehiculo(v);
		} catch (VehiculoRepetidoException e) {
		}
		
		try {
			empresa.agregarCliente("agus","1234","Agustin");
		} catch (UsuarioYaExisteException e) {
		}
	
		Cliente clienteGet1 = empresa.getClientes().get("agus");
	
		Pedido pedido1 = new Pedido(clienteGet1, 3,false,true,10,Constantes.ZONA_SIN_ASFALTAR);
		
		try {
			empresa.agregarPedido(pedido1);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
		}
		
		assertTrue("El método validarPedido debería devolver true", empresa.validarPedido(pedido1));
		
		// CASO 3 - NO SE ENCUENTRA VEHICULO QUE SATISFAGA EL PEDIDO, LA LISTA INICIAL ES CON EL VEHICULO V
		
		try {
			empresa.agregarCliente("jorgito","1234","Jorge");
		} catch (UsuarioYaExisteException e) {
		}
	
		Cliente clienteGet3 = empresa.getClientes().get("jorgito");
	
		Pedido pedido3 = new Pedido(clienteGet3, 8,false,true,10,Constantes.ZONA_SIN_ASFALTAR);
		
		try {
			empresa.agregarPedido(pedido3);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
		}
		
		assertFalse("El método validarPedido debería devolver false", empresa.validarPedido(pedido3));
		
		
		
		
	}
	
	@Test
	public void testLogin(){

		
		// CASO 2 - EL USUARIO NO ES CLIENTE NI ADMIN
		try {
			empresa.login("Lauta","123");
			fail("Se esperaba la excepción UsuarioNoExisteException.");
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
			assertTrue("La excepción capturada no es UsuarioNoExisteException", e instanceof UsuarioNoExisteException);
	    }
		
		// CASO 1
		try {
			empresa.agregarCliente("Lauta", "123", "lautaro");
			
		} catch (UsuarioYaExisteException e) {
		}
		
		Usuario logueado;
		try {
			logueado = empresa.login("Lauta","123");
			assertEquals("El método debería devolver el usuario esperado", logueado,empresa.getUsuarioLogeado());
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
		}
	    
		
		// CASO 3
		
		try {
			empresa.login("Lauta","333");
			fail("Se esperaba la excepción PasswordErroneaException.");
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
			assertTrue("La excepción capturada no es PasswordErroneaException", e instanceof PasswordErroneaException);
	    }
		
	}

	@Test
	public void testCalificacionDeChofer(){
		
		// CASO 1
		Vehiculo v = new Auto("HUD557",4,false); // Testeado previamente.
				
		try {
			empresa.agregarVehiculo(v);
		} catch (VehiculoRepetidoException e) {
		}
				
		Chofer chofer33 = new ChoferPermanente("40159003","Anacleto",2000,0); // Se que funciona por el test de la capa de datos
				
					
		try {
			empresa.agregarChofer(chofer33);
		} catch (ChoferRepetidoException e) {
		}

				
		try {
			empresa.agregarCliente("manu","4567","manuel");
		} catch (UsuarioYaExisteException e) {
		}
				
		Cliente clienteGet1 = empresa.getClientes().get("manu");
				
		Pedido pedido1 = new Pedido(clienteGet1, 2,false,false,3,Constantes.ZONA_PELIGROSA);
				
		try {
			empresa.agregarPedido(pedido1);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
						| ClienteConPedidoPendienteException e) {

		}

		try {
			empresa.crearViaje(pedido1,chofer33,v);
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
						| VehiculoNoValidoException | ClienteConViajePendienteException e) {
		}
				
		try {
			empresa.login("manu", "4567");
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
		}

		try {
			empresa.pagarYFinalizarViaje(4); // TESTEADO EN TESTPAGARYFINALIZARVIAJE
		} catch (ClienteSinViajePendienteException e) {
		}
		
		
		try {
			assertEquals("No devuelve el calculo de la calificacion correctamente",4.0,empresa.calificacionDeChofer(chofer33), 0.001);
		} catch (SinViajesException e) {
		}
		
		empresa.logout();
		
		// CASO 2 - NUNCA REALIZO VIAJES EL CHOFER
		
		Chofer chofer38 = new ChoferPermanente("12345","Jorge",2000,0); 
		try {
			empresa.agregarChofer(chofer38);
		} catch (ChoferRepetidoException e) {
		}
		
		try {
			empresa.calificacionDeChofer(chofer38);
			fail("Se esperaba la excepción SinViajesException");
		} catch (SinViajesException e) {
		}
		
		
		
	}

	@Test
	public void testPagarYFinalizarViaje(){
	  
		// CASO 1
		
		//Testeado en testPagarYFinalizarViaje
		
		Vehiculo v = new Auto("HUD557",4,false);
		
		try {
			empresa.agregarVehiculo(v);
		} catch (VehiculoRepetidoException e) {
		}
		
		Chofer chofer = new ChoferPermanente("40159003","Anacleto",2000,0); // Se que funciona por el test de la capa de datos
		
		
		
		try {
			empresa.agregarChofer(chofer);
		} catch (ChoferRepetidoException e) {
		}

		
		try {
			empresa.agregarCliente("manu","4567","manuel");
		} catch (UsuarioYaExisteException e) {
		}
		
        try {
			empresa.login("manu", "4567"); //testeado previamente
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
		}
		
		Cliente clienteGet1 = empresa.getClientes().get("manu");
		
		Pedido pedido1 = new Pedido(clienteGet1, 2,false,false,3,Constantes.ZONA_PELIGROSA);
		
		try {
			empresa.agregarPedido(pedido1);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {

		}

		try {
			empresa.crearViaje(pedido1,chofer,v);
		} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
				| VehiculoNoValidoException | ClienteConViajePendienteException e) {
		}
		

		try {
			empresa.pagarYFinalizarViaje(4);
			assertEquals("No se agrego el viaje a viajes terminados, no se finalizo el viaje",1,empresa.getViajesTerminados().size());
		} catch (ClienteSinViajePendienteException e) {
			fail("Se lanzó una excepción inesperada: " + e.getMessage());
		}
		
		empresa.logout();
		// CASO 2
		
		try {
			empresa.agregarCliente("agus", "1234", "Agustin");
		} catch (UsuarioYaExisteException e) {
		}
		try {
			empresa.login("agus", "1234");
		} catch (UsuarioNoExisteException | PasswordErroneaException e) {
		}
		
		try {
			empresa.pagarYFinalizarViaje(2);
			fail("Se esperaba la excepción ClienteSinViajePendienteException");
		} catch (ClienteSinViajePendienteException e) {
			assertTrue("La excepción capturada no es ClienteSinViajePendienteException", e instanceof ClienteSinViajePendienteException);
		}
	}

	@Test
	public void testGetHistorialViajeClienteYChofer() {
	
	    Vehiculo v = new Auto("HUD557", 4, false); // Testeado previamente.
	    try {
	        empresa.agregarVehiculo(v);
	    } catch (VehiculoRepetidoException e) {
	    }
	    
	    Chofer chofer = new ChoferPermanente("40159003", "Anacleto", 2000, 0); // Se que funciona por el test de la capa de datos
	    try {
	        empresa.agregarChofer(chofer);
	    } catch (ChoferRepetidoException e) {
	    }
	    
	    try {
	        empresa.agregarCliente("manu", "4567", "manuel");
	    } catch (UsuarioYaExisteException e) {
	    }
	    
	    try {
	        empresa.login("manu", "4567");
	    } catch (UsuarioNoExisteException | PasswordErroneaException e) {
	    }
	    
	    Cliente clienteGet1 = empresa.getClientes().get("manu");
	    Pedido pedido1 = new Pedido(clienteGet1, 2, false, false, 3, Constantes.ZONA_PELIGROSA);
	    
	    try {
	        empresa.agregarPedido(pedido1);
	    } catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException | ClienteConPedidoPendienteException e) {
	    }
	    
	    try {
	        empresa.crearViaje(pedido1, chofer, v);
	    } catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException | VehiculoNoValidoException | ClienteConViajePendienteException e) {
	
	    }
	    
	    try {
	        empresa.pagarYFinalizarViaje(4);
	    } catch (ClienteSinViajePendienteException e) {
	    }
	    
	    empresa.logout();
	    
	    ArrayList<Viaje> ViajesHistorialCliente = empresa.getHistorialViajeCliente(clienteGet1);
	    assertNotNull("La lista de viajes del historial del cliente no debería ser nula", ViajesHistorialCliente);
	    
	    ArrayList<Viaje> ViajesHistorialChofer = empresa.getHistorialViajeChofer(chofer);
	    assertNotNull("La lista de viajes del historial del chofer no debería ser nula", ViajesHistorialChofer);
	}

	@Test
	public void testGetPedidoCliente() {
		
		//Testeado en TestAgregarPedido
		
		Vehiculo v = new Auto("ABC123",4,false); // Testeado previamente.
		
		try {
			empresa.agregarVehiculo(v);
		} catch (VehiculoRepetidoException e) {
		}
		

		try {
			empresa.agregarCliente("manu", "5678", "Manuel");
			empresa.agregarCliente("agus", "1234", "Agustin");
		} catch (UsuarioYaExisteException e) {
		}
		
		Cliente clienteGet = empresa.getClientes().get("manu");
		
		Pedido pedido = new Pedido(clienteGet, 3,false,true,10,Constantes.ZONA_PELIGROSA);
		
		try {
			empresa.agregarPedido(pedido);
		} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
				| ClienteConPedidoPendienteException e) {
		}
		
		assertEquals("No devuelve el pedido que realizo el cliente",empresa.getPedidoDeCliente(clienteGet),pedido);
		
		Cliente clienteGet2 = empresa.getClientes().get("agus");
		assertNull("El cliente no habia realizado pedido, debería volver NULL",empresa.getPedidoDeCliente(clienteGet2));
		
		
	
	}

	@Test
	public void testGetViajeDeCliente() {
		
		// Testeado en testCrearViaje
		
				Vehiculo v = new Auto("HUD557",4,false); // Testeado previamente.
				
				try {
					empresa.agregarVehiculo(v);
				} catch (VehiculoRepetidoException e) {
				}
				
				Chofer chofer = new ChoferPermanente("40159003","Anacleto",2000,0); 
				
				try {
					empresa.agregarChofer(chofer);
				} catch (ChoferRepetidoException e) {
				}

				
				try {
					empresa.agregarCliente("manuGrx4","Lago343","Manuel");
					empresa.agregarCliente("agus","1234", "agustin");
				} catch (UsuarioYaExisteException e) {
				}
				
				Cliente clienteGet1 = empresa.getClientes().get("manuGrx4");
				Cliente clienteGet2 = empresa.getClientes().get("agus");
				
				Pedido pedido1 = new Pedido(clienteGet1, 2,false,false,3,Constantes.ZONA_PELIGROSA);
				try {
					empresa.agregarPedido(pedido1);
				} catch (SinVehiculoParaPedidoException | ClienteNoExisteException | ClienteConViajePendienteException
						| ClienteConPedidoPendienteException e) {

				}

				try {
					empresa.crearViaje(pedido1,chofer,v);
				} catch (PedidoInexistenteException | ChoferNoDisponibleException | VehiculoNoDisponibleException
						| VehiculoNoValidoException | ClienteConViajePendienteException e) {
				}
				
				assertEquals("No retorna el viaje que tiene el cliente en proceso",empresa.getViajeDeCliente(clienteGet1),empresa.getViajesIniciados().get(clienteGet1));
				assertNull("El cliente no tiene un viaje en proceso, deberia retornar NULL",empresa.getViajeDeCliente(clienteGet2));
		
	}

}
