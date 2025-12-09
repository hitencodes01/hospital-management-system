package reception;

import auth.Login;
import background.BGImage;
import components.MyButton;
import database.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reception extends JFrame implements ActionListener {

    JPanel panel1 ;
    JButton addBtn , viewBtn ,backBtn;
    JLabel tP;
    Connection con;
    int total;
    String formatDate;

   public Reception(){
        con = new ConnectionDB().connect();
        panel1 = new BGImage();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,20,10,20);
//        row 0
        JLabel heading = new JLabel("XYZ Hospital");
        heading.setFont(new Font("Times New Roman",Font.BOLD,50));
        heading.setForeground(Color.BLUE);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        panel1.add(heading,gbc);
//      row 1
        JLabel labelDate = new JLabel("Date");
        labelDate.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel1.add(labelDate,gbc);
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter formatDateObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatDate = dateObj.format(formatDateObj);
        JLabel currentDate = new JLabel(formatDate);
        currentDate.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = 1;
        gbc.gridx = 2;
        panel1.add(currentDate,gbc);
//      row 2
        JLabel totalPatients = new JLabel("Total Patients");
        totalPatients.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel1.add(totalPatients,gbc);
        loader();
        tP = new JLabel(String.valueOf(total));
        tP.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridx = 2;
        panel1.add(tP,gbc);
//        row 3
        addBtn = new MyButton("Add Patient",20);
        addBtn.addActionListener(this);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel1.add(addBtn,gbc);
        viewBtn = new MyButton("View Patients",20);
        viewBtn.addActionListener(this);
        gbc.gridx = 2;
        panel1.add(viewBtn,gbc);
//        row 4
        backBtn = new MyButton("Back",20);
        backBtn.addActionListener(this);
        gbc.gridy = 0;
        gbc.gridx = 6;
        gbc.gridwidth = 2;
        panel1.add(backBtn,gbc);

//        frame setting
        add(panel1,BorderLayout.CENTER);
        setTitle("Reception Window");
        setSize(1650,1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void loader(){
        try{
            PreparedStatement getPatientCount = con.prepareStatement("select count(id) as total from patient where admissionDate = ?;");
            getPatientCount.setString(1,formatDate);
            ResultSet rs = getPatientCount.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void actionPerformed(ActionEvent event){
            if(event.getSource()==addBtn){
                new AddPatient(formatDate);
                setVisible(false);
            }
            if(event.getSource()==viewBtn){
                new ViewPatients(formatDate);
                setVisible(false);
            }
            if(event.getSource()==backBtn){
                new Login();
                setVisible(false);
            }
    }

    public static void main(String[] args){
        new Reception();
    }
}
