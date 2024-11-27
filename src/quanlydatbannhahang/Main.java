package quanlydatbannhahang;

import frame.DangNhapJFrame;
import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DangNhapJFrame().setVisible(true);
        });
    }
    
}
