package dialog;

import com.toedter.calendar.JDateChooser;
import dao.KhachHangDAO;
import entity.KhachHang;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class TimKiemKhachHangDialog extends JDialog {

    private JPanel pnlHeader, pnlContent, pnlButton;
    private JLabel lblHeader, lblMa, lblTen, lblGioiTinh, lblSoDienThoai, lblNgaySinh, lblCMND;
    private JTextField txtMa, txtTen, txtSoDienThoai, txtCMND, txtNgaySinh;
    private JComboBox<String> cbGioiTinh;
    private String[] cbItems = {"", "Nam", "Nữ"};
    private JDateChooser lich;
    private JButton btnTimKiem, btnThoat;
    private DefaultTableModel tableModel;

    public TimKiemKhachHangDialog(JFrame parent, DefaultTableModel tableModel) {
        super(parent, "Tìm kiếm khách hàng");
        this.tableModel = tableModel;
        initComponents();
        initEvents();
    }

    public void initComponents() {
        pnlHeader = new JPanel();
        pnlContent = new JPanel(new MigLayout("fillx, insets 20, wrap 2", "[100][-1, grow, fill]", "[]10[]"));
        pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        lblHeader = new JLabel("Thông tin khách hàng");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        lblMa = new JLabel("Mã khách hàng:");
        lblTen = new JLabel("Tên khách hàng:");
        lblGioiTinh = new JLabel("Giới tính:");
        lblSoDienThoai = new JLabel("Số điện thoại:");
        lblNgaySinh = new JLabel("Ngày sinh:");
        lblCMND = new JLabel("Chứng minh nhân dân");

        txtMa = new JTextField(20);
        txtTen = new JTextField(20);
        txtSoDienThoai = new JTextField(20);
        txtCMND = new JTextField(20);
        txtNgaySinh = new JTextField(20);
        txtNgaySinh.setEditable(false);

        cbGioiTinh = new JComboBox<>(cbItems);
        lich = new JDateChooser();

        btnThoat = new JButton("Thoát");
        btnTimKiem = new JButton("Lưu");

        pnlHeader.setPreferredSize(new Dimension(600, 60));
        pnlHeader.setBackground(Color.orange);
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.white);
        pnlHeader.add(lblHeader);

        pnlContent.add(lblMa);
        pnlContent.add(txtMa, "growx");

        pnlContent.add(lblTen);
        pnlContent.add(txtTen, "growx");

        pnlContent.add(lblGioiTinh);
        pnlContent.add(cbGioiTinh, "growx");

        pnlContent.add(lblSoDienThoai);
        pnlContent.add(txtSoDienThoai, "growx");

        pnlContent.add(lblNgaySinh);
        pnlContent.add(lich, "growx");

        pnlContent.add(new JLabel("Ngày sinh (đã chọn):"));
        pnlContent.add(txtNgaySinh, "growx");

        pnlContent.add(lblCMND);
        pnlContent.add(txtCMND, "growx");

        pnlButton.add(btnTimKiem);
        pnlButton.add(btnThoat);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlContent, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
    }

    private void initEvents() {
        btnThoat.addActionListener(e -> thoatAction());

        btnTimKiem.addActionListener(e -> timKiemAction());

        lich.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    txtNgaySinh.setText(dateFormat.format(lich.getDate()));
                }
            }
        });
    }

    private boolean valid() {
        if (!txtMa.getText().trim().matches("^KH\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng phải bắt đầu bằng 'KH' và theo sau là 4 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String tiengVietPattern = "^[a-zA-ZÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠƯẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỂỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪỬỮỰỲỴỶỸ"
                + "àáâãèéêếìíòóôõùúăđĩũơưạảấầẩẫậắằẳẵặẹẻẽềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹ\\s]+$";
        if (!txtTen.getText().trim().matches(tiengVietPattern)) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng chỉ được chứa chữ cái, dấu và khoảng trắng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtSoDienThoai.getText().trim().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là dãy số có 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (lich.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!txtCMND.getText().trim().matches("\\d{12}")) {
            JOptionPane.showMessageDialog(this, "Chứng minh nhân dân phải là dãy số có 12 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        LocalDate ngaySinh = lich.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hienTai = LocalDate.now();
        Period tuoi = Period.between(ngaySinh, hienTai);
        if (tuoi.getYears() < 18) {
            JOptionPane.showMessageDialog(this, "Khách hàng chưa đủ 18 tuổi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void thoatAction() {
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void timKiemAction() {
        // Validate the input first
        if (!valid()) {
            return;
        }

        // Extract input data
        String maKH = txtMa.getText().trim();
        String tenKH = txtTen.getText().trim();
        boolean gioiTinh = cbGioiTinh.getSelectedItem().equals("Nam");
        String soDienThoai = txtSoDienThoai.getText().trim();
        String cmnd = txtCMND.getText().trim();
        java.util.Date ngaySinh = lich.getDate();

        // Create a new KhachHang object (presuming there's a constructor for this)
        KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinh, soDienThoai, new java.sql.Date(ngaySinh.getTime()), cmnd);

        // Use KhachHangDAO to search for customers (assuming you have a search method in KhachHangDAO)
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        java.util.List<KhachHang> results = khachHangDAO.timkhachhang(maKH, tenKH, soDienThoai, cmnd, new java.sql.Date(ngaySinh.getTime()), gioiTinh);

        // Clear the existing rows in the table model
        tableModel.setRowCount(0);

        // Add the search results to the table model
        for (KhachHang kh : results) {
            tableModel.addRow(new Object[]{
                kh.getMakhachhang(), kh.getTenkhachhang(), kh.isGioitinh(), kh.getSodienthoai(), kh.getNgaysinh().toString(), kh.getCmnd()
            });
        }

        // Optionally, show a message if no results were found
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
