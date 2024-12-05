/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialog;

/**
 *
 * @author Thành Trung
 */
import com.toedter.calendar.JDateChooser;
import dao.KhachHangDAO;
import entity.KhachHang;
import java.awt.*;
import java.beans.*;
import java.text.SimpleDateFormat;
import java.time.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class CapNhatKhachHangDialog extends JDialog {

    private KhachHang khachhang;
    private KhachHangDAO khachHangDAO;
    private JPanel pnlHeader, pnlContent, pnlButton;
    private JLabel lblHeader, lblMa, lblTen, lblGioiTinh, lblSoDienThoai, lblNgaySinh, lblCMND;
    private JTextField txtMa, txtTen, txtSoDienThoai, txtCMND, txtNgaySinh;
    private JComboBox<String> cbGioiTinh;
    private String[] cbItems = {"Nam", "Nữ"};
    private JDateChooser lich;
    private JButton btnLuu, btnThoat;

    public CapNhatKhachHangDialog(KhachHang khachHang) {
        this.khachhang = khachHang;
        this.khachHangDAO = new KhachHangDAO();
        initComponents();
        initEvents();
        loadData();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setModal(true);
        pnlHeader = new JPanel();
        pnlContent = new JPanel(new MigLayout("fillx, insets 20, wrap 2", "[100][-1, grow, fill]", "[]10[]"));
        pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        lblHeader = new JLabel("Cập nhật thông tin khách hàng");
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
        btnLuu = new JButton("Lưu");

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

        pnlButton.add(btnLuu);
        pnlButton.add(btnThoat);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlContent, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);

        txtMa.setEditable(false); // Không cho phép sửa mã khách hàng
    }

    private void initEvents() {
        btnThoat.addActionListener(e -> thoatAction());

        btnLuu.addActionListener(e -> luuAction());

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
        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    private void luuAction() {
        String ten = txtTen.getText().trim();
        String gioiTinh = (String) cbGioiTinh.getSelectedItem();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String cmnd = txtCMND.getText().trim();
        java.util.Date ngaySinh = lich.getDate();

        if (valid()) {
            khachhang.setTenkhachhang(ten);
            khachhang.setGioitinh(gioiTinh.equals("Nam"));
            khachhang.setSodienthoai(soDienThoai);
            khachhang.setNgaysinh(new java.sql.Date(ngaySinh.getTime()));
            khachhang.setCmnd(cmnd);

            dao.KhachHangDAO.suakhachhang(khachhang);
            JOptionPane.showMessageDialog(this, "Thông tin khách hàng đã được cập nhật!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void thoatAction() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát mà không lưu?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void loadData() {
        if (khachhang != null) {
            txtMa.setText(khachhang.getMakhachhang());
            txtTen.setText(khachhang.getTenkhachhang());
            cbGioiTinh.setSelectedItem(khachhang.isGioitinh() ? "Nam" : "Nữ");
            txtSoDienThoai.setText(khachhang.getSodienthoai());
            txtCMND.setText(khachhang.getCmnd());
            lich.setDate(khachhang.getNgaysinh());
        }
    }
}
