import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            VentanaTalentoHumano ventana =
                    new VentanaTalentoHumano();

            ventana.setVisible(true);
        });
    }
}