package custom_components.menu;

import java.awt.*;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.*;

public class MenuAnimation {
    
    public static void showMenu(Component com, MenuItem item, MigLayout layout, boolean isShow){
        int height = com.getPreferredSize().height;
        Animator animator = new Animator(300, new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction) {
                float f = isShow ? fraction : 1f - fraction;
                layout.setComponentConstraints(com, "h " + height * f + "!");
                item.setAnimate(f);
                com.revalidate();
                item.repaint();
            }
            
        });
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        animator.start();
    }
}
