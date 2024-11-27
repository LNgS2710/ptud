package dialog;

import dao.NhanVienDAO;
import entity.NhanVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChiTietNhanVienDialog extends JDialog {
    private JTextField txtMa;
    private JTextField txtTen;
    private JTextField txtEmail;
    private JTextField txtSoDienThoai;
    private JCheckBox chkTrangThai;
    private JButton btnSave;
    private JButton btnCancel;
    private NhanVien nhanVien;
    private NhanVienDAO nhanVienDAO;

    public ChiTietNhanVienDialog(Frame parent, NhanVien nhanVien) {
        super(parent, "Chi tiết nhân viên", true);
        this.nhanVien = nhanVien;
        nhanVienDAO = new NhanVienDAO();
        initComponents();
        setLocationRelativeTo(parent);
        if (nhanVien != null) {
            loadData();
        }
    }

    private void initComponents() {
        setLayout(new GridLayout(6, 2, 5, 5));

        // Labels and TextFields
        add(new JLabel("Mã nhân viên:"));
        txtMa = new JTextField();
        txtMa.setEnabled(nhanVien == null); // Disable if editing
        add(txtMa);

        add(new JLabel("Tên nhân viên:"));
        txtTen = new JTextField();
        add(txtTen);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Số điện thoại:"));
        txtSoDienThoai = new JTextField();
        add(txtSoDienThoai);

        add(new JLabel("Trạng thái:"));
        chkTrangThai = new JCheckBox();
        add(chkTrangThai);

        // Buttons
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
    }

    private void loadData() {
        txtMa.setText(nhanVien.getMa());
        txtTen.setText(nhanVien.getTen());
        txtEmail.setText(nhanVien.getEmail());
        txtSoDienThoai.setText(nhanVien.getSoDienThoai());
        chkTrangThai.setSelected(nhanVien.isTrangThai());
    }

    private void handleSave() {
        if (validateInputs()) {
            if (nhanVien == null) {
                nhanVien = new NhanVien(txtMa.getText().trim(), txtTen.getText().trim(), 
                                         txtEmail.getText().trim(), txtSoDienThoai.getText().trim(), 
                                         chkTrangThai.isSelected());
                nhanVienDAO.insertNhanVien(nhanVien);
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                nhanVien.setTen(txtTen.getText().trim());
                nhanVien.setEmail(txtEmail.getText().trim());
                nhanVien.setSoDienThoai(txtSoDienThoai.getText().trim());
                nhanVien.setTrangThai(chkTrangThai.isSelected());
                nhanVienDAO.updateNhanVien(nhanVien);
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        }
    }

    private boolean validateInputs() {
        if (txtMa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
