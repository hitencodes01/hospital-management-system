package background;

import javax.swing.*;
import java.awt.*;

public class BGImage extends JPanel {
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(new ImageIcon("LoginBG.png").getImage(),0,0,getWidth(),getHeight(),this);
    }
}
