package panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatLineBorder;
import dao.BanDAO;
import dao.KhachHangDAO;
import dao.PhieuDatBanDAO;
import database.JDBC;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.PhieuDatBan;
import dao.KhuyenMaiDao;
import dialog.CapNhatKhuyenMaiDialog;
import dialog.ThemKhuyenMaiDialog;

import java.awt.BorderLayout;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.TableColumnModel;


public class DanhSachKhuyenMai extends JPanel implements ActionListener{

    String[] cols = {"Mã khuyến mãi", "Mô tả", "Chiết khấu", "Trạng thái"};
    private JTable table;
    private DefaultTableModel model;
    private JPanel pnlTable, pnlControl;
    private JTextField txtMaKM;
    private JComboBox<String> cbTrangThai;
    private JButton btnTimKiem, btnThem, btnXoa, btnCapNhat;
    private KhuyenMaiDao khuyenMaiDao;
    private List<KhuyenMai> dsKhuyenMai;

    public DanhSachKhuyenMai() {
        khuyenMaiDao = new KhuyenMaiDao();  // Đảm bảo dao được khởi tạo tại đây
        init();
        loadTableData();

    }

    private void init() {
        FlatLightLaf.setup();
        setPreferredSize(new Dimension(980, 700));
        setLayout(new BorderLayout());

         // Initialize control panel (Top)
        pnlControl = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Tăng khoảng cách giữa các thành phần
        pnlControl.setBorder(BorderFactory.createTitledBorder("Quản lý khuyến mãi"));


        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16); // Phông chữ to hơn

        // Input field for Mã khuyến mãi
        JLabel lblMaKM = new JLabel("Mã khuyến mãi:");
        lblMaKM.setFont(labelFont); // Áp dụng font lớn cho label
        pnlControl.add(lblMaKM);
        txtMaKM = new JTextField(); // Tăng độ rộng của text field
        txtMaKM.setPreferredSize(new Dimension(150, 30));
        pnlControl.add(txtMaKM);

        // ComboBox for Trạng thái
        JLabel lblTT = new JLabel("Trạng thái:");
        lblTT.setFont(labelFont);
        pnlControl.add(lblTT);
        cbTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Hết hạn"});
        cbTrangThai.setPreferredSize(new Dimension(100, 32)); // Tăng kích thước ComboBox
        pnlControl.add(cbTrangThai);

        // Buttons
        FlatLineBorder buttonBorder = new FlatLineBorder(
                new Insets(10, 20, 10, 20),
                UIManager.getColor("Component.accentColor"),
                2f,
                8
        );
        Font buttonFont = new Font("Roboto", Font.BOLD, 14);
        
