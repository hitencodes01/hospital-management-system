package admin;

import auth.Login;
import background.BGImage;
import components.GetDate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin extends JFrame implements ActionListener {
    JPanel adminPanel;
    JMenuBar adminMenu;
    JMenu menuDoctor , menuPatient , menuStaff , menuVisitors;
    JMenuItem addDoctor , viewDoctor , viewPatients , addStaff , viewStaff, viewVisitors;
    Image  backgroundImage ;
    JButton backToLogin;

//    font
    Font times30Plain = new Font("Times New Roman" , Font.PLAIN , 30);
    Font headingFont = new Font("Times New Roman", Font.BOLD, 42);

//    constructor function
    public  Admin(){
        backgroundImage = new ImageIcon("LoginBG.png").getImage();
        adminPanel = new BGImage();
        adminPanel.setLayout(null);

//        heading
        JLabel heading = new JLabel("Hospital Management System",SwingConstants.CENTER);
        heading.setBounds(0, 20, 1650, 60);
        heading.setFont(headingFont);
        heading.setForeground(new Color(30, 30, 30));
        heading.setOpaque(false);

//        menu bar
        adminMenu = new JMenuBar();
        adminMenu.setBounds(250, 110, 1150, 55);
        adminMenu.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        adminMenu.setBackground(new Color(33, 26, 223));

//        menu items styling
        menuDoctor = createStyledMenu("Doctors");
        menuPatient = createStyledMenu("Patients");
        menuStaff = createStyledMenu("Staff");

//        menu items

//        doctors MenuItems
        addDoctor = createStyledMenuItem("Add");
        viewDoctor = createStyledMenuItem("View");

//        staff MenuItems
        addStaff = createStyledMenuItem("Add");
        viewStaff = createStyledMenuItem("View");

//        patient MenuItems
        viewPatients = createStyledMenuItem("View");

//          visitors MenuItems

//        adding MenuItems

        menuDoctor.add(addDoctor);
        menuDoctor.add(viewDoctor);

        menuStaff.add(addStaff);
        menuStaff.add(viewStaff);

        menuPatient.add(viewPatients);



        JLabel quotation1 = new JLabel("Healing takes time,");
        quotation1.setFont(new Font("Algerian", Font.BOLD, 40));
        quotation1.setBounds(500, 250, 1600, 200);
        adminPanel.add(quotation1);
        JLabel quotation2 = new JLabel("and asking for help is a courageous step. ");
        quotation2.setFont(new Font("Algerian", Font.BOLD, 40));
        quotation2.setBounds(300, 295, 1600, 200);
        adminPanel.add(quotation2);
        JLabel quotation3 = new JLabel("Trust the process, ");
        quotation3.setFont(new Font("Algerian", Font.BOLD, 40));
        quotation3.setBounds(500, 340, 1600, 200);
        adminPanel.add(quotation3);
        JLabel quotation4 = new JLabel("and brighter days will come.");
        quotation4.setFont(new Font("Algerian", Font.BOLD, 40));
        quotation4.setBounds(400, 385, 1600, 200);
        adminPanel.add(quotation4);

//        adding menus
        adminMenu.add(menuDoctor);
        adminMenu.add(Box.createHorizontalStrut(50)); // spacing between menus
        adminMenu.add(menuStaff);
        adminMenu.add(Box.createHorizontalStrut(50));
        adminMenu.add(menuPatient);
        adminMenu.add(Box.createHorizontalStrut(450));

//        backToLogin
        backToLogin = new JButton("Back");
        backToLogin.addActionListener(this);
        backToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLogin.setFont(times30Plain);
        backToLogin.setBackground(Color.WHITE);
        backToLogin.setForeground(Color.BLACK);
        backToLogin.setFocusPainted(false);
        backToLogin.setBorderPainted(false);
        backToLogin.setContentAreaFilled(false);  // avoid white inner box
        backToLogin.setOpaque(true);              // enable custom background color


//        adding components
        adminPanel.add(heading);
        adminPanel.add(adminMenu);
        adminMenu.add(backToLogin);

//        frame setting
        add(adminPanel);
        setSize(1650,1080);
        setVisible(true);
        setTitle("Admin");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
//    functionality
    @Override
    public void actionPerformed(ActionEvent event){
       if(event.getSource() == addDoctor){
           new AddDoctor();
           setVisible(false);
       }if(event.getSource() == viewDoctor){
           new ViewDoctor();
            setVisible(false);
       }if(event.getSource() == addStaff){
          new AddStaff();
          this.setVisible(false);
       }if(event.getSource() == viewStaff){
           new ViewStaff();
           this.setVisible(false);
       }if(event.getSource() == viewPatients){
           new GetDate();
            this.setVisible(false);
       }if(event.getSource() == viewVisitors){
           System.out.println("viewDoctor");
       }if(event.getSource()==backToLogin){
           new Login();
           this.setVisible(false);
        }
    }
//      create style menu function
    private JMenu createStyledMenu(String title){
            JMenu menu = new JMenu(title);
            menu.setFont(times30Plain);
            menu.setForeground(new Color(255, 255, 255));
            menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
            menu.setBorder(new EmptyBorder(10,10,10,10));
        return menu;
    }
    //      create style menuItems function
    private JMenuItem createStyledMenuItem(String title){
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setFont(times30Plain);
        menuItem.setForeground(new Color(0, 0, 255));
        menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuItem.setBorder(new EmptyBorder(20,20,20,20));
        menuItem.addActionListener(this);
        return menuItem;
    }

    public static void main(String[] args) {
        new Admin();
    }
}
