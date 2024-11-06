package testPersistencia;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistencia.PersistenciaBIN;
import persistencia.UtilPersistencia;
import modeloNegocio.Empresa;
import modeloDatos.*;
import persistencia.EmpresaDTO;
import java.io.IOException;

public class TestPersistenciaEmpresa {

    private PersistenciaBIN persistencia;
    private Empresa empresa;
    private final String archivo = "EmpresaPersistencia.dat";
    private Cliente cliente1,cliente2;
    private Pedido pedido1,pedido2,pedido3;
    private Auto auto1,auto2;
    private Chofer chofer1,chofer2;

    @Before
    public void setUp() throws Exception {
        persistencia = new PersistenciaBIN();
        empresa = Empresa.getInstance();

        // Configurar datos iniciales en la instancia de Empresa
        empresa.agregarCliente("Jorge", "123", "Jorge Carranza");
        empresa.agregarCliente("Ricardo", "123", "Ricardo Centurión");
        
        auto1 = new Auto("ABC123", 4, true);
        empresa.agregarVehiculo(auto1);
        auto2 = new Auto("ABC124", 4, true);
        empresa.agregarVehiculo(auto2);
        
        chofer1 = new ChoferTemporario("111", "Choferazo");
        empresa.agregarChofer(chofer1);
        chofer2 = new ChoferTemporario("444", "Nahuel");
        empresa.agregarChofer(chofer2);
        
        cliente1 = empresa.getClientes().get("Jorge");
        pedido1 = new Pedido(cliente1, 2, false, false, 12, "ZONA_STANDARD");
        empresa.agregarPedido(pedido1);
        cliente2 = empresa.getClientes().get("Ricardo");
        pedido2 = new Pedido(cliente2, 2, false, false, 12, "ZONA_STANDARD");
        empresa.agregarPedido(pedido2);
        
        empresa.login("Jorge", "123");
        
        empresa.crearViaje(pedido1, chofer1, auto1);
        empresa.pagarYFinalizarViaje(4);
        
        pedido3 = new Pedido(cliente1, 3, false, true, 2, "ZONA_STANDARD");
        empresa.agregarPedido(pedido3);
        empresa.crearViaje(pedido3, chofer1, auto1);       
    }

    @After
    public void tearDown() throws Exception {
        persistencia = null;
        empresa = null;
    }

    @Test
    public void testGuardarYLeerEmpresa() throws IOException, ClassNotFoundException {
        // Convertir la Empresa a un DTO
        EmpresaDTO empresaDTO = UtilPersistencia.EmpresaDtoFromEmpresa();

        // Guardar el DTO
        persistencia.abrirOutput(archivo);
        persistencia.escribir(empresaDTO);
        persistencia.cerrarOutput();

        // Leer el DTO
        persistencia.abrirInput(archivo);
        EmpresaDTO empresaDTOLeida = (EmpresaDTO) persistencia.leer();
        persistencia.cerrarInput();

        // Cargar los datos en una nueva instancia de Empresa
        UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeida);

        // Verificar que los datos fueron correctamente persistidos y recuperados
        Cliente clienteCargado = empresa.getClientes().get("Jorge");
        assertNotNull("El cliente cargado no debería ser null", clienteCargado);
        assertEquals("El nombre real debería ser Jorge Carranza", "Jorge Carranza", clienteCargado.getNombreReal());
        assertEquals("El nombre de usuario debería ser Jorge", "Jorge", clienteCargado.getNombreUsuario());
        assertEquals("La contraseña debería ser 123", "123", clienteCargado.getPass());

        Vehiculo autoCargado = empresa.getVehiculos().get("ABC123");  
        assertNotNull("El auto cargado no debería ser null", autoCargado);
        assertEquals("La patente debería ser ABC", "ABC123", autoCargado.getPatente());
        assertEquals("La cantidad de plazas debería ser 4", 4, autoCargado.getCantidadPlazas());
        assertTrue("Debería permitir mascota", autoCargado.isMascota());

        Chofer choferCargado = empresa.getChoferes().get("111");  
        assertNotNull("El chofer cargado no debería ser null", choferCargado);
        assertEquals("El nombre de chofer debería ser Choferazo", "Choferazo", choferCargado.getNombre());
        assertEquals("El DNI debería ser 111", "111", choferCargado.getDni());
        
        Pedido pedidoCargado = empresa.getPedidos().get(cliente2);
        assertNotNull("El pedido cargado no debería ser null", pedidoCargado);
		assertEquals("El cliente debería ser Ricardo", cliente2, pedidoCargado.getCliente());
		assertEquals("La cantidad de pasajeros debería ser 2", 2, pedidoCargado.getCantidadPasajeros());
		assertFalse("No debería pedir baul",  pedidoCargado.isBaul());
		assertFalse("No debería pedir mascota", pedidoCargado.isMascota());
		assertEquals("La cantidad de KM debería ser 12", 12, pedidoCargado.getKm());
		assertEquals("La zona debería ser standar", "ZONA_STANDARD", pedidoCargado.getZona());
		
		Usuario usuariologueado = empresa.getUsuarioLogeado();
		assertNotNull("El usuario logueado no debería ser null", usuariologueado);
		
		Viaje viajeIniciado = empresa.getViajesIniciados().get(cliente1);
		assertNotNull("El viaje iniciado no debería ser null", viajeIniciado);
		
		Viaje viajeFinalizados = empresa.getViajesTerminados().getFirst();
		assertNotNull("El viaje finalizado no debería ser null", viajeFinalizados);
    }
}
