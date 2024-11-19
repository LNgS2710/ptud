package panel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.BanDAO;
import dialog.ChiTietBanDialog;
import dialog.TimKiemBanDialog;
import entity.Ban;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class QuanLyBanForm extends JPanel implements ActionListener {

    JPanel pnlHeader, pnlContent, pnlButton;
    JLabel lblHeader;
    JButton btnAdd, btnDelete, btnEdit, btnSearch;
    String[] columnNames = {"Mã bàn", "Vị trí", "Số chỗ", "Trạng thái"};
    Object[][] cells = {};
    DefaultTableModel tableModel;
    JTable table;

    public QuanLyBanForm() {
        FlatLightLaf.setup();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(980, 700));
        setBackground(Color.white);

        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblHeader = new JLabel("Danh sách bàn");
        pnlContent = new JPanel(new MigLayout("wrap 1", "[grow]", "[]10[grow]"));
        pnlButton = new JPanel(new MigLayout("insets 0, gap 0", "[][][][]"));
        btnAdd = new JButton();
        btnDelete = new JButton();
        btnEdit = new JButton();
        btnSearch = new JButton();
        tableModel = new DefaultTableModel(cells, columnNames);
        table = new JTable(tableModel);

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
        loadBan();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnAdd)) {
            themAction();
        } else if (o.equals(btnEdit)) {
            suaAction();
        } else if (o.equals(btnDelete)) {
            xoaAction();
        } else if (o.equals(btnSearch)) {
            timAction();
        }
    }

    private void themAction() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ChiTietBanDialog dialog = new ChiTietBanDialog(parentFrame);
        dialog.setVisible(true);

        loadBan();
    }

    private void suaAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn cần sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maBan = (String) table.getValueAt(selectedRow, 0);
        Ban selectedBan = BanDAO.getBan(maBan);

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ChiTietBanDialog dialog = new ChiTietBanDialog(parentFrame, selectedBan);
        dialog.setVisible(true);
        loadBan();
    }

    private void xoaAction() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn cần xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maBan = (String) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bàn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = BanDAO.deleteBan(maBan);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa bàn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadBan();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa bàn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void timAction() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        TimKiemBanDialog dialog = new TimKiemBanDialog(parentFrame, tableModel);
        dialog.setVisible(true);
    }

    private void loadBan() {
        ArrayList<Ban> banList = BanDAO.getAllBans();
        tableModel.setRowCount(0);

        for (Ban ban : banList) {
            Object[] row = new Object[]{
                ban.getMa(),
                ban.getViTri(),
                ban.getSoCho(),
                ban.isTrangThai() ? "Đã Đặt" : "Trống"
            };
            tableModel.addRow(row);
        }
    }

}
