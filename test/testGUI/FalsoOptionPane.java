package testGUI;


import vista.IOptionPane; // Para obtener el mensaje del option pane y comparar si es el correcto


public class FalsoOptionPane implements IOptionPane {
    private String mensaje = null;

    public FalsoOptionPane() {
        super();
    }

    @Override
    public void ShowMessage(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
