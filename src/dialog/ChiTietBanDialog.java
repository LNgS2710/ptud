package dialog;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.BanDAO;
import entity.Ban;
import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class ChiTietBanDialog extends JDialog {

    private JPanel pnlHeader, pnlCenter, pnlFooter;
    private JLabel lblHeader, lblMa, lblViTri, lblSoCho, lblTrangThai;
    private JTextField txtMa;
    private JComboBox<String> cbViTri;
    private String[] cbItems = {"Trong nhà", "Ngoài trời"};
    private JSpinner spinnerSoCho;
    private JToggleButton btnTrangThai;
    private JButton btnThoat, btnLuu;
    private Ban ban;

    public ChiTietBanDialog(JFrame parent) {
        super(parent, "Chi Tiết Món Ăn", true);
        initComponents();
        initListeners();
    }

    public ChiTietBanDialog(JFrame parent, Ban ban) {
        super(parent, "Sửa Bàn", true);
        this.ban = ban; // This will hold the Ban to be edited
        initComponents();
        populateFields(ban); // Populate fields with the Ban data
        initListeners();
    }

    private void initComponents() {
        FlatLightLaf.setup();
        setPreferredSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCenter = new JPanel(new MigLayout("fillx, insets 20, gapx 10, gapy 15"));
        pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        lblHeader = new JLabel("Chi tiết bàn");
        lblMa = new JLabel("Mã bàn:");
        lblViTri = new JLabel("Vị trí:");
        lblSoCho = new JLabel("Số chỗ");
        lblTrangThai = new JLabel("Trạng thái");

        txtMa = new JTextField(20);

        spinnerSoCho = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        cbViTri = new JComboBox<>(cbItems);
        btnTrangThai = new JToggleButton("Trống");

        btnLuu = new JButton("Lưu");
        btnThoat = new JButton("Thoát");

        pnlHeader.setPreferredSize(new Dimension(600, 60));
        pnlHeader.setBackground(Color.orange);
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.white);
        lblHeader.setIcon(new FlatSVGIcon("svg/table.svg"));

        btnLuu.setBackground(Color.orange);
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderPainted(false);

        btnThoat.setBackground(Color.LIGHT_GRAY);
        btnThoat.setForeground(Color.BLACK);

        pnlHeader.add(lblHeader);

        pnlCenter.add(lblMa, "cell 0 1");
        pnlCenter.add(txtMa, "cell 1 1");

        pnlCenter.add(lblViTri, "cell 0 2");
        pnlCenter.add(cbViTri, "cell 1 2");

        pnlCenter.add(lblSoCho, "cell 0 3");
        pnlCenter.add(spinnerSoCho, "cell 1 3");

        pnlCenter.add(lblTrangThai, "cell 0 4");
        pnlCenter.add(btnTrangThai, "cell 1 4");

        pnlFooter.add(btnThoat);
        pnlFooter.add(btnLuu);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlFooter, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void populateFields(Ban ban) {
        txtMa.setText(ban.getMaBan());
        cbViTri.setSelectedItem(ban.getViTri());
        spinnerSoCho.setValue(ban.getSoCho());
        btnTrangThai.setSelected(ban.isTrangThai());
        if (ban.isTrangThai()) {
            btnTrangThai.setText("Đã Đặt");
        } else {
            btnTrangThai.setText("Trống");
        }
        txtMa.setEnabled(false); // Disable editing of ma in Edit mode
    }

    private void themBan() {
        String ma = txtMa.getText();
        String viTri = (String) cbViTri.getSelectedItem();
        int soCho = (int) spinnerSoCho.getValue();
        boolean trangThai = btnTrangThai.isSelected();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã bàn không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Ban newBan = new Ban(ma, viTri, soCho, trangThai);
        boolean success = BanDAO.insertBan(newBan);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm bàn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm bàn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaBan() {
        String ma = ban.getMa();
        String viTri = (String) cbViTri.getSelectedItem();
        int soCho = (int) spinnerSoCho.getValue();
        boolean trangThai = btnTrangThai.isSelected();

        ban.setViTri(viTri);
        ban.setSoCho(soCho);
        ban.setTrangThai(trangThai);

        boolean success = BanDAO.updateBan(ban);
        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật bàn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật bàn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initListeners() {
        // Action for closing the dialog
        btnThoat.addActionListener(e -> dispose());

        // Action for saving the data (Add or Edit)
        btnLuu.addActionListener(e -> {
            if (ban == null) { // Add mode
                themBan();
            } else { // Edit mode
                suaBan();
            }
        });

        btnTrangThai.addActionListener(e -> {
            if (btnTrangThai.isSelected()) {
                btnTrangThai.setText("Đã Đặt");
            } else {
                btnTrangThai.setText("Trống");
            }
        });
    }
}
