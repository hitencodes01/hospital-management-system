package components;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {
   public MyButton(String text,int size){
        super(text);
        setFont(new Font("Times New Roman", Font.BOLD, size));
        setForeground(Color.WHITE);        // text color
        setBackground(Color.BLUE); // blue shade
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);  // avoid white inner box
        setOpaque(true);              // enable custom background color
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isRollover()) {
            setBackground(new Color(0, 90, 255));
        } else {
            setBackground(new Color(0, 70, 200));
        }
        super.paintComponent(g);
    }
}
