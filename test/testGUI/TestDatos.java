package testGUI;

import java.awt.AWTException;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
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
import excepciones.*;
import modeloDatos.*;
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
        Vehiculo vehiculo = new Auto("asd123",4,true);
        empresa.agregarVehiculo(vehiculo);
        Chofer chofer = new ChoferTemporario("999","riki");
        empresa.agregarChofer(chofer);
		empresa.agregarCliente("Manu", "123456", "manuel");
		empresa.agregarCliente("Lauti", "123456", "Lautaro");
		empresa.agregarCliente("Luken", "123456", "Lucas");
        Pedido pedido = new Pedido(empresa.getClientes().get("Lauti"),2,false,false,2,Constantes.ZONA_STANDARD);
		empresa.agregarPedido(pedido);
		empresa.crearViaje(pedido, empresa.getChoferes().get("999"), empresa.getVehiculos().get("asd123"));
		empresa.login("Lauti", "123456");
		empresa.pagarYFinalizarViaje(5);
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

    @Test
    public void testCrearPedidoExitoso() {
        robot.delay(TestUtils.getDelay());
        int cantidad = empresa.getPedidos().size();
        
        // Loguear al usuario para acceder a la creación de pedidos
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Manu", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Completar los campos necesarios para el pedido
        JTextField cantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
        JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);

        TestUtils.clickComponent(cantPasajeros, robot);
        TestUtils.tipeaTexto("3", robot);  // Pasajeros entre 1 y 10
        TestUtils.clickComponent(cantKm, robot);
        TestUtils.tipeaTexto("5", robot);  // Kilómetros mayor o igual a 0
        TestUtils.clickComponent(checkMascota, robot);  // Selección de opciones adicionales
        TestUtils.clickComponent(checkBaul, robot);
        
        // Verificar que el botón NUEVO_PEDIDO esté habilitado
        Assert.assertTrue("El botón NUEVO_PEDIDO debería estar habilitado", botonNuevoPedido.isEnabled());

        // Crear el pedido
        TestUtils.clickComponent(botonNuevoPedido, robot);

        // Verificar que los campos se borren después de crear el pedido
        Assert.assertEquals("El campo CantPasajeros debería estar vacío después del pedido", "", cantPasajeros.getText());
        Assert.assertEquals("El campo CantKm debería estar vacío después del pedido", "", cantKm.getText());
        
        // Verificar que el pedido fue agregado
        Assert.assertEquals("Debería haber un pedido adicional", cantidad + 1, empresa.getPedidos().size());
    }

    @Test
    public void testCrearPedidoSinVehiculoDisponible() {
        robot.delay(TestUtils.getDelay());

        // Loguear al usuario
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Manu", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Completar los campos del pedido para una configuración sin vehículo disponible
        JTextField cantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
        JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);

        TestUtils.clickComponent(cantPasajeros, robot);
        TestUtils.tipeaTexto("5", robot);  // Configurar una cantidad de pasajeros que ningún vehículo puede soportar
        TestUtils.clickComponent(cantKm, robot);
        TestUtils.tipeaTexto("20", robot);  // Distancia suficiente para generar el pedido
        TestUtils.clickComponent(checkMascota, robot);  // Opciones adicionales
        TestUtils.clickComponent(checkBaul, robot);

        // Intentar crear el pedido
        TestUtils.clickComponent(botonNuevoPedido, robot);

        // Verificar que se muestra el mensaje de error "Sin vehículo para pedido"
        Assert.assertEquals("Debería mostrar el mensaje de error por falta de vehículo", Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(), op.getMensaje());
    }

    @Test
    public void testSeleccionZonaExclusiva() {
        robot.delay(TestUtils.getDelay());

        // Loguear al usuario
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Manu", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Seleccionar cada zona y verificar exclusividad
        JRadioButton ZonaStandard = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_STANDARD);
        JRadioButton ZonaSinAsfaltar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_SIN_ASFALTAR);
        JRadioButton ZonaPeligrosa = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_PELIGROSA);

        TestUtils.clickComponent(ZonaStandard, robot);
        Assert.assertTrue("La zona standard debería estar seleccionada", ZonaStandard.isSelected());
        Assert.assertFalse("La zona sin asfaltar no debería estar seleccionada", ZonaSinAsfaltar.isSelected());
        Assert.assertFalse("La zona peligrosa no debería estar seleccionada", ZonaPeligrosa.isSelected());

        TestUtils.clickComponent(ZonaSinAsfaltar, robot);
        Assert.assertFalse("La zona standard no debería estar seleccionada", ZonaStandard.isSelected());
        Assert.assertTrue("La zona sin asfaltar debería estar seleccionada", ZonaSinAsfaltar.isSelected());
        Assert.assertFalse("La zona peligrosa no debería estar seleccionada", ZonaPeligrosa.isSelected());

        TestUtils.clickComponent(ZonaPeligrosa, robot);
        Assert.assertFalse("La zona standard no debería estar seleccionada", ZonaStandard.isSelected());
        Assert.assertFalse("La zona sin asfaltar no debería estar seleccionada", ZonaSinAsfaltar.isSelected());
        Assert.assertTrue("La zona peligrosa debería estar seleccionada", ZonaPeligrosa.isSelected());
    }
    
    @Test
    public void testCrearPedidoConCamposIncompletos() {
        robot.delay(TestUtils.getDelay());

        // Loguear al usuario
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Manu", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Obtener referencia al botón y verificar que esté deshabilitado cuando los campos están vacíos
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
        Assert.assertFalse("El botón NUEVO_PEDIDO debería estar deshabilitado con campos vacíos", botonNuevoPedido.isEnabled());

        // Rellenar solo algunos campos y verificar que el botón sigue deshabilitado
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);

        TestUtils.clickComponent(cantKm, robot);
        TestUtils.tipeaTexto("150", robot);
        TestUtils.clickComponent(checkBaul, robot);

        Assert.assertFalse("El botón NUEVO_PEDIDO debería seguir deshabilitado con campos incompletos", botonNuevoPedido.isEnabled());
    }

    @Test
    public void testImpedirNuevoPedidoConPedidoPendiente() throws PedidoInexistenteException, ClienteConPedidoPendienteException, SinVehiculoParaPedidoException, ClienteNoExisteException, ClienteConViajePendienteException {
        robot.delay(TestUtils.getDelay());
        
        // Configuración del cliente y creación de un pedido inicial
        Cliente cliente = empresa.getClientes().get("Manu");
        Pedido pedidoInicial = new Pedido(cliente, 2, false, false, 4, Constantes.ZONA_STANDARD);
        empresa.agregarPedido(pedidoInicial);

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

        // Verificar que el JTextArea PEDIDO_O_VIAJE_ACTUAL contiene el pedido y no permite crear otro
        JTextArea pedidoOViajeActual = (JTextArea) TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
        Assert.assertFalse("El JTextArea PEDIDO_O_VIAJE_ACTUAL debería tener contenido", pedidoOViajeActual.getText().isEmpty());

        // Verificar que los campos para un nuevo pedido están deshabilitados
        JTextField cantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JRadioButton zonaStandard = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_STANDARD);
        JRadioButton zonaSinAsfaltar = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_SIN_ASFALTAR);
        JRadioButton zonaPeligrosa = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.ZONA_PELIGROSA);
        JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
        JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
        JTextField calificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
        JTextField valorViaje = (JTextField) TestUtils.getComponentForName(ventana, Constantes.VALOR_VIAJE);
        
        Assert.assertFalse("El campo CantPasajeros debería estar deshabilitado", cantPasajeros.isEnabled());
        Assert.assertFalse("El campo CantKm debería estar deshabilitado", cantKm.isEnabled());
        Assert.assertFalse("La zona Standard debería estar deshabilitada", zonaStandard.isEnabled());
        Assert.assertFalse("La zona Sin Asfaltar debería estar deshabilitada", zonaSinAsfaltar.isEnabled());
        Assert.assertFalse("La zona Peligrosa debería estar deshabilitada", zonaPeligrosa.isEnabled());
        Assert.assertFalse("El checkbox Mascota debería estar deshabilitado", checkMascota.isEnabled());
        Assert.assertFalse("El checkbox Baul debería estar deshabilitado", checkBaul.isEnabled());

        // Verificar que el TextField CALIFICACION esté deshabilitado
        Assert.assertFalse("El campo Calificacion debería estar deshabilitado", calificacion.isEnabled());

        // Verificar que el TextField VALOR_VIAJE esté vacío
        Assert.assertTrue("El campo Valor Viaje debería estar vacío", valorViaje.getText().isEmpty());
    }

    
    @Test
    public void testFinalizarViajeYCalificarChofer() throws PedidoInexistenteException, ChoferNoDisponibleException, VehiculoNoDisponibleException, VehiculoNoValidoException, ClienteConViajePendienteException {
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

        // Completar los campos necesarios para el pedido
        JTextField cantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JButton aceptar = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);
        
        TestUtils.clickComponent(cantPasajeros, robot);
        TestUtils.tipeaTexto("2", robot);  // Pasajeros entre 1 y 10
        TestUtils.clickComponent(cantKm, robot);
        TestUtils.tipeaTexto("4", robot);  // Kilómetros mayor o igual a 0
        TestUtils.clickComponent(aceptar, robot);
        // admin crea el viaje
        Cliente clienteActual = empresa.getClientes().get("Manu");
        empresa.crearViaje(empresa.getPedidos().get(clienteActual), empresa.getChoferes().get("999"), empresa.getVehiculos().get("asd123"));
        // Completar calificación para habilitar el botón CALIFICAR_PAGAR
        JTextField calificacion = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_DE_VIAJE);
        JButton botonCalificarPagar = (JButton) TestUtils.getComponentForName(ventana, Constantes.CALIFICAR_PAGAR);

        TestUtils.clickComponent(calificacion, robot);
        TestUtils.tipeaTexto("5", robot);  // Calificación válida entre 0 y 5
        robot.delay(5000);
        
        // El campo de calificación debe estar disponible
        Assert.assertTrue("El campo de calificación debería estar disponible", calificacion.isEnabled());
        // Verificar que el botón CALIFICAR_PAGAR esté habilitado
        Assert.assertTrue("El botón CALIFICAR_PAGAR debería estar habilitado con una calificación válida", botonCalificarPagar.isEnabled());

        // Finalizar el viaje
        TestUtils.clickComponent(botonCalificarPagar, robot);

        // Verificar que el JTextArea PEDIDO_O_VIAJE_ACTUAL esté vacío
        JTextArea pedidoOViajeActual = (JTextArea) TestUtils.getComponentForName(ventana, Constantes.PEDIDO_O_VIAJE_ACTUAL);
        Assert.assertTrue("El JTextArea PEDIDO_O_VIAJE_ACTUAL debería estar vacío después de finalizar el viaje", pedidoOViajeActual.getText().isEmpty());

        // Verificar que el viaje se haya agregado a la lista histórica LISTA_VIAJES_CLIENTE
        javax.swing.JList<Viaje> listaViajesCliente = (javax.swing.JList<Viaje>) TestUtils.getComponentForName(ventana, Constantes.LISTA_VIAJES_CLIENTE);
        Assert.assertTrue("La lista de viajes históricos debería contener al menos un viaje", listaViajesCliente.getModel().getSize() > 0);

        // Verificar que el campo CALIFICACION se borre después de finalizar
        Assert.assertEquals("El campo CALIFICACION debería estar vacío después de finalizar el viaje", "", calificacion.getText());

        // Confirmar que el panel esté en estado inicial (estado 1)
        JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
        Assert.assertNotNull("Debería haberse regresado al panel de inicio de sesión (estado 1)", panelLogin);
    }

    
    // ADMIN
    //VISUALIZACIÓN DE INFORMACIÓN
    @Test
    public void testAdminSeleccionaChofer() throws SinViajesException{
    	// PAGO UN VIAJE PARA QUE APAREZCA EL HISTORIAL DE ESE CHOFER
    	
    	// Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);
  
        

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("admin", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("admin", robot);
        TestUtils.clickComponent(aceptarLog, robot);
        
        JList choferes = (JList)  TestUtils.getComponentForName(ventana, Constantes.LISTA_CHOFERES_TOTALES);
        choferes.setSelectedIndex(0);
        
        JList viajesChofer = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VIAJES_DE_CHOFER);
        
        JTextField CalificacionChofer = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_CHOFER);
        // Debe contener la calificacion de ese chofer
        Assert.assertEquals("La calificación del chofer no es correcta", empresa.calificacionDeChofer(empresa.getChoferes().get("999")), Double.parseDouble(CalificacionChofer.getText()), 0.01);
        // Verifica que la lista no esté vacía
        Assert.assertTrue("La lista de viajes debería tener al menos un viaje", viajesChofer.getModel().getSize() > 0);

        // Verifica que los elementos de la lista sean de tipo Viaje
        for (int i = 0; i < viajesChofer.getModel().getSize(); i++) {
            Object viaje = viajesChofer.getModel().getElementAt(i);
            Assert.assertTrue("Cada elemento debe ser de tipo Viaje", viaje instanceof Viaje);
         
      
        }
    }

    @Test
    public void testAdminSeleccionaChofr(){
    	// PAGO UN VIAJE PARA QUE APAREZCA EL HISTORIAL DE ESE CHOFER
    	
    	// Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);
  
        

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("admin", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("admin", robot);
        TestUtils.clickComponent(aceptarLog, robot);
        
        JList choferes = (JList)  TestUtils.getComponentForName(ventana, Constantes.LISTA_CHOFERES_TOTALES);
        choferes.setSelectedIndex(0);
        
        JList viajesChofer = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VIAJES_DE_CHOFER);
        
        // Verifica que la lista no esté vacía
        Assert.assertTrue("La lista de viajes debería tener al menos un viaje", viajesChofer.getModel().getSize() > 0);

        // Verifica que los elementos de la lista sean de tipo Viaje
        for (int i = 0; i < viajesChofer.getModel().getSize(); i++) {
            Object viaje = viajesChofer.getModel().getElementAt(i);
            Assert.assertTrue("Cada elemento debe ser de tipo Viaje", viaje instanceof Viaje);
        }
    }


}    


    
