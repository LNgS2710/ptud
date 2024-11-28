package panel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import dao.KhachHangDAO;
import dialog.ChiTietKhachHangDialog;
import entity.KhachHang;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class QuanLyKhachHangForm extends JPanel {

    private JPanel pnlHeader, pnlButton, pnlTable;
    private JLabel lblHeader;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;
    private String[] cols = {"Mã khách hàng", "Tên khách hàng", "Giới tính", "Số điện thoại", "Ngày sinh", "Số CMND"};
    private DefaultTableModel model;
    private JTable table;

    public QuanLyKhachHangForm() {
        FlatLightLaf.setup();
        initComponents();
        initEvents();
        loadTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(980, 700));

        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlButton = new JPanel(new MigLayout("insets 0, gap 0", "[][][][]"));
        pnlTable = new JPanel(new MigLayout("wrap 1", "[grow]", "[]10[grow]"));

        lblHeader = new JLabel("Quản lý khách hàng");

        btnThem = new JButton();
        btnSua = new JButton();
        btnXoa = new JButton();
        btnTimKiem = new JButton();

        pnlHeader.setPreferredSize(new Dimension(980, 50));
        pnlHeader.setBackground(Color.white);
        pnlHeader.setBorder(new MatteBorder(0, 0, 3, 0, Color.orange));
        pnlButton.setBackground(Color.white);

        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.orange);

        btnThem.setBackground(Color.orange);
        btnThem.setIcon(new FlatSVGIcon("svg/add.svg"));
        btnSua.setBackground(Color.orange);
        btnSua.setIcon(new FlatSVGIcon("svg/edit.svg"));
        btnXoa.setBackground(Color.orange);
        btnXoa.setIcon(new FlatSVGIcon("svg/delete.svg"));
        btnTimKiem.setBackground(Color.orange);
        btnTimKiem.setIcon(new FlatSVGIcon("svg/search.svg"));

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        pnlHeader.add(lblHeader);
        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnTimKiem);
        pnlTable.add(pnlButton, "align right, wrap");
        pnlTable.add(scrollPane, "grow, span");

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlTable, BorderLayout.CENTER);
    }

    private void loadTable() {
        model.setRowCount(0);
        ArrayList<KhachHang> list = KhachHangDAO.getallkhachhang();
        for (KhachHang khachHang : list) {
            model.addRow(new Object[]{
                khachHang.getMakhachhang(),
                khachHang.getTenkhachhang(),
                khachHang.isGioitinh() ? "Nam" : "Nữ",
                khachHang.getSodienthoai(),
                khachHang.getNgaysinh(),
                khachHang.getCmnd()
            });
        }
    }

    private void initEvents() {
        btnThem.addActionListener(e -> {
            ChiTietKhachHangDialog dialog = new ChiTietKhachHangDialog(TOOL_TIP_TEXT_KEY);
            dialog.setVisible(true);
            loadTable();
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String maKhachHang = (String) model.getValueAt(selectedRow, 0);
                KhachHang khachhang = KhachHangDAO.getkhachhangbyma(maKhachHang);
                if (khachhang != null) {
                    ChiTietKhachHangDialog dialog = new ChiTietKhachHangDialog((JFrame) SwingUtilities.getWindowAncestor(this), khachhang, true);
                    dialog.setVisible(true);
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để sửa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String maKhachHang = (String) model.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc chắn muốn xóa khách hàng " + maKhachHang + "?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    KhachHangDAO.xoakhachhang(maKhachHang);
                    loadTable();
                    JOptionPane.showMessageDialog(this, "Khách hàng đã được xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnTimKiem.addActionListener(e -> {
            // Code to search for a customer
        });
    }
}
