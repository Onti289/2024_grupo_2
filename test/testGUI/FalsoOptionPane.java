package testGUI;

import vista.IOptionPane;

public class FalsoOptionPane implements IOptionPane {
    private String mensaje = null;

    public FalsoOptionPane() {
        super();
    }

    @Override
    public void ShowMessage(String mensaje) {
        this.mensaje = mensaje; // Captura el mensaje mostrado
    }

    // Devuelve el último mensaje capturado
    public String getMensaje() {
        return mensaje;
    }

    // Limpia el mensaje capturado (opcional, útil para asegurar que las pruebas no reutilicen mensajes previos)
    public void clearMensaje() {
        this.mensaje = null;
    }
}
