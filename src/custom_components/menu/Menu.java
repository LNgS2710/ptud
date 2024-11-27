package custom_components.menu;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class Menu extends JComponent {

    private MenuEvent event;
    private MigLayout layout;
    private String[][] menuItems = new String[][]{
        {"MÀN HÌNH CHÍNH"},
        {"BÀN", "PHIẾU ĐẶT BÀN", "DANH SÁCH BÀN"},
        {"MÓN ĂN", "PHIẾU ĐẶT MÓN", "THỰC ĐƠN"},
        {"KHUYẾN MÃI", "DANH SÁCH KHUYẾN MÃI"},
        {"NHÂN VIÊN", "DANH SÁCH NHÂN VIÊN", "DANH SÁCH TÀI KHOẢN"},
        {"KHÁCH HÀNG", "DANH SÁCH KHÁCH HÀNG"},
        {"THỐNG KÊ"},
        {"HÓA ĐƠN"}
    };

    public MenuEvent getEvent() {
        return event;
    }

    public void setEvent(MenuEvent event) {
        this.event = event;
    }

    public Menu() {
        init();
    }

    public void init() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        for (int i = 0; i < menuItems.length; i++) {
            addMenu(menuItems[i][0], i);
        }
    }

    private Icon getIcon(int index) {
        URL url = getClass().getResource("/icon/" + index + ".svg");
        if (url != null) {
            return new FlatSVGIcon(url);
        } else {
            return null;
        }
    }

    private void addMenu(String menuName, int index) {
        int length = menuItems[index].length;
        MenuItem item = new MenuItem(menuName, index, length > 1);
        Icon icon = getIcon(index);
        if (icon != null) {
            item.setIcon(icon);
        }
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (length > 1) {
                    if (!item.isSelected()) {
                        item.setSelected(true);
                        addSubMenu(item, index, length, getComponentZOrder(item));
                    } else {
                        hideMenu(item, index);
                        item.setSelected(false);
                    }
                } else {
                    if (event != null) {
                        event.selected(index, 0);
                    }
                }
            }

        });
        add(item);
        revalidate();
        repaint();
    }

    private void addSubMenu(MenuItem item, int index, int length, int indexZOrder) {
        JPanel pnl = new JPanel(new MigLayout("wrap 1, fillx, gapy 0, inset 0", "fill"));
        pnl.setName(index + "");
        pnl.setOpaque(false);
        for (int i = 1; i < length; i++) {
            MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
            subItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (event != null) {
                        event.selected(index, subItem.getIndex());
                    }
                }
            });
            subItem.initSubMenu(i, length);
            pnl.add(subItem);
        }
        add(pnl, "h 0!", indexZOrder + 1);
        revalidate();
        repaint();
        MenuAnimation.showMenu(pnl, item, layout, true);
    }

    private void hideMenu(MenuItem item, int index) {
        for (Component com : getComponents()) {
            if (com instanceof JPanel && com.getName() != null && com.getName().equals(index + "")) {
                com.setName(null);
                MenuAnimation.showMenu(com, item, layout, false);
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0x78B3CE));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(g);
    }

}
