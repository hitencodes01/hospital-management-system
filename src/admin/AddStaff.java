package admin;

import background.BGImage;
import database.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStaff extends JFrame implements ActionListener {
    JPanel form;
    JTextField id ,name ;
    JComboBox<String> department;
    JButton addStaff ,backToAdmin;
    Connection con;

    AddStaff(){
        Font times30Plain = new Font("Times New Roman" , Font.PLAIN , 30);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 42);
        form = new BGImage();
        form.setLayout(null);
        JLabel Staff = new JLabel("Add Staff Member");
        Staff.setBounds(640,100,500,50);
        Staff.setFont(headingFont);
        JLabel staffId = new JLabel("Staff-Id");
        staffId.setBounds(540,200,100,40);
        staffId.setFont(times30Plain);
        JLabel staffName = new JLabel("Name");
        staffName.setBounds(550,290,100,40);
        staffName.setFont(times30Plain);
        JLabel staffDepartment = new JLabel("Department");
        staffDepartment.setBounds(500,380,200,40);
        staffDepartment.setFont(times30Plain);

        id = new JTextField();
        id.setBounds(700,200,300,50);
        id.setFont(times30Plain);
        name = new JTextField();
        name.setBounds(700,290,300,50);
        name.setFont(times30Plain);
        String [] choices = {"Select","Sanitation","Security","Account","Assistant","Receptionist"};
        department = new JComboBox<>(choices);
        department.setBounds(700,380,300,50);
        department.setFont(times30Plain);
        department.addActionListener(this);

        addStaff = new JButton("Add");
        addStaff.setBounds(550,500,100,40);
        addStaff.setForeground(new Color(255,255,255));
        addStaff.setBackground(new Color(0,0,255));
        addStaff.setFont(times30Plain);
        addStaff.addActionListener(this);

        backToAdmin = new JButton("Back to Admin");
        backToAdmin.setFont(times30Plain);
        backToAdmin.setForeground(new Color(255,255,255));
        backToAdmin.setBackground(new Color(0,0,255));
        backToAdmin.setBounds(700 , 500 , 300 , 40);
        backToAdmin.addActionListener(this);

        form.add(Staff);
        form.add(staffId);
        form.add(staffName);
        form.add(staffDepartment);
        form.add(id);
        form.add(name);
        form.add(department);
        form.add(addStaff);
        form.add(backToAdmin);

        add(form);
        setVisible(true);
        setTitle("Add Staff");
        setSize(1650,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == addStaff){
            int firstText = Integer.parseInt(this.id.getText().trim());
            String secondText  = this.name.getText().trim();
            Object role = department.getSelectedItem();
            assert role != null;
            String thirdText = role.toString().trim();
            if(this.id.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Id cannot be empty");
            } else if (secondText.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Name cannot be empty");
            } else if (thirdText.equals("Select")) {
                JOptionPane.showMessageDialog(this,"Select Department");
            } else {
                try{
                    ConnectionDB db = new ConnectionDB();
                    con = db.connect();
                    String query = "insert into staff(id,name,department) values(?,?,?);";
                    PreparedStatement post = con.prepareStatement(query);
                    post.setInt(1,firstText);
                    post.setString(2,secondText);
                    post.setString(3,thirdText);

                    if(department.equals("Receptionist")){
                        try{
                            PreparedStatement postReceptionist = con.prepareStatement("insert into receptionist values(?,?,?);");
                            postReceptionist.setInt(1,firstText);
                            postReceptionist.setString(1,secondText);
                            postReceptionist.setString(3,"1234");
                            int result = postReceptionist.executeUpdate();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    id.setText("");
                    name.setText("");
                    department.setSelectedItem("Select");
                    int res = post.executeUpdate();
                    if(res>0){
                        JOptionPane.showMessageDialog(this,"Successfully Staff Added");
                        new Admin();
                        setVisible(false);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,"Staff already present");
                }

            }
        }if(event.getSource() == backToAdmin){
            new Admin();
            setVisible(false);
        }
    }

}