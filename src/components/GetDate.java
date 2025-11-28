package components;

import admin.ViewPatientByAdmin;
import background.BGImage;
import reception.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetDate extends JFrame implements ActionListener {
    JPanel panel;
    JButton get;
    JComboBox<String> dayBox , yearBox, monthBox;
    String currentDate;

    public GetDate(){
        panel = new BGImage();
        panel.setLayout(null);
        Font times30Bold = new Font("Times New Roman",Font.BOLD,30);
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i-1] = String.valueOf(i);

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun",
                "Jul","Aug","Sep","Oct","Nov","Dec"};

        String[] years = new String[100];
        int start = 1980;
        for (int i = 0; i < 100; i++) years[i] = "" + (start + i);
        JLabel enterDay = new JLabel("Enter Day:");
        enterDay.setBounds(500,200,200,50);
        JLabel enterMonth = new JLabel("Enter Month:");
        enterMonth.setBounds(500,280,200,50);
        JLabel enterYear = new JLabel("Enter Year:");
        enterYear.setBounds(500,360,200,50);
        dayBox = new JComboBox<>(days);
        dayBox.setBounds(750,200,200,50);
        dayBox.setFont(times30Bold);
        monthBox = new JComboBox<>(months);
        monthBox.setBounds(750,280,200,50);
        monthBox.setFont(times30Bold);
        yearBox = new JComboBox<>(years);
        yearBox.setBounds(750,360,200,50);
        yearBox.setFont(times30Bold);
        get = new MyButton("Get Date",40);
        get.setBounds(500,440,300,50);
        get.addActionListener(this);

        panel.add(enterYear);
        panel.add(enterDay);
        panel.add(enterMonth);
        panel.add(dayBox);
        panel.add(monthBox);
        panel.add(yearBox);
        panel.add(get);
        add(panel);
        setVisible(true);
        setSize(1650,1080);
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
