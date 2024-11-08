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


public class TestDatos
{
    Robot robot;
    Controlador controlador;
    Ventana ventana;
    
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
    }

    @After
    public void tearDown() throws Exception
    {
        ventana.setVisible(false);
    }


    @Test
    public void testRegistroOk()
    {
        robot.delay(TestUtils.getDelay());
        /*ConjuntoUsuarios conjunto = controlador.getConjunto();
        int cantidad = conjunto.cantidadDeUsuarios();
        int cantidadactual; */
        
        //obtengo las referencias a los componentes necesarios
        JTextField nombre = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_USSER_NAME);
        JTextField contra =(JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_PASSWORD);
        JTextField confirmaContra =(JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_CONFIRM_PASSWORD);
        JTextField nombreReal = (JTextField) TestUtils.getComponentForName(ventana, Constantes.REG_REAL_NAME);
        JButton Bregistrar = (JButton) TestUtils.getComponentForName(ventana, Constantes.REG_BUTTON_REGISTRAR);
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
        TestUtils.clickComponent(Bregistrar, robot);
        cantidadactual = conjunto.cantidadDeUsuarios();
        //verifico los resultados
        Assert.assertEquals("Deberia haber un elemento mas que antes", cantidad + 1, cantidadactual);
        Assert.assertEquals("Mensaje incorrecto, deberia decir"+Mensajes.USUARIO_REGISTRADO.getValor(),Mensajes.USUARIO_REGISTRADO.getValor(),op.getMensaje());
    }



}
