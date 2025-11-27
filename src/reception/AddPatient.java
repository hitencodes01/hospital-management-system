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


public class AddPatient extends JFrame implements ActionListener {

    JPanel form;
    JTextField patientId, patientName, patientAge, patientMedicalCondition, patientContact  , roomNo ;
    JButton addPatientBtn , backToHomeBtn;
    JCheckBox patientConsent ,patientLegalConsent;
    Connection con;
    String currentDate;

    AddPatient(String currentDate){
        this.currentDate = currentDate;
        Font times20Plain = new Font("Times New Roman" , Font.PLAIN , 20);
        con = new ConnectionDB().connect();
        form = new BGImage();
        form.setLayout(null);

        JLabel heading = new JLabel("Add Patient");
        heading.setBounds(640,30,500,20);
        heading.setFont(new Font("Times New Roman",Font.BOLD,30));
        form.add(heading);

        JLabel pId = new JLabel("Patient-ID");
        pId.setBounds(500,100,200,30);
        pId.setFont(times20Plain);
        JLabel pName = new JLabel("Name");
        pName.setBounds(520,160,200,30);
        pName.setFont(times20Plain);
        JLabel pAge = new JLabel("Age");
        pAge.setBounds(530,220,200,30);
        pAge.setFont(times20Plain);
        JLabel pCondition = new JLabel("Condition");
        pCondition.setBounds(500,280,200,30);
        pCondition.setFont(times20Plain);
        JLabel pContact = new JLabel("Contact");
        pContact.setBounds(510,340,200,30);
        pContact.setFont(times20Plain);
        JLabel pRoomNo = new JLabel("Room NO");
        pRoomNo.setBounds(510,400,200,30);
        pRoomNo.setFont(times20Plain);

        patientConsent = new JCheckBox("Patient Consent");
        patientConsent.setBounds(530,560,600,30);
        patientConsent.setFont(new Font("Times New Roman",Font.PLAIN,8));
        patientConsent.setOpaque(true);
        patientLegalConsent = new JCheckBox("Patient Legal Consent");
        patientLegalConsent.setBounds(530,520,600,30);
        patientLegalConsent.setFont(new Font("Times New Roman",Font.PLAIN,8));
        patientLegalConsent.setOpaque(true);

        patientId = new JTextField();
        patientId.setBounds(650,100,300,30);
        patientId.setFont(times20Plain);
        patientName = new JTextField();
        patientName.setBounds(650,160,300,30);
        patientName.setFont(times20Plain);
        patientAge = new JTextField();
        patientAge.setBounds(650,220,300,30);
        patientAge.setFont(times20Plain);
        patientMedicalCondition = new JTextField();
        patientMedicalCondition.setBounds(650,280,300,30);
        patientMedicalCondition.setFont(times20Plain);
        patientContact = new JTextField();
        patientContact.setBounds(650,340,300,30);
        patientContact.setFont(times20Plain);
        roomNo = new JTextField();
        roomNo.setBounds(650 , 400 , 300 , 30);
        roomNo.setFont(times20Plain);


        addPatientBtn = new MyButton("Add",30);
        addPatientBtn.addActionListener(this);
        addPatientBtn.setBounds(600,620,200,50);
        backToHomeBtn = new MyButton("Back",30);
        backToHomeBtn.addActionListener(this);
        backToHomeBtn.setBounds(840,620,200,50);

        form.add(pName);
        form.add(pId);
        form.add(pAge);
        form.add(pCondition);
        form.add(pContact);
        form.add(pRoomNo);
        form.add(patientConsent);
        form.add(patientLegalConsent);
        form.add(patientId);
        form.add(patientName);
        form.add(patientAge);
        form.add(patientMedicalCondition);
        form.add(patientContact);
        form.add(roomNo);
        form.add(addPatientBtn);
        form.add(backToHomeBtn);
        add(form);
        setVisible(true);
        setTitle("Add Patient");
        setSize(1650,1080);
    }

    public void actionPerformed(ActionEvent event){
            if(event.getSource()==backToHomeBtn){
                new Reception();
                setVisible(false);
            }
            if(event.getSource()==addPatientBtn){
                boolean pConsent = false;
                boolean pLegalConsent = false;
                    if(patientConsent.isSelected()){
                        pConsent = true;
                    }
                    if (patientLegalConsent.isSelected()) {
                        pLegalConsent = true;
                    }
                try{
                    PreparedStatement postPatient = con.prepareStatement("INSERT INTO patient (id, name, age, patientCondition, contact, room, admissionDate, consent, legalConsent, assigned) VALUES (?,?,?,?,?,?,?,?,?,?);");
                    postPatient.setInt(1,Integer.parseInt(patientId.getText()));
                    postPatient.setString(2,patientName.getText());
                    postPatient.setString(3,patientAge.getText());
                    postPatient.setString(4,patientMedicalCondition.getText());
                    postPatient.setString(5,patientContact.getText());
                    postPatient.setString(6,roomNo.getText());
                    postPatient.setString(7,currentDate);
                    postPatient.setBoolean(8,pConsent);
                    postPatient.setBoolean(9,pLegalConsent);
                    postPatient.setBoolean(10,false);
                    int result = postPatient.executeUpdate();
                    if(result>0){
                        patientId.setText("");
                        patientName.setText("");
                        patientAge.setText("");
                        patientMedicalCondition.setText("");
                        patientContact.setText("");
                        roomNo.setText("");
                        JOptionPane.showMessageDialog(this,"Patient Added");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

    }

}
