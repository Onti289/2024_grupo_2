package testGUI;

import java.awt.AWTException;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import controlador.Controlador;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;
import java.lang.reflect.Field;

public class TestDatos {
    private Robot robot;
    private Controlador controlador;
    private Ventana ventana;
    private Empresa empresa = Empresa.getInstance();
    private FalsoOptionPane op = new FalsoOptionPane();

    public TestDatos() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        controlador = new Controlador();
        ventana = new Ventana();
        
        // Usar reflexión para inyectar el FalsoOptionPane en la Ventana
        Field optionPaneField = Ventana.class.getDeclaredField("optionPane");
        optionPaneField.setAccessible(true);
        optionPaneField.set(ventana, op);
        
        empresa.agregarCliente("Manu", "123456", "manuel");
        empresa.agregarCliente("Lauti", "123456", "Lautaro");
        empresa.agregarCliente("Luken", "123456", "Lucas");
    }

    @After
    public void tearDown() throws Exception {
        ventana.setVisible(false);
    }

    @Test
    public void testLoginCorrecto() {
        robot.delay(TestUtils.getDelay());
        
        // Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Manu", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Verificar que se muestre el mensaje correcto
        //Assert.assertEquals("Mensaje incorrecto", Mensajes.LOGIN_OK.getValor(), op.getMensaje());
    }

    @Test
    public void testLoginUsuarioDesconocido() {
        robot.delay(TestUtils.getDelay());
        
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("UsuarioDesconocido", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("Asd123", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        robot.delay(1000);

        Assert.assertEquals("Mensaje incorrecto", Mensajes.USUARIO_DESCONOCIDO.getValor(), op.getMensaje());
    }


    @Test
    public void testLoginContrasenaIncorrecta() {
        robot.delay(TestUtils.getDelay());
        
        // Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        // Llenar los campos con un usuario correcto y contraseña incorrecta
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Manu", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("ContrasenaIncorrecta", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Añadir un retraso adicional para esperar el mensaje
        robot.delay(1500);  // Aumenta el retraso si es necesario

        // Verificar que se muestre el mensaje "Password incorrecto"
        Assert.assertEquals("Mensaje incorrecto", Mensajes.PASS_ERRONEO.getValor(), op.getMensaje());
        op.clearMensaje();  // Limpia el mensaje para futuras pruebas
    }


    @Test
    public void testLoginBotonDeshabilitado() {
        robot.delay(TestUtils.getDelay());
        
        // Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        // Dejar campos vacíos y verificar que el botón esté deshabilitado
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("", robot);
        
        Assert.assertFalse("El botón LOGIN debería estar deshabilitado", aceptarLog.isEnabled());
    }

    @Test
    public void testRegistroOk() {
        robot.delay(TestUtils.getDelay());
        int cantidad = empresa.getClientes().size();

        // Obtener los componentes del formulario de registro
        JButton BRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REGISTRAR);
        TestUtils.clickComponent(BRegistrar, robot);

        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
        JTextField contra = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
        JTextField confirmaContra = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
        JTextField nombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);
        JButton Bregistrar2 = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);

        // Llenar el formulario de registro
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Agus", robot);
        TestUtils.clickComponent(contra, robot);
        TestUtils.tipeaTexto("Asd123", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("Asd123", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("Agustin", robot);
        TestUtils.clickComponent(Bregistrar2, robot);

        // Verificar que el cliente fue agregado
        Assert.assertEquals("Debería haber un cliente adicional", cantidad + 1, empresa.getClientes().size());
    }
}
