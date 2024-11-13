package testGUI;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
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

public class TestEnabledDisabled {
    private Robot robot;
    private Controlador controlador;
    private Ventana ventana;
    private Empresa empresa = Empresa.getInstance();
    private FalsoOptionPane op = new FalsoOptionPane();

    public TestEnabledDisabled() {
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
    
    @Test
    public void testCaracterNombreyPassLogin() {
    	robot.delay(TestUtils.getDelay());
        
        // Obtener componentes de Login
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
   

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("a", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("a", robot);

        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);
       
        Assert.assertTrue("Deberia estar disponible el boton login", aceptarLog.isEnabled());
    }
    

    @Test
    public void testCaracterRegistro() {

        robot.delay(TestUtils.getDelay());

        JButton BRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REGISTRAR);
        TestUtils.clickComponent(BRegistrar, robot);

        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
        JTextField contra = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
        JTextField confirmaContra = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
        JTextField nombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);
        
        
        // Llenar el formulario de registro
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("a", robot);
        TestUtils.clickComponent(contra, robot);
        TestUtils.tipeaTexto("a", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("a", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("a", robot);

        JButton Bregistrar2 = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
        Assert.assertTrue("Deberia estar disponible el boton login", Bregistrar2.isEnabled());
        
    }

    // Registro de un nuevo chofer
    
    @Test
    public void testCaracterChoferPermanente() {
    	robot.delay(TestUtils.getDelay());
    	
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
        
        robot.delay(TestUtils.getDelay());
        JRadioButton botonChoferPermanente = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.PERMANENTE);
        
        // Configurar los campos con un DNI no registrado
        TestUtils.clickComponent(botonChoferPermanente, robot);
        JTextField dniField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
        JTextField nombreField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
        JTextField cantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
        JTextField anioIngreso = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
        

        TestUtils.clickComponent(dniField, robot);
        TestUtils.tipeaTexto("1", robot); 
        TestUtils.clickComponent(nombreField, robot);
        TestUtils.tipeaTexto("a", robot);
        TestUtils.clickComponent(cantHijos, robot);
        TestUtils.tipeaTexto("1", robot);  
        TestUtils.clickComponent(anioIngreso, robot);
        TestUtils.tipeaTexto("2000", robot);  
        
        JButton nuevoChoferButton = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);
        Assert.assertTrue("Deberia estar disponible el boton nuevoChofer", nuevoChoferButton.isEnabled());

    }

    @Test
    public void testCaracterChoferTemporario() {
    	robot.delay(TestUtils.getDelay());
    	
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
        
        robot.delay(TestUtils.getDelay());
        JRadioButton botonChoferTemporario = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.TEMPORARIO);
        
        // Configurar los campos con un DNI no registrado
        TestUtils.clickComponent(botonChoferTemporario, robot);
        JTextField dniField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
        JTextField nombreField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);


        TestUtils.clickComponent(dniField, robot);
        TestUtils.tipeaTexto("1", robot); 
        TestUtils.clickComponent(nombreField, robot);
        TestUtils.tipeaTexto("a", robot);
        
        JButton nuevoChoferButton = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);
        Assert.assertTrue("Deberia estar disponible el boton nuevoChofer", nuevoChoferButton.isEnabled());

    }

    // Registro de un nuevo vehiculo
    
    @Test
    public void testCaracterMoto() {
    	
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
        
    	// Obtener componentes de Login
        JTextField patente = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PATENTE);
        JRadioButton moto = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.MOTO);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(patente, robot);
        TestUtils.tipeaTexto("J", robot);
        TestUtils.clickComponent(moto, robot);
        
        JButton nuevoVehiculoButton = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);
        Assert.assertTrue("Deberia estar disponible el boton nuevoChofer", nuevoVehiculoButton.isEnabled());
    }

    @Test
    public void testCaracterAuto() {
    	
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
        
    	// Obtener componentes de Login
        JTextField patente = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PATENTE);
        JTextField cantPlazas = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANTIDAD_PLAZAS);
        JRadioButton auto = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.AUTO);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(auto, robot);
        TestUtils.clickComponent(patente, robot);
        TestUtils.tipeaTexto("J", robot);
        TestUtils.clickComponent(cantPlazas, robot);
        TestUtils.tipeaTexto("2", robot);
        
        JButton nuevoVehiculoButton = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);
        Assert.assertTrue("Deberia estar disponible el boton nuevoChofer", nuevoVehiculoButton.isEnabled());
    }

    @Test
    public void testCaracterCombi() {
    	
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
        
    	// Obtener componentes de Login
        JTextField patente = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PATENTE);
        JTextField cantPlazas = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANTIDAD_PLAZAS);
        JRadioButton combi = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.COMBI);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(combi, robot);
        TestUtils.clickComponent(patente, robot);
        TestUtils.tipeaTexto("J", robot);
        TestUtils.clickComponent(cantPlazas, robot);
        TestUtils.tipeaTexto("6", robot);
        
        JButton nuevoVehiculoButton = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);
        Assert.assertTrue("Deberia estar disponible el boton nuevoChofer", nuevoVehiculoButton.isEnabled());
    }
}


