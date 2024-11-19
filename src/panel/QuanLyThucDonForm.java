package panel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.MonAnDAO;
import dialog.ChiTietMonAnDialog;
import dialog.TimKiemMonDialog;
import entity.MonAn;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.TableModel;
import net.miginfocom.swing.MigLayout;

public class QuanLyThucDonForm extends JPanel implements ActionListener {

    JPanel pnlHeader, pnlContent, pnlButton;
    JLabel lblHeader;
    JButton btnAdd, btnDelete, btnEdit, btnSearch;
    String[] columnNames = {"Tên món ăn", "Mã món ăn", "Loại món ăn", "Trạng thái bán"};
    Object[][] cells = {};
    DefaultTableModel tableModel;
    JTable table;

    public QuanLyThucDonForm() {
        FlatLightLaf.setup();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(980, 700));
        setBackground(Color.white);

        //create
        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblHeader = new JLabel("Thực đơn");
        pnlContent = new JPanel(new MigLayout("wrap 1", "[grow]", "[]10[grow]"));
        pnlButton = new JPanel(new MigLayout("insets 0, gap 0", "[][][][]"));
        btnAdd = new JButton();
        btnDelete = new JButton();
        btnEdit = new JButton();
        btnSearch = new JButton();
        tableModel = new DefaultTableModel(cells, columnNames);
        table = new JTable(tableModel);
        //modify
        pnlHeader.setPreferredSize(new Dimension(980, 50));
        pnlHeader.setBackground(Color.white);
        pnlHeader.setBorder(new MatteBorder(0, 0, 3, 0, Color.orange));
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.orange);
        pnlContent.setBackground(Color.white);
        pnlButton.setBackground(Color.white);
        btnAdd.setPreferredSize(new Dimension(50, 50));
        btnDelete.setPreferredSize(new Dimension(50, 50));
        btnEdit.setPreferredSize(new Dimension(50, 50));
        btnSearch.setPreferredSize(new Dimension(50, 50));
        btnAdd.setBackground(Color.orange);
        btnAdd.setIcon(new FlatSVGIcon("svg/add.svg"));
        btnDelete.setBackground(Color.orange);
        btnDelete.setIcon(new FlatSVGIcon("svg/delete.svg"));
        btnEdit.setBackground(Color.orange);
        btnEdit.setIcon(new FlatSVGIcon("svg/edit.svg"));
        btnSearch.setBackground(Color.orange);
        btnSearch.setIcon(new FlatSVGIcon("svg/search.svg"));

        //add
        add(pnlHeader, BorderLayout.NORTH);
        pnlHeader.add(lblHeader);
        add(pnlContent, BorderLayout.CENTER);
        pnlButton.add(btnAdd);
        pnlButton.add(btnDelete);
        pnlButton.add(btnEdit);
        pnlButton.add(btnSearch);
        pnlContent.add(pnlButton, "align right, wrap");
        pnlContent.add(new JScrollPane(table), "grow, span");
        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnEdit.addActionListener(this);
        btnSearch.addActionListener(this);
        loadTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnAdd)) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            ChiTietMonAnDialog dialog = new ChiTietMonAnDialog(parentFrame);
            dialog.setVisible(true);
            loadTable();
        }

        if (o.equals(btnDelete)) {
            xoaAction();
        }

        if (o.equals(btnEdit)) {
            suaAction();
        }

        if (o.equals(btnSearch)) {
            timKiemAction();
        }
    }

    public void xoaAction() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
        String ma = (String) tableModel.getValueAt(row, 1);
        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa món ăn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.YES_OPTION) {
            MonAnDAO.deleteMonAn(ma);
            JOptionPane.showMessageDialog(this, "Xóa món ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
        }
    }

    public void suaAction() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn để sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
        String ma = (String) tableModel.getValueAt(row, 1);
        int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa món ăn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        MonAn monAn = MonAnDAO.getMonAn(ma);
        if (monAn != null) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            ChiTietMonAnDialog dialog = new ChiTietMonAnDialog(parentFrame, monAn);
            dialog.setVisible(true);
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy món ăn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemAction() {
        TimKiemMonDialog searchDialog = new TimKiemMonDialog((JFrame) SwingUtilities.getWindowAncestor(this), tableModel);
        searchDialog.setVisible(true);
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<MonAn> list = MonAnDAO.getDSMonAn();
        for (MonAn monAn : list) {
            Object[] row = {
                monAn.getTen(),
                monAn.getMa(),
                monAn.getLoai(),
                monAn.isTrangThai() ? "Có" : "Không"
            };
            tableModel.addRow(row);
        }
    }
}
