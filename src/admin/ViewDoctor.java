package admin;

import background.BGImage;
import components.MyButton;
import database.ConnectionDB;
import doctor.DoctorProfile;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewDoctor extends JFrame implements ActionListener {
    Connection con;
    private ResultSet resultSet;
    JPanel viewDoctor;
    GridBagConstraints gbc;
    JButton viewDoc , removeDoc, back;
    int y = 0;
    int docId;

    private List<JButton> viewButtons = new ArrayList<>();
    private List<JButton> removeButtons = new ArrayList<>();

    public ViewDoctor(){
//        connection
        con = new ConnectionDB().connect();
//        panel and gbc
        viewDoctor = new BGImage();
        viewDoctor.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);
        JLabel heading = new JLabel("Doctors");
        heading.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        viewDoctor.add(heading,gbc);
        back = new MyButton("Back",20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
                setVisible(false);
            }
        });
        gbc.gridx = 4;
        viewDoctor.add(back,gbc);

//        r1
        gbc.gridy = y++;
        JLabel id = new JLabel("ID");
        id.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        viewDoctor.add(id,gbc);
        JLabel name = new JLabel("Name");
        name.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        viewDoctor.add(name,gbc);
        JLabel specialization = new JLabel("Specialization");
        specialization.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridx = 4;
        gbc.gridwidth = 3;
        viewDoctor.add(specialization,gbc);
        JLabel view = new JLabel("Event");
        view.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridx = 7;
        gbc.gridwidth = 1;
        viewDoctor.add(view,gbc);
        JLabel remove = new JLabel("Delete");
        remove.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridx = 8;
        gbc.gridwidth = 1;
        viewDoctor.add(remove,gbc);

//        multiple row-generator
//        loader function
        resultSet = loader();
        try {
            while (resultSet.next()) {
                rowGenerator(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        frame setting
        add(viewDoctor);
        setSize(1650,1080);
        setVisible(true);
        setTitle("Doctors List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private ResultSet loader(){
        try {
            PreparedStatement get = con.prepareStatement(
                    "SELECT * FROM doctor",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            return get.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void rowGenerator(ResultSet doctor){
        try{
            int doctorId = doctor.getInt("id");
            gbc.gridy = y++;
            gbc.insets = new Insets(10, 20, 10, 20);
            JLabel id = new JLabel(String.valueOf(doctor.getInt("id")));
            id.setFont(new Font("Times New Roman",Font.PLAIN,30));
            gbc.gridy = y++;
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            viewDoctor.add(id,gbc);
            JLabel name = new JLabel(doctor.getString("name"));
            name.setFont(new Font("Times New Roman",Font.PLAIN,30));
            gbc.gridx = 1;
            gbc.gridwidth = 3;
            viewDoctor.add(name,gbc);
            JLabel specialization = new JLabel(doctor.getString("specialization"));
            specialization.setFont(new Font("Times New Roman",Font.PLAIN,30));
            gbc.gridx = 4;
            gbc.gridwidth = 3;
            viewDoctor.add(specialization,gbc);
            viewDoc = new MyButton("View",20);
            viewDoc.setFont(new Font("Times New Roman",Font.PLAIN,10));
            viewDoc.putClientProperty("id", doctorId);
            viewDoc.setActionCommand("view");
            viewDoc.addActionListener(this);
            gbc.gridx = 7;
            gbc.gridwidth = 1;
            viewDoctor.add(viewDoc,gbc);
            viewButtons.add(viewDoc);
            removeDoc = new MyButton("Remove",20);
            removeDoc.setFont(new Font("Times New Roman",Font.PLAIN,10));
            removeDoc.putClientProperty("id", doctorId);
            removeDoc.setActionCommand("remove");
            removeDoc.addActionListener(this);
            removeDoc.setBackground(Color.RED);
            removeDoc.setForeground(Color.WHITE);
            gbc.gridx = 8;
            viewDoctor.add(removeDoc,gbc);
            removeButtons.add(removeDoc);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void viewDoctorProfile(int id){
        try{
            PreparedStatement getDoctor = con.prepareStatement("select * from doctor where id = ? ;");
            getDoctor.setInt(1,id);
            ResultSet doctorResultSet = getDoctor.executeQuery();
            if(doctorResultSet.next()){
                new DoctorProfile(doctorResultSet,2);
            }
            setVisible(false);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void removeDoctor(int id){
        try{
            PreparedStatement getDoctor = con.prepareStatement("delete from doctor where id = ? ;");
            getDoctor.setInt(1,id);
            int result = getDoctor.executeUpdate();
            if(result>0){
                JOptionPane.showMessageDialog(viewDoctor,"Successfully Removed");
            }
            new ViewDoctor();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void actionPerformed(ActionEvent event){
        JButton btn = (JButton) event.getSource();
        int id = (int) btn.getClientProperty("id");
        if ("view".equals(btn.getActionCommand())) {
            viewDoctorProfile(id);
        } else if ("remove".equals(btn.getActionCommand())) {
            removeDoctor(id);
        }
    }

}
