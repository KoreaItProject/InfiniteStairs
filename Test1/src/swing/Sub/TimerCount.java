package swing.Sub;

import swing.GameStartFrame;
import swing.Setting;

public class TimerCount extends Thread {
    private int gameTime, timer = 0;
    public int time;

    public TimerCount() {
        super();
        gameTime = new Setting().getGameTime();

    }

    @Override
    public void run() {

        try {
            while (gameTime + 1 > timer) { // 100>0

                if (GameStartFrame.gameRunning == false)
                    break;

                Thread.sleep(1000);
                timer++;
            }
            GameStartFrame.gameRunning = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public String getTime() {
        time = gameTime - timer;
        int minute = time / 60;
        int second = time % 60;

        return minute + "분 " + second + "초";
    }

    public int getTime2() {
        return time;
    }

}
