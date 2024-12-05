package dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

//import dao.HoaDonDao;
import dao.KhachHangDAO;
import dao.PhieuDatBanDAO;
import dao.BanDAO;
import entity.KhachHang;
import entity.PhieuDatBan;
import entity.Ban;
import frame.MainFrame;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static org.jdesktop.swingx.table.TableUtilities.isUpdate;
import panel.QuanLyKhachHangForm;

public class DatBanChoDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnDatBan;
	private JButton btnHuy;
	private JTextField tfSDTKhach;
	@SuppressWarnings("unused")
//	private HoaDonDao hoaDonDao;
	private KhachHangDAO khachHangDao;
	private JLabel lblTenKhach;
	private JLabel lblIconKiemTraSDT;
	private JLabel lblMaBan;
	private JButton btnKiemTraSDTKKhach;
	private JComboBox<String> cbPhut;
	private JComboBox<String> cbGio;
	private JRadioButton radioNgayMai;
	private JRadioButton radioHomNay;
	private BanDAO banDao;
	private PhieuDatBanDAO phieuDatBanDao;
	private Ban ban;
	boolean trangThai = false;
	private DefaultComboBoxModel<String> gioModel;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel phutModel;
	private PhieuDatBanChoDialog phieuDatBanChoDialog;
	private JCheckBox cbInPhieuDat;
        private JLabel lblViTriBan;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DatBanChoDialog(Ban ban) {
		setModal(true);
		this.ban = ban;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 139, 139));
		panel.setBounds(0, 0, 486, 59);
		setSize(500, 435);
		getContentPane().add(panel);
		setLocationRelativeTo(null);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("ĐẶT BÀN CHỜ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 476, 59);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Ngày nhận bàn");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(33, 237, 157, 35);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Giờ nhận bàn");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(33, 287, 157, 35);
		getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Giờ");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1.setBounds(264, 287, 57, 35);
		getContentPane().add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Phút");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1.setBounds(383, 287, 55, 35);
		getContentPane().add(lblNewLabel_1_1_1_1);

		cbGio = new JComboBox<String>();
		cbGio.setFont(new Font("Tahoma", Font.BOLD, 16));
		cbGio.setModel(gioModel = new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
		cbGio.setBounds(200, 287, 65, 35);
		getContentPane().add(cbGio);

		cbPhut = new JComboBox<String>();
		cbPhut.setFont(new Font("Tahoma", Font.BOLD, 16));
		cbPhut.setModel(phutModel = new DefaultComboBoxModel(
				new String[] { "0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
		cbPhut.setBounds(319, 287, 65, 35);
		getContentPane().add(cbPhut);

		btnDatBan = new JButton("Đặt bàn");
		btnDatBan.setBackground(new Color(65, 105, 225));
		btnDatBan.setForeground(new Color(255, 255, 255));
		btnDatBan.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDatBan.setBounds(314, 346, 135, 35);
		getContentPane().add(btnDatBan);

		btnHuy = new JButton("Hủy");
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnHuy.setBackground(new Color(128, 128, 128));
		btnHuy.setBounds(33, 346, 78, 35);
		getContentPane().add(btnHuy);

		cbInPhieuDat = new JCheckBox("In phiếu đặt");
		cbInPhieuDat.setSelected(true);
		cbInPhieuDat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbInPhieuDat.setBounds(200, 351, 106, 21);
		getContentPane().add(cbInPhieuDat);

		tfSDTKhach = new JTextField();
		tfSDTKhach.setColumns(10);
		tfSDTKhach.setBounds(200, 148, 150, 35);
		getContentPane().add(tfSDTKhach);

		lblIconKiemTraSDT = new JLabel("");
		lblIconKiemTraSDT.setBounds(177, 148, 22, 33);
		getContentPane().add(lblIconKiemTraSDT);

		JLabel lblSdtKhach = new JLabel("SĐT Khách");
		lblSdtKhach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSdtKhach.setBounds(33, 148, 157, 35);
		getContentPane().add(lblSdtKhach);

		btnKiemTraSDTKKhach = new JButton("Kiểm tra");
		btnKiemTraSDTKKhach.setBounds(358, 148, 90, 35);
		getContentPane().add(btnKiemTraSDTKKhach);

		lblTenKhach = new JLabel("");
		lblTenKhach.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTenKhach.setBounds(200, 190, 220, 35);
		getContentPane().add(lblTenKhach);

		JLabel lblTenKhach01 = new JLabel("Tên Khách");
		lblTenKhach01.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTenKhach01.setBounds(33, 190, 157, 35);
		getContentPane().add(lblTenKhach01);

		JLabel lblBanSo = new JLabel("Bàn số");
		lblBanSo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBanSo.setBounds(33, 60, 157, 35);
                getContentPane().add(lblBanSo); 
                
		lblMaBan = new JLabel("1");
		lblMaBan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMaBan.setBounds(200, 60, 220, 35);
                getContentPane().add(lblMaBan); 
                lblMaBan.setText(String.valueOf(ban.getSoThuTu()));
                
                JLabel lblViTri = new JLabel("Vị trí");
		lblViTri.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblViTri.setBounds(33, 103, 157, 35);
                getContentPane().add(lblViTri);
                
                lblViTriBan = new JLabel("Ngoài trời");
		lblViTriBan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblViTriBan.setBounds(200, 103, 220, 35);
                getContentPane().add(lblViTriBan);
                lblViTriBan.setText(ban.getViTri().substring(0));

		radioHomNay = new JRadioButton("Hôm nay");
		radioHomNay.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioHomNay.setSelected(true);
		radioHomNay.setBounds(203, 247, 103, 21);
		getContentPane().add(radioHomNay);

		radioNgayMai = new JRadioButton("Ngày mai");
		radioNgayMai.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioNgayMai.setBounds(346, 247, 103, 21);
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioNgayMai);
		bg.add(radioHomNay);
		getContentPane().add(radioNgayMai);
		try {
			khachHangDao = new KhachHangDAO();
			banDao = new BanDAO();
			phieuDatBanDao = new PhieuDatBanDAO();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		btnKiemTraSDTKKhach.addActionListener(this);
		btnDatBan.addActionListener(this);
		btnHuy.addActionListener(this);
		radioNgayMai.addActionListener(this);
		radioHomNay.addActionListener(this);
		thietLapGio();
//		datBanChoDialog = new DatBanChoDialog();
	}
        
        @SuppressWarnings({ "deprecation", "unchecked" })
	public void thietLapGio() {
		int gio = 1;
		int phut = 0;
		if (radioHomNay.isSelected()) {
			Date date = new Date();
			gio = date.getHours();
			phut = date.getMinutes() % 5 == 0 ? date.getMinutes() : ((date.getMinutes() / 5) * 5) + 5;
			if (phut == 60) {
				gio += 1;
				phut = 5;
			}
		}
		if (gio < 8) {
			gio = 8;
		}
		gioModel.removeAllElements();
		phutModel.removeAllElements();
		for (int i = gio; i < 23; i++) {
			gioModel.addElement(i + "");
		}
		for (int i = phut; i < 60; i += 5) {
			phutModel.addElement(i + "");
		}

	}


	public KhachHang kiemTraSDTKhach() {
		String sdt = tfSDTKhach.getText().toString();
		if (sdt.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Bạn chưa nhập số điện thoại Khách");
			lblIconKiemTraSDT.setIcon(new ImageIcon(getClass().getResource("/icon/remove1.png")));
			tfSDTKhach.selectAll();
			tfSDTKhach.requestFocus();
			return null;
		}
		if (!sdt.matches(
				"(^(03)[2-9]\\d{7})|(^(07)[06-9]\\d{7})|(^(08)[1-5]\\d{7})|(^(056)\\d{7})|(^(058)\\d{7})|(^(059)\\d{7})|(^(09)[0-46-9]\\d{7})")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại không đúng địng dạng");
			tfSDTKhach.selectAll();
			lblIconKiemTraSDT.setIcon(new ImageIcon(getClass().getResource("/icon/remove1.png")));
			tfSDTKhach.requestFocus();
			return null;
		}
		KhachHang KhachHang = khachHangDao.layKhachHangTheoSDT(sdt);
		if (KhachHang == null) {
                        lblIconKiemTraSDT.setIcon(new ImageIcon(getClass().getResource("/icon/remove1.png")));
			int xacNhan = JOptionPane.showConfirmDialog(this, "Khách hàng không có trong hệ thống, Bạn có muốn thêm khách hàng không", "Thông báo", JOptionPane.YES_NO_OPTION);
			if (xacNhan == JOptionPane.YES_OPTION) {
                            ChiTietKhachHangDialog dialogThemKhachHang = new ChiTietKhachHangDialog(sdt);
                            dialogThemKhachHang.setVisible(true);
                            if (ChiTietKhachHangDialog.khachHang == null) {
                                    dialogThemKhachHang.dispose();
                                    return null;
                            }
                            dialogThemKhachHang.dispose();
                            KhachHang = ChiTietKhachHangDialog.khachHang;
			}
		}
		lblTenKhach.setText(KhachHang.getTenkhachhang());
                lblIconKiemTraSDT.setIcon(new ImageIcon(getClass().getResource("/icon/true.png")));
		return KhachHang;
	}

	@SuppressWarnings("deprecation")
        @Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object.equals(btnKiemTraSDTKKhach)) {
			if (kiemTraSDTKhach() == null) {
                            lblIconKiemTraSDT.setIcon(new ImageIcon(getClass().getResource("/icon/remove1.png")));
			}
			return;
		}
		if (object.equals(btnDatBan)) {
			KhachHang khachHang = kiemTraSDTKhach();
			int gio = Integer.parseInt(cbGio.getSelectedItem().toString());
			int phut = Integer.parseInt(cbPhut.getSelectedItem().toString());
			Date date = new Date();
			if (radioHomNay.isSelected()) {
				if (gio < date.getDay() || (gio == date.getHours() && phut < date.getMinutes())) {
					JOptionPane.showMessageDialog(this, "Thời gian phải trước thời gian hiện tại");
					return;
				}
			} else {
				// add one day
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.DATE, 1);
				date = c.getTime();
			}
			date.setMinutes(phut);
			date.setHours(gio);
			if (khachHang == null)
				return;
                        List<PhieuDatBan> dsPhieuDatBan = phieuDatBanDao.layPhieuDatBanTheoBan(ban.getMaBan());
                        for (PhieuDatBan phieu : dsPhieuDatBan) {
                            // Kiểm tra thời gian giữa các phiếu đặt
                                long timeDiff = Math.abs(date.getTime() - phieu.getThoiGianNhanBan().getTime());
                                if (timeDiff < (2 * 60 * 60 * 1000 + 30 * 60 * 1000)) { // 2 giờ 30 phút
                                    JOptionPane.showMessageDialog(this, "Thời gian nhận bàn phải cách nhau ít nhất 2 giờ 30 phút với các đặt chờ hiện có.");
                                    return;
                                }
                            
                        }
			int xacNhan = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn đặt bàn chờ không ", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (xacNhan != JOptionPane.YES_OPTION) {
				return;
			}
			PhieuDatBan phieuDatBan = new PhieuDatBan(ban, khachHang, MainFrame.nhanVien, date);
                        if (!banDao.suaTrangThaiBan(ban.getMaBan(), MainFrame.banCho)
					|| !phieuDatBanDao.themPhieuDatBan(phieuDatBan)) {
				JOptionPane.showMessageDialog(this, "Đặt phòng KHÔNG thành công");
				setVisible(false);
				dispose();
				return;
			}
                        
                        if (!ban.getTrangThai().equals("1")) {
                            if (!banDao.suaTrangThaiBan(ban.getMaBan(), "1")) {
                                JOptionPane.showMessageDialog(this, "Không thể cập nhật trạng thái bàn");
                                return;
                            }
                        }
                        
//			if (!banDao.suaTrangThaiBan(ban.getMaBan(), "1")
//					|| !phieuDatBanDao.themPhieuDatBan(phieuDatBan)) {
//				JOptionPane.showMessageDialog(this, "Đặt bàn KHÔNG thành công");
//				setVisible(false);
//				dispose();
//				return;
//			}
			if (cbInPhieuDat.isSelected()) {
				this.phieuDatBanChoDialog = new PhieuDatBanChoDialog();
                                this.phieuDatBanChoDialog.khoiTao(phieuDatBan);
				this.phieuDatBanChoDialog.xuatFile();

			} 
                        else
				JOptionPane.showMessageDialog(this, "Đặt bàn thành công");

			trangThai = true;
			setVisible(false);
			dispose();
		}
		if (object.equals(btnHuy)) {
			setVisible(false);
			dispose();
		}
		if (object.equals(radioHomNay) || object.equals(radioNgayMai)) {
			thietLapGio();
		}
	}
//        public static void main(String[] args) {
//            SwingUtilities.invokeLater(() -> {
//            DatBanChoDialog dialog = new DatBanChoDialog();
//            dialog.setVisible(true);
//        });
//    }

}
