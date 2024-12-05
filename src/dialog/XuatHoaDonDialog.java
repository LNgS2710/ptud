/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialog;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
public class XuatHoaDonDialog extends JDialog{
    private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final JTable table = new JTable();
	private DefaultTableModel tableModel;
	private JLabel lblTongThanhTien;
	private JLabel lblTienThanhToan;
	private JLabel lblThue;
	private JScrollPane scrollPane;
	private JLabel lblGioBatDau;
	private JLabel lblGioKetThuc;
	private JLabel lblChietKhau;
	private JLabel lblTenNhanVien;
	private JLabel lblTenKhachHang;
        private JLabel lblViTri;
	private JLabel lblBanSo;
//	private HoaDonDao hoaDonDao;
	private JLabel lblMaHoaDon;
	public JButton btnXuatPDF;
	private JButton btnQuayLai;
	private int xacNhan;


	public XuatHoaDonDialog() {
		setModal(true);
		setLocationRelativeTo(null);
		setResizable(false);

		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setBounds(100, 100, 750, 800);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tên quán");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 118, 20);
		contentPanel.add(lblNewLabel);

		JLabel lblaCh = new JLabel("Địa chỉ: ");
		lblaCh.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblaCh.setBounds(10, 33, 110, 20);
		contentPanel.add(lblaCh);

		JLabel lblNewLabel_1 = new JLabel("Nhà hàng ENJOY");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1.setBounds(112, 10, 191, 20);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Số 286 Nguyễn Văn Nghi Phường 4 Gò vấp TP Hồ Chí Minh");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblNewLabel_2.setBounds(112, 33, 546, 20);
		contentPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("HÓA ĐƠN THANH TOÁN");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 35));
		lblNewLabel_3.setBounds(193, 69, 460, 53);
		contentPanel.add(lblNewLabel_3);

                JLabel lblbnSo = new JLabel("Bàn:");
		lblbnSo.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblbnSo.setBounds(43, 132, 110, 13);
		contentPanel.add(lblbnSo);

		JLabel lblVitr = new JLabel("Vị trí:\r\n");
		lblVitr.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblVitr.setBounds(43, 162, 110, 13);
		contentPanel.add(lblVitr);

		lblBanSo = new JLabel("1");
		lblBanSo.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblBanSo.setBounds(145, 132, 179, 13);
		contentPanel.add(lblBanSo);


		lblViTri = new JLabel("Ngoài trời");
		lblViTri.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblViTri.setBounds(145, 162, 215, 13);
		contentPanel.add(lblViTri);
                
		JLabel lblNhnVin = new JLabel("Nhân viên:");
		lblNhnVin.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNhnVin.setBounds(43, 192, 110, 13);
		contentPanel.add(lblNhnVin);

		JLabel lblKhchHng = new JLabel("Khách hàng:\r\n");
		lblKhchHng.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblKhchHng.setBounds(43, 222, 110, 13);
		contentPanel.add(lblKhchHng);

		lblTenNhanVien = new JLabel("Tên nhân viên");
		lblTenNhanVien.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblTenNhanVien.setBounds(145, 192, 179, 13);
		contentPanel.add(lblTenNhanVien);


		lblTenKhachHang = new JLabel("Tên khách hàng");
		lblTenKhachHang.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblTenKhachHang.setBounds(145, 222, 215, 13);
		contentPanel.add(lblTenKhachHang);

		JLabel lblaCh_1 = new JLabel("Giờ ra");
		lblaCh_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblaCh_1.setBounds(451, 162, 110, 13);
		contentPanel.add(lblaCh_1);

		JLabel lblaCh_1_1 = new JLabel("Giờ vào");
		lblaCh_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblaCh_1_1.setBounds(451, 132, 110, 13);
		contentPanel.add(lblaCh_1_1);

		JLabel lblNewLabel_5 = new JLabel("Mã HD:\r\n");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(10, 56, 70, 20);
		contentPanel.add(lblNewLabel_5);

		lblMaHoaDon = new JLabel("HD001");
		lblMaHoaDon.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblMaHoaDon.setBounds(112, 56, 98, 20);
		contentPanel.add(lblMaHoaDon);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 252, 736, 300);

		contentPanel.add(scrollPane);

		table.setModel(tableModel = new DefaultTableModel(new Object[][] {},
				new String[] {"Tên món", "Đơn giá", "Số lượng", "Thành tiền" }));
		scrollPane.setViewportView(table);
		table.setEnabled(false);

		JLabel lblNewLabel_8 = new JLabel("Tổng tiền:");
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_8.setBounds(420, 565, 130, 30);
		contentPanel.add(lblNewLabel_8);

		JLabel lblNewLabel_8_1 = new JLabel("Thuế VAT");
		lblNewLabel_8_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_8_1.setBounds(420, 595, 130, 30);
		contentPanel.add(lblNewLabel_8_1);

		lblTongThanhTien = new JLabel("000");
		lblTongThanhTien.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTongThanhTien.setBounds(571, 565, 156, 30);
		contentPanel.add(lblTongThanhTien);

                
		lblThue = new JLabel("000");

		lblThue.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblThue.setBounds(571, 595, 156, 30);
		contentPanel.add(lblThue);

		JLabel lblNewLabel_8_1_1 = new JLabel("Tiền thanh toán");
		lblNewLabel_8_1_1.setForeground(Color.RED);
		lblNewLabel_8_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_8_1_1.setBounds(420, 655, 130, 30);
		contentPanel.add(lblNewLabel_8_1_1);

                
		lblTienThanhToan = new JLabel("000");
		lblTienThanhToan.setForeground(Color.RED);
		lblTienThanhToan.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTienThanhToan.setBounds(571, 655, 156, 30);
		contentPanel.add(lblTienThanhToan);


		JLabel lblNewLabel_9_2_1 = new JLabel();
		lblNewLabel_9_2_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_9_2_1.setBounds(125, 582, 226, 30);
		contentPanel.add(lblNewLabel_9_2_1);

		lblGioBatDau = new JLabel("18:20 20/11/2021");
		lblGioBatDau.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblGioBatDau.setBounds(571, 132, 165, 13);
		contentPanel.add(lblGioBatDau);

		lblGioKetThuc = new JLabel("20:20 20/11/2021");
		lblGioKetThuc.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblGioKetThuc.setBounds(571, 162, 165, 13);
		contentPanel.add(lblGioKetThuc);

		JLabel lblNewLabel_8_1_1_1_2 = new JLabel("Chiết khấu");
		lblNewLabel_8_1_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_8_1_1_1_2.setBounds(420, 625, 130, 30);
		contentPanel.add(lblNewLabel_8_1_1_1_2);

		lblChietKhau = new JLabel("10%");
		lblChietKhau.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblChietKhau.setBounds(571, 625, 156, 30);
		contentPanel.add(lblChietKhau);


		btnXuatPDF = new JButton("Xuất PDF");
		btnXuatPDF.setForeground(new Color(255, 255, 255));
		btnXuatPDF.setBackground(new Color(255, 165, 0));
		btnXuatPDF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXuatPDF.setBounds(581, 691, 104, 35);
		contentPanel.add(btnXuatPDF);

//		btnXuatPDF.addActionListener(this);
                table.setBackground(Color.WHITE); // Đặt nền bảng trắng
                table.setOpaque(true); // Đảm bảo bảng hiển thị nền được đặt
                table.setSelectionBackground(new Color(220, 220, 220)); // Đặt màu nền khi chọn hàng
                table.setSelectionForeground(Color.BLACK); // Màu chữ khi chọn hàng

                table.getTableHeader().setBackground(Color.WHITE); // Đặt nền tiêu đề bảng trắng
                table.getTableHeader().setForeground(Color.BLACK); // Đặt màu chữ tiêu đề bảng
                table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
                table.setRowHeight(30); // Tăng chiều cao của mỗi dòng trong bảng
                table.setAutoCreateRowSorter(true);
		table.setDefaultEditor(Object.class, null);

		// set select only one row

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
	}
        public static void main(String[] args) {
        XuatHoaDonDialog dialog = new XuatHoaDonDialog();
        dialog.setVisible(true);
    }
}