        btnTimKiem = new JButton();
        btnTimKiem.setIcon(new FlatSVGIcon("icon/search.svg"));
        btnTimKiem.setPreferredSize(new Dimension(30, 30));
        btnTimKiem.putClientProperty(FlatClientProperties.STYLE, "arc: 8;");
        pnlControl.add(btnTimKiem);

        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(120, 30));
        pnlControl.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(120, 30));
        pnlControl.add(btnXoa);

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setPreferredSize(new Dimension(120, 30));
        pnlControl.add(btnCapNhat);

        btnTimKiem.setForeground(UIManager.getColor("Component.accentColor"));
        btnThem.setForeground(UIManager.getColor("Component.accentColor"));
        btnXoa.setForeground(UIManager.getColor("Component.accentColor"));
        btnCapNhat.setForeground(UIManager.getColor("Component.accentColor"));
        
        btnTimKiem.setFont(buttonFont);
        btnThem.setFont(buttonFont);
        btnXoa.setFont(buttonFont);
        btnCapNhat.setFont(buttonFont);

        btnTimKiem.setBorder(buttonBorder);
        btnThem.setBorder(buttonBorder);
        btnXoa.setBorder(buttonBorder);
        btnCapNhat.setBorder(buttonBorder);
        
        // Add control panel to the top
        add(pnlControl, BorderLayout.NORTH);

        // Initialize table panel (Center)
        pnlTable = new JPanel(new BorderLayout());
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(30); // Tăng chiều cao của mỗi dòng trong bảng
        JScrollPane scrollPane = new JScrollPane(table);
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Add table panel to the center
        add(pnlTable, BorderLayout.CENTER);
        btnThem.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnXoa.addActionListener(this);
        cbTrangThai.addActionListener(this);
        adjustTableColumns();
        
        
    }
    private void loadTableData() {
        
        model.setRowCount(0); // Xóa dữ liệu cũ
        List<KhuyenMai> danhSach = khuyenMaiDao.layDanhSachKhuyenMai();
        
        for (KhuyenMai km : danhSach) {
            model.addRow(new Object[]{km.getMaKM(), km.getMoTa(), km.getChietKhau(), km.isTrangThai() ? "Hoạt động" : "Hết hạn"});
        }
    }
    private void clear() {
            table.clearSelection();
            txtMaKM.setText("");
            cbTrangThai.setSelectedIndex(2);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object.equals(btnThem)) {
            new ThemKhuyenMaiDialog().setVisible(true);
            loadTableData();
        }
        
        if (object.equals(btnCapNhat)) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Kiểm tra xem người dùng đã chọn dòng hay chưa
                // Lấy thông tin từ dòng đã chọn
                String maKM = table.getValueAt(selectedRow, 0).toString(); // Giả sử cột 0 là mã khuyến mãi
                String moTa = table.getValueAt(selectedRow, 1).toString(); // Giả sử cột 1 là mô tả
                String chietKhau = table.getValueAt(selectedRow, 2).toString(); // Giả sử cột 2 là chiết khấu
                String trangThai = table.getValueAt(selectedRow, 3).toString(); // Giả sử cột 3 là trạng thái

                // Tạo đối tượng KhuyenMai với dữ liệu đã lấy
                KhuyenMai khuyenMai = new KhuyenMai();
                khuyenMai.setMaKM(maKM);
                khuyenMai.setMoTa(moTa);
                khuyenMai.setChietKhau(Float.parseFloat(chietKhau));
                khuyenMai.setTrangThai(trangThai.equals("Hoạt động"));

                // Mở dialog cập nhật với đối tượng khuyến mãi
                CapNhatKhuyenMaiDialog dialog = new CapNhatKhuyenMaiDialog(khuyenMai);
                dialog.setVisible(true);

                // Tải lại dữ liệu bảng nếu cần
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để cập nhật.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        if (object.equals(btnXoa)) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khuyến mãi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maKM = (String) model.getValueAt(selectedRow, 0);
                boolean success = khuyenMaiDao.xoaKhuyenMai(maKM);
            
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadTableData(); // Cập nhật lại bảng sau khi xóa
                } else {
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xóa khuyến mãi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                loadTableData();
            }
        }
        
        if(object.equals(btnTimKiem)){
            loadTableData();
        }
        
        if (object == cbTrangThai) {
            loadTableData();
	}
    }

        public void itemStateChanged(ItemEvent e) {
            Object o = e.getSource();
            if(o == cbTrangThai){      
            loadTableData();
            }
        }
    
    
    private void adjustTableColumns() {
    // Lấy model của cột trong table
    TableColumnModel columnModel = table.getColumnModel();

    // Cột Mã khuyến mãi (Mã khuyến mãi có thể giữ nguyên kích thước hoặc tùy chỉnh thêm)
    columnModel.getColumn(0).setPreferredWidth(100); // Cột "Mã khuyến mãi"

    // Cột Mô tả - Dài ra
    columnModel.getColumn(1).setPreferredWidth(350); // Cột "Mô tả" dài ra

    // Cột Chiết khấu - Nhỏ lại
    columnModel.getColumn(2).setPreferredWidth(100); // Cột "Chiết khấu" nhỏ lại

    // Cột Trạng thái - Nhỏ lại
    columnModel.getColumn(3).setPreferredWidth(100); // Cột "Trạng thái" nhỏ lại
}
}
