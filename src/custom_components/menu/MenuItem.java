package custom_components.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuItem extends JButton{
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHasSubmenu() {
        return hasSubmenu;
    }

    public void setHasSubmenu(boolean hasSubmenu) {
        this.hasSubmenu = hasSubmenu;
    }

    public int getSubMenuIndex() {
        return subMenuIndex;
    }

    public void setSubMenuIndex(int subMenuIndex) {
        this.subMenuIndex = subMenuIndex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    public float getAnimate() {
        return animate;
    }

    public void setAnimate(float animate) {
        this.animate = animate;
    }
    
    private int index;
    private boolean hasSubmenu;
    private int subMenuIndex;
    private int length;
    private float animate;

    
    
    public MenuItem(String name, int index, boolean hasSubmenu){
        super(name);
        this.index = index;
        this.hasSubmenu = hasSubmenu;
        setContentAreaFilled(false);
        setHorizontalAlignment(SwingConstants.LEFT);
        setFont(new Font("Segoe UI Black", Font.BOLD,  12));
        setBorder(new EmptyBorder(9, 10, 9, 10));
        setForeground(new Color(255, 255, 255));
        setIconTextGap(10);
    }
    
    public void initSubMenu(int subMenuIndex, int length){
        this.subMenuIndex = subMenuIndex;
        this.length = length;
        setBorder(new EmptyBorder(9, 33, 9, 10));
        setBackground(new Color(245, 245, 245));
        setForeground(new Color(51, 51, 51));
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(length != 0){
            g2.setColor(new Color(200, 200, 200));
            if(subMenuIndex == 1){
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            } else if (subMenuIndex == length - 1){
                g2.drawLine(18, 0, 18, getHeight() / 2);
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            } else {
                g2.drawLine(18, 0, 18, getHeight());
                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            }
        } else if(hasSubmenu){
            g2.setColor(new Color(255, 255, 255));
            int arrowWidth = 8;
            int arrowHeight = 4;
            Path2D p = new Path2D.Double();
            p.moveTo(0, arrowHeight * animate);
            p.lineTo(arrowWidth / 2, (1f - animate) * arrowHeight);
            p.lineTo(arrowWidth, arrowHeight * animate);
            g2.translate(getWidth() - arrowWidth - 15, (getHeight() - arrowHeight) / 2);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.draw(p);
        }
        g2.dispose();
    }
    
    
}
