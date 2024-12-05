package panel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatLineBorder;
import dao.BanDAO;
import dialog.DatBanChoDialog;
import dialog.ThemBanJDialog;
import dialog.TimKiemPhieuDatBanCho;
import entity.Ban;
import entity.PhieuDatBan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import view.until.FormatCustom;



public class DanhSachBanJPanel extends JPanel {

    private JPanel pnlButton, pnlTable;
    private JButton btnThem, btnKhongDatTruoc, btnDatBanCho, btnTimPhieu;
    private JTable table;
    private JScrollPane scrollPane;
    private BanDAO banDAO;
    private Ban ban;
    private PhieuDatBan phieuDatBan;

    public DanhSachBanJPanel() {
        init();
        initEvent();
    }

    private void init() {
        FlatLightLaf.setup();

        setPreferredSize(new Dimension(980, 700));
        setLayout(new BorderLayout());

        banDAO = new BanDAO();
        ban = new Ban();

        btnThem = new JButton("Thêm bàn mới");
        btnThem.setBorder(new FlatLineBorder(
                new Insets(10, 20, 10, 20),
                UIManager.getColor("Component.accentColor"),
                2f,
                8
        ));
        btnKhongDatTruoc = new JButton("Không đặt trước");
        btnKhongDatTruoc.setBorder(new FlatLineBorder(
                new Insets(10, 20, 10, 20),
                UIManager.getColor("Component.accentColor"),
                2f,
                8
        ));
        btnDatBanCho = new JButton("Đặt bàn chờ");
        btnDatBanCho.setBorder(new FlatLineBorder(
                new Insets(10, 20, 10, 20),
                UIManager.getColor("Component.accentColor"),
                2f,
                8
        ));
        btnTimPhieu = new JButton("Phiếu đặt bàn");
        btnTimPhieu.setBorder(new FlatLineBorder(
                new Insets(10, 20, 10, 20),
                UIManager.getColor("Component.accentColor"),
                2f,
                8
        ));

        btnDatBanCho.setForeground(UIManager.getColor("Component.accentColor"));
        btnDatBanCho.setFont(new Font("Roboto", Font.BOLD, 14));
        btnTimPhieu.setForeground(UIManager.getColor("Component.accentColor"));
        btnTimPhieu.setFont(new Font("Roboto", Font.BOLD, 14));
        btnThem.setForeground(UIManager.getColor("Component.accentColor"));
        btnThem.setFont(new Font("Roboto", Font.BOLD, 14));
        btnKhongDatTruoc.setForeground(UIManager.getColor("Component.accentColor"));
        btnKhongDatTruoc.setFont(new Font("Roboto", Font.BOLD, 14));

        pnlButton = new JPanel(new MigLayout());
        pnlButton.add(btnThem, "split 2, left");
        pnlButton.add(btnKhongDatTruoc, "left");
        pnlButton.add(btnDatBanCho, "split 2, left");
        pnlButton.add(btnTimPhieu, "wrap, left");

        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã Bàn", "Số Thứ Tự", "Vị Trí", "Số Chỗ", "Trạng Thái"}, 0);
        table.setModel(model);
        table.setFont(new Font("Roboto", Font.PLAIN, 14));
        table.setRowHeight(24);
        table.setAutoCreateRowSorter(true);

        scrollPane = new JScrollPane(table);
        pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(UIManager.getColor("Component.accentColor"), 2),
                "Danh sách bàn",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Roboto", Font.BOLD, 16)
        ));
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        add(pnlButton, BorderLayout.NORTH);
        add(pnlTable, BorderLayout.CENTER);
    }

    private void initEvent() {
        loadTableData();
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(DanhSachBanJPanel.this);
                ThemBanJDialog dialog = new ThemBanJDialog(parentFrame);
                dialog.setVisible(true);
                loadTableData();
            }
        });
        btnKhongDatTruoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Vui lòng chọn một bàn trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String maBan = table.getValueAt(selectedRow, 0).toString();

                Ban ban = banDAO.getBanById(maBan);

                if (ban == null) {
                    JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Không tìm thấy thông tin bàn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ban.getTrangThai().equals("1")) {
                    JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Bàn đã được đặt. Không thể đặt bàn chờ!", "Không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (ban.getTrangThai().equals("2")) {
                    JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Bàn đang sử dụng. Không thể đặt bàn chờ!", "Không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    return;
                }

//                KhongDatTruocDialog phieuDatBanCho = new KhongDatTruocDialog(ban);
//                phieuDatBanCho.setVisible(true);

                loadTableData();
            }
        });
        btnDatBanCho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    // No row is selected, show an error message
                    JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Vui lòng chọn một bàn trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Get the MaBan of the selected row from the table
                String maBan = table.getValueAt(selectedRow, 0).toString(); // Assuming the MaBan is in the first column

                // Use BanDAO to retrieve the Ban object based on MaBan
                Ban ban = banDAO.layThongTinBanQuaMa(maBan); // Fetch the Ban object

                if (ban == null) {
                    JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Không tìm thấy thông tin bàn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(ban.getTrangThai().equals("2")){
                    JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Bàn đang sử dụng. Không thể đặt bàn chờ!", "Không hợp lệ", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Pass the selected Ban object to the dialog
                DatBanChoDialog phieuDatBanCho = new DatBanChoDialog(ban);
                phieuDatBanCho.setVisible(true);

                // Reload the table data if necessary after dialog is closed
                loadTableData();
            }
        });
        btnTimPhieu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimKiemPhieuDatBanCho timPhieu = new TimKiemPhieuDatBanCho();
                timPhieu.setVisible(true);
            }
       }); 
    
    }

    private void loadTableData() {
        List<Ban> danhSachBan = banDAO.dsBan();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Ban ban : danhSachBan) {
            model.addRow(new Object[]{
                ban.getMaBan(),
                ban.getSoThuTu(),
                ban.getViTri(),
                ban.getSoCho(),
                ban.getTrangThai()
            });
        }
    }
}





