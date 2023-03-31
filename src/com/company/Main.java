package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends JPanel {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Monk Practice");
        frame.setSize(1920, 1080);
        frame.setUndecorated(true);
        frame.setLocation(0,0);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new PracticePanel(frame, PracticePanel.Type.ODD));
        frame.setVisible(true);
    }

    JButton oddPractice;
    JButton evenPractice;
    JButton bothPractice;
    JButton endless;

    public Main(JFrame frame){
        int width = frame.getWidth();
        int height = frame.getHeight();
        oddPractice = new JButton();
        oddPractice.setText("Odd Window");
        oddPractice.setBounds(0, 0, width/2, height/2);
        evenPractice = new JButton();
        evenPractice.setText("even Window");
        evenPractice.setBounds(width/2, 0, width/2, height/2);
        bothPractice = new JButton();
        bothPractice.setText("Either Window");
        bothPractice.setBounds(0, height/2, width/2, height/2);
        endless = new JButton();
        endless.setText("Free play");
        endless.setBounds(width/2, height/2, width/2, height/2);

        oddPractice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setContentPane(new PracticePanel(frame, PracticePanel.Type.ODD));
                    frame.repaint();
                    frame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        evenPractice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setContentPane(new PracticePanel(frame, PracticePanel.Type.EVEN));
                    frame.repaint();
                    frame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        bothPractice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setContentPane(new PracticePanel(frame, PracticePanel.Type.EITHER));
                    frame.repaint();
                    frame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        endless.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setContentPane(new PracticePanel(frame, PracticePanel.Type.ENDLESS));
                    frame.repaint();
                    frame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(oddPractice);
        this.add(evenPractice);
        this.add(bothPractice);
        this.add(endless);
    }
}
