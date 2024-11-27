package panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.ui.FlatLineBorder;
import dao.DangNhapDAO;
import entity.NhanVien;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;
import frame.MainFrame;
import javax.swing.JOptionPane;

public class DangNhapJPanel extends JPanel {

    private JLabel lblTieuDe, lblNgonNgu;
    private JTextField txtMaNhanVien;
    private JPasswordField pwMatKhau;
    private JButton btnDangNhap;
    private ResourceBundle bundle;
    private JComboBox<String> cbNgonNgu;
    private DangNhapDAO dangNhapDAO = new DangNhapDAO();

    public DangNhapJPanel() {
        init();
        initEvent();
    }

    private void init() {
        FlatArcOrangeIJTheme.setup();
        loadLocale(new Locale("vi"));
        setBackground(Color.white);
        setPreferredSize(new Dimension(300, 270));
        setLayout(new MigLayout());
        putClientProperty("JComponent.roundRect", true);
        setBorder(new FlatLineBorder(new Insets(16, 16, 16, 16), Color.lightGray, 2f, 8));

        lblTieuDe = new JLabel(bundle.getString("lblTieuDe"));
        lblTieuDe.setFont(new Font("Roboto", Font.BOLD, 30));
        lblTieuDe.setForeground(UIManager.getColor("Component.accentColor"));
        add(lblTieuDe, "gapbottom 30, wrap, align left");

        txtMaNhanVien = new JTextField();
        txtMaNhanVien.setFont(new Font("Roboto", Font.PLAIN, 16));
        UIManager.put("TextField.underlinedStyle", true);
        txtMaNhanVien.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, bundle.getString("txtMaNhanVien"));
        txtMaNhanVien.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        add(txtMaNhanVien, "grow, push, gapbottom 10, wrap, height 40:40:");

        pwMatKhau = new JPasswordField();
        pwMatKhau.setFont(new Font("Roboto", Font.PLAIN, 16));
        pwMatKhau.putClientProperty("JComponent.outline", "underline");
        pwMatKhau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, bundle.getString("pwMatKhau"));
        pwMatKhau.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
        add(pwMatKhau, "grow, push, gapbottom 20, wrap, height 40:40:");

        btnDangNhap = new JButton(bundle.getString("btnDangNhap"));
        btnDangNhap.setFont(new Font("Roboto", Font.BOLD, 18));
        btnDangNhap.putClientProperty(FlatClientProperties.STYLE,
                "arc: 8;");
        btnDangNhap.setForeground(UIManager.getColor("Component.accentColor"));
        btnDangNhap.setBackground(Color.white);
        btnDangNhap.setBorder(new FlatLineBorder(new Insets(16, 16, 16, 16), UIManager.getColor("Component.accentColor"), 2f, 8));
        add(btnDangNhap, "grow, push, gapbottom 20, wrap, height 40:40:55");

        lblNgonNgu = new JLabel(bundle.getString("lblNgonNgu"));
        lblNgonNgu.setFont(new Font("Roboto", Font.PLAIN, 14));
        add(lblNgonNgu, "split 2, align left");
        cbNgonNgu = new JComboBox<>(new String[]{bundle.getString("cb1"), bundle.getString("cb2")});
        cbNgonNgu.setFont(new Font("Roboto", Font.PLAIN, 14));
        add(cbNgonNgu, "grow, push, height 30:30:40");
        
        
    }

    public void loadLocale(Locale locale) {
        String baseName = "properties/dangnhap";
        bundle = ResourceBundle.getBundle(baseName, locale);
    }

    public void changeLanguage(Locale newLocale) {
        loadLocale(newLocale);
        lblTieuDe.setText(bundle.getString("lblTieuDe"));
        txtMaNhanVien.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, bundle.getString("txtMaNhanVien"));
        pwMatKhau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, bundle.getString("pwMatKhau"));
        btnDangNhap.setText(bundle.getString("btnDangNhap"));
        lblNgonNgu.setText(bundle.getString("lblNgonNgu"));
        cbNgonNgu.removeAllItems();
        cbNgonNgu.addItem(bundle.getString("cb1"));
        cbNgonNgu.addItem(bundle.getString("cb2"));
        revalidate();
        repaint();
    }

    private void initEvent() {
        cbNgonNgu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbNgonNgu.getSelectedItem();
                Locale newLocale;
                if (cbNgonNgu.getSelectedIndex() == 1) {
                    newLocale = new Locale("en");
                } else {
                    newLocale = new Locale("vi");
                }
                changeLanguage(newLocale);
            }
        });
         btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = e.getSource();
                if(o == btnDangNhap){
                    if (txtMaNhanVien.getText().length() == 10) {
                        NhanVien quanLy = dangNhapDAO.getNhanVienDangNhap(txtMaNhanVien.getText());
                        if (quanLy != null && pwMatKhau.getText().equals(quanLy.getMatKhau())) {
                                if (quanLy.getChucVu().equals("Nhân viên") || quanLy.getChucVu().equals("Quản lý")) {
                                        setVisible(false);
                                        new MainFrame(quanLy).setVisible(true);
                                } else {
                                        JOptionPane.showMessageDialog(null, "Bạn không còn quyền truy cập");
                                        }
                                } else
                                        JOptionPane.showMessageDialog(null, "Số điện thoại hoặc mật khẩu không chính xác");
                        } else {
                                JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại thật");
                        }
                        }
                    }
            
           });
    }


}