//public class DanhSachBanJPanel extends JPanel {
//
//    private JPanel pnlButton, pnlTable;
//    private JButton btnThem,btnDatBanCho, btnTimPhieu;
//    private JTable table;
//    private JScrollPane scrollPane;
//    private BanDAO banDAO;
//
//    public DanhSachBanJPanel() {
//        init();
//        initEvent();
//    }
//
//    private void init() {
//        FlatLightLaf.setup();
//
//        setPreferredSize(new Dimension(980, 700));
//        setLayout(new BorderLayout());
//
//        banDAO = new BanDAO();
//
//        btnThem = new JButton("Thêm bàn mới");
//        btnThem.setBorder(new FlatLineBorder(
//                new Insets(10, 20, 10, 20),
//                UIManager.getColor("Component.accentColor"),
//                2f,
//                8
//        ));
//        btnDatBanCho = new JButton("Đặt bàn chờ");
//        btnDatBanCho.setBorder(new FlatLineBorder(
//                new Insets(10, 20, 10, 20),
//                UIManager.getColor("Component.accentColor"),
//                2f,
//                8
//        ));
//        btnTimPhieu = new JButton("Phiếu đặt bàn");
//        btnTimPhieu.setBorder(new FlatLineBorder(
//                new Insets(10, 20, 10, 20),
//                UIManager.getColor("Component.accentColor"),
//                2f,
//                8
//        ));
//        btnThem.setForeground(UIManager.getColor("Component.accentColor"));
//        btnThem.setFont(new Font("Roboto", Font.BOLD, 14));
//        btnDatBanCho.setForeground(UIManager.getColor("Component.accentColor"));
//        btnDatBanCho.setFont(new Font("Roboto", Font.BOLD, 14));
//        btnTimPhieu.setForeground(UIManager.getColor("Component.accentColor"));
//        btnTimPhieu.setFont(new Font("Roboto", Font.BOLD, 14));
//
//        pnlButton = new JPanel(new MigLayout());
//        pnlButton.add(btnThem, "split 2,, left");
//        pnlButton.add(btnDatBanCho, "split 2, left");
//        pnlButton.add(btnTimPhieu, "wrap, left");
////        pnlButton.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.accentColor"), 2)));
//
//        table = new JTable();
//        DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã Bàn", "Số Thứ Tự", "Vị Trí", "Số Chỗ"}, 0);
//        table.setModel(model);
//        table.setFont(new Font("Roboto", Font.PLAIN, 14));
//        table.setRowHeight(24);
//        table.setAutoCreateRowSorter(true);
//
//        scrollPane = new JScrollPane(table);
//        pnlTable = new JPanel(new BorderLayout());
//        pnlTable.setBorder(BorderFactory.createTitledBorder(
//                BorderFactory.createLineBorder(UIManager.getColor("Component.accentColor"), 2),
//                "Danh sách bàn",
//                TitledBorder.DEFAULT_JUSTIFICATION,
//                TitledBorder.DEFAULT_POSITION,
//                new Font("Roboto", Font.BOLD, 16)
//        ));
//        pnlTable.add(scrollPane, BorderLayout.CENTER);
//
//        add(pnlButton, BorderLayout.NORTH);
//        add(pnlTable, BorderLayout.CENTER);
//    }
//
//    private void initEvent() {
//        loadTableData();
//        btnThem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(DanhSachBanJPanel.this);
//                ThemBanJDialog dialog = new ThemBanJDialog(parentFrame);
//                dialog.setVisible(true);
//                loadTableData();
//            }
//        });
//        btnDatBanCho.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            int selectedRow = table.getSelectedRow();
//            if (selectedRow == -1) {
//                // No row is selected, show an error message
//                JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Vui lòng chọn một bàn trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            // Get the MaBan of the selected row from the table
//            String maBan = table.getValueAt(selectedRow, 0).toString(); // Assuming the MaBan is in the first column
//
//            // Use BanDAO to retrieve the Ban object based on MaBan
//            Ban ban = banDAO.layThongTinBanQuaMa(maBan); // Fetch the Ban object
//
//            if (ban == null) {
//                JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Không tìm thấy thông tin bàn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            
//            if(ban.getTrangThai().equals("1")){
//                JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Bàn đã được đặt. Không thể đặt bàn chờ!", "Không hợp lệ", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//            if(ban.getTrangThai().equals("2")){
//                JOptionPane.showMessageDialog(DanhSachBanJPanel.this, "Bàn đang sử dụng. Không thể đặt bàn chờ!", "Không hợp lệ", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//            
//            // Pass the selected Ban object to the dialog
//            DatBanChoDialog phieuDatBanCho = new DatBanChoDialog(ban);
//            phieuDatBanCho.setVisible(true);
//
//            // Reload the table data if necessary after dialog is closed
//            loadTableData();
//        }
//    });
//        btnTimPhieu.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            TimKiemPhieuDatBanCho timPhieu = new TimKiemPhieuDatBanCho();
//            timPhieu.setVisible(true);
//        }
//       }); 
//    }
//
//    private void loadTableData() {
//        List<Ban> danhSachBan = banDAO.dsBan();
//
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.setRowCount(0);
//
//        for (Ban ban : danhSachBan) {
//            model.addRow(new Object[]{
//                ban.getMaBan(),
//                ban.getSoThuTu(),
//                ban.getViTri(),
//                ban.getSoCho()
//            });
//        }
//    }
//    
//}
