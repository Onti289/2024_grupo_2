package testControlador;

import controlador.Controlador;
import modeloNegocio.Empresa;
import vista.IVista;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import persistencia.IPersistencia;
import static org.junit.Assert.*;

public class TestControlador {
    
    private Controlador controlador;
    private IVista vistaMock;
    private Empresa empresa;
    private IPersistencia persistenciaMock;

    @Before
    public void setUp() {
        empresa = Empresa.getInstance();
        vistaMock = Mockito.mock(IVista.class);
        persistenciaMock = Mockito.mock(IPersistencia.class);
        
        controlador = new Controlador();
        controlador.setVista(vistaMock);
        controlador.setPersistencia(persistenciaMock);
    }

    @Test
    public void testLoginCorrecto() {
        // Mock de entrada en la vista
        Mockito.when(vistaMock.getUsserName()).thenReturn("username");
        Mockito.when(vistaMock.getPassword()).thenReturn("password");

        // Mock de comportamiento esperado en Empresa
        Mockito.doNothing().when(empresa).login("username", "password");

        // Ejecutar el método y verificar si Empresa realiza login
        controlador.login();
        Mockito.verify(vistaMock).actualizar();
    }

    @Test
    public void testRegistroCorrecto() {
        // Mock de datos de registro en vista
        Mockito.when(vistaMock.getRegNombreReal()).thenReturn("nombre real");
        Mockito.when(vistaMock.getRegUsserName()).thenReturn("usuario");
        Mockito.when(vistaMock.getRegPassword()).thenReturn("password");
        Mockito.when(vistaMock.getRegConfirmPassword()).thenReturn("password");

        // Ejecutar el método y verificar que Empresa agrega el cliente
        controlador.registrar();
        Mockito.verify(vistaMock).mostrarMensaje("Registro exitoso");
    }

    @Test
    public void testCalificarPagar() {
        Mockito.when(vistaMock.getCalificacion()).thenReturn(4);

        controlador.calificarPagar();
        
        Mockito.verify(vistaMock).actualizar();
    }

    @Test
    public void testLogout() {
        controlador.logout();

        // Verificar si se intentó escribir los datos después del logout
        Mockito.verify(persistenciaMock).guardar(empresa, "empresa.bin");
        Mockito.verify(vistaMock).mostrarMensaje("Sesión cerrada exitosamente");
    }
}