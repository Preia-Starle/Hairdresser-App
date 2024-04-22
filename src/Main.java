import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                Hairsalon_GUI appGUI = new Hairsalon_GUI();
                appGUI.createAndShowGUI();
            });
    }
}