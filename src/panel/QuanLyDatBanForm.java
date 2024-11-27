package panel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class QuanLyDatBanForm extends JPanel {
    private JPanel pnlHeader, pnlContent, pnlTable, pnlButton;
    private JLabel lblHeader;
    private JLabel lblMaphieu, lblMakhachhang, lblMaban, lblNgaydat, lblGiodat, lblTrangthai;
    private JTextField txtMaphieu;
    private JComboBox<String> cboMakhachhang, cboMaban, cboTrangthai;
    private JDateChooser dateChooser;
    private JSpinner timeSpinner;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnTimKiemKhachHang, btnChonBan;
    private JTable table;
    private DefaultTableModel model;
    
    public void QuanLyDatBanForm(){
        FlatLightLaf.setup();
        initComponents();
    }
    
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(980, 700));
        
        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlContent = new JPanel(new MigLayout("insets 20, fillx", "[][grow,fill]"));
        pnlTable = new JPanel(new MigLayout("insets 20, fillx", "[grow]"));
        pnlButton = new JPanel(new MigLayout("", "[]10[]10[]10[]", "[]"));

        lblHeader = new JLabel("Quản lý đặt bàn");
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.ORANGE);
        pnlHeader.add(lblHeader);
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(new MatteBorder(0, 0, 2, 0, Color.ORANGE));
        pnlHeader.setPreferredSize(new Dimension(980, 60));

        lblMaphieu = new JLabel("Mã phiếu:");
        lblMakhachhang = new JLabel("Mã khách hàng:");
        lblMaban = new JLabel("Mã bàn:");
        lblNgaydat = new JLabel("Ngày đặt:");
        lblGiodat = new JLabel("Giờ đặt:");
        lblTrangthai = new JLabel("Trạng thái:");

        txtMaphieu = new JTextField(20);

        cboMakhachhang = new JComboBox<>();
        cboMaban = new JComboBox<>();

        btnTimKiemKhachHang = new JButton("Tìm kiếm");
        btnChonBan = new JButton("Chọn bàn");

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");

        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);

        String[] trangthaiOptions = {"Xác nhận", "Đã hủy"};
        cboTrangthai = new JComboBox<>(trangthaiOptions);

        pnlContent.add(lblMaphieu, "right");
        pnlContent.add(txtMaphieu, "growx, wrap");
        pnlContent.add(lblMakhachhang, "right");
        pnlContent.add(cboMakhachhang, "growx, split 2");
        pnlContent.add(btnTimKiemKhachHang, "wrap");
        pnlContent.add(lblMaban, "right");
        pnlContent.add(cboMaban, "growx, split 2");
        pnlContent.add(btnChonBan, "wrap");
        pnlContent.add(lblNgaydat, "right");
        pnlContent.add(dateChooser, "growx, wrap");
        pnlContent.add(lblGiodat, "right");
        pnlContent.add(timeSpinner, "growx, wrap");
        pnlContent.add(lblTrangthai, "right");
        pnlContent.add(cboTrangthai, "growx, wrap");

        btnThem = new JButton();
        btnSua = new JButton();
        btnXoa = new JButton();
        btnTimKiem = new JButton();

        btnThem.setIcon(new FlatSVGIcon("svg/add.svg"));
        btnSua.setIcon(new FlatSVGIcon("svg/edit.svg"));
        btnXoa.setIcon(new FlatSVGIcon("svg/delete.svg"));
        btnTimKiem.setIcon(new FlatSVGIcon("svg/search.svg"));
//        btnChonBan.setIcon(new FlatSVGIcon("svg/select.svg"));

        for (JButton btn : new JButton[]{btnThem, btnSua, btnXoa, btnTimKiem, btnChonBan}) {
            btn.setBackground(Color.ORANGE);
            btn.setPreferredSize(new Dimension(40, 40));
        }

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnTimKiem);
        pnlButton.add(btnChonBan);
        pnlButton.setBackground(Color.WHITE);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlContent, BorderLayout.CENTER);
        add(pnlTable, BorderLayout.SOUTH);
    }
}
