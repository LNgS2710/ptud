package custom_components;

import java.awt.*;
import javax.swing.*;

public class LogoBox extends JPanel {

    PictureBox logo;

    public LogoBox() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(80, 100));

        //create
        logo = new PictureBox();

        //add
        add(logo, BorderLayout.CENTER);

        //modify
        logo.setImage(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png")));

    }
}
