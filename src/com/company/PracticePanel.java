package com.company;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

public class PracticePanel extends JPanel implements ActionListener {

    public enum Type {
        ODD,
        EVEN,
        EITHER,
        ENDLESS,
    }

    public void init() throws IOException {
        gifs.put(Practice.Attack.DRAGON_KICK, Utils.readGif(new FileInputStream(new File("Resources/gifs/Dragon Kick_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.TRUE_STRIKE, Utils.readGif(new FileInputStream(new File("Resources/gifs/True Strike_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.SNAP_PUNCH, Utils.readGif(new FileInputStream(new File("Resources/gifs/Snap Punch_s.gif"))));
        System.out.println("loaded");
        gifs.put(Practice.Attack.BOOTSHINE, Utils.readGif(new FileInputStream(new File("Resources/gifs/Bootshine_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.TWIN_SNAKES, Utils.readGif(new FileInputStream(new File("Resources/gifs/Twin Snakes_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.DEMOLISH, Utils.readGif(new FileInputStream(new File("Resources/gifs/Demolish_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.RIDDLE_OF_FIRE, Utils.readGif(new FileInputStream(new File("Resources/gifs/Riddle of Fire_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.BROTHERHOOD, Utils.readGif(new FileInputStream(new File("Resources/gifs/Brotherhood_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.PERFECT_BALANCE, Utils.readGif(new FileInputStream(new File("Resources/gifs/Perfect Balance_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.FORBIDDEN_CHAKRA, Utils.readGif(new FileInputStream(new File("Resources/gifs/Forbidden Chakra_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.PHANTOM_RUSH, Utils.readGif(new FileInputStream(new File("Resources/gifs/Phantom Rush_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.ELIXIR_FIELD, Utils.readGif(new FileInputStream(new File("Resources/gifs/Elixir Field_s.gif"))));
        System.out.println("loading");
        gifs.put(Practice.Attack.RISING_PHOENIX, Utils.readGif(new FileInputStream(new File("Resources/gifs/Rising Phoenix_s.gif"))));
        System.out.println("loading");
    }

    Type startingType;
    JFrame frame;

    Practice practice;
    Image bg_img = ImageIO.read(new File("Resources/static/BG.png"));
    Image dragonkick = ImageIO.read(new File("Resources/static/dragonkick.png"));
    Image truestrike = ImageIO.read(new File("Resources/static/truestrike.png"));
    Image snappunch = ImageIO.read(new File("Resources/static/snappunch.png"));
    Image bootshine = ImageIO.read(new File("Resources/static/bootshine.png"));
    Image ts_buff = ImageIO.read(new File("Resources/static/ts_buff.png"));
    Image demolish = ImageIO.read(new File("Resources/static/demolish.png"));
    Image riddleoffire = ImageIO.read(new File("Resources/static/riddleoffire.png"));
    Image brotherhood = ImageIO.read(new File("Resources/static/brotherhood.png"));
    Image perfectbalance = ImageIO.read(new File("Resources/static/perfect_balance.png"));
    Image forbiddenchakra = ImageIO.read(new File("Resources/static/forbiddenchakra.png"));
    Image elixirfield = ImageIO.read(new File("Resources/static/elixirfield.png"));
    Image celestialrevolution = ImageIO.read(new File("Resources/static/celestialrevolution.png"));
    Image risingphoenix = ImageIO.read(new File("Resources/static/risingphoenix.png"));
    Image phantomrush = ImageIO.read(new File("Resources/static/phantomrush.png"));
    Image outline = ImageIO.read(new File("Resources/static/outline.png"));
    Image meditation = ImageIO.read(new File("Resources/static/meditation.png"));
    Image bs_no_buff = ImageIO.read(new File("Resources/static/bs_no_buff.png"));
    Practice.QueuedAttack attackGif;
    Map<Practice.Attack, Utils.ImageFrame[]> gifs = new HashMap<>();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.drawImage(bg_img, 0, 0, 1920, 1080, null);
        if (practice.attackHistory.size() > 0){
            if (attackGif == null || practice.attackHistory.get(practice.attackHistory.size() - 1).timestamp != attackGif.timestamp) {
                attackGif = practice.attackHistory.get(practice.attackHistory.size() - 1);
            }
        }
        //System.out.println("gifs.size(): " + gifs.size());
        //System.out.println("start time: " + attackGif.timestamp + ", current time: " + System.currentTimeMillis() + ", end time: " + attackGif.timestamp + 17*gifs.size());
        if (attackGif != null && gifs.get(attackGif.attack) != null) {
            g.drawImage(gifs.get(attackGif.attack)[Math.min(gifs.get(attackGif.attack).length-1, (int)((System.currentTimeMillis() - attackGif.timestamp)/17))].getImage(), 820,300, 1080-820, 670-300, null);
        }

        g.drawImage(dragonkick, 880, 696, 51, 51, null);
        if (practice.form == Practice.Form.FORM_SHIFT || practice.form == Practice.Form.OPO_OPO) {
            g.drawImage(outline, 879, 695, 53, 53, null);
        }
        g.drawImage(truestrike, 934, 696, 51, 51, null);
        if (practice.form == Practice.Form.FORM_SHIFT || practice.form == Practice.Form.RAPTOR) {
            g.drawImage(outline, 933, 695, 53, 53, null);
        }
        g.drawImage(snappunch, 991, 696, 51, 51, null);
        if (practice.form == Practice.Form.FORM_SHIFT || practice.form == Practice.Form.COEURL) {
            g.drawImage(outline, 990, 695, 53, 53, null);
        }
        if (practice.gcd_cd > 0) {
            drawCD(g, 880, 696, 51, 51, practice.gcd_cd, Practice.GCD, 190, false);
            drawCD(g, 934, 696, 51, 51, practice.gcd_cd, Practice.GCD, 190, false);
            drawCD(g, 991, 696, 51, 51, practice.gcd_cd, Practice.GCD, 190, false);
        }
        g.drawImage(riddleoffire, 830, 995, 80, 80, null);
        if (practice.rof_cd > 0) {
            drawCD(g, 830, 995, 80, 80, practice.rof_cd, Practice.ROF_CD, 190, true);
        }
        g.drawImage(brotherhood, 920, 995, 80, 80, null);
        if (practice.bh_cd > 0) {
            drawCD(g, 920, 995, 80, 80, practice.bh_cd, Practice.BH_CD, 190, true);
        }
        g.drawImage(perfectbalance, 920, 897, 80, 80, null);
        if (practice.pb_stacks > 0) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(965, 940, 40, 45);
        }
        if (practice.pb_cd > 0) {
            if (practice.pb_stacks > 0) {
                drawCD(g, 920, 897, 80, 80, practice.pb_cd, Practice.PB_CD, 80, true);
            } else {
                drawCD(g, 920, 897, 80, 80, practice.pb_cd, Practice.PB_CD, 190, true);
            }
        }
        if (practice.pb_stacks > 0) {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
            g.setColor(new Color(210, 255, 210));
            g.drawString(String.valueOf(practice.pb_stacks), 970, 980);
        }
        if (practice.pb_stage == 4) {
            if (practice.solar_nadi && practice.lunar_nadi) {
                g.drawImage(phantomrush, 720, 696, 51, 51, null);
            } else if (practice.pb_attacks[0] == practice.pb_attacks[1] && practice.pb_attacks[0] == practice.pb_attacks[2]) {
                g.drawImage(elixirfield, 720, 696, 51, 51, null);
            } else if (practice.pb_attacks[0] != practice.pb_attacks[1] && practice.pb_attacks[0] != practice.pb_attacks[2] && practice.pb_attacks[1] != practice.pb_attacks[2]) {
                g.drawImage(risingphoenix, 720, 696, 51, 51, null);
            } else {
                g.drawImage(celestialrevolution, 720, 696, 51, 51, null);
            }
            g.drawImage(outline, 719, 695, 53, 53, null);
        }
        g.drawImage(meditation, 772, 696, 51, 51, null);
        if (practice.chakra >= 5) {
            g.drawImage(forbiddenchakra, 772, 696, 51, 51, null);
            g.drawImage(outline, 771, 695, 53, 53, null);
        }
        if (practice.lunar_nadi) {
            g.setColor(new Color(120, 50, 150));
            g.fillOval(875, 755, 25, 25);
        }
        if (practice.solar_nadi) {
            g.setColor(new Color(170, 170, 170));
            g.fillOval(1920 - 875 - 25, 755, 25, 25);
        }
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        if (practice.bs_dur > 0) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(848, 504, 41, 51);
            g.drawImage(bootshine, 848, 484, 41, 41, null);
            g.setColor(new Color(210, 255, 210));
            g.drawString(String.valueOf((int)(practice.bs_dur/1000+0.5)), 856, 547);
        } else {
            g.drawImage(bs_no_buff, 824, 486, null);
        }
        if (practice.ts_dur > 0) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(863, 565, 28, 46);
            g.drawImage(ts_buff, 863, 555, 28, 36, null);
            g.setColor(new Color(210, 255, 210));
            g.drawString(String.valueOf((int)(practice.ts_dur/1000+0.5)), 869, 604);
        }
        if (practice.dem_dur > 0) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(1013, 488, 30, 45);
            g.drawImage(demolish, 1013, 488, 30, 30, null);
            g.setColor(new Color(210, 255, 210));
            g.drawString(String.valueOf((int)(practice.dem_dur/1000+0.5)), 1018, 530);
        }

        g.setColor(Color.white);
        g.drawString("x: " + PracticeListener.x + " y: " + PracticeListener.y, 50, 150);
        g.drawString(practice.toString(), 50, 170);
        g.drawString(practice.attackQueue.stream().map(new Function<Practice.QueuedAttack, String>() {
            @Override
            public String apply(Practice.QueuedAttack queuedAttack) {
                return "  " + queuedAttack.attack + ": " + queuedAttack.timestamp;
            }
        }).reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                return s + s2;
            }
        }).orElse(""), 50, 190);
        g.drawString(practice.attackHistory.stream().map(new Function<Practice.QueuedAttack, String>() {
            @Override
            public String apply(Practice.QueuedAttack queuedAttack) {
                return "  " + queuedAttack.attack + ": " + queuedAttack.timestamp;
            }
        }).reduce(new BinaryOperator<String>() {
            @Override
            public String apply(String s, String s2) {
                return s + s2;
            }
        }).orElse(""), 50, 200);
    }

    private void drawCD(Graphics g, int x, int y, int width, int height, long remaining, long max, int alpha, boolean drawCDText) {
        float ratio = (float)remaining/(float)max;
        g.setColor(new Color(120, 120, 120, alpha));
        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();
        xPoints.add(x + width/2);
        yPoints.add(y + height/2);
        xPoints.add(x + width/2);
        yPoints.add(y);

        if (ratio < 0.125){
            xPoints.add((int) (x + width/2 - (ratio)/0.125*width/2));
            yPoints.add(y);
        }
        if (ratio > 0.125) {
            xPoints.add(x);
            yPoints.add(y);
            if (ratio <= 0.375) {
                xPoints.add(x);
                yPoints.add((int) (y + (ratio-0.125)/0.25*height));
            }
        }
        if (ratio > 0.375) {
            xPoints.add(x);
            yPoints.add(y+height);
            if (ratio <= 0.625) {
                xPoints.add((int) (x + (ratio-0.375)/0.25*width));
                yPoints.add(y+height);
            }
        }
        if (ratio > 0.625) {
            xPoints.add(x+width);
            yPoints.add(y+height);
            if (ratio <= 0.875) {
                xPoints.add(x+width);
                yPoints.add((int) (y + height - (ratio-0.625)/0.25*height));
            }
        }
        if (ratio > 0.875) {
            xPoints.add(x+width);
            yPoints.add(y);
            xPoints.add((int) (x + width -  (ratio-0.875)/0.125*width/2));
            yPoints.add(y);
        }
        g.fillPolygon(xPoints.stream().mapToInt(Integer::intValue).toArray(), yPoints.stream().mapToInt(Integer::intValue).toArray(), xPoints.size());
        if (drawCDText) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x + width/2 - 20, y + height/2 - 20, 40, 40);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
            g.setColor(new Color(210, 255, 210));
            g.drawString(String.valueOf((int)(remaining/1000)+1), x + width/2 - 18, y + height/2 + 18);
        }
    }

    public PracticePanel(JFrame frame, Type type) throws IOException {
        init();
        this.startingType = type;
        this.frame = frame;
        reset();
        new PracticeListener(this);
    }

    public void backToTitle() {
        frame.setContentPane(new Main(frame));
    }

    public void reset() {
        Timer t = new Timer(17, this);
        t.start();
        practice = new Practice(startingType);
        actionPerformed(null);
        PracticeListener.practice = practice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        practice.update();
        repaint();
    }
}
