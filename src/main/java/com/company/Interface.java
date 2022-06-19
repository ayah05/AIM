package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Interface {
    private JFrame frame;
    private JPanel mainPanel;
    private JButton anamneseButton;
    private JButton medicationButton;
    private JButton interactionsButton;
    private JPanel menue;
    private JPanel display_Anamnese;
    private JPanel display_Medication;
    private JPanel display_Interaction;
    private JButton button1;
    private JButton button2;
    private JTextField textField2;
    private JButton button3;
    private JButton button4;
    private JTextField textField3;
    private JTextField textField1;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private ImageIcon background;
    private JLabel jBackground;
    private ImageIcon backgroundS;
    private JLabel jBackgroundS;

    Interface(){

        int delay = 1200; //milliseconds
        frame = new JFrame("AIM");
        frame.add(mainPanel);
        mainPanel.add(menue);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //start background image
        backgroundS = new ImageIcon("./images/StartBild.PNG");
        jBackgroundS=new JLabel(backgroundS);
        mainPanel.add(jBackgroundS);

        jBackgroundS.setVisible(true);
        frame.pack();
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jBackgroundS.setVisible(false);
            }
        };
        new javax.swing.Timer(delay, taskPerformer).start();

        //normal background image
        background = new ImageIcon("./images/StartBild.PNG");
        jBackground= new JLabel(background);
        mainPanel.add(jBackground);
        jBackground.add(anamneseButton);
        jBackground.add(medicationButton);
        jBackground.add(interactionsButton);
        jBackground.add(display_Anamnese);
        jBackground.add(display_Interaction);
        jBackground.add(display_Medication);


        jBackground.setSize(1823,1013);
        mainPanel.setSize(jBackground.getSize());


        frame.pack();
        //Change Buttons to transparent
        anamneseButton.setBorder(null);
        anamneseButton.setBorderPainted(false);
        anamneseButton.setContentAreaFilled(false);
        anamneseButton.setOpaque(false);
        anamneseButton.setForeground(Color.WHITE);
        medicationButton.setBorder(null);
        medicationButton.setBorderPainted(false);
        medicationButton.setContentAreaFilled(false);
        medicationButton.setOpaque(false);
        medicationButton.setForeground(Color.WHITE);
        interactionsButton.setBorder(null);
        interactionsButton.setBorderPainted(false);
        interactionsButton.setContentAreaFilled(false);
        interactionsButton.setOpaque(false);
        interactionsButton.setForeground(Color.WHITE);
        //display settings
        display_Anamnese.setVisible(false);
        display_Interaction.setVisible(false);
        display_Medication.setVisible(false);





        anamneseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(display_Anamnese.isVisible()==false)
                {
                    display_Anamnese.setVisible(true);
                }
                else{
                    display_Anamnese.setVisible(false);
                }
                //set display transparent
                display_Anamnese.setBorder(null);
                display_Anamnese.setOpaque(false);
                display_Anamnese.setForeground(Color.WHITE);
                display_Anamnese.setSize(400,400);
            }
        });
        medicationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(display_Medication.isVisible()==false)
                {
                    display_Medication.setVisible(true);
                }
                else{
                    display_Medication.setVisible(false);
                }
                //set display transparent
                display_Medication.setBorder(null);
                display_Medication.setOpaque(false);
                display_Medication.setForeground(Color.WHITE);
            }
        });
        interactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(display_Interaction.isVisible()==false)
                {
                    display_Interaction.setVisible(true);
                }
                else{
                    display_Interaction.setVisible(false);
                }
                //set display transparent
                display_Interaction.setBorder(null);
                display_Interaction.setOpaque(false);
                display_Interaction.setForeground(Color.WHITE);
            }
        });
    }


}
