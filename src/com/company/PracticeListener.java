package com.company;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

// This class is from a friend who got it from a teacher.
// It implements all listeners needed for javax.swing.JFrame/Panel
// As well as specific implementation for what to do when these events exist.

public class PracticeListener implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {
    public static int x, y;
    public static boolean clicked;
    public static boolean entered;
    public static int mouseWheel;
    public static boolean[] mouseButtons = new boolean[3];
    public static boolean[] keyboard = new boolean[250];
    public static Practice practice;
    PracticePanel jpanel;

    public PracticeListener(PracticePanel jpanel) {
        jpanel.setFocusable(true);
        jpanel.addKeyListener(this);
        jpanel.addMouseListener(this);
        jpanel.addMouseMotionListener(this);
        jpanel.addMouseWheelListener(this);
        this.jpanel = jpanel;
    }

    public void keyPressed(KeyEvent key) {
        System.out.println("Key hit: " + key.getExtendedKeyCode());
        keyboard[(int) key.getKeyCode()] = true;
        if (key.getKeyCode() == 112) { // F1
            jpanel.startingType = PracticePanel.Type.ODD;
            jpanel.reset();
        }
        if (key.getKeyCode() == 113) { // F2
            jpanel.startingType = PracticePanel.Type.EVEN;
            jpanel.reset();
        }
        if (key.getKeyCode() == 114) { // F3
            jpanel.startingType = PracticePanel.Type.EITHER;
            jpanel.reset();
        }
        if (key.getKeyCode() == 115) { // F4
            jpanel.startingType = PracticePanel.Type.ENDLESS;
            jpanel.reset();
        }
        if (key.getKeyCode() == 192) { // `
            jpanel.reset();
        } else if (key.getKeyCode() == 69 && !keyboard[16]) { // E
            practice.addAttackToQueue(Practice.Attack.DRAGON_KICK);
        } else if (key.getKeyCode() == 69 && keyboard[16]) { // Shift E
            practice.addAttackToQueue(Practice.Attack.BOOTSHINE);
        } else if (key.getKeyCode() == 80 && !keyboard[16]) { // P
            practice.addAttackToQueue(Practice.Attack.TRUE_STRIKE);
        } else if (key.getKeyCode() == 80 && keyboard[16]) { // Shift P
            practice.addAttackToQueue(Practice.Attack.TWIN_SNAKES);
        } else if (key.getKeyCode() == 79 && !keyboard[16]) { // O
            practice.addAttackToQueue(Practice.Attack.SNAP_PUNCH);
        } else if (key.getKeyCode() == 79 && keyboard[16]) { // Shift Oe
            practice.addAttackToQueue(Practice.Attack.DEMOLISH);
        } else if (key.getKeyCode() == 81 && !keyboard[16]) { // Q
            practice.addAttackToQueue(Practice.Attack.FORBIDDEN_CHAKRA);
        } else if (key.getKeyCode() == 81 && keyboard[16]) { // Shift Q
            practice.addAttackToQueue(Practice.Attack.PERFECT_BALANCE_FINISHER);
        } else if (key.getKeyCode() == 50 && !keyboard[16]) { // 2
            practice.addAttackToQueue(Practice.Attack.RIDDLE_OF_FIRE);
        } else if (key.getKeyCode() == 51 && !keyboard[16]) { // 3
            practice.addAttackToQueue(Practice.Attack.BROTHERHOOD);
        } else if (key.getKeyCode() == 51 && keyboard[16]) { // Shift 3
            practice.addAttackToQueue(Practice.Attack.PERFECT_BALANCE);
        }
    }

    public void keyReleased(KeyEvent key) {
        keyboard[(int) key.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent key) {

    }

    public void mousePressed(MouseEvent e) {
        clicked = true;
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                mouseButtons[0] = true;
                break;
            case MouseEvent.BUTTON2:
                mouseButtons[1] = true;
                break;
            case MouseEvent.BUTTON3:
                mouseButtons[2] = true;
                break;
        }
        // System.out.println(x + " " + y);

    }

    public void mouseReleased(MouseEvent e) {
        clicked = false;
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                mouseButtons[0] = false;
                break;
            case MouseEvent.BUTTON2:
                mouseButtons[1] = false;
                break;
            case MouseEvent.BUTTON3:
                mouseButtons[2] = false;
                break;
        }
    }

    public void mouseClicked(MouseEvent e) {
        // System.out.println(x + " " + y);
    }

    public void mouseEntered(MouseEvent e) {
        entered = true;
    }

    public void mouseExited(MouseEvent e) {
        entered = false;
    }

    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheel += e.getWheelRotation();
    }
}