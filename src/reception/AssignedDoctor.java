package reception;

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
import java.util.HashMap;
import java.util.List;


public class AssignedDoctor extends JFrame implements ActionListener {
    Connection con;
    ResultSet resultSet;
    JPanel panel;
    JButton assigned;
    JComboBox<String> doctors;
    HashMap<String,Integer> doctorsData;
    int id;
    AssignedDoctor(int id){
        this.id = id;
        doctorsData = new HashMap<>();
        con = new ConnectionDB().connect();
        panel = new BGImage();
        panel.setLayout(null);
        getDoctors();
        JLabel selectLabel = new JLabel("Select Doctor");
        selectLabel.setFont(new Font("Times New Roman",Font.BOLD,50));
        selectLabel.setBounds(300,200,100,30);
        panel.add(selectLabel);
        String [] doctorsName = {"Select","hiten","Aman"};
        doctors = new JComboBox<String>(doctorsName);
        doctors.setBounds(500,200,200,50);
        doctors.setFont(new Font("Times New Roman",Font.BOLD,30));
        panel.add(doctors);
        assigned = new MyButton("Assign",50);
        assigned.setBounds(500,400,100,100);
        assigned.addActionListener(this);
        panel.add(assigned);
        add(panel);
        setTitle("Assigning Doctor");
        setVisible(true);
        setSize(1650,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource()==assigned){
            Object obj = doctors.getSelectedItem();
            assert obj != null;
            String selectedDoctor = String.valueOf(obj);
            try{
                uploader(id,selectedDoctor);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void uploader(int id,String str){
        try{
            PreparedStatement update = con.prepareStatement("update patient set assigned=? , doctorID = ? , doctorName = ? where id=?;");
            update.setBoolean(1,true);
            update.setInt(2,doctorsData.get(str));
            update.setString(3,str);
            update.setInt(4,id);
            int res = update.executeUpdate();
            if(res>0){
                JOptionPane.showMessageDialog(this,str +" Assigned Successfully");
                doctors.setSelectedItem("Select");
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void getDoctors(){
        try{
            PreparedStatement get = con.prepareStatement("select id, name from doctor;");
            resultSet = get.executeQuery();
            while (resultSet.next()){
                doctorsData.put(resultSet.getString("name"),resultSet.getInt("id"));

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
