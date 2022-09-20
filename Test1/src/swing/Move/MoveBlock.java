package swing.Move;



import javax.swing.JLabel;

import swing.GameStartFrame;




public class MoveBlock extends Thread {
    JLabel[] blockArr;
    int moveX, moveY;
    

    public MoveBlock(JLabel[] blockArr, int moveX, int moveY) {
        this.blockArr = blockArr;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    @Override
    public void run() {
        try {
        
            for (int k = 0; k < 10; k++) {
                Thread.sleep(13);
                for (int i = 0; i < blockArr.length; i++) {
                    blockArr[i].setLocation(blockArr[i].getLocation().x- moveX / 10,
                    blockArr[i].getLocation().y + moveY / 10);
                    
                }
                
            }
            GameStartFrame.totalMoveX-=moveX;
            GameStartFrame.totalMoveY+=moveY;

      

        } catch (InterruptedException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }
}
