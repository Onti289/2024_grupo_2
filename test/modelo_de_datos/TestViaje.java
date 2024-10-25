package modelo_de_datos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modeloDatos.*;

public class TestViaje {

    // Variables de instancia reutilizables en todas las pruebas
    private Cliente cliente;
    private Pedido pedido;
    private Chofer chofer;
    private Auto auto;
    private Viaje viaje;

    @Before
    public void setUp() throws Exception {
        // Inicializamos los objetos comunes aquí
        cliente = new Cliente("LautaATR", "***", "Lautaro");
        pedido = new Pedido(cliente, 3, true, true, 12, "ZONA_STANDARD");
        chofer = new ChoferTemporario("41370", "alejo");
        auto = new Auto("AAA", 4, true);

        // Creamos el objeto Viaje para reutilizarlo
        viaje = new Viaje(pedido, chofer, auto);
    }

    @After
    public void tearDown() throws Exception {
        // Libera recursos si es necesario (opcional)
        viaje = null;
    }

    @Test
    public void testConstructorViaje() {
        // Verificamos los atributos del viaje
        assertEquals("Pedido incorrecto", pedido, viaje.getPedido());
        assertEquals("Chofer incorrecto", chofer, viaje.getChofer());
        assertEquals("Auto incorrecto", auto, viaje.getVehiculo());
    }

    @Test
    public void testFinalizarViajes() {
        // Simulamos la finalización del viaje
        viaje.finalizarViaje(3);

        // Verificamos los resultados
        assertEquals("La calificación debería ser 3", 3, viaje.getCalificacion());
        assertTrue("El viaje debería estar finalizado", viaje.isFinalizado());
    }
    
    @Test
    public void testCalculoCostos() {
    	Pedido pedido2 = new Pedido(cliente, 3, false, false, 12, "ZONA_SIN_ASFALTAR");
    	Pedido pedido3 = new Pedido(cliente, 3, false, false, 12, "ZONA_PELIGROSA");

    	viaje.setValorBase(100.0);
    	
        assertEquals("El costo debería ser 360", 360, viaje.getValor(),0.00000001);
        
        Viaje viaje2 = new Viaje(pedido2, chofer, auto);
    	viaje2.setValorBase(100.0);

        Viaje viaje3 = new Viaje(pedido3, chofer, auto);
    	viaje3.setValorBase(100.0);

        assertEquals("El costo debería ser 240", 240, viaje2.getValor(),0.00000001);
        assertEquals("El costo debería ser 240", 220, viaje3.getValor(),0.00000001);
        
    }
}
