package doctor;

import background.BGImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Doctor extends JFrame implements ActionListener {
    ResultSet rs;
    JPanel doctorPanel;
    JLabel idLabel, nameLabel, specLabel, passLabel;
    JButton backBtn ,updtBtn;

    public Doctor(ResultSet doctor) {
        try {
            rs = doctor;
            int id = doctor.getInt("id");
            String name = formatName(doctor.getString("name"));
            String spec = doctor.getString("specialization");
            String password = doctor.getString("password");

            // PANEL with background image
            doctorPanel = new BGImage();
            doctorPanel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.CENTER;

            // -------- TITLE --------
            JLabel title = new JLabel("Your Details");
            title.setFont(new Font("Arial", Font.BOLD, 28));
            title.setForeground(Color.BLACK);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            doctorPanel.add(title, gbc);

            gbc.gridwidth = 1;

            // ================ ROW 1 ================
            idLabel = new JLabel("ID: ");
            idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            idLabel.setForeground(Color.BLACK);

            JLabel idValue = new JLabel(String.valueOf(id));
            idValue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            idValue.setForeground(Color.BLACK);

            gbc.gridy = 1;
            gbc.gridx = 0;
            doctorPanel.add(idLabel, gbc);
            gbc.gridx = 1;
            doctorPanel.add(idValue, gbc);

            // ================ ROW 2 ================
            nameLabel = new JLabel("Name: ");
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 22));
            nameLabel.setForeground(Color.BLACK);

            JLabel nameValue = new JLabel(name);
            nameValue.setFont(new Font("Arial", Font.PLAIN, 22));
            nameValue.setForeground(Color.BLACK);

            gbc.gridy = 2; gbc.gridx = 0;
            doctorPanel.add(nameLabel, gbc);
            gbc.gridx = 1;
            doctorPanel.add(nameValue, gbc);

            // ================ ROW 3 ================
            specLabel = new JLabel("Specialization: ");
            specLabel.setFont(new Font("Arial", Font.PLAIN, 22));
            specLabel.setForeground(Color.BLACK);

            JLabel specValue = new JLabel(spec);
            specValue.setFont(new Font("Arial", Font.PLAIN, 22));
            specValue.setForeground(Color.BLACK);

            gbc.gridy = 3;
            gbc.gridx = 0;
            doctorPanel.add(specLabel, gbc);
            gbc.gridx = 1;
            doctorPanel.add(specValue, gbc);

            // ================ ROW 4 ================
            passLabel = new JLabel("Password: ");
            passLabel.setFont(new Font("Arial", Font.PLAIN, 22));
            passLabel.setForeground(Color.BLACK);

            JLabel passValue = new JLabel(password);
            passValue.setFont(new Font("Arial", Font.PLAIN, 22));
            passValue.setForeground(Color.BLACK);

            gbc.gridy = 4;
            gbc.gridx = 0;
            doctorPanel.add(passLabel, gbc);
            gbc.gridx = 1;
            doctorPanel.add(passValue, gbc);

            // BACK BUTTON
            backBtn = new JButton("Log out");
            backBtn.setFont(new Font("Arial", Font.BOLD, 24));
            backBtn.setForeground(Color.BLACK);
            backBtn.addActionListener(this);
            // UPDATE BUTTON
            updtBtn = new JButton("Update Profile");
            updtBtn.addActionListener(this);
            updtBtn.setFont(new Font("Arial", Font.BOLD, 24));
            updtBtn.setForeground(Color.BLACK);
            gbc.gridy = 5;
            gbc.gridx = 0;
            doctorPanel.add(backBtn, gbc);
            gbc.gridx = 1;
            doctorPanel.add(updtBtn,gbc);


            add(doctorPanel);
            setTitle("Welcome " + name);
            setSize(800, 600);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backBtn){

            this.setVisible(false);
        }
        if(e.getSource() == updtBtn){
            new DoctorProfile(rs,1);
            this.setVisible(false);
        }
    }
    private String formatName(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
