package dialog;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import custom_components.PictureBox;
import dao.MonAnDAO;
import entity.MonAn;
import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class ChiTietMonAnDialog extends JDialog {

    private boolean isConfirmed = false;
    private MonAn monAn;
    JPanel pnlHeader, pnlCenter, pnlFooter;
    JLabel lblHeader, lblTen, lblMa, lblLoai, lblGia, lblSoLuong, lblTrangThai;
    PictureBox picture;
    JTextField txtTen, txtMa, txtGia;
    JSpinner spinnerSoLuong;
    JComboBox<String> cbLoai;
    String[] cbItems = {"Khai vị", "Món chính", "Nước uống", "Tráng miệng"};
    JToggleButton btnTrangThai;
    JButton btnDuongDan, btnThoat, btnLuu;
    JFileChooser fileChooser;

    public ChiTietMonAnDialog(JFrame parent) {
        super(parent, "Chi Tiết Món Ăn", true);
        initComponents();
    }

    public ChiTietMonAnDialog(JFrame parent, MonAn monAn) {
        super(parent, "Chi Tiết Món Ăn", true);
        this.monAn = monAn;
        initComponents();
        if (monAn != null) {
            loadMonAnData(); // Load existing data into the fields
        }
    }

    private void initComponents() {
        FlatLightLaf.setup();
        setPreferredSize(new Dimension(600, 400));
        setLayout(new BorderLayout());

        // Initialize ALL components first
        pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlCenter = new JPanel(new MigLayout("fillx, insets 20, gapx 10, gapy 15", "[right][fill,250]"));
        pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        lblHeader = new JLabel("Chi tiết món ăn");
        lblMa = new JLabel("Mã:");
        lblTen = new JLabel("Tên:");
        lblLoai = new JLabel("Loại:");
        lblGia = new JLabel("Giá:");
        lblSoLuong = new JLabel("Số lượng:");
        lblTrangThai = new JLabel("Trạng thái bán:");

        txtMa = new JTextField(20);
        txtTen = new JTextField(20);
        txtGia = new JTextField(20);

        spinnerSoLuong = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        cbLoai = new JComboBox<>(cbItems);
        btnTrangThai = new JToggleButton("Không");

        picture = new PictureBox();
        btnDuongDan = new JButton("Chọn ảnh");
        btnThoat = new JButton("Thoát");
        btnLuu = new JButton("Lưu");

        pnlHeader.setPreferredSize(new Dimension(600, 60));
        pnlHeader.setBackground(Color.orange);
        lblHeader.setFont(new Font("Helvetica", Font.BOLD, 24));
        lblHeader.setForeground(Color.white);
        lblHeader.setIcon(new FlatSVGIcon("svg/food.svg"));

        picture.setPreferredSize(new Dimension(200, 200));

        btnLuu.setBackground(Color.orange);
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setBorderPainted(false);

        btnThoat.setBackground(Color.LIGHT_GRAY);
        btnThoat.setForeground(Color.BLACK);

        btnDuongDan.setBackground(Color.orange);
        btnDuongDan.setForeground(Color.WHITE);
        btnDuongDan.setBorderPainted(false);

        pnlHeader.add(lblHeader);

        pnlCenter.add(lblMa, "cell 0 0");
        pnlCenter.add(txtMa, "cell 1 0");

        pnlCenter.add(lblTen, "cell 0 1");
        pnlCenter.add(txtTen, "cell 1 1");

        pnlCenter.add(lblLoai, "cell 0 2");
        pnlCenter.add(cbLoai, "cell 1 2");

        pnlCenter.add(lblSoLuong, "cell 0 3");
        pnlCenter.add(spinnerSoLuong, "cell 1 3");

        pnlCenter.add(lblGia, "cell 0 4");
        pnlCenter.add(txtGia, "cell 1 4");

        pnlCenter.add(lblTrangThai, "cell 0 5");
        pnlCenter.add(btnTrangThai, "cell 1 5");

        pnlCenter.add(picture, "cell 2 0 1 4");
        pnlCenter.add(btnDuongDan, "cell 2 4");

        pnlFooter.add(btnThoat);
        pnlFooter.add(btnLuu);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlCenter, BorderLayout.CENTER);
        add(pnlFooter, BorderLayout.SOUTH);

        // Add events
        btnLuu.addActionListener(e -> {
            if (validateInput()) {
                String ma = txtMa.getText().trim();
                String ten = txtTen.getText().trim();
                String loai = (String) cbLoai.getSelectedItem();
                int soLuong = (Integer) spinnerSoLuong.getValue();
                double gia = Double.parseDouble(txtGia.getText().trim());
                boolean trangThai = btnTrangThai.isSelected();
                String imgPath = fileChooser.getSelectedFile().getAbsolutePath();
                if (monAn == null) {
                    if (MonAnDAO.isMaExists(ma)) {
                        JOptionPane.showMessageDialog(this, "Mã món ăn đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        txtMa.requestFocus();
                        return;
                    }
                    luuAction(ma, ten, loai, soLuong, gia, trangThai, imgPath);
                } else {
                    suaAction(ma, ten, loai, soLuong, gia, trangThai, imgPath);
                }

                dispose();
            }
        });

        btnThoat.addActionListener(e -> dispose());

        btnTrangThai.addActionListener(e -> {
            btnTrangThai.setText(btnTrangThai.isSelected() ? "Có" : "Không");
        });

        btnDuongDan.addActionListener(e -> {
            fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
                picture.setImage(imageIcon);
                picture.repaint();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private boolean validateInput() {
        String maPattern = "^F\\d{4}$";
        String tenPattern = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
        if (!txtMa.getText().matches(maPattern)) {
            JOptionPane.showMessageDialog(this, "Mã không hợp lệ!\nMã phải bắt đầu bằng 'F' và theo sau bởi 4 số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMa.requestFocus();
            return false;
        }
        if (!txtTen.getText().matches(tenPattern)) {
            JOptionPane.showMessageDialog(this, "Tên không hợp lệ!\nTên chỉ được chứa chữ cái và khoảng trắng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTen.requestFocus();
            return false;
        }
        if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() || txtGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        double gia = Double.parseDouble(txtGia.getText().trim());
        if (gia <= 1000) {
            JOptionPane.showMessageDialog(this, "Giá không được nhỏ hơn 1,000!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtGia.requestFocus();
            return false;
        }
        if (gia % 1000 != 0) {
            JOptionPane.showMessageDialog(this, "Giá phải là bội số của 1,000!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtGia.requestFocus();
            return false;
        }
        return true;
    }

    public void luuAction(String ma, String ten, String loai, int soLuong, double gia, boolean trangThai, String imgPath) {
        if (monAn == null) {
            MonAnDAO.insertMonAn(ma, ten, loai, soLuong, gia, trangThai, imgPath);
            JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void suaAction(String ma, String ten, String loai, int soLuong, double gia, boolean trangThai, String imgPath) {
        monAn.setTen(ten);
        monAn.setLoai(loai);
        monAn.setSoLuong(soLuong);
        monAn.setGia(gia);
        monAn.setTrangThai(trangThai);
        monAn.setImgPath(imgPath);
        MonAnDAO.updateMonAn(monAn);
        JOptionPane.showMessageDialog(this, "Cập nhật món ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadMonAnData() {
        txtMa.setText(monAn.getMa());
        txtMa.setEnabled(false); // Disable editing of 'ma' because it is a primary key
        txtTen.setText(monAn.getTen());
        cbLoai.setSelectedItem(monAn.getLoai());
        spinnerSoLuong.setValue(monAn.getSoLuong());
        txtGia.setText(String.valueOf(monAn.getGia()));
        btnTrangThai.setSelected(monAn.isTrangThai());
        btnTrangThai.setText(monAn.isTrangThai() ? "Có" : "Không");

        // Set the image
        if (monAn.getImgPath() != null) {
            ImageIcon imageIcon = new ImageIcon(monAn.getImgPath());
            picture.setImage(imageIcon);
        }
    }
}
