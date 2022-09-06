package swing.Skill;


import javax.swing.JLabel;

import swing.GameStartFrame;




public class SkillIce extends Thread {
    JLabel iceBackbl;

    public SkillIce(JLabel iceBackbl) {
        this.iceBackbl = iceBackbl;
    }

    @Override
    public void run() {
        GameStartFrame.stop = 1;


        try {
            iceBackbl.setVisible(true);
            Thread.sleep(1500);
            iceBackbl.setVisible(false);
            GameStartFrame.stop = 0;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
} 