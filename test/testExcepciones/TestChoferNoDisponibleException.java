package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.*;
import modeloNegocio.*;
import modeloDatos.*;

public class TestChoferNoDisponibleException {

    private Empresa empresa;
    private Chofer chofer1;
    private Auto auto1, auto2;
    private Cliente cliente1, cliente2;
    private Pedido pedido1, pedido2;

    @Before
    public void setUp() throws Exception {
        empresa = Empresa.getInstance();

        chofer1 = new ChoferTemporario("41370", "alejo");
        empresa.agregarChofer(chofer1);

        auto1 = new Auto("123", 4, true);
        auto2 = new Auto("321", 4, true);
        empresa.agregarVehiculo(auto1);
        empresa.agregarVehiculo(auto2);

        empresa.agregarCliente("Lauta", "***", "Lautaro");
        empresa.agregarCliente("pepe", "***", "pepe");

        cliente1 = empresa.getClientes().get("Lauta");
        cliente2 = empresa.getClientes().get("pepe");

        pedido1 = new Pedido(cliente1, 2, true, true, 12, "ZONA_STANDARD");
        pedido2 = new Pedido(cliente2, 2, true, true, 12, "ZONA_STANDARD");

        empresa.agregarPedido(pedido1);
        empresa.agregarPedido(pedido2);
    }

    @After
    public void tearDown() throws Exception {
        empresa = null;
    }

    @Test
    public void testChoferNoDisponibleException() throws PedidoInexistenteException, VehiculoNoDisponibleException, VehiculoNoValidoException, ClienteConViajePendienteException {
        try {
            empresa.crearViaje(pedido1, chofer1, auto1);
            empresa.crearViaje(pedido2, chofer1, auto2);

            // Si no se lanza la excepción, la prueba falla
            fail("Se esperaba excepción ChoferNoDisponibleException");
        } catch (ChoferNoDisponibleException e) {
            // Excepción esperada, la prueba pasa
        }
    }
}
