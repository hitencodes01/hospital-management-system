package doctor;

import admin.ViewDoctor;
import background.BGImage;
import database.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorProfile extends JFrame implements ActionListener {

    Connection con;
    ResultSet rs;
    JPanel profilePanel;
    JLabel labelName, labelPassword, labelAge, labelEmail , labelContact , labelDegree , labelAddress;
    JTextField tname,tpassword,tage,temail,tcontact,tdegree;
    JTextArea taddress;
    JButton update , back;
    int id, choice;

//    constructor
    public DoctorProfile(ResultSet doctor,int choice){
        try{
//            connection
            con = new ConnectionDB().connect();
            rs = doctor;
            id = rs.getInt("id");
            this.choice = choice;
//            panel with bgImage
            profilePanel = new BGImage();
            profilePanel.setLayout(new GridBagLayout());
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15,15,15,15);
            gbc.anchor = GridBagConstraints.CENTER;

            JLabel title = new JLabel("Your Details");
            title.setFont(new Font("Arial", Font.BOLD, 28));
            title.setForeground(Color.BLACK);
            
            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            profilePanel.add(title,gbc);
            gbc.gridwidth = 1;
//            r1
            labelName = new JLabel("Name");
            labelName.setFont(new Font("Times New Roman", Font.BOLD, 20));
            labelName.setForeground(Color.BLACK);
            tname = new JTextField(20);
            tname.setText(rs.getString("name"));
            tname.setFont(new Font("Times New Roman", Font.BOLD, 20));
            tname.setForeground(Color.BLACK);
            gbc.gridy = 1;
            gbc.gridx = 0;
            profilePanel.add(labelName, gbc);
            gbc.gridx = 1;
            profilePanel.add(tname, gbc);
//            r2
            labelPassword = new JLabel("Password");
            labelPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
            labelPassword.setForeground(Color.BLACK);
            tpassword = new JTextField(20);
            tpassword.setText(rs.getString("password"));
            tpassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
            tpassword.setForeground(Color.BLACK);
            gbc.gridy = 2;
            gbc.gridx = 0;
            profilePanel.add(labelPassword, gbc);
            gbc.gridx = 1;
            profilePanel.add(tpassword, gbc);
//            r3
            labelAge = new JLabel("Age");
            labelAge.setFont(new Font("Times New Roman", Font.BOLD, 20));
            labelAge.setForeground(Color.BLACK);
            tage = new JTextField(20);
            tage.setText(rs.getString("age"));
            tage.setFont(new Font("Times New Roman", Font.BOLD, 20));
            tage.setForeground(Color.BLACK);
            gbc.gridy = 3;
            gbc.gridx = 0;
            profilePanel.add(labelAge, gbc);
            gbc.gridx = 1;
            profilePanel.add(tage, gbc);
//            r4
            labelEmail = new JLabel("E-Mail");
            labelEmail.setFont(new Font("Times New Roman", Font.BOLD, 20));
            labelEmail.setForeground(Color.BLACK);
            temail = new JTextField(20);
            temail.setText(rs.getString("email"));
            temail.setFont(new Font("Times New Roman", Font.BOLD, 20));
            temail.setForeground(Color.BLACK);
            gbc.gridy = 4;
            gbc.gridx = 0;
            profilePanel.add(labelEmail, gbc);
            gbc.gridx = 1;
            profilePanel.add(temail, gbc);
 //            r5
            labelContact = new JLabel("Contact");
            labelContact.setFont(new Font("Times New Roman", Font.BOLD, 20));
            labelContact.setForeground(Color.BLACK);
            tcontact = new JTextField(20);
            tcontact.setText(rs.getString("contact"));
            tcontact.setFont(new Font("Times New Roman", Font.BOLD, 20));
            tcontact.setForeground(Color.BLACK);
            gbc.gridy = 5;
            gbc.gridx = 0;
            profilePanel.add(labelContact, gbc);
            gbc.gridx = 1;
            profilePanel.add(tcontact, gbc);
 //            r6
            labelDegree = new JLabel("Degree");
            labelDegree.setFont(new Font("Times New Roman", Font.BOLD, 20));
            labelDegree.setForeground(Color.BLACK);
            tdegree = new JTextField(20);
            tdegree.setText(rs.getString("degree"));
            tdegree.setFont(new Font("Times New Roman", Font.BOLD, 20));
            tdegree.setForeground(Color.BLACK);
            gbc.gridy = 6;
            gbc.gridx = 0;
            profilePanel.add(labelDegree, gbc);
            gbc.gridx = 1;
            profilePanel.add(tdegree, gbc);
 //            r7
            labelAddress = new JLabel("Address");
            labelAddress.setFont(new Font("Times New Roman", Font.BOLD, 20));
            labelAddress.setForeground(Color.BLACK);
            taddress = new JTextArea(3,20);
            taddress.setText(rs.getString("address"));
            taddress.setFont(new Font("Times New Roman", Font.BOLD, 20));
            taddress.setForeground(Color.BLACK);
            gbc.gridy = 7;
            gbc.gridx = 0;
            profilePanel.add(labelAddress, gbc);
            gbc.gridx = 1;
            profilePanel.add(taddress, gbc);
//            r8
            update = new JButton("Update");
            update.setFont(new Font("Times New Roman", Font.BOLD, 20));
            update.setForeground(Color.BLACK);
            update.addActionListener(this);
            back = new JButton("Back");
            back.setFont(new Font("Times New Roman", Font.BOLD, 20));
            back.setForeground(Color.BLACK);
            back.addActionListener(this);

            gbc.gridy = 8;
            gbc.gridx = 0;
            profilePanel.add(update, gbc);
            gbc.gridx = 1;
            profilePanel.add(back, gbc);
            
//            frame setting
            add(profilePanel);
            setVisible(true);
            setTitle("Update Profile");
            setSize(800, 800);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    override action function
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            try {
                String query = "update doctor set name = ? , password = ? , age = ? , email = ? , contact = ? , degree = ? , address = ? where id = ?;";
                PreparedStatement put = con.prepareStatement(query);
                put.setString(1, tname.getText());
                put.setString(2, tpassword.getText());
                put.setString(3, tage.getText());
                put.setString(4, temail.getText());
                put.setString(5, tcontact.getText());
                put.setString(6, tdegree.getText());
                put.setString(7, taddress.getText());
                put.setInt(8,id);
                int result = put.executeUpdate();
                if (result > 0) {
                    try{
                        PreparedStatement get = con.prepareStatement("select * from doctor where id = ? ;");
                        get.setInt(1,id);
                        rs = get.executeQuery();
                        if(rs.next()){
                            JOptionPane.showMessageDialog(this, "Updated Successfully");
                            if(choice==1){
                                new Doctor(rs);
                            } else if (choice==2) {
                                new ViewDoctor();
                            }
                            setVisible(false);                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==back){
            if(choice==1){
                new Doctor(rs);
            } else if (choice==2) {
                new ViewDoctor();
            }
            setVisible(false);
        }
    }

}
