package components;

import admin.ViewPatientByAdmin;
import background.BGImage;
import reception.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetDate extends JFrame implements ActionListener {
    JPanel panel;
    JButton get;
    JComboBox<String> dayBox , yearBox, monthBox;
    String currentDate;

    public GetDate(){
        panel = new BGImage();
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i-1] = String.valueOf(i);

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun",
                "Jul","Aug","Sep","Oct","Nov","Dec"};

        String[] years = new String[100];
        int start = 1980;
        for (int i = 0; i < 100; i++) years[i] = "" + (start + i);

        dayBox = new JComboBox<>(days);
        monthBox = new JComboBox<>(months);
        yearBox = new JComboBox<>(years);
        get = new JButton("Get Date");
        get.addActionListener(this);

        panel.add(dayBox);
        panel.add(monthBox);
        panel.add(yearBox);
        panel.add(get);
        add(panel);
        setVisible(true);
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public  void actionPerformed(ActionEvent event){
        if (event.getSource()==get){
            String d = (String) dayBox.getSelectedItem();
            int m = monthBox.getSelectedIndex() + 1;
            String y = (String) yearBox.getSelectedItem();
            currentDate =  d + "-" + m + "-" + y;
            new ViewPatientByAdmin(currentDate);

            this.setVisible(false);
        }
    }

}
