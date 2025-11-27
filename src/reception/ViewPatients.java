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

class ViewPatients extends JFrame implements ActionListener {

    Connection con;
    JPanel panel;
    JButton back;
    GridBagConstraints gbc;
    ResultSet resultSet;
    int y = 0;
    int patientId;
    String currentDate;

    public ViewPatients(String currentDate){
        this.currentDate = currentDate;
        con = new ConnectionDB().connect();
        panel = new BGImage();
        panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);
        JLabel heading = new JLabel("Patients");
        heading.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        panel.add(heading,gbc);
        back = new MyButton("Back",20);
        back.addActionListener(this);
        gbc.gridx = 4;
        panel.add(back,gbc);
//          row1
        JLabel id = new JLabel("ID");
        id.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = ++y;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(id,gbc);
        JLabel name = new JLabel("Name");
        name.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panel.add(name,gbc);
        JLabel disease = new JLabel("Disease");
        disease.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 4;
        gbc.gridwidth = 4;
        panel.add(disease,gbc);
        JLabel pDate = new JLabel("Date");
        pDate.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 8;
        gbc.gridwidth = 2;
        panel.add(pDate,gbc);
        JLabel pAge = new JLabel("Age");
        pAge.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 10;
        gbc.gridwidth = 1;
        panel.add(pAge,gbc);
        JLabel pContact = new JLabel("Contact");
        pContact.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 11;
        gbc.gridwidth = 2;
        panel.add(pContact,gbc);
        JLabel docAssigned = new JLabel("Assigned");
        docAssigned.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 13;
        gbc.gridwidth = 1;
        panel.add(docAssigned,gbc);
        JLabel pRoom = new JLabel("Room");
        pRoom.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 14;
        gbc.gridwidth = 1;
        panel.add(pRoom,gbc);

//        multiple row
        try{
            resultSet = loader();
            while (resultSet.next()){
                generator(resultSet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        add(panel);
        setVisible(true);
        setSize(1650,1080);
        setTitle("Staff List");
    }

    protected ResultSet loader(){
        try{
            PreparedStatement getPatient = con.prepareStatement("select * from patient where admissionDate=?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            getPatient.setString(1,currentDate);
            return getPatient.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void generator(ResultSet rs){
        gbc.gridy = y++;
        gbc.insets = new Insets(10, 20, 10, 20);
        try{
            patientId = rs.getInt("id");
            JLabel id = new JLabel(String.valueOf(patientId));
            id.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridy = y;
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            panel.add(id,gbc);
            JLabel name = new JLabel(rs.getString("name"));
            name.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridx = 1;
            gbc.gridwidth = 3;
            panel.add(name,gbc);
            JLabel condition = new JLabel(rs.getString("patientCondition"));
            condition.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridx = 4;
            gbc.gridwidth = 4;
            panel.add(condition,gbc);
            JLabel date = new JLabel(rs.getString("admissionDate"));
            date.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridx = 8;
            gbc.gridwidth = 2;
            panel.add(date,gbc);
            JLabel age = new JLabel(rs.getString("age"));
            age.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridy = y;
            gbc.gridx = 10;
            gbc.gridwidth = 1;
            panel.add(age,gbc);
            JLabel contact = new JLabel(rs.getString("contact"));
            contact.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridy = y;
            gbc.gridx = 11;
            gbc.gridwidth = 2;
            panel.add(contact,gbc);
            JLabel docAssigned = new JLabel(rs.getString("assigned"));
            docAssigned.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridy = y;
            gbc.gridx = 13;
            gbc.gridwidth = 1;
            panel.add(docAssigned,gbc);
            JLabel roomNo = new JLabel(rs.getString("room"));
            roomNo.setFont(new Font("Times New Roman",Font.BOLD,20));
            gbc.gridy = y;
            gbc.gridx = 14;
            gbc.gridwidth = 1;
            panel.add(roomNo,gbc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void actionPerformed(ActionEvent event){
       if(event.getSource()==back){
           new Reception();
           setVisible(false);
       }
    }
}

