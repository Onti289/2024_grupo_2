package testExcepciones;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import excepciones.ClienteSinViajePendienteException;
import modeloDatos.Auto;
import modeloDatos.Chofer;
import modeloDatos.ChoferTemporario;
import modeloDatos.Cliente;
import modeloNegocio.Empresa;

public class TestClienteSinViajePendienteException {

    private Empresa empresa;
    private Chofer chofer1;
    private Auto auto1;
    private Cliente cliente;    
    @Before
	public void setUp() throws Exception {
		empresa = Empresa.getInstance();
        empresa.agregarCliente("Lauta", "***", "Lautaro");
        cliente = empresa.getClientes().get("Lauta");
        chofer1 = new ChoferTemporario("111", "Messi");
        empresa.agregarChofer(chofer1);
        auto1 = new Auto("123", 4, true);
        empresa.agregarVehiculo(auto1);
	}

	@After
	public void tearDown() throws Exception {
		empresa = null;
	}
	
    @Test
    public void test() {
        try {
            empresa.pagarYFinalizarViaje(3);

            // Si no se lanza la excepción, la prueba falla
            fail("Se esperaba excepción ClienteSinViajePendienteException");
        } catch (ClienteSinViajePendienteException e) {
            // Excepción esperada, la prueba pasa
        }
    }

}
