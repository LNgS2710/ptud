package frame;

import panel.QuanLyDatBanForm;
import java.awt.Color;
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
import dialog.TimKiemPhieuDatBanCho;
import entity.NhanVien;
import panel.DanhSachBanJPanel;

public class MainFrame extends JFrame {

    //define
    Menu menu;
    JPanel pnlBase, pnlMenu, pnlBody, pnlBot;
    JLabel lbltenNV;
    public static NhanVien nhanVien;

    public MainFrame(NhanVien nhanVien) {
        //JFrame settings
        this.nhanVien = nhanVien;
        setSize(1215, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //create
        menu = new Menu();
        pnlBase = new JPanel();
        pnlMenu = new JPanel();
        pnlBody = new JPanel();
        pnlBot = new JPanel();

        //modify
        pnlBase.setLayout(new BorderLayout());
        pnlMenu.setLayout(new BorderLayout());
        pnlBody.setLayout(new BorderLayout());
        pnlBot.setLayout(new BorderLayout());
        
        pnlMenu.setPreferredSize(new Dimension(220, 700));
        pnlBody.setPreferredSize(new Dimension(980, 700));
        pnlBot.setBackground(new Color(0x78B3CE));
        //add        
        add(pnlBase, BorderLayout.CENTER);
        pnlMenu.add(menu);
        pnlBase.add(pnlMenu, BorderLayout.WEST);
        pnlBase.add(pnlBody, BorderLayout.EAST);

        lbltenNV = new JLabel("NV: Nguyễn Thành Trung");
        lbltenNV.setHorizontalAlignment(SwingConstants.LEFT);
        lbltenNV.setFont(new Font("SansSerif", Font.BOLD,16));
        lbltenNV.setForeground(new Color(0xFBF8EF));
        
        if(nhanVien != null){
            if(nhanVien.getChucVu().trim().equalsIgnoreCase("Quản lý")){
                lbltenNV.setText("QL: " + nhanVien.getTenNV());
            }else{
                lbltenNV.setText("NV: " + nhanVien.getTenNV());
            }
        }
        
        pnlBot.add(lbltenNV, BorderLayout.WEST);
        pnlMenu.add(pnlBot, BorderLayout.NORTH);
        
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
                if(index == 1 && subIndex == 1) showForm(new TimKiemPhieuDatBanCho());
                if(index == 1 && subIndex == 2) showForm(new DanhSachBanJPanel());
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
        new MainFrame(nhanVien).setVisible(true);
    }
}
