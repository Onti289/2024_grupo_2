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

    @Before
    public void setUp() throws Exception {
        persistencia = new PersistenciaBIN();
        empresa = Empresa.getInstance();

        // Configurar datos iniciales en la instancia de Empresa
        empresa.agregarCliente("user1", "pass1", "Cliente1");
        empresa.agregarVehiculo(new Auto("ABC123", 4, true));
        empresa.agregarChofer(new ChoferTemporario("111", "Chofer1"));
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

        // Guardar el DTO en el archivo binario
        persistencia.abrirOutput(archivo);
        persistencia.escribir(empresaDTO);
        persistencia.cerrarOutput();

        // Leer el DTO desde el archivo binario
        persistencia.abrirInput(archivo);
        EmpresaDTO empresaDTOLeida = (EmpresaDTO) persistencia.leer();
        persistencia.cerrarInput();

        // Cargar los datos en una nueva instancia de Empresa
        UtilPersistencia.empresaFromEmpresaDTO(empresaDTOLeida);

        // Verificar que los datos fueron correctamente persistidos y recuperados
        Cliente clienteCargado = empresa.getClientes().get("user1");
        assertNotNull("El cliente cargado no debería ser null", clienteCargado);
        assertEquals("Cliente1", clienteCargado.getNombreUsuario());

        Auto autoCargado = empresa.getVehiculo("ABC123");  
        assertNotNull("El auto cargado no debería ser null", autoCargado);
        assertEquals(4, autoCargado.getCantidadPlazas());
        assertTrue(autoCargado.isMascota());

        Chofer choferCargado = empresa.getChofer("111");  
        assertNotNull("El chofer cargado no debería ser null", choferCargado);
        assertEquals("Chofer1", choferCargado.getNombre());
    }
}
