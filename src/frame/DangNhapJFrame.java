package frame;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.ui.FlatLineBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import panel.DangNhapJPanel;
import panel.ImageJPanel;

public class DangNhapJFrame extends JFrame{
    JPanel pnlNen;
    DangNhapJPanel pnlDangNhap;
    JButton btnThoat;
    public DangNhapJFrame(){
        init();
        initEvent();
    }
    
    private void init(){
        FlatArcOrangeIJTheme.setup();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setExtendedState(JFrame.MAXIMIZED_BOTH);
	setUndecorated(true);
        
        pnlNen = new ImageJPanel("src/image/DangNhapBackground.jpg");
        pnlDangNhap = new DangNhapJPanel();
        pnlNen.setLayout(new MigLayout("fill"));
        pnlNen.add(pnlDangNhap, "align center");
        btnThoat = new JButton();
        btnThoat.putClientProperty("JButton.buttonType", "roundRect");
        btnThoat.setIcon(new FlatSVGIcon("icon/shutdown.svg"));
        btnThoat.setOpaque(false);
        pnlNen.add(btnThoat, "pos 1.0al 1.0al, w 40:40:40, h 40:40:40");
        setContentPane(pnlNen);
    }
    
    private void initEvent(){
        btnThoat.addActionListener(e -> {
            dispose();
        });
    }
}
