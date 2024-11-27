package dialog;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.BanDAO;
import entity.Ban;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class TimKiemBanDialog extends JDialog {

    private JPanel pnlHeader, pnlCenter, pnlFooter;
    private JLabel lblHeader, lblMa, lblViTri, lblSoCho, lblTrangThai;
    private JTextField txtMa;
    private JComboBox<String> cbViTri;
    private String[] cbItems = {"", "Trong nhà", "Ngoài trời"}; // "" for no selection
    private JSpinner spinnerSoCho;
    private JToggleButton btnTrangThai;
    private JButton btnThoat, btnTim;
    private DefaultTableModel tableModel;

    public TimKiemBanDialog(JFrame parent) {
        super(parent, "Tìm Kiếm Bàn", true);
        initComponents();
        initListeners();
    }

    public TimKiemBanDialog(JFrame parent, DefaultTableModel tableModel) {
        super(parent, "Tìm Kiếm Món Ăn", true);
        this.tableModel = tableModel;
        initComponents();
        initListeners();
    }

    private void initComponents() {
        FlatLightLaf.setup();
        setPreferredSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCenter = new JPanel(new MigLayout("fillx, insets 20, gapx 10, gapy 15"));
        pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        lblHeader = new JLabel("Tìm kiếm bàn");
        lblMa = new JLabel("Mã bàn:");
        lblViTri = new JLabel("Vị trí:");
        lblSoCho = new JLabel("Số chỗ");
        lblTrangThai = new JLabel("Trạng thái");

        txtMa = new JTextField(20);

        spinnerSoCho = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        cbViTri = new JComboBox<>(cbItems);
        btnTrangThai = new JToggleButton("Trống");

        btnTim = new JButton("Tìm");
        btnThoat = new JButton("Thoát");

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

        pnlCenter.add(lblMa, "cell 0 1");
        pnlCenter.add(txtMa, "cell 1 1");

        pnlCenter.add(lblViTri, "cell 0 2");
        pnlCenter.add(cbViTri, "cell 1 2");

        pnlCenter.add(lblSoCho, "cell 0 3");
        pnlCenter.add(spinnerSoCho, "cell 1 3");

        pnlCenter.add(lblTrangThai, "cell 0 4");
        pnlCenter.add(btnTrangThai, "cell 1 4");

        pnlFooter.add(btnThoat);
        pnlFooter.add(btnTim);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlFooter, BorderLayout.SOUTH);

        btnThoat.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(null);
    }

    private void initListeners() {
        // Action for closing the dialog
        btnThoat.addActionListener(e -> dispose());

        // Action for search button
        btnTim.addActionListener(e -> timBan());

        // Toggle text based on the button state
        btnTrangThai.addActionListener(e -> {
            if (btnTrangThai.isSelected()) {
                btnTrangThai.setText("Đã Đặt");
            } else {
                btnTrangThai.setText("Trống");
            }
        });
    }

    private void timBan() {
        String ma = txtMa.getText().trim();
        String viTri = (String) cbViTri.getSelectedItem();
        Integer soCho = (Integer) spinnerSoCho.getValue();  // Using spinner to get soCho
        Boolean trangThai = btnTrangThai.isSelected();

        ArrayList<Ban> results = BanDAO.searchBan(
                ma.isEmpty() ? null : ma,
                viTri,
                soCho == 0 ? null : soCho, // Assuming 0 means no search criteria for soCho
                trangThai
        );

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có kết quả phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tableModel.setRowCount(0);  // Clear the table
            for (Ban ban : results) {
                tableModel.addRow(new Object[]{
                    ban.getMa(),
                    ban.getViTri(),
                    ban.getSoCho(),
                    ban.isTrangThai() ? "Đã đặt" : "Trống"
                });
            }
        }
    }
}
