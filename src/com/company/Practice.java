package com.company;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Practice {

    public static long GCD = 1920;
    public static long GCD_FOR_OGCD = 500;
    public static long OGCD_FOR_OGCD = 700;
    public static long ROF_CD = 60000;
    public static long ROF_DUR = 20000;
    public static long BH_CD = 120000;
    public static long BH_DUR = 15000;
    public static long PB_CD = 40000;
    public static long TS_DUR = 15900; // 15000 + 900 (delay of attack)
    public static long DEM_DUR = 19700; // 18000 + 1700 (delay of attack)
    public static long BS_DUR = 30000; // lazy to care about delay

    public static long BUFFER_SIZE = 500; //0.5 seconds

    public enum Attack {
        DRAGON_KICK(Form.OPO_OPO),
        BOOTSHINE(Form.OPO_OPO),
        TRUE_STRIKE(Form.RAPTOR),
        TWIN_SNAKES(Form.RAPTOR),
        SNAP_PUNCH(Form.COEURL),
        DEMOLISH(Form.COEURL),
        PERFECT_BALANCE_FINISHER(Form.FORM_SHIFT),
        ELIXIR_FIELD(Form.FORM_SHIFT),
        RISING_PHOENIX(Form.FORM_SHIFT),
        RAGING_CYCLONE(Form.FORM_SHIFT),
        PHANTOM_RUSH(Form.FORM_SHIFT),
        FORBIDDEN_CHAKRA(Form.OGCD),
        PERFECT_BALANCE(Form.OGCD),
        RIDDLE_OF_FIRE(Form.OGCD),
        BROTHERHOOD(Form.OGCD);

        private Form form;
        Attack(Form form) {
            this.form = form;
        }

        Form getForm() {
            return form;
        }
    }

    public class QueuedAttack implements Comparable {
        public Attack attack;
        public long timestamp;

        public QueuedAttack(Attack attack, long timestamp) {
            this.attack = attack;
            this.timestamp = timestamp;
        }

        @Override
        public int compareTo(Object o) {
            return (int)(timestamp - ((QueuedAttack)o).timestamp);
        }
    }

    public enum Form {
        OPO_OPO,
        RAPTOR,
        COEURL,
        FORM_SHIFT,
        OGCD,
    }

    private long randomizeCD(float min_cd, float max_cd) {
        return (long)(Math.random()*(max_cd-min_cd)*1000 + min_cd*1000);
    }

    private Form randomizeForm() {
        double random = Math.random() * 3;
        if (random > 2) {
            return Form.COEURL;
        } else if (random > 1) {
            return Form.RAPTOR;
        } else {
            return Form.OPO_OPO;
        }
    }

    public Practice(PracticePanel.Type type) {
        pb_attacks = new Form[3];
        attackHistory = new ArrayList<>();
        attackQueue = new PriorityQueue<>();
        lastTime = System.currentTimeMillis();
        if (PracticePanel.Type.ODD == type || (PracticePanel.Type.EITHER == type && Math.random() < 0.5)) {
            rof_cd = randomizeCD(20, 25);
            pb_stacks = 1;
            pb_cd = 40000;
            bh_cd = randomizeCD(80, 85);
            dem_dur = randomizeCD(-1, 18);
            ts_dur = randomizeCD(-1, 15);
            if (Math.random() > 0.5) bs_dur = BS_DUR;
            form = randomizeForm();
            lunar_nadi = true;
            solar_nadi = true;
        } else if (PracticePanel.Type.EVEN == type || PracticePanel.Type.EITHER == type) {
            pb_stacks = 1;
            pb_cd = 15000;
            rof_cd = randomizeCD(20, 25);
            bh_cd = randomizeCD((float)rof_cd/1000, (float)rof_cd/1000 + 5);
            dem_dur = randomizeCD(-1, 18);
            ts_dur = randomizeCD(-1, 15);
            if (Math.random() > 0.5) bs_dur = BS_DUR;
            form = randomizeForm();
            lunar_nadi = true;
            solar_nadi = true;
        } else if (PracticePanel.Type.ENDLESS == type) {
            form = Form.FORM_SHIFT;
            chakra = 5;
            pb_stacks = 2;
        }
    }

    long lastTime;
    Form form;
    long gcd_cd;
    long ogcd_cd;
    long rof_cd;
    long rof_dur;
    long bh_cd;
    long bh_dur;
    long pb_cd;
    int pb_stacks;
    long ts_dur;
    long dem_dur;
    long bs_dur;
    int chakra;
    Form[] pb_attacks;
    int pb_stage;
    boolean lunar_nadi;
    boolean solar_nadi;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Practice:\n");
        sb.append("\nlast time: " + lastTime);
        sb.append("\nform: " + form);
        sb.append("\ngcd_cd: " + gcd_cd);
        sb.append("\nogcd_cd: " + ogcd_cd);
        sb.append("\nrof_cd: " + rof_cd);
        sb.append("\nbh_cd: " + bh_cd);
        sb.append("\nbh_dur: " + bh_dur);
        sb.append("\npb_cd: " + pb_cd);
        sb.append("\npb_stacks: " + pb_stacks);
        sb.append("\nts_dur: " + ts_dur);
        sb.append("\ndem_dur: " + dem_dur);
        sb.append("\nbs_dur: " + bs_dur);
        sb.append("\nchakra: " + chakra);
        sb.append("\npb_stage: " + pb_stage);
        sb.append("\nlunar_nadi: " + lunar_nadi);
        sb.append("\nsolar_nadi: " + solar_nadi);
        return sb.toString();
    }


    PriorityQueue<QueuedAttack> attackQueue;
    List<QueuedAttack> attackHistory;

    public void addAttackToQueue(Attack attack) {
        attackQueue.add(new QueuedAttack(attack, System.currentTimeMillis()));
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - lastTime;
        gcd_cd -= timeDiff;
        ogcd_cd -= timeDiff;
        rof_cd -= timeDiff;
        rof_dur -= timeDiff;
        bh_cd -= timeDiff;
        bh_dur -= timeDiff;
        pb_cd -= timeDiff;
        bs_dur -= timeDiff;
        ts_dur -= timeDiff;
        dem_dur -= timeDiff;
        bs_dur -= timeDiff;
        if (pb_cd < 0 && pb_stacks < 2) {
            pb_stacks++;
            if (pb_stacks == 1) {
                pb_cd += PB_CD;
            }
        }
        Iterator<QueuedAttack> queuedAttackIterator = attackQueue.iterator();
        List<QueuedAttack> toRemove = new ArrayList<>();
        while (queuedAttackIterator.hasNext()) {
            QueuedAttack queuedAttack = queuedAttackIterator.next();
            if (queuedAttack == null) {
                break;
            } else if (queuedAttack.timestamp + 500 < lastTime) {
                toRemove.add(queuedAttack);
                continue;
            }
            if (queuedAttack.attack.form == Form.OGCD) {
                long timeAgoAttackWentOff = currentTime - Math.max(currentTime + ogcd_cd, queuedAttack.timestamp);
                if (ogcd_cd <= 0) { // attack can go through
                    if (queuedAttack.attack == Attack.FORBIDDEN_CHAKRA && chakra >= 5) {
                        chakra = 0;
                        attackHistory.add(new QueuedAttack(Attack.FORBIDDEN_CHAKRA, currentTime - timeAgoAttackWentOff));
                    } else if (queuedAttack.attack == Attack.BROTHERHOOD && bh_cd <= 0) {
                        bh_cd = BH_CD - timeAgoAttackWentOff;
                        bh_dur = BH_DUR - timeAgoAttackWentOff;
                        attackHistory.add(new QueuedAttack(Attack.BROTHERHOOD, currentTime - timeAgoAttackWentOff));
                    } else if (queuedAttack.attack == Attack.RIDDLE_OF_FIRE && rof_cd <= 0) {
                        rof_cd = ROF_CD - timeAgoAttackWentOff;
                        rof_dur = ROF_DUR - timeAgoAttackWentOff;
                        attackHistory.add(new QueuedAttack(Attack.RIDDLE_OF_FIRE, currentTime - timeAgoAttackWentOff));
                    } else if (queuedAttack.attack == Attack.PERFECT_BALANCE && pb_stacks > 0) {
                        form = Form.FORM_SHIFT;
                        pb_stacks--;
                        if (pb_stacks == 1) pb_cd = PB_CD;
                        pb_stage = 1;
                        attackHistory.add(new QueuedAttack(Attack.PERFECT_BALANCE, currentTime - timeAgoAttackWentOff));
                    }
                    ogcd_cd = OGCD_FOR_OGCD - timeAgoAttackWentOff;
                    if (ogcd_cd > gcd_cd) gcd_cd = ogcd_cd;
                    toRemove.add(queuedAttack);
                    break;
                }
            } else {
                long timeAgoAttackWentOff = currentTime - Math.max(currentTime + gcd_cd, queuedAttack.timestamp);
                if (gcd_cd <= 0) { // attack can go through
                    if (pb_stage == 4 && queuedAttack.attack == Attack.PERFECT_BALANCE_FINISHER) {
                        if (solar_nadi && lunar_nadi) {
                            solar_nadi = false;
                            lunar_nadi = false;
                            attackHistory.add(new QueuedAttack(Attack.PHANTOM_RUSH, currentTime - timeAgoAttackWentOff));
                        } else if (pb_attacks[0] == pb_attacks[1] && pb_attacks[0] == pb_attacks[2]) {
                            lunar_nadi = true;
                            attackHistory.add(new QueuedAttack(Attack.ELIXIR_FIELD, currentTime - timeAgoAttackWentOff));
                        } else if (pb_attacks[0] != pb_attacks[1] && pb_attacks[0] != pb_attacks[2] && pb_attacks[1] != pb_attacks[2]) {
                            solar_nadi = true;
                            attackHistory.add(new QueuedAttack(Attack.RISING_PHOENIX, currentTime - timeAgoAttackWentOff));
                        } else {
                            if (lunar_nadi) {
                                solar_nadi = true;
                            } else {
                                lunar_nadi = true;
                            }
                            attackHistory.add(new QueuedAttack(Attack.RAGING_CYCLONE, currentTime - timeAgoAttackWentOff));
                        }
                        pb_stage = 0;
                        form = Form.FORM_SHIFT;
                    } else if (queuedAttack.attack.form == form || Form.FORM_SHIFT == form) {
                        // valid attack
                        if (pb_stage == 4) {
                            // pass
                        } else if (queuedAttack.attack == Attack.TWIN_SNAKES) {
                            ts_dur = TS_DUR - timeAgoAttackWentOff;
                        } else if (queuedAttack.attack == Attack.DEMOLISH) {
                            dem_dur = DEM_DUR - timeAgoAttackWentOff;
                        } else if (queuedAttack.attack == Attack.DRAGON_KICK) {
                            bs_dur = BS_DUR - timeAgoAttackWentOff;
                        } else if (queuedAttack.attack == Attack.BOOTSHINE) {
                            bs_dur = 0;
                        }
                        attackHistory.add(new QueuedAttack(queuedAttack.attack, currentTime - timeAgoAttackWentOff));

                        if (pb_stage > 0 && pb_stage < 4) {
                            pb_attacks[pb_stage - 1] = queuedAttack.attack.form;
                            pb_stage++;
                        }
                        form = queuedAttack.attack.form;
                        if (form == Form.OPO_OPO) form = Form.RAPTOR;
                        else if (form == Form.RAPTOR) form = Form.COEURL;
                        else if (form == Form.COEURL) form = Form.OPO_OPO;
                        if (pb_stage > 0 && pb_stage < 4) form = Form.FORM_SHIFT;

                        if (bh_dur > 0) chakra++;
                        if (queuedAttack.attack == Attack.BOOTSHINE || Math.random() > 0.3) chakra++;
                    } else {
                        toRemove.add(queuedAttack);
                        continue;
                    }

                    gcd_cd = GCD - timeAgoAttackWentOff;
                    ogcd_cd = GCD_FOR_OGCD - timeAgoAttackWentOff;
                    toRemove.add(queuedAttack);
                    break;
                }
            }
        }
        lastTime = currentTime;
        for(QueuedAttack attack : toRemove) {
            attackQueue.remove(attack);
        }
    }

}
