package panel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.NhanVienDAO;
import dialog.ChiTietNhanVienDialog;
import dialog.TimKiemNhanVienDialog;
import entity.NhanVien;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class QuanLyNhanVienForm extends JPanel implements ActionListener {

    private JPanel pnlHeader, pnlContent, pnlButton;
    private JLabel lblHeader;
    private JButton btnAdd, btnDelete, btnEdit, btnSearch;
    private String[] columnNames = {"Mã nhân viên", "Tên nhân viên", "Email", "Số điện thoại", "Trạng thái"};
    private DefaultTableModel tableModel;
    private JTable table;

    public QuanLyNhanVienForm() {
        FlatLightLaf.setup();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(980, 700));
        setBackground(Color.white);

        // Create components
        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblHeader = new JLabel("Quản Lý Nhân Viên");
        pnlContent = new JPanel(new MigLayout("wrap 1", "[grow]", "[]10[grow]"));
        pnlButton = new JPanel(new MigLayout("insets 0, gap 0", "[][][][]"));
        btnAdd = new JButton();
        btnDelete = new JButton();
        btnEdit = new JButton();
        btnSearch = new JButton();
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        // Modify components
        setupHeaderPanel();
        setupButtonPanel();
        setupTable();

        // Load the table data
        loadTable();
    }

    private void setupHeaderPanel() {
        pnlHeader.setPreferredSize(new Dimension(980, 50));
        pnlHeader.setBackground(Color.white);
        pnlHeader.setBorder(new MatteBorder(0, 0, 3, 0, Color.orange));
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.orange);
        pnlHeader.add(lblHeader);
        add(pnlHeader, BorderLayout.NORTH);
    }

    private void setupButtonPanel() {
        pnlButton.setBackground(Color.white);
        btnAdd.setBackground(Color.orange);
        btnAdd.setIcon(new FlatSVGIcon("svg/add.svg"));
        btnDelete.setBackground(Color.orange);
        btnDelete.setIcon(new FlatSVGIcon("svg/delete.svg"));
        btnEdit.setBackground(Color.orange);
        btnEdit.setIcon(new FlatSVGIcon("svg/edit.svg"));
        btnSearch.setBackground(Color.orange);
        btnSearch.setIcon(new FlatSVGIcon("svg/search.svg"));

        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnEdit.addActionListener(this);
        btnSearch.addActionListener(this);

        pnlButton.add(btnAdd);
        pnlButton.add(btnDelete);
        pnlButton.add(btnEdit);
        pnlButton.add(btnSearch);
        pnlContent.add(pnlButton, "align right, wrap");
        add(pnlContent, BorderLayout.CENTER);
    }

    private void setupTable() {
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        pnlContent.add(scrollPane, "grow, span");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(btnAdd)) {
            openDetailDialog(null); // Open dialog for adding new employee
        } else if (source.equals(btnDelete)) {
            deleteAction();
        } else if (source.equals(btnEdit)) {
            editAction();
        } else if (source.equals(btnSearch)) {
            openSearchDialog();
        }
    }

    private void deleteAction() {
        NhanVienDAO nhanVienDAO = new NhanVienDAO();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ma = (String) tableModel.getValueAt(row, 0); // Assuming the first column is "Mã nhân viên"
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            nhanVienDAO.deleteNhanVien(ma);
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
        }
    }

    private void editAction() {
        NhanVienDAO nhanVienDAO = new NhanVienDAO();
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ma = (String) tableModel.getValueAt(row, 0); // Assuming the first column is "Mã nhân viên"
        NhanVien nhanVien = nhanVienDAO.getNhanVien(ma);
        if (nhanVien != null) {
            openDetailDialog(nhanVien); // Open dialog for editing existing employee
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDetailDialog(NhanVien nhanVien) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ChiTietNhanVienDialog dialog = new ChiTietNhanVienDialog(parentFrame, nhanVien);
        dialog.setVisible(true);
        loadTable(); // Reload the table after closing the dialog
    }

    private void openSearchDialog() {
        TimKiemNhanVienDialog searchDialog = new TimKiemNhanVienDialog((JFrame) SwingUtilities.getWindowAncestor(this), tableModel);
        searchDialog.setVisible(true);
    }

    private void loadTable() {
        NhanVienDAO nhanVienDAO = new NhanVienDAO();
        tableModel.setRowCount(0); // Clear existing data
        List<NhanVien> list = nhanVienDAO.getDSNhanVien();
        for (NhanVien nhanVien : list) {
            Object[] row = {
                nhanVien.getMa(),
                nhanVien.getTen(),
                nhanVien.getEmail(),
                nhanVien.getSoDienThoai(),
                nhanVien.isTrangThai() ? "Có" : "Không"
            };
            tableModel.addRow(row);
        }
    }
}
