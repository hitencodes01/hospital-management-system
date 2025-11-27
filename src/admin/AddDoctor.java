package admin;

import background.BGImage;
import database.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddDoctor extends JFrame implements ActionListener {
    JPanel form;
    JTextField id, name, spectra, password;
    JButton addDoc ,backToAdmin;
    Connection con;
    PreparedStatement pmst ;
    AddDoctor(){
        Font times30Plain = new Font("Times New Roman" , Font.PLAIN , 30);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 42);
        form = new BGImage();
        form.setLayout(null);
        JLabel Doctor = new JLabel("Add Doctor");
        Doctor.setBounds(640,100,500,50);
        Doctor.setFont(headingFont);
        JLabel docId = new JLabel("Doctor-Id");
        docId.setBounds(530,200,150,40);
        docId.setFont(times30Plain);
        JLabel doctorName = new JLabel("Name");
        doctorName.setBounds(550,290,100,40);
        doctorName.setFont(times30Plain);
        JLabel specialization = new JLabel("Specialization");
        specialization.setBounds(500,380,200,40);
        specialization.setFont(times30Plain);
        JLabel doctorPassword = new JLabel("Password");
        doctorPassword.setBounds(530,470,200,40);
        doctorPassword.setFont(times30Plain);
        id = new JTextField();
        id.setBounds(700,200,300,50);
        id.setFont(times30Plain);
        name = new JTextField();
        name.setBounds(700,290,300,50);
        name.setFont(times30Plain);
        spectra = new JTextField();
        spectra.setBounds(700,380,300,50);
        spectra.setFont(times30Plain);
        password = new JTextField();
        password.setBounds(700,470,300,50);
        password.setFont(times30Plain);
        addDoc = new JButton("Add");
        addDoc.setBounds(550,560,100,40);
        addDoc.setForeground(new Color(255,255,255));
        addDoc.setBackground(new Color(0,0,255));
        addDoc.setFont(times30Plain);
        addDoc.addActionListener(this);
        backToAdmin = new JButton("Back to Admin");
        backToAdmin.setFont(times30Plain);
        backToAdmin.setForeground(new Color(255,255,255));
        backToAdmin.setBackground(new Color(0,0,255));
        backToAdmin.setBounds(700 , 560 , 300 , 40);
        backToAdmin.addActionListener(this);
        form.add(Doctor);
        form.add(docId);
        form.add(doctorName);
        form.add(specialization);
        form.add(doctorPassword);
        form.add(id);
        form.add(name);
        form.add(spectra);
        form.add(password);
        form.add(addDoc);
        form.add(backToAdmin);
        add(form);
        setVisible(true);
        setSize(1650,1080);
        setTitle("Add Doctor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == addDoc){
            int idDoctor = Integer.parseInt(this.id.getText());
            String firstText = this.name.getText();
            String secondText  = spectra.getText();
            String thirdText = this.password.getText();
            if(firstText.isEmpty()){
                JOptionPane.showMessageDialog(this,"Name cannot be empty");
            } else if (secondText.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Specialization cannot be empty");
            } else if (thirdText.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Password cannot be empty");
            } else if (this.id.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this,"ID cannot be empty");
            } else {
                try{
                    ConnectionDB db = new ConnectionDB();
                    con = db.connect();
//                    inserting into doctors table
                    String query = "insert into doctor(id,name,specialization,password) values(?,?,?,?);";
                    pmst = con.prepareStatement(query);
                    pmst.setInt(1,idDoctor);
                    pmst.setString(2,firstText);
                    pmst.setString(3,secondText);
                    pmst.setString(4,thirdText);
                    id.setText("");
                    name.setText("");
                    spectra.setText("");
                    password.setText("");
                    int res = pmst.executeUpdate();
                    if(res>0){
                        JOptionPane.showMessageDialog(this,"doctor.Doctor Added Succesfully");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,"doctor.Doctor ID already existed!");
                }
            }
        }if(event.getSource() == backToAdmin){
            new Admin();
            setVisible(false);
        }
    }

}