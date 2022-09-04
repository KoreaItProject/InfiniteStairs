package swing.Char;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import swing.GameStartFrame;

public class CharDown extends Thread{
    JLabel charlbl;
    ImageIcon [] charArr,charDown;
    
    public CharDown(JLabel charlbl,ImageIcon [] charDown,ImageIcon [] charArr){

        this.charArr=charArr;
        this.charDown=charDown;
        this.charlbl=charlbl;
    }

    @Override
    public void run() {
        GameStartFrame.stop=1;
        // TODO Auto-generated method stub
        int start=0,end=charDown.length/2;
        if(GameStartFrame.moveX>0){
            start=charDown.length/2;   
            end=charDown.length;
        }
        for(int i=start;i<end;i++){
            charlbl.setIcon(charDown[i]);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        charlbl.setIcon(charArr[0+start]);
        GameStartFrame.stop=0;



        super.run();
    }
}