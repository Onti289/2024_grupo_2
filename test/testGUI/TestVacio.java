package testGUI;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloNegocio.Empresa;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;

public class TestVacio {
	private Robot robot;
    private Controlador controlador;
    private Ventana ventana;
    private Empresa empresa = Empresa.getInstance();
    private FalsoOptionPane op = new FalsoOptionPane();
    
    public TestVacio() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    
    @Before
    public void setUp() throws Exception{
        controlador = new Controlador(); 
        ventana = new Ventana();
        controlador.setVista(ventana);
        ventana.setOptionPane(op);
    }

    @After
    public void tearDown() throws Exception {
        ventana.setVisible(false);
    }
    
    // PANEL LOGIN
    @Test
    public void testLogin() {
        robot.delay(TestUtils.getDelay());
        
        // Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        Assert.assertEquals("Mensaje incorrecto", Mensajes.USUARIO_DESCONOCIDO.getValor(), op.getMensaje());
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
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contra, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(Bregistrar2, robot);

        // Verificar que el cliente fue agregado
        Assert.assertEquals("Debería haber un cliente adicional", cantidad + 1, empresa.getClientes().size());
        
        // Verificar si el panel 'Panel_Login' ha sido añadido a la ventana
        JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
        Assert.assertNotNull("El panel Cliente no se desplegó correctamente", panelLogin);
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

    @Test
    public void testRegistroCancelarHabilitado() {
    	robot.delay(TestUtils.getDelay());
        JButton BRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REGISTRAR);
        TestUtils.clickComponent(BRegistrar, robot);
        JButton BCancelar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_CANCELAR);
        
        robot.delay(2000);
        TestUtils.clickComponent(BCancelar, robot);

        // Verificar si el panel 'Panel_Login' ha sido añadido a la ventana
        JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
        Assert.assertNotNull("El panel Cliente no se desplegó correctamente", panelLogin);
    }
    
    // PANEL CLIENTE
    @Test
    public void testTituloPanelCliente(){
    	robot.delay(TestUtils.getDelay());
        
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
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contra, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(Bregistrar2, robot);
    	
        // Obtener componentes de Login
        JTextField nombreaux = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombreaux, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Verifico si el titulo del panel del cliente es el nombre del cliente logueado
        JPanel panelCliente = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_CLIENTE);
        TitledBorder borderTituloCliente = (TitledBorder) panelCliente.getBorder();
        Assert.assertEquals("Lauta", borderTituloCliente.getTitle());    	
    }
    
    @Test
    public void testCerrarSesionBotonHabilitado(){
    	robot.delay(TestUtils.getDelay());
        
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
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contra, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(Bregistrar2, robot);
    	
        // Obtener componentes de Login
        JTextField nombreaux = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombreaux, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(aceptarLog, robot);
        robot.delay(TestUtils.getDelay());
    	JButton BCerrarSesion = (JButton) TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_CLIENTE);
        TestUtils.clickComponent(BCerrarSesion, robot);
       // Verificar si el panel 'Panel_Login' ha sido añadido a la ventana
          JPanel panelLogin = (JPanel) TestUtils.getComponentForName(ventana, Constantes.PANEL_LOGIN);
          Assert.assertNotNull("El panel Cliente no se desplegó correctamente", panelLogin);
    }
    
    @Test
    public void testCrearPedidoSinVehiculosNiChoferesDisponibles() {
        robot.delay(TestUtils.getDelay());
        
        // Registro del usuario Lauta
        JButton botonRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REGISTRAR);
        TestUtils.clickComponent(botonRegistrar, robot);

        JTextField nombreRegistro = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
        JTextField contrasenaRegistro = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
        JTextField confirmaContraRegistro = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
        JTextField nombreRealRegistro = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);
        JButton botonConfirmarRegistro = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);

        TestUtils.clickComponent(nombreRegistro, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contrasenaRegistro, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(confirmaContraRegistro, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(nombreRealRegistro, robot);
        TestUtils.tipeaTexto("Lautaro", robot);
        TestUtils.clickComponent(botonConfirmarRegistro, robot);

        // Loguear al usuario para acceder a la creación de pedidos
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Lauta", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("123456", robot);
        TestUtils.clickComponent(aceptarLog, robot);

        // Completar los campos necesarios para intentar crear un pedido
        JTextField cantPasajeros = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_PAX);
        JTextField cantKm = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANT_KM);
        JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_MASCOTA);
        JCheckBox checkBaul = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_BAUL);
        JButton botonNuevoPedido = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_PEDIDO);

        TestUtils.clickComponent(cantPasajeros, robot);
        TestUtils.tipeaTexto("3", robot);
        TestUtils.clickComponent(cantKm, robot);
        TestUtils.tipeaTexto("5", robot);
        TestUtils.clickComponent(checkMascota, robot);
        TestUtils.clickComponent(checkBaul, robot);
        
        // Intentar crear el pedido
        TestUtils.clickComponent(botonNuevoPedido, robot);

        // Verificar que se muestra el mensaje de error correspondiente
        Assert.assertEquals("Mensaje incorrecto", Mensajes.SIN_VEHICULO_PARA_PEDIDO.getValor(), op.getMensaje());
    }

}
