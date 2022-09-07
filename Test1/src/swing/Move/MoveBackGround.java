package swing.Move;


import javax.swing.JLabel;



public class MoveBackGround extends Thread {
    JLabel backlbl;

    public MoveBackGround(JLabel backlbl) {
        this.backlbl = backlbl;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 16; i++) {
                backlbl.setLocation(0, backlbl.getLocation().y + 1);
                Thread.sleep(25);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
