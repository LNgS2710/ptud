package dialog;

import dao.NhanVienDAO;
import entity.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TimKiemNhanVienDialog extends JDialog {
    private JTextField txtSearchMa;
    private JTextField txtSearchTen;
    private JTable table;
    private DefaultTableModel tableModel;
    private NhanVienDAO nhanVienDAO;

    public TimKiemNhanVienDialog(Frame parent, DefaultTableModel tableModel) {
        super(parent, "Tìm kiếm nhân viên", true);
        this.tableModel = tableModel;  // Store the reference to update later if needed
        nhanVienDAO = new NhanVienDAO();
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Search panel
        JPanel searchPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        searchPanel.add(new JLabel("Mã nhân viên:"));
        txtSearchMa = new JTextField();
        searchPanel.add(txtSearchMa);
        
        searchPanel.add(new JLabel("Tên nhân viên:"));
        txtSearchTen = new JTextField();
        searchPanel.add(txtSearchTen);

        JButton btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(btnSearch);
        
        JButton btnCancel = new JButton("Hủy");
        searchPanel.add(btnCancel);

        add(searchPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Mã nhân viên", "Tên nhân viên", "Email", "Số điện thoại", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button actions
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchNhanVien();
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

    private void searchNhanVien() {
        String ma = txtSearchMa.getText().trim();
        String ten = txtSearchTen.getText().trim();
        tableModel.setRowCount(0); // Clear previous results

        List<NhanVien> results = nhanVienDAO.searchNhanVien(ma, ten);
        for (NhanVien nhanVien : results) {
            Object[] row = {
                nhanVien.getMa(),
                nhanVien.getTen(),
                nhanVien.getEmail(),
                nhanVien.getSoDienThoai(),
                nhanVien.isTrangThai() ? "Hoạt động" : "Không hoạt động"
            };
            tableModel.addRow(row);
        }

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
