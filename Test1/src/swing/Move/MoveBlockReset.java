package swing.Move;



import javax.swing.JLabel;




public class MoveBlockReset extends Thread {
    JLabel[] blockArr;
    int totalMoveX, totalMoveY;

    public MoveBlockReset(JLabel[] blockArr, int totalMoveX, int totalMoveY) {
        this.blockArr = blockArr;
        this.totalMoveX = totalMoveX;
        this.totalMoveY = totalMoveY;
    }

    @Override
    public void run() {
        try {
        
                for (int k = 0; k < 10; k++) {
                    Thread.sleep(15);
                    for (int i = 0; i < blockArr.length; i++) {
                        blockArr[i].setLocation(blockArr[i].getLocation().x - totalMoveX / 10,
                        blockArr[i].getLocation().y - totalMoveY / 10);
                        
                    }
                    
                }

                
      

        } catch (InterruptedException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }
}
