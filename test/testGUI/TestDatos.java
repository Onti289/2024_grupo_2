package testGUI;

import java.awt.AWTException;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import controlador.Controlador;
import excepciones.ClienteConPedidoPendienteException;
import excepciones.ClienteConViajePendienteException;
import excepciones.ClienteNoExisteException;
import excepciones.SinVehiculoParaPedidoException;
import excepciones.UsuarioYaExisteException;
import modeloDatos.Pedido;
import modeloDatos.Vehiculo;
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
    public void setUp() throws Exception{
        controlador = new Controlador(); 
        ventana = new Ventana();
        controlador.setVista(ventana); // ERA ESTA LINEA
        ventana.setOptionPane(op);
        Vehiculo vehiculo = new vehiculo("asd123",3,false);
        //empresa.agregarVehiculo(vehiculo);
        //Chofer chofer = new ChoferTemporario("")
        empresa.agregarChofer(chofer);
		empresa.agregarCliente("Manu", "123456", "manuel");
		empresa.agregarCliente("Lauti", "123456", "Lautaro");
		empresa.agregarCliente("Luken", "123456", "Lucas");
        Pedido pedido = new Pedido(empresa.getClientes().get("Lauti"),2,false,false,2,Constantes.ZONA_STANDARD);
		empresa.agregarPedido(pedido);

    }

    @After
    public void tearDown() throws Exception {
        ventana.setVisible(false);
    }
    
    // PANEL LOGIN
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

    // PANEL REGISTRO
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
    public void testRegistroCancelarHabilitado() {
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
    // PANEL CLIENTE
    @Test
    public void testTituloPanelCliente(){
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

        // Verifico si el titulo del panel del cliente es el nombre del cliente logueado
        JPanel panelCliente = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);
        TitledBorder borderTituloCliente = (TitledBorder) panelCliente.getBorder();
        Assert.assertEquals("manuel", borderTituloCliente.getTitle());
    	
    }
    @Test
    public void testCerrarSesionBotonHabilitado(){
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
        robot.delay(TestUtils.getDelay());
    	JButton BCerrarSesion = (JButton) TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_CLIENTE);
        TestUtils.clickComponent(BCerrarSesion, robot);
       // Verificar si el panel 'Panel_Login' ha sido añadido a la ventana
          JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
          Assert.assertNotNull("El panel Cliente no se desplegó correctamente", panelLogin);
    	
    }
    @Test
    public void testSinPedidoNiViajes(){
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

        JTextArea textArea = (JTextArea) TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
        Assert.assertTrue("El JTextArea debería estar vacío", textArea.getText().isEmpty());
        
     // Verificar que los JTextField, JRadioButton y JCheckBox en el sub-panel "Nuevo Pedido" están habilitados
        JTextField cantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JRadioButton ZonaStandard = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_STANDARD);
        JRadioButton ZonaSinAsfaltar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_SIN_ASFALTAR);
        JRadioButton ZonaPeligrosa = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_PELIGROSA);
        JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
        JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
        
        Assert.assertTrue("El campo CantPasajeros debería estar habilitado", cantPasajeros.isEnabled());
        Assert.assertTrue("El campo CantKm debería estar habilitado", cantKm.isEnabled());
        Assert.assertTrue("La zona stantard debería estar habilitada", ZonaStandard.isEnabled());
        Assert.assertTrue("La zona sin asfaltar debería estar habilitada", ZonaSinAsfaltar.isEnabled());
        Assert.assertTrue("La zona peligrosa debería estar habilitada", ZonaPeligrosa.isEnabled());
        Assert.assertTrue("El checkbox Mascota debería estar habilitado", checkMascota.isEnabled());
        Assert.assertTrue("El checkbox Baul debería estar habilitado", checkBaul.isEnabled());

        // Verificar que el JTextField CALIFICACION está deshabilitado
        JTextField calificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
        Assert.assertFalse("El campo Calificacion debería estar deshabilitado", calificacion.isEnabled());

        // Verificar que el JTextField VALOR_VIAJE está vacío
        JTextField valorViaje = (JTextField) TestUtils.getComponentForName(ventana, Constantes.VALOR_VIAJE);
        Assert.assertTrue("El campo Valor Viaje debería estar vacío", valorViaje.getText().isEmpty());
    	
    }
    @Test
    public void testConPedido() {

    	robot.delay(TestUtils.getDelay());
        
        // Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Lauti", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);
        
        // Verificar que los JTextField, JRadioButton y JCheckBox en el sub-panel "Nuevo Pedido" están deshabilitados
        JTextField cantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JRadioButton ZonaStandard = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_STANDARD);
        JRadioButton ZonaSinAsfaltar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_SIN_ASFALTAR);
        JRadioButton ZonaPeligrosa = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_PELIGROSA);
        JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
        JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
        
        robot.delay(2000);
        
        Assert.assertFalse("El campo CantPasajeros debería estar deshabilitado", cantPasajeros.isEnabled());
        Assert.assertFalse("El campo CantKm debería estar deshabilitado", cantKm.isEnabled());
        Assert.assertFalse("La zona stantard debería estar deshabilitada", ZonaStandard.isEnabled());
        Assert.assertFalse("La zona sin asfaltar debería estar deshabilitada", ZonaSinAsfaltar.isEnabled());
        Assert.assertFalse("La zona peligrosa debería estar deshabilitada", ZonaPeligrosa.isEnabled());
        Assert.assertFalse("El checkbox Mascota debería estar deshabilitado", checkMascota.isEnabled());
        Assert.assertFalse("El checkbox Baul debería estar deshabilitado", checkBaul.isEnabled());
        
        // Verificar que el JTextField CALIFICACION está deshabilitado
        JTextField calificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
        Assert.assertFalse("El campo Calificacion debería estar deshabilitado", calificacion.isEnabled());

        // Verificar que el JTextField VALOR_VIAJE está vacío
        JTextField valorViaje = (JTextField) TestUtils.getComponentForName(ventana, Constantes.VALOR_VIAJE);
        Assert.assertTrue("El campo Valor Viaje debería estar vacío", valorViaje.getText().isEmpty());
    }

}
