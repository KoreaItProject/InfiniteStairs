package swing.Skill;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import swing.GameStartFrame;


public class SkillBlackEye extends Thread {
    JLabel blackEyelbl;

    public SkillBlackEye(JLabel blackEyelbl) {
        this.blackEyelbl = blackEyelbl;

    }

    @Override
    public void run() {

        try {
            blackEyelbl.setVisible(true);
            Thread.sleep(5000);
            blackEyelbl.setVisible(false);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}