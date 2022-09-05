package swing.Sub;

import swing.GameStartFrame;
import swing.Setting;

public class TimerCount extends Thread{
    private int gameTime,timer=0;
    public TimerCount(){
        super();
        gameTime=new Setting().getGameTime();
       
    }

    @Override
    public void run() {
       
        try {
            while(gameTime>timer){

                if(GameStartFrame.gameRunning==false)
                    break;
                   
                    Thread.sleep(1000);
                    timer++;
            }
            GameStartFrame.gameRunning=false;
            System.out.println();
           
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
       

    }

    public String getTime(){
        int time = gameTime-timer;
        int minute = time/60;
        int second = time%60;

            return minute+"분 "+second+"초";
    }

}
