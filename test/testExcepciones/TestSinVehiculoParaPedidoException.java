package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloDatos.Pedido;
import modeloNegocio.Empresa;

public class TestSinVehiculoParaPedidoException {

	private Empresa empresa;
	private Cliente cliente;
    private Pedido pedido1;
    private Chofer chofer1;
	@Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
        empresa.agregarCliente("Lauta", "***", "Lautaro");
        cliente = empresa.getClientes().get("Lauta");
        pedido1 = new Pedido(cliente, 2, true, true, 12, "ZONA_STANDARD");
        chofer1 = new ChoferTemporario("111", "Messi");
        empresa.agregarChofer(chofer1);
	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
    @Test
    public void test() throws ClienteNoExisteException, ClienteConViajePendienteException, ClienteConPedidoPendienteException {
        try {
        	empresa.agregarPedido(pedido1);
        	
            // Si no se lanza la excepción, la prueba falla
            fail("Se esperaba excepción SinVehiculoParaPedidoException");
        } catch (SinVehiculoParaPedidoException e) {
            // Excepción esperada, la prueba pasa
        }
    }
}
