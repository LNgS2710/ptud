/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dialog;

import dao.BanDAO;
import dao.PhieuDatBanDAO;
import dao.KhachHangDAO;
import entity.PhieuDatBan;
import entity.Ban;
import entity.KhachHang;
import entity.NhanVien;
import frame.MainFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import view.until.FormatCustom;

/**
 *
 * @author Thành Trung
 */
public class TimKiemPhieuDatBanCho extends JDialog implements ActionListener, MouseListener, KeyListener{
    private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField tfSDTKhach;
	private JButton btnTim;
	private DefaultTableModel tableModel;
	private JButton btnXuatPDF;
	private JButton btnQuayLai;
	private JButton btnLamMoi;
	private BanDAO banDao;
//	private HoaDonDao hoaDonDao;
	private JButton btnHuyBan;
	private JButton btnNhanBan;
	private JTextField tfMaPhieuDat, tfBanSo;
	private JScrollPane scrollPane;
	private JComboBox<String> comboBoxTrangThai;
	private PhieuDatBanDAO phieuDatBanDao;
	private List<PhieuDatBan> dsPhieuDatBan;
	private JButton btnXemBan;
	private PhieuDatBanChoDialog phieuDatBanChoDialog;

	public TimKiemPhieuDatBanCho() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setSize(930, 488);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x78B3CE));
		panel.setBounds(0, 0, 923, 53);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tìm phiếu đặt bàn");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(0, 0, 913, 53);
		panel.add(lblNewLabel);

		tfSDTKhach = new JTextField();
		tfSDTKhach.setBounds(390, 75, 130, 30);
		getContentPane().add(tfSDTKhach);
		tfSDTKhach.setColumns(10);

		btnTim = new JButton("T\u00ECm");
		btnTim.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnTim.setBackground(new Color(0x78B3CE));
		btnTim.setForeground(new Color(0, 0, 0));
		btnTim.setBounds(545, 75, 100, 30);
		getContentPane().add(btnTim);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 127, 896, 274);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Mã phiếu đặt", "Bàn - Vị trí",
				"SĐT Khách", "Thời gian lập phiếu", "Thời gian nhận bàn", "Trạng thái" }));
		scrollPane.setViewportView(table);
		btnXuatPDF = new JButton("Xuất phiếu");
		btnXuatPDF.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnXuatPDF.setForeground(Color.WHITE);
		btnXuatPDF.setBackground(new Color(210, 105, 30));
		btnXuatPDF.setBounds(507, 411, 125, 30);
		getContentPane().add(btnXuatPDF);

		btnQuayLai = new JButton("Quay l\u1EA1i");
		btnQuayLai.setForeground(Color.WHITE);
		btnQuayLai.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnQuayLai.setBackground(new Color(0, 0, 255));
		btnQuayLai.setBounds(10, 411, 111, 30);
		getContentPane().add(btnQuayLai);

		JLabel lblNewLabel_1_1 = new JLabel("SĐT Khách");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(280, 75, 115, 30);
		getContentPane().add(lblNewLabel_1_1);

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLamMoi.setBackground(new Color(0, 128, 128));
		btnLamMoi.setBounds(133, 411, 111, 30);
		getContentPane().add(btnLamMoi);

		table.setDefaultEditor(Object.class, null);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(35);

		comboBoxTrangThai = new JComboBox<String>();
		comboBoxTrangThai.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxTrangThai
				.setModel(new DefaultComboBoxModel<String>(new String[] { "Tất cả", "Còn hiệu lực", "Hết Hiệu lực" }));
		comboBoxTrangThai.setBounds(763, 75, 143, 30);
		comboBoxTrangThai.setSelectedIndex(1);
		getContentPane().add(comboBoxTrangThai);

		btnNhanBan = new JButton("Nhận bàn");
		btnNhanBan.setForeground(Color.WHITE);
		btnNhanBan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNhanBan.setBackground(new Color(34, 139, 34));
		btnNhanBan.setBounds(777, 411, 125, 30);
		getContentPane().add(btnNhanBan);

		btnHuyBan = new JButton("Hủy bàn");
		btnHuyBan.setForeground(Color.WHITE);
		btnHuyBan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHuyBan.setBackground(new Color(220, 20, 60));
		btnHuyBan.setBounds(642, 411, 125, 30);
		getContentPane().add(btnHuyBan);

		JLabel lblNewLabel_1_1_1 = new JLabel("Trạng thái");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(662, 75, 105, 30);
		getContentPane().add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_2 = new JLabel("Mã phiếu đặt");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_2.setBounds(10, 75, 120, 30);
//		getContentPane().add(lblNewLabel_1_1_2);

		tfMaPhieuDat = new JTextField();
		tfMaPhieuDat.setColumns(10);
		tfMaPhieuDat.setBounds(135, 75, 130, 30);
//		getContentPane().add(tfMaPhieuDat);
                
                JLabel lblNewLabel_1_1_2_2 = new JLabel("Bàn số");
		lblNewLabel_1_1_2_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_1_2_2.setBounds(10, 75, 120, 30);
		getContentPane().add(lblNewLabel_1_1_2_2);

		tfBanSo = new JTextField();
		tfBanSo.setColumns(10);
		tfBanSo.setBounds(135, 75, 130, 30);
		getContentPane().add(tfBanSo);

