package dialog;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.MonAnDAO;
import entity.MonAn;
import panel.QuanLyThucDonForm;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class TimKiemMonDialog extends JDialog {

    JPanel pnlHeader, pnlCenter, pnlFooter;
    JLabel lblHeader, lblTen, lblMa, lblLoai, lblGia, lblTrangThai;
    JTextField txtTen, txtMa, txtGia;
    JComboBox<String> cbLoai;
    JToggleButton btnTrangThai;
    JButton btnThoat, btnTim;
    String[] cbItems = {"Khai vị", "Món chính", "Nước uống", "Tráng miệng"};
    private DefaultTableModel tableModel;

    public TimKiemMonDialog(JFrame parent, DefaultTableModel tableModel) {
        super(parent, "Tìm Kiếm Món Ăn", true);
        this.tableModel = tableModel;
        initComponents();
    }

    private void initComponents() {
        FlatLightLaf.setup(); // Set up FlatLaf theme

        setPreferredSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCenter = new JPanel(new MigLayout("fillx, insets 20, gapx 10, gapy 15", "[right][fill,250]"));
        pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        lblHeader = new JLabel("Tìm kiếm món ăn");
        lblMa = new JLabel("Mã:");
        lblTen = new JLabel("Tên:");
        lblLoai = new JLabel("Loại:");
        lblGia = new JLabel("Giá:");
        lblTrangThai = new JLabel("Trạng thái bán:");

        txtMa = new JTextField(20);
        txtTen = new JTextField(20);
        txtGia = new JTextField(20);

        cbLoai = new JComboBox<>(cbItems);
        btnTrangThai = new JToggleButton("Không");

        btnThoat = new JButton("Thoát");
        btnTim = new JButton("Tìm kiếm");
        pnlHeader.setPreferredSize(new Dimension(600, 60));
        pnlHeader.setBackground(Color.orange);
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.white);
        lblHeader.setIcon(new FlatSVGIcon("svg/search.svg"));

        btnTim.setBackground(Color.orange);
        btnTim.setForeground(Color.WHITE);
        btnTim.setBorderPainted(false);

        btnThoat.setBackground(Color.LIGHT_GRAY);
        btnThoat.setForeground(Color.BLACK);

        pnlHeader.add(lblHeader);

        pnlCenter.add(lblMa, "cell 0 0");
        pnlCenter.add(txtMa, "cell 1 0");

        pnlCenter.add(lblTen, "cell 0 1");
        pnlCenter.add(txtTen, "cell 1 1");

        pnlCenter.add(lblLoai, "cell 0 2");
        pnlCenter.add(cbLoai, "cell 1 2");

        pnlCenter.add(lblGia, "cell 0 3");
        pnlCenter.add(txtGia, "cell 1 3");

        pnlCenter.add(lblTrangThai, "cell 0 4");
        pnlCenter.add(btnTrangThai, "cell 1 4");

        pnlFooter.add(btnThoat);
        pnlFooter.add(btnTim);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlFooter, BorderLayout.SOUTH);

        btnThoat.addActionListener(e -> dispose());

        btnTrangThai.addActionListener(e -> {
            btnTrangThai.setText(btnTrangThai.isSelected() ? "Có" : "Không");
        });

        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemAction();
            }

        });

        pack();
        setLocationRelativeTo(null);
    }

    private void timKiemAction() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        String giaText = txtGia.getText().trim();
        String loai = (String) cbLoai.getSelectedItem();

        Double gia = null;
        Boolean trangThai = null;

        if (!giaText.isEmpty()) {
            try {
                gia = Double.parseDouble(giaText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        trangThai = btnTrangThai.isSelected();

        ArrayList<MonAn> results = MonAnDAO.searchMonAn(
                ma.isEmpty() ? null : ma,
                ten.isEmpty() ? null : ten,
                gia,
                trangThai,
                loai
        );

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có kết quả phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tableModel.setRowCount(0);
            for (MonAn monAn : results) {
                tableModel.addRow(new Object[]{
                    monAn.getMa(),
                    monAn.getTen(),
                    monAn.getLoai(),
                    monAn.getSoLuong(),
                    monAn.getGia(),
                    monAn.isTrangThai() ? "Có" : "Không"
                });
            }
        }
    }
}
