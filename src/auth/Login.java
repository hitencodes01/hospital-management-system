package auth;

import database.ConnectionDB;
import admin.*;
import doctor.Doctor;
import background.BGImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame implements ActionListener  {
    Connection con;
    PreparedStatement post;
    ResultSet res;
    JPanel panel;
    JLabel name, pass, role;
    JTextField textFieldName;
    JPasswordField passwordField;
    JButton login;
    JComboBox<String> combRole;

//    constructor function
    public Login(){
//        panel
        panel = new BGImage();
        panel.setLayout(null);

//        establishing connection with database
        ConnectionDB db = new ConnectionDB();
        con = db.connect();

//        fonts
       final Font timesNewRoman20Bold = new Font("Aerial" , Font.BOLD , 20);

//        labels
        name = new JLabel("Username");
        name.setBounds(550,200,100,40);
        name.setFont(timesNewRoman20Bold);
        pass = new JLabel("Password");
        pass.setBounds(550,260,100,40);
        pass.setFont(timesNewRoman20Bold);
        role = new JLabel("Role");
        role.setBounds(550,310,80,40);
        role.setFont(timesNewRoman20Bold);

//        text fields
        textFieldName = new JTextField(30);
        textFieldName.setBounds(700,200,200,40);
        textFieldName.setFont(timesNewRoman20Bold);
        passwordField = new JPasswordField(10);
        passwordField.setBounds(700,260,200,40);
        passwordField.setFont(timesNewRoman20Bold);

//        buttons
        login = new JButton("Login");
        login.setBounds(700 , 400 , 100 ,50 );
        login.addActionListener(this);
        login.setFont(timesNewRoman20Bold);

//        combo box
        String[] items = {"Select","Doctor","Receptionist","Admin"};
        combRole = new JComboBox<>(items);
        combRole.setBounds(700,320,200,30);
        combRole.setFont(timesNewRoman20Bold);
        combRole.addActionListener(this);

//      components adding
        panel.add(name);
        panel.add(pass);
        panel.add(textFieldName);
        panel.add(passwordField);
        panel.add(login);
        panel.add(role);
        panel.add(combRole);


//        frame settings
        add(panel);
        setTitle("Login Window");
        setSize(1650,1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

//    actionPerformed Method
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == login){
            String username = textFieldName.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            Object role = combRole.getSelectedItem();
            assert role != null;
            String role1 = role.toString().trim();

//            validation
            if (username.isEmpty()){
               JOptionPane.showMessageDialog(this,"UserName can not be empty!");
                return;
            }
            else if (password.isEmpty()){
                JOptionPane.showMessageDialog(this,"Password cannot be empty!");
                return;
            }
            else if(role1.equals("Select")){
                    JOptionPane.showMessageDialog(this,"Please Select the Role!");
                    return;
            }
           loginUser(username,password,role1);
        }
    }

//    login user function
    protected void loginUser(String username ,String password,String role){
        if(role.equals("Admin")){
            String query = "select name , password  from admin where name = ? and password = ?  ;";
            verification(username,password,role,query);

        } else if (role.equals("Doctor")) {
            String query = "select *  from doctor where name = ? and password = ?  ;";
            verification(username,password,role,query);
        }
    }

//    verification function
    private void verification(String username ,String password,String role,String query){
        try{
            post = con.prepareStatement(query);
            post.setString(1,username);
            post.setString(2,password);
            res = post.executeQuery();
//       setting empty strings ui++
            textFieldName.setText("");
            passwordField.setText("");
            combRole.setSelectedItem("Select");
//       verification
            if(res.next()){
                String usernameFromDB = res.getString("name");
                String passwordFromDB = res.getString("password");
                if(username.equals(usernameFromDB) && password.equals(passwordFromDB)){
                    String message = "Welcome " + usernameFromDB ;
                    JOptionPane.showMessageDialog(this,message);
                    switch (role) {
                        case "Admin" :
                            new Admin();
                            this.setVisible(false);
                            break;
                        case "Doctor" :
                            new Doctor(res);
                            this.setVisible(false);
                            break;
                    }
                }else {
                    JOptionPane.showMessageDialog(this,"User Not Found!");
                }
            }else {
                JOptionPane.showMessageDialog(this,"Incorrect Username or Password!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
