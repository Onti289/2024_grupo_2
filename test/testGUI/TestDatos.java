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
import testGUI.TestUtils;
import util.Constantes;
import util.Mensajes;
import vista.Ventana;


public class TestDatos
{
    Robot robot;
    Controlador controlador;
    Ventana ventana;
    Empresa empresa = Empresa.getInstance();
    FalsoOptionPane op = new FalsoOptionPane();
    
    public TestDatos()
    {
        try
        {
            robot = new Robot();
        } catch (AWTException e)
        {
        }
    }

    @Before
    public void setUp() throws Exception
    {
        controlador = new Controlador();
        ventana = new Ventana();
        ventana.setOptionPane(op);
        empresa.agregarCliente("Manu", "123456", "manuel");
        empresa.agregarCliente("Lauti", "123456", "Lautaro");
        empresa.agregarCliente("Luken", "123456", "Lucas");
    }

    @After
    public void tearDown() throws Exception
    {
        ventana.setVisible(false);
    }
    
    

    //@Test
    //public void testLogOk()
    //{
        //robot.delay(TestUtils.getDelay());
        //obtengo las referencias a los componentes necesarios
        //JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        //JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        //JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        //lleno los JTextField
        //TestUtils.clickComponent(nombre, robot);
        //TestUtils.tipeaTexto("Agus", robot);
        //TestUtils.clickComponent(contrasena, robot);
        //TestUtils.tipeaTexto("Asd123", robot);
        //TestUtils.clickComponent(aceptarLog, robot);
        //verifico los resultados
        
        //Assert.assertEquals("Deberia coincidir el nombre de usuario con el nombre ingresado", "juan",controlador.getUsuarioactual().getNombre());
        //Assert.assertEquals("Memnsaje incorrecto, deber�a decir"+Mensajes.LOGIN_OK.getValor(),Mensajes.LOGIN_OK.getValor(),op.getMensaje());
    //}
    
    @Test
    public void testRegistroOk()
    {
        robot.delay(TestUtils.getDelay());
        int cantidad = empresa.getClientes().size();
        int cantidadactual; 
        
        //obtengo las referencias a los componentes necesarios
        JButton BRegistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REGISTRAR);
        TestUtils.clickComponent(BRegistrar, robot);
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
        JTextField contra =(JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
        JTextField confirmaContra =(JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
        JTextField nombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);
        JButton Bregistrar2 = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
        JButton Bcancelar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_CANCELAR);
        
        //lleno los JTextField
        TestUtils.clickComponent(nombre, robot);
        TestUtils.tipeaTexto("Agus", robot);
        TestUtils.clickComponent(contra, robot);
        TestUtils.tipeaTexto("Asd123", robot);
        TestUtils.clickComponent(confirmaContra, robot);
        TestUtils.tipeaTexto("Asd123", robot);
        TestUtils.clickComponent(nombreReal, robot);
        TestUtils.tipeaTexto("Agustin", robot);
        TestUtils.clickComponent(Bregistrar2, robot);
        
        
        //JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        //JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        //JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);

        JTextField nombre1 = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_USUARIO);
        JTextField contrasena = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PASSWORD);
        JButton aceptarLog = (JButton) TestUtils.getComponentForName(ventana, Constantes.LOGIN);
        
        TestUtils.clickComponent(nombre1, robot);
        TestUtils.tipeaTexto("Agus", robot);
        TestUtils.clickComponent(contrasena, robot);
        TestUtils.tipeaTexto("Asd123", robot);
        TestUtils.clickComponent(aceptarLog, robot);
        cantidadactual = empresa.getClientes().size();

        Assert.assertEquals("Deberia haber un elemento mas que antes", cantidad + 1, cantidadactual);
        //Assert.assertEquals("Mensaje incorrecto, deberia decir"+Mensajes.USUARIO_REGISTRADO.getValor(),Mensajes.USUARIO_REGISTRADO.getValor(),op.getMensaje());
    }



}
