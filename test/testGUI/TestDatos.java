package testGUI;

import java.awt.AWTException;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JPanel;
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

    @Test
    public void testCantidad()

    {
        Assert.assertEquals("Debe haber 3 usuarios registrados", 3, empresa.getClientes().size());
    }
    
    @Before
    public void setUp() throws Exception {
        controlador = new Controlador(); 
        ventana = new Ventana();
        controlador.setVista(ventana); // ERA ESTA LINEA
        ventana.setOptionPane(op);
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

        // Verificar si el panel 'Panel_Cliente' ha sido añadido a la ventana
        JPanel panelCliente = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);
        Assert.assertNotNull("El panel Cliente no se desplegó correctamente", panelCliente);
        // Verificar si se logueo el cliente ingresado
        Assert.assertEquals("Deberia coincidir el nombre de usuario con el nombre ingresado", "Manu",empresa.getUsuarioLogeado().getNombreUsuario());
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


        // Verificar que se muestre el mensaje "Password incorrecto"
        Assert.assertEquals("Contraseña incorrecta", Mensajes.PASS_ERRONEO.getValor(), op.getMensaje());
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
        
        // Verificar si el panel 'Panel_Login' ha sido añadido a la ventana
        JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
        Assert.assertNotNull("El panel Cliente no se desplegó correctamente", panelLogin);
    }
    @Test
    public void testRegistroCancelar() {
    	robot.delay(TestUtils.getDelay());
        JButton BRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REGISTRAR);
        TestUtils.clickComponent(BRegistrar, robot);
        JButton BCancelar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_CANCELAR);
        
        robot.delay(TestUtils.getDelay());
        TestUtils.clickComponent(BCancelar, robot);

     // Verificar si el panel 'Panel_Login' ha sido añadido a la ventana
        JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
        Assert.assertNotNull("El panel Cliente no se desplegó correctamente", panelLogin);
    }
    @Test
    public void testRegistroRepetido() {

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
        TestUtils.tipeaTexto("Manu", robot);
        TestUtils.clickComponent(contra, robot);
        TestUtils.tipeaTexto("123456a", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("123456a", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("manuell", robot);
        TestUtils.clickComponent(Bregistrar2, robot);

        // Verificar que no se agregó ningun cliente
        Assert.assertEquals("Debería tener las mismas cantidad de clientes", cantidad, empresa.getClientes().size());
        
     // Verificar que se muestre el mensaje "Usuario repetido"
        Assert.assertEquals("Contraseña incorrecta", Mensajes.USUARIO_REPETIDO.getValor(), op.getMensaje());
    }
    @Test
    public void testRegistroNoCoincidePass() {

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
        TestUtils.tipeaTexto("Asdddddddd", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("Asd123", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("Agustin", robot);
        TestUtils.clickComponent(Bregistrar2, robot);

        Assert.assertEquals("Debería haber la misma cantidad de clientes", cantidad, empresa.getClientes().size());
        
        // Verificar que se muestre el mensaje "Pass no coincide"
        Assert.assertEquals("Contraseña incorrecta", Mensajes.PASS_NO_COINCIDE.getValor(), op.getMensaje());
    }
}
