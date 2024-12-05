/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialog;

/**
 *
 * @author Thành Trung
 */
import dao.KhuyenMaiDao;
import entity.KhuyenMai;
import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class ThemKhuyenMaiDialog extends JDialog {

    private KhuyenMai khuyenMai;
    private KhuyenMaiDao khuyenMaiDAO;
    private JPanel pnlHeader, pnlContent, pnlButton;
    private JLabel lblHeader, lblMaKM, lblMoTa, lblChietKhau, lblTrangThai;
    private JTextField txtMaKM, txtMoTa, txtChietKhau;
    private JComboBox<String> cbTrangThai;
    private JButton btnLuu, btnThoat;

    private String[] trangThaiOptions = {"Hoạt động", "Hết hạn"};

    public ThemKhuyenMaiDialog() {
        khuyenMaiDAO = new KhuyenMaiDao();
        initComponents();
        initEvents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setModal(true);
        pnlHeader = new JPanel();
        pnlContent = new JPanel(new MigLayout("fillx, insets 20, wrap 2", "[150][-1, grow, fill]", "[]10[]"));
        pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 300));

        // Header
        lblHeader = new JLabel("Thêm khuyến mãi");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);

        // Labels
        lblMaKM = new JLabel("Mã khuyến mãi:");
        lblMoTa = new JLabel("Mô tả:");
        lblChietKhau = new JLabel("Chiết khấu (%):");
        lblTrangThai = new JLabel("Trạng thái:");

        // Input fields
        txtMaKM = new JTextField(20);
        txtMoTa = new JTextField(20);
        txtChietKhau = new JTextField(20);

        cbTrangThai = new JComboBox<>(trangThaiOptions);

        // Buttons
        btnLuu = new JButton("Lưu");
        btnThoat = new JButton("Thoát");

        // Header panel
        pnlHeader.setPreferredSize(new Dimension(500, 60));
        pnlHeader.setBackground(new Color(0x78B3CE));
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.white);
        pnlHeader.add(lblHeader);

        // Content panel
        pnlContent.add(lblMaKM);
        pnlContent.add(txtMaKM, "growx");

        pnlContent.add(lblMoTa);
        pnlContent.add(txtMoTa, "growx");

        pnlContent.add(lblChietKhau);
        pnlContent.add(txtChietKhau, "growx");

        pnlContent.add(lblTrangThai);
        pnlContent.add(cbTrangThai, "growx");

        // Button panel
        pnlButton.add(btnLuu);
        pnlButton.add(btnThoat);

        // Add panels to the dialog
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlContent, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
    }

    private void initEvents() {
        btnThoat.addActionListener(e -> thoatAction());

        btnLuu.addActionListener(e -> luuAction());
    }

    private boolean valid() {
        if (txtMaKM.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khuyến mãi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMoTa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double chietKhau = Double.parseDouble(txtChietKhau.getText().trim());
            if (chietKhau < 0 || chietKhau > 100) {
                JOptionPane.showMessageDialog(this, "Chiết khấu phải nằm trong khoảng từ 0 đến 100!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Chiết khấu phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void luuAction() {
        if (valid()) {
            String maKM = txtMaKM.getText().trim();
            String moTa = txtMoTa.getText().trim();
            double chietKhau = Double.parseDouble(txtChietKhau.getText().trim());
            String trangThai = (String) cbTrangThai.getSelectedItem();

            khuyenMai = new KhuyenMai();
            khuyenMai.setMaKM(maKM);
            khuyenMai.setMoTa(moTa);
            khuyenMai.setChietKhau((float) chietKhau);
            khuyenMai.setTrangThai(trangThai.equals("Hoạt động"));

            khuyenMaiDAO.themKhuyenMai(khuyenMai);

            JOptionPane.showMessageDialog(this, "Khuyến mãi đã được thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void thoatAction() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát mà không lưu?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}