//		btnXemBan = new JButton("Xem bàn");
//		btnXemBan.setForeground(Color.WHITE);
//		btnXemBan.setFont(new Font("Tahoma", Font.PLAIN, 16));
//		btnXemBan.setBackground(new Color(30, 144, 255));
//		btnXemBan.setBounds(372, 411, 125, 30);
//		getContentPane().add(btnXemBan);

		btnTim.addActionListener(this);
		btnHuyBan.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnNhanBan.addActionListener(this);
		btnQuayLai.addActionListener(this);
		btnXuatPDF.addActionListener(this);
		btnTim.addActionListener(this);
		comboBoxTrangThai.addActionListener(this);
//		btnXemBan.addActionListener(this);
		tfMaPhieuDat.addKeyListener(this);
		tfSDTKhach.addKeyListener(this);

		banDao = new BanDAO();
//		hoaDonDao = new HoaDonDao(MainFrame.sessionFactory);
		phieuDatBanChoDialog = new PhieuDatBanChoDialog();
		phieuDatBanDao = new PhieuDatBanDAO();
                khoiTao();

	}

	private void xoaBang() {
		for (int i = tableModel.getRowCount(); i > 0; i--) {
			tableModel.removeRow(0);
		}
	}

	private void themDuLieuVaoBang() {
		String maPhieuDat = tfMaPhieuDat.getText().trim();
		String sdtKhach = tfSDTKhach.getText().trim();
		int tinhTrang = comboBoxTrangThai.getSelectedIndex();
		dsPhieuDatBan = phieuDatBanDao.layDanhSachPhieuDatBan(maPhieuDat.length() == 0 ? "" : "PDB" + maPhieuDat,
				sdtKhach, tinhTrang);
		xoaBang();
		if (dsPhieuDatBan == null || dsPhieuDatBan.size() == 0) {
			return;
		}

		for (PhieuDatBan p : dsPhieuDatBan) {
			tableModel.addRow(new String[] { p.getMaPDB().substring(3), p.getBan().getSoThuTu() + "  -  " + p.getBan().getViTri(),
					p.getKhachHang().getSodienthoai(), FormatCustom.dinhDanhThoiGian(p.getThoiGianDat()),
					FormatCustom.dinhDanhThoiGian(p.getThoiGianNhanBan()),
					!p.isTrangThai()? "Hết hiệu lực" : "Còn hiệu lực" });
		}

	}

	public void khoiTao() {

		tfMaPhieuDat.setText("");
		tfSDTKhach.setText("");
		comboBoxTrangThai.setSelectedIndex(1);
		themDuLieuVaoBang();
		setVisible(true);
	}

	public static void main(String[] args) {

		new TimKiemPhieuDatBanCho().setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
		Object object = e.getSource();
		if (object == btnLamMoi) {
			khoiTao();
			return;
		}
		if (object == comboBoxTrangThai) {
			themDuLieuVaoBang();
			return;
		}
		if (object == btnTim) {
			if (!tfMaPhieuDat.getText().trim().equals("") || !tfSDTKhach.getText().trim().equals(""))
				comboBoxTrangThai.setSelectedIndex(0);
			return;
		}
		if (object == btnQuayLai) {
			setVisible(false);
			return;
		}

		// below need select table
		int indexRow = table.getSelectedRow();
		if (indexRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt bàn");
			return;
		}
		PhieuDatBan phieuDatBan = dsPhieuDatBan.get(indexRow);
		if (object == btnHuyBan) {
			int xacnhan = JOptionPane.showConfirmDialog(this,
					"Bạn có chắc chắn HUỶ bàn chờ của :" + phieuDatBan.getKhachHang().getTenkhachhang(), "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (xacnhan != JOptionPane.YES_OPTION) {
				return;
			}
			if (!phieuDatBanDao.huyPhieuDatBan(phieuDatBan.getMaPDB())) {
				JOptionPane.showMessageDialog(this, "Hủy bàn chờ THẤT BẠI");
				return;
			}
//			if (phieuDatBan.getBan().getTrangThai().equals()) {
//				banDao.suaTrangThaiBan(phieuDatBan.getBan().getMaBan(), "0");
//                        }
//			} else {
//				banDao.suaTrangThaiBan(phieuDatBan.getBan().getMaBan(), MainFrame.banTrong);
//			}
			JOptionPane.showMessageDialog(this, "Hủy phiếu đặt bàn " + phieuDatBan.getMaPDB().substring(3) + " thành công ");
			banDao.suaTrangThaiBan(phieuDatBan.getBan().getMaBan(), "0");
                        themDuLieuVaoBang();
                        khoiTao();
			return;
		}

		System.out.println("o");
		if (object == btnXuatPDF) {
			System.out.println("Xuất");
			this.phieuDatBanChoDialog.khoiTao(phieuDatBan);
			this.phieuDatBanChoDialog.xuatFile();
			return;
		}

	}


	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(tfMaPhieuDat) || e.getSource().equals(tfSDTKhach)) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnTim.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
