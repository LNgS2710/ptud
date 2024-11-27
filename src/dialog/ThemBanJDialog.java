package dialog;

import dao.BanDAO;
import entity.Ban;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemBanJDialog extends JDialog {

    private JTextField txtMaBan, txtSoCho;
    private JComboBox<String> cboViTri;
    private JButton btnAdd, btnCancel;
    private BanDAO banDAO;

    public ThemBanJDialog(Frame owner) {
        super(owner, "Thêm Bàn", true);
        banDAO = new BanDAO();
        init();
        initEvent();
    }

    private void init() {
        setSize(400, 250);
        setLocationRelativeTo(getOwner());
        setLayout(new MigLayout("fill", "[][grow,fill]", "[][grow,fill]"));

        JPanel pnlForm = new JPanel(new MigLayout("insets 10", "[right]20[fill,grow]"));
        
        pnlForm.add(new JLabel("Mã Bàn:"), "cell 0 0");
        txtMaBan = new JTextField();
        pnlForm.add(txtMaBan, "cell 1 0, growx");

        pnlForm.add(new JLabel("Vị Trí:"), "cell 0 1");
        cboViTri = new JComboBox<>(new String[]{"Ngoài trời", "Trong nhà"});
        pnlForm.add(cboViTri, "cell 1 1, growx");

        pnlForm.add(new JLabel("Số Chỗ:"), "cell 0 2");
        txtSoCho = new JTextField();
        pnlForm.add(txtSoCho, "cell 1 2, growx");

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Thêm");
        btnCancel = new JButton("Hủy");
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnCancel);

        add(pnlForm, "grow, wrap");
        add(pnlButtons, "center, wrap");
    }

    private void initEvent() {
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themBan();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void themBan() {
        String maBan = txtMaBan.getText();
        String viTri = cboViTri.getSelectedItem().toString();
        int soCho;

        try {
            soCho = Integer.parseInt(txtSoCho.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số Chỗ không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Ban newBan = new Ban(maBan, 0, viTri, soCho, "false");

        boolean isSuccess = banDAO.themBan(newBan);

        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Thêm bàn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm bàn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

