package admin;

import background.BGImage;
import database.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewStaff extends JFrame implements ActionListener {
    Connection con;
    JPanel panel;
    JButton remove ,back;
    GridBagConstraints gbc;
    ResultSet resultSet;
    int y = 0;
    protected List<JButton> removeStaff = new ArrayList<>();

    ViewStaff(){
        con = new ConnectionDB().connect();
        panel = new BGImage();
        panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);
        JLabel heading = new JLabel("Staff Members");
        heading.setFont(new Font("Times New Roman",Font.BOLD,20));
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        panel.add(heading,gbc);
        back = new JButton("Back");
        back.setBackground(Color.BLUE);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        gbc.gridx = 4;
        panel.add(back,gbc);
//          row1
        JLabel id = new JLabel("ID");
        id.setFont(new Font("Times New Roman",Font.BOLD,30));
        y++;
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(id,gbc);
        JLabel name = new JLabel("Name");
        name.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridy = y;
        gbc.gridx = 1;
        gbc.gridwidth = 4;
        panel.add(name,gbc);
        JLabel department = new JLabel("Department");
        department.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridy = y;
        gbc.gridx = 5;
        gbc.gridwidth = 4;
        panel.add(department,gbc);
        JLabel remove = new JLabel("Remove");
        remove.setFont(new Font("Times New Roman",Font.BOLD,30));
        gbc.gridy = y;
        gbc.gridx = 9;
        gbc.gridwidth = 2;
        panel.add(remove,gbc);

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected ResultSet loader(){
        try{
            PreparedStatement getStaff = con.prepareStatement("select * from staff;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            return getStaff.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void generator(ResultSet rs){
        gbc.gridy = y++;
        gbc.insets = new Insets(10, 20, 10, 20);
//          row1
       try{
           int staffId = rs.getInt("id");
           JLabel id = new JLabel(String.valueOf(staffId));
           id.setFont(new Font("Times New Roman",Font.PLAIN,20));
           gbc.gridy = y++;
           gbc.gridx = 0;
           gbc.gridwidth = 1;
           panel.add(id,gbc);
           JLabel name = new JLabel(rs.getString("name"));
           name.setFont(new Font("Times New Roman",Font.PLAIN,20));
           gbc.gridx = 1;
           gbc.gridwidth = 4;
           panel.add(name,gbc);
           JLabel department = new JLabel(rs.getString("department"));
           department.setFont(new Font("Times New Roman",Font.PLAIN,20));
           gbc.gridx = 5;
           gbc.gridwidth = 4;
           panel.add(department,gbc);
           remove = new JButton("Remove");
           remove.setFont(new Font("Times New Roman",Font.PLAIN,20));
           gbc.gridx = 9;
           gbc.gridwidth = 2;
           remove.setBackground(Color.RED);
           remove.setForeground(Color.WHITE);
           removeStaff.add(remove);
           remove.putClientProperty("id",staffId);
           remove.setActionCommand("remove");
           remove.addActionListener(this);
           panel.add(remove,gbc);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    protected void removeStaffMethod(int id){
        try{
            PreparedStatement delete = con.prepareStatement("delete from staff where id = ?");
            delete.setInt(1,id);
            int result = delete.executeUpdate();
            if(result>0){
                JOptionPane.showMessageDialog(panel,"Successfully remove");
                new ViewStaff();
                this.setVisible(false);
            }
            setVisible(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actionPerformed(ActionEvent event){
        JButton btn = (JButton) event.getSource();
        int id = (int) btn.getClientProperty("id");
        if("remove".equals(btn.getActionCommand())){
           removeStaffMethod(id);
        }
        if(event.getSource()==back){
            new Admin();
            setVisible(false);
        }
    }
}
