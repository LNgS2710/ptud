/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialog;

import entity.PhieuDatBan;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import view.until.FormatCustom;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


/**
 *
 * @author Thành Trung
 */
public class PhieuDatBanChoDialog extends JDialog {
    private JLabel lblTenNhanVien;
    private JLabel lblTenKhachHang;
    private JLabel lblGioLap;
    private JLabel lblGioNhanBan;
    private JLabel lblMaPhieuDat;
    private JLabel lblSDTKhach;
    private JLabel lblMaBan;
    private JLabel lblViTri;
    private PhieuDatBan phieuDatBan;
        public PhieuDatBanChoDialog() {
            setModal(true);
            setLocationRelativeTo(null);
            setResizable(false);

            getContentPane().setBackground(Color.WHITE);
            getContentPane().setLayout(null);
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(null);
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPanel.setBackground(SystemColor.control);
            contentPanel.setBounds(0, 0, 600, 650);
            getContentPane().add(contentPanel);

            // Các nhãn và thành phần cũ vẫn giữ nguyên
            JLabel lblNewLabel = new JLabel("Tên quán");
            lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lblNewLabel.setBounds(37, 10, 118, 13);
            contentPanel.add(lblNewLabel);

            JLabel lblaCh = new JLabel("Địa chỉ: ");
            lblaCh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lblaCh.setBounds(37, 33, 110, 13);
            contentPanel.add(lblaCh);

            JLabel lblNewLabel_1 = new JLabel("Nhà hàng ENJOY");
            lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lblNewLabel_1.setBounds(151, 10, 191, 13);
            contentPanel.add(lblNewLabel_1);

            JLabel lblNewLabel_2 = new JLabel("286 Nguyễn Văn Nghi Phường 4 Gò vấp TP Hồ Chí Minh");
            lblNewLabel_2.setFont(new Font("Times New Roman", Font.ITALIC, 16));
            lblNewLabel_2.setBounds(149, 33, 395, 13);
            contentPanel.add(lblNewLabel_2);

            JLabel lblNewLabel_3 = new JLabel("PHIẾU ĐẶT BÀN CHỜ");
            lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 35));
            lblNewLabel_3.setBounds(0, 68, 591, 53);
            contentPanel.add(lblNewLabel_3);

            // Tạo khoảng trống và điều chỉnh các thành phần phía dưới
            JLabel lblNhanVien = new JLabel("Nhân viên lập:");
            lblNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblNhanVien.setBounds(37, 284, 200, 30);  // Đã thay đổi độ Y của lblNhnVin
            contentPanel.add(lblNhanVien);

            JLabel lblKhachHang = new JLabel("Khách hàng:");
            lblKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblKhachHang.setBounds(37, 335, 200, 30);  // Đã thay đổi độ Y của lblKhchHng
            contentPanel.add(lblKhachHang);

            lblTenNhanVien = new JLabel("Lê Ngọc Sơn");
            lblTenNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblTenNhanVien.setBounds(320, 284, 271, 30);  // Đã thay đổi độ Y của lblTenNhanVien
            contentPanel.add(lblTenNhanVien);

            lblTenKhachHang = new JLabel("Nguyễn Thành Trung");
            lblTenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblTenKhachHang.setBounds(320, 335, 271, 30);  // Đã thay đổi độ Y của lblTenKhachHang
            contentPanel.add(lblTenKhachHang);

            JLabel lbl = new JLabel("Thời gian nhận bàn");
            lbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
            lbl.setBounds(37, 492, 244, 30);  // Đã thay đổi độ Y của lbl
            contentPanel.add(lbl);

            JLabel lblaCh_1_1 = new JLabel("Thời gian lập phiếu");
            lblaCh_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblaCh_1_1.setBounds(37, 439, 200, 30);  // Đã thay đổi độ Y của lblaCh_1_1
            contentPanel.add(lblaCh_1_1);

            lblGioLap = new JLabel("18:20 20/11/2021");
            lblGioLap.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblGioLap.setBounds(320, 439, 271, 30);  // Đã thay đổi độ Y của lblGioLap
            contentPanel.add(lblGioLap);

            lblGioNhanBan = new JLabel("20:20 20/11/2021");
            lblGioNhanBan.setFont(new Font("Times New Roman", Font.BOLD, 25));
            lblGioNhanBan.setBounds(320, 492, 271, 30);  // Đã thay đổi độ Y của lblGioNhanBan
            contentPanel.add(lblGioNhanBan);

            lblMaPhieuDat = new JLabel("HDAA001");
            lblMaPhieuDat.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblMaPhieuDat.setBounds(320, 141, 271, 30);
            contentPanel.add(lblMaPhieuDat);

            JLabel lblNewLabel_5 = new JLabel("Mã phiếu đặt:");
            lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblNewLabel_5.setBounds(37, 141, 200, 30);
            contentPanel.add(lblNewLabel_5);

            lblSDTKhach = new JLabel("0394566461");
            lblSDTKhach.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblSDTKhach.setBounds(320, 386, 271, 30);  // Đã thay đổi độ Y của lblSDTKhach
            contentPanel.add(lblSDTKhach);

            JLabel lblSinThoi = new JLabel("Số điện thoại");
            lblSinThoi.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblSinThoi.setBounds(37, 386, 200, 30);  // Đã thay đổi độ Y của lblSinThoi
            contentPanel.add(lblSinThoi);

            JLabel lblNewLabel_5_1 = new JLabel("Bàn số");
            lblNewLabel_5_1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblNewLabel_5_1.setBounds(37, 187, 200, 30);
            contentPanel.add(lblNewLabel_5_1);

            lblMaBan = new JLabel("B0001");
            lblMaBan.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblMaBan.setBounds(320, 187, 271, 30);  // Đã thay đổi độ Y của lblMaBan
            contentPanel.add(lblMaBan);

            JLabel lblViTriNew = new JLabel("Vị trí");
            lblViTriNew.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblViTriNew.setBounds(37, 237, 100, 30); // Đặt "Vị trí" ở một vị trí thích hợp
            contentPanel.add(lblViTriNew);

            lblViTri = new JLabel("Ngoài trời");
            lblViTri.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            lblViTri.setBounds(320, 237, 271, 30); // Đặt "Ngoài trời" tiếp theo, ở bên cạnh "Vị trí"
            contentPanel.add(lblViTri);

            setBackground(Color.WHITE);
            setBounds(0, 0, 600, 650);
        }


	public void lamMoi() {
		lblMaPhieuDat.setText("");
                lblMaBan.setText("");
		lblTenNhanVien.setText("");
		lblTenKhachHang.setText("");
		lblSDTKhach.setText("");
		lblGioLap.setText("");
		lblGioNhanBan.setText("");
	}

	public void khoiTao(PhieuDatBan phieuDatBan) {
		this.phieuDatBan = phieuDatBan;
//		setVisible(true);
		lamMoi();
		lblMaPhieuDat.setText(phieuDatBan.getMaPDB());
                lblMaBan.setText(String.valueOf(phieuDatBan.getBan().getSoThuTu()));
                lblViTri.setText(phieuDatBan.getBan().getViTri());
		lblTenNhanVien.setText(phieuDatBan.getNhanVienLap().getTenNV());
		lblTenKhachHang.setText(phieuDatBan.getKhachHang().getTenkhachhang());
		lblSDTKhach.setText(phieuDatBan.getKhachHang().getSodienthoai());
		lblGioLap.setText(FormatCustom.dinhDanhThoiGian(phieuDatBan.getThoiGianDat()));
		lblGioNhanBan.setText(FormatCustom.dinhDanhThoiGian(phieuDatBan.getThoiGianNhanBan()));
	}

	public void xuatFile() {
		int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có muốn xem phiếu đặt bàn (PDF)", "Thông báo", JOptionPane.YES_NO_OPTION);

		String path = phieuDatBan.getMaPDB();
		path = "phieuDatBan\\" + path + ".pdf";
		if (!path.matches("(.)+(\\.pdf)$")) {
			path += ".pdf";
		}
		Container content = this.getContentPane();
		int height = content.getHeight();
		int width = content.getHeight();
		BufferedImage img = new BufferedImage(content.getWidth(), content.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();
		content.printAll(g2d);
		g2d.dispose();
		try {
			Document d = new Document();
			PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(path));
			d.open();

			PdfContentByte contentByte = writer.getDirectContent();
			Image image = Image.getInstance(contentByte, scaleImage(595, height, img), 1);

			PdfTemplate template = contentByte.createTemplate(width, height);
			image.setAbsolutePosition(0, 0);
			template.addImage(image);
			contentByte.addTemplate(template, 0, 100);
			d.close();

			if (xacNhan == JOptionPane.YES_OPTION)
				Desktop.getDesktop().open(new File(path));
			else {
				JOptionPane.showMessageDialog(this, "Xuất phiếu đặt bàn " + phieuDatBan.getMaPDB() + " Thành công");
			}
		} catch (IOException | DocumentException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Không thành công");
		}

		setVisible(false);
		dispose();
	}

	public BufferedImage scaleImage(int WIDTH, int HEIGHT, BufferedImage img) {
		BufferedImage bi = null;
		try {
			ImageIcon ii = new ImageIcon(img);
			bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(
					new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bi;
	}
}
