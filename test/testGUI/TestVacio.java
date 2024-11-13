package testGUI;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controlador.Controlador;
import modeloDatos.Chofer;
import modeloDatos.Vehiculo;
import modeloNegocio.Empresa;
import testPersistencia.TestPersistenciaEmpresa;
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
    
    
    @Test
    public void testCantidad()
    {
        Assert.assertEquals("Debe haber 0 clientes registrados", 0, empresa.getClientes().size());
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
    
    // PANEL ADMIN
    // VISUALIZACIÓN DE INFORMACIÓN
    @Test
    public void testPanelVacioAdmin(){
    	
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
        
        JTextField dniChofer = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
        Assert.assertTrue("El campo DNI del chofer debería estar vacío", dniChofer.getText().isEmpty());

        JTextField nombreChofer = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
        Assert.assertTrue("El campo nombre del chofer debería estar vacío", nombreChofer.getText().isEmpty());

        JRadioButton permanente = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.PERMANENTE);
        Assert.assertTrue("El botón de selección Permanente debería estar seleccionado", permanente.isSelected());

        JRadioButton temporario = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.TEMPORARIO);
        Assert.assertFalse("El botón de selección Temporario debería estar deseleccionado", temporario.isSelected());

        JTextField chCantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
        Assert.assertTrue("El campo cantidad de hijos debería estar vacío", chCantHijos.getText().isEmpty());

        JTextField chAnio = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
        Assert.assertTrue("El campo año del chofer debería estar vacío", chAnio.getText().isEmpty());

        JButton nuevoChofer = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);
        Assert.assertFalse("El botón Nuevo Chofer debería estar deshabilitado", nuevoChofer.isEnabled());

        JTextField patente = (JTextField) TestUtils.getComponentForName(ventana, Constantes.PATENTE);
        Assert.assertTrue("El campo patente debería estar vacío", patente.getText().isEmpty());

        JRadioButton auto = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.AUTO);
        Assert.assertTrue("El botón de selección Auto debería estar seleccionado", auto.isSelected());

        JRadioButton combi = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.COMBI);
        Assert.assertFalse("El botón de selección Combi debería estar deseleccionado", combi.isSelected());

        JRadioButton moto = (JRadioButton) TestUtils.getComponentForName(ventana, Constantes.MOTO);
        Assert.assertFalse("El botón de selección Moto debería estar deseleccionado", moto.isSelected());

        JCheckBox checkMascota = (JCheckBox) TestUtils.getComponentForName(ventana, Constantes.CHECK_VEHICULO_ACEPTA_MASCOTA);
        Assert.assertFalse("El checkbox acepta mascota debería estar deseleccionado", checkMascota.isSelected());

        JTextField cantidadPlazas = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CANTIDAD_PLAZAS);
        Assert.assertTrue("El campo cantidad de plazas debería estar vacío", cantidadPlazas.getText().isEmpty());

        JTextField totalSueldosAPagar = (JTextField) TestUtils.getComponentForName(ventana, Constantes.TOTAL_SUELDOS_A_PAGAR);
        Assert.assertFalse("El campo total sueldos a pagar no debería estar vacío (0,00)", totalSueldosAPagar.getText().isEmpty());

        JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);
        Assert.assertFalse("El botón Nuevo Vehículo debería estar deshabilitado", nuevoVehiculo.isEnabled());

        JList listaPedidosPendientes = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_PEDIDOS_PENDIENTES);
        Assert.assertEquals("La lista de pedidos pendientes debería estar vacía", 0, listaPedidosPendientes.getModel().getSize());

        JList listaVehiculosDisponibles = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VEHICULOS_DISPONIBLES);
        Assert.assertEquals("La lista de vehículos disponibles debería estar vacía", 0, listaVehiculosDisponibles.getModel().getSize());

        JList listaChoferesLibres = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_CHOFERES_LIBRES);
        Assert.assertEquals("La lista de choferes libres debería estar vacía", 0, listaChoferesLibres.getModel().getSize());

        JList listaChoferesTotales = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_CHOFERES_TOTALES);
        Assert.assertEquals("La lista de choferes totales debería estar vacía", 0, listaChoferesTotales.getModel().getSize());

        JList listaViajesDeChofer = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VIAJES_DE_CHOFER);
        Assert.assertEquals("La lista de viajes del chofer debería estar vacía", 0, listaViajesDeChofer.getModel().getSize());

        JTextField calificacionChofer = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CALIFICACION_CHOFER);
        Assert.assertTrue("El campo calificación del chofer debería estar vacío", calificacionChofer.getText().isEmpty());

        JTextField sueldoDeChofer = (JTextField) TestUtils.getComponentForName(ventana, Constantes.SUELDO_DE_CHOFER);
        Assert.assertTrue("El campo sueldo del chofer debería estar vacío", sueldoDeChofer.getText().isEmpty());

        JList listadoDeClientes = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTADO_DE_CLIENTES);
        Assert.assertEquals("La lista de clientes debería estar vacía", 0, listadoDeClientes.getModel().getSize());

        JList listaVehiculosTotales = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VEHICULOS_TOTALES);
        Assert.assertEquals("La lista de vehículos totales debería estar vacía", 0, listaVehiculosTotales.getModel().getSize());

        JList listaViajesHistoricos = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VIAJES_HISTORICOS);
        Assert.assertEquals("La lista de viajes históricos debería estar vacía", 0, listaViajesHistoricos.getModel().getSize());

        JButton nuevoViaje = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VIAJE);
        Assert.assertFalse("El botón Nuevo Viaje debería estar deshabilitado", nuevoViaje.isEnabled());

        JButton cerrarSesionAdmin = (JButton) TestUtils.getComponentForName(ventana, Constantes.CERRAR_SESION_ADMIN);
        Assert.assertTrue("El botón Cerrar Sesión debería estar habilitado", cerrarSesionAdmin.isEnabled());

    }
    
    //Registro de un nuevo chofer
    @Test
    public void testRegistrarChoferNuevoVacio() {
    	
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
        
        // Configurar los campos con un DNI no registrado
        JTextField dniField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.DNI_CHOFER);
        JTextField nombreField = (JTextField) TestUtils.getComponentForName(ventana, Constantes.NOMBRE_CHOFER);
        JTextField cantHijos = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_CANT_HIJOS);
        JTextField anioIngreso = (JTextField) TestUtils.getComponentForName(ventana, Constantes.CH_ANIO);
        JButton nuevoChoferButton = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_CHOFER);

        TestUtils.clickComponent(nuevoChoferButton, robot);
        TestUtils.clickComponent(dniField, robot);
        TestUtils.tipeaTexto("111", robot);  
        TestUtils.clickComponent(nombreField, robot);
        TestUtils.tipeaTexto("Edgardo", robot);
        TestUtils.clickComponent(cantHijos, robot);
        TestUtils.tipeaTexto("2", robot);  
        TestUtils.clickComponent(anioIngreso, robot);
        TestUtils.tipeaTexto("2020", robot);  
        TestUtils.clickComponent(nuevoChoferButton, robot);
        
        // Verificar que el chofer se agregó a la lista
        JList listaChoferes = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_CHOFERES_TOTALES);

        Assert.assertTrue("Debe haber un chofer en la lista",listaChoferes.getModel().getSize() == 1);
        
        ListModel choferesModel = listaChoferes.getModel();
        boolean choferEncontrado = false;
        for (int i = 0; i < choferesModel.getSize(); i++) {
            if (((Chofer) choferesModel.getElementAt(i)).getDni().equals("111")) {
                choferEncontrado = true;
            }
        }
        Assert.assertTrue("El chofer debería estar registrado en la lista", choferEncontrado);

        // Verificar que los campos de texto están vacíos
        Assert.assertTrue("El campo DNI debería estar vacío", dniField.getText().isEmpty());
        Assert.assertTrue("El campo Nombre debería estar vacío", nombreField.getText().isEmpty());
    }

 // Registro de un nuevo vehiculo
    @Test
    public void testRegistrarVehiculoVacio() {
    	
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
        JButton nuevoVehiculo = (JButton) TestUtils.getComponentForName(ventana, Constantes.NUEVO_VEHICULO);

        // Llenar los campos para un login correcto
        TestUtils.clickComponent(combi, robot);
        TestUtils.clickComponent(patente, robot);
        TestUtils.tipeaTexto("JJJ333", robot);
        TestUtils.clickComponent(cantPlazas, robot);
        TestUtils.tipeaTexto("6", robot);
        TestUtils.clickComponent(nuevoVehiculo, robot);

        // Verificar que el vehiculo se agregó a la lista
        
        JList listaVehiculos = (JList) TestUtils.getComponentForName(ventana, Constantes.LISTA_VEHICULOS_TOTALES);
        ListModel vehiculoModel = listaVehiculos.getModel();
        
        Assert.assertTrue("Debe haber un vehiculo en la lista",listaVehiculos.getModel().getSize() == 1);
        
        boolean VehiculoEncontrado = false;
        for (int i = 0; i < vehiculoModel.getSize(); i++) {
            if (((Vehiculo) vehiculoModel.getElementAt(i)).getPatente().equals("JJJ333")) {
                VehiculoEncontrado = true;
            }
        }
        Assert.assertTrue("El vehiculo debería estar registrado en la lista", VehiculoEncontrado);

        // Verificar que los campos de texto están vacíos
        Assert.assertTrue("El campo patente debería estar vacío", patente.getText().isEmpty());
        Assert.assertTrue("El campo Cantidad plazas debería estar vacío", cantPlazas.getText().isEmpty());
    }







}
