package frame;

import panel.QuanLyDatBanForm;
import panel.HomeForm;
import panel.QuanLyBanForm;
import panel.QuanLyThucDonForm;
import panel.QuanLyKhachHangForm;
import panel.QuanLyNhanVienForm;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import java.awt.*;
import javax.swing.*;
import custom_components.menu.Menu;
import custom_components.menu.MenuEvent;

public class MainFrame extends JFrame {

    //define
    Menu menu;
    JPanel pnlBase, pnlMenu, pnlBody;

    public MainFrame() {
        //JFrame settings
        setSize(1215, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //create
        menu = new Menu();
        pnlBase = new JPanel();
        pnlMenu = new JPanel();
        pnlBody = new JPanel();

        //modify
        pnlBase.setLayout(new BorderLayout());
        pnlMenu.setLayout(new BorderLayout());
        pnlBody.setLayout(new BorderLayout());
        pnlMenu.setPreferredSize(new Dimension(220, 700));
        pnlBody.setPreferredSize(new Dimension(980, 700));
        //add        
        add(pnlBase, BorderLayout.CENTER);
        pnlMenu.add(menu);
        pnlBase.add(pnlMenu, BorderLayout.WEST);
        pnlBase.add(pnlBody, BorderLayout.EAST);

        

        //events
        showForm(new HomeForm());
        menu.setEvent(new MenuEvent() {
            @Override
            public void selected(int index, int subIndex) {
                if (index == 0) {
                    showForm(new HomeForm());
                    System.out.println(pnlBody.getWidth() + " " + pnlBody.getHeight());
                    System.out.println(menu.getWidth() + " " + menu.getHeight());
                }
                if(index == 1 && subIndex == 1) showForm(new QuanLyDatBanForm());
                if(index == 1 && subIndex == 2) showForm(new QuanLyBanForm());
                if(index == 2 && subIndex == 2) showForm(new QuanLyThucDonForm());
                if(index == 4 && subIndex == 1) showForm(new QuanLyNhanVienForm());
                if(index == 5 && subIndex == 1) showForm(new QuanLyKhachHangForm());
            }

        });
        
    }

    private void showForm(Component com) {
        pnlBody.removeAll();
        pnlBody.add(com, BorderLayout.CENTER);
        pnlBody.repaint();
        pnlBody.revalidate();
    }

    public static void main(String args[]) {
        FlatLightFlatIJTheme.setup();
        new MainFrame().setVisible(true);
    }
}
