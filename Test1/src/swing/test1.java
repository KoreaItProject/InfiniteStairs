package swing;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class test1 extends JFrame {

    int FramW = 1000, FramH = 900, blockW = 100, blockH = 50, blockX = 450, blockY = 500,
            charW = 200, charH = 200, charX = 400, charY = 310, startBackH = -4140;

    int moveX = -110, moveY = 50;
    Image imgch;
    int keyCount = 0, gauge = 100;
    int hp=7;
    static int stop = 0;
    boolean gameRunning=true;

    public test1() {

        super("J프레임 테스트"); // 프레임의 타이틀
        setSize(FramW, FramH); // 컨테이너 크기 지정
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);
        setLocationRelativeTo(null);

        JLabel[] blockArr = new JLabel[500];
        int result[] = new int[500];

        ImageIcon background = new ImageIcon("Test1/src/img/backg.png");
        imgch = background.getImage().getScaledInstance(FramW, 5000, Image.SCALE_SMOOTH);
        ImageIcon backgroundIcon = new ImageIcon(imgch);

        // 레이블을 넣기 위한 패널 생성

        JPanel backPanel = new JPanel() ;

        // 패널 레이아웃 설정 레이아웃을 설정해야지 버튼의 위치 크기를 조절할 수 있다.(기본 레이아웃은 불가)
        backPanel.setLayout(null);

    

        //캐릭터
        ImageIcon[] charArr = new ImageIcon[12];
        for(int i=0;i<charArr.length;i++){
            charArr[i]=new ImageIcon((new ImageIcon("Test1/src/img/snowChar/snowChar"+i+".png")).getImage().getScaledInstance(charW, charH, Image.SCALE_SMOOTH));
        }
        // 이미지 레이블 생성
        ImageIcon[] charDown = new ImageIcon[12];
        for(int i=0;i<charArr.length;i++){
            charDown[i]=new ImageIcon((new ImageIcon("Test1/src/img/snowChar/snowChar"+(i+24)+".png")).getImage().getScaledInstance(charW, charH, Image.SCALE_SMOOTH));
        }


        //hp아이콘
        ImageIcon hpimg = new ImageIcon("Test1/src/img/hp.png");
        imgch = hpimg.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon hpIcon = new ImageIcon(imgch);
        JLabel []hplbl=new JLabel[hp];
        for(int i=0;i<hp;i++){
            hplbl[i]=new JLabel(hpIcon);
            hplbl[i].setBounds(10+40*i,10,50,50);
            backPanel.add(hplbl[i]);
        }
       


        // 게이지바5
        JProgressBar gaugeBar = new JProgressBar();
        gaugeBar.setValue(gauge);
        backPanel.add(gaugeBar);
        gaugeBar.setBounds(-1, 835, 985, 40);
         // step 레이블
        JLabel stepsJL = new JLabel("steps: ");
        JLabel stepsJL2 = new JLabel(keyCount + "");

        // step 사이즈 및 폰트
        int size = stepsJL.getFont().getSize();
        stepsJL.setForeground(Color.BLUE);
        stepsJL2.setForeground(Color.BLUE);

        Font font = new Font("Gothic", Font.BOLD, size + 10);
        stepsJL.setFont(font);
        stepsJL2.setFont(font);

        // step 위치값
        stepsJL.setBounds(30, -430, 1000, 1000);
        stepsJL2.setBounds(100, -430, 1000, 1000);

        backPanel.add(stepsJL);
        backPanel.add(stepsJL2);

        // 아이스 배경
        ImageIcon iceBackimg = new ImageIcon("Test1/src/img/iceback.png");
        imgch = iceBackimg.getImage().getScaledInstance(FramW, FramH, Image.SCALE_SMOOTH);
        ImageIcon iceBackIcon = new ImageIcon(imgch);
        JLabel iceBackbl = new JLabel(iceBackIcon);
        iceBackbl.setBounds(0, 0, FramW, FramH);
        backPanel.add(iceBackbl);
        iceBackbl.setVisible(false);

        // 스킬 아이콘
        ImageIcon blackEye = new ImageIcon("Test1/src/img/blackEye.png");
        imgch = blackEye.getImage().getScaledInstance(FramW, FramH, Image.SCALE_SMOOTH);
        ImageIcon blackEyeIcon = new ImageIcon(imgch);
        JLabel backlbl = new JLabel(backgroundIcon);
        JLabel charlbl = new JLabel(charArr[0]);
        JLabel blackEyelbl = new JLabel(blackEyeIcon);
        blackEyelbl.setBounds(0, 0, FramW, 900);
        blackEyelbl.setVisible(false);
        backPanel.add(blackEyelbl);

        

        // 블록아이콘
        ImageIcon block = new ImageIcon("Test1/src/img/block.png");
        imgch = block.getImage().getScaledInstance(blockW, blockH, Image.SCALE_SMOOTH);
        ImageIcon blockIcon = new ImageIcon(imgch);

   
        
        // 패널에 모두 추가
        backPanel.add(charlbl);
        for (int i = 0; i < blockArr.length; i++) {

            blockArr[i] = (new JLabel(blockIcon));
            backPanel.add(blockArr[i]);
            blockArr[i].setBounds(blockX, blockY, blockW, blockH);
            int n = 0;
            if (i != 0) {
                n = (int) (Math.random() * 2);
            }
            if (n == 0) {
                blockX -= 110;

            } else {
                blockX += 110;

            }
            result[i] = n;
            blockY -= 50;

        }

        backPanel.setSize(FramW, FramH);
        backPanel.add(backlbl);

        // 초기 위치

        charlbl.setBounds(charX, charY, charW, charW);
        backlbl.setBounds(0, startBackH, FramW, 5000);
        // 컨테이너에 패널 추가
        add(backPanel);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                if (stop == 0) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                  

                            if (result[keyCount] == 0 && moveX < 0 || result[keyCount] == 1 && moveX > 0) {
                                hp-=1;
                                if(hp<=0){
                                    gameRunning=false;

                                }else{
                                    stop=1;
                                    new charDown(charlbl, moveX, charDown, charArr).start();
                                    if(gauge<100&&gauge>0)
                                        gaugeUp(gaugeBar, gauge-=6);
                                    for(int i=0;i<hplbl.length;i++){
                                        hplbl[i].setVisible(false);

                                    }
                                    for(int i=0;i<hp;i++){
                                        hplbl[i].setVisible(true);
                                    }
                                }

                              
                            }else{
                                new MoveBackGround(backlbl).start();    
                                moveX *= -1;

                                new MoveBlockGround(blockArr, moveX, moveY).start();
                                new CharAni(charlbl, moveX, charArr).start();
                                    if(gauge<100){
                                        gaugeUp(gaugeBar, gauge += 3);
                                    }
                                
                                keyCount++;
                                stepsJL2.setText(keyCount + "");
                            }

                           
                            break;

                        case KeyEvent.VK_RIGHT:

                           

                            if (result[keyCount] == 1 && moveX < 0 || result[keyCount] == 0 && moveX > 0) {
                                hp-=1;
                                if(hp<=0){
                                    gameRunning=false;
                                    
                                }else{
                                    stop=1;
                                    new charDown(charlbl, moveX, charDown, charArr).start();
                                    if(gauge<100&&gauge>0)
                                         gaugeUp(gaugeBar, gauge-=6);

                                    for(int i=0;i<hplbl.length;i++){
                                        hplbl[i].setVisible(false);

                                    }
                                    for(int i=0;i<hp;i++){
                                        hplbl[i].setVisible(true);
                                    }
                                }

                            }else{
                                new MoveBackGround(backlbl).start();
                                new MoveBlockGround(blockArr, moveX, moveY).start();
                                new CharAni(charlbl, moveX, charArr).start();
                                if(gauge<100){
                                    gaugeUp(gaugeBar, gauge += 3);
                                }
                                keyCount++;
                                stepsJL2.setText(keyCount + "");
                            

                            }
                            break;
                            

                        case KeyEvent.VK_SPACE:

                            if (gauge >= 100) {
                                gaugeUp(gaugeBar, gauge = 0);
                                System.out.println("스킬사용");
                                skillBlackEye(blackEyelbl);
                                //skillIce(iceBackbl);
                               // stop = 1;

                            } else {
                                System.out.println("게이지 부족" + gauge);
                            }

                            break;
                    } // switch
                } // if

            }

        });

        //게이지 내려줌

        try {
            while(gameRunning){
                Thread.sleep(250);
                if(gauge<100&&gauge>0){
                    gaugeUp(gaugeBar, --gauge);
                    System.out.println(123);
                }
            }
            if(gameRunning==false){
                System.out.println("게임오버");
                new test1();
                dispose();

            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    

    }

    // 게이지 채워주는 함수
    public void gaugeUp(JProgressBar gaugeBar, int gauge) {
        gaugeBar.setValue(gauge);
    }
   
    // 시야가려주는 스킬
    public void skillBlackEye(JLabel blackEyelbl) {

        new SkillBlackEye(blackEyelbl).start();

    }

    // 얼음 스킬
    public void skillIce(JLabel iceBackbl) {
        new SkillIce(iceBackbl).start();
    }

    public static void main(String[] args) {

        new test1();

    }
}
//캐릭터 애니메이션
class CharAni extends Thread{
    JLabel charlbl;
    ImageIcon [] charArr;
    int moveX;
    CharAni(JLabel charlbl, int moveX,ImageIcon []charArr){

        this.charArr=charArr;
        this.charlbl=charlbl;
        this.moveX=moveX;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int start=0,end=charArr.length/2;
        if(moveX>0){
            start=charArr.length/2;   
            end=charArr.length;
        }
        for(int i=start;i<end;i++){
            charlbl.setIcon(charArr[i]);
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        charlbl.setIcon(charArr[0+start]);


        super.run();
    }
}
//틀렸을때 애니메이션
class charDown extends Thread{
    JLabel charlbl;
    ImageIcon [] charArr,charDown;
    int moveX;
    charDown(JLabel charlbl, int moveX,ImageIcon [] charDown,ImageIcon [] charArr){

        this.charArr=charArr;
        this.charDown=charDown;
        this.charlbl=charlbl;
        this.moveX=moveX;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int start=0,end=charDown.length/2;
        if(moveX>0){
            start=charDown.length/2;   
            end=charDown.length;
        }
        for(int i=start;i<end;i++){
            charlbl.setIcon(charDown[i]);
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        charlbl.setIcon(charArr[0+start]);
        test1.stop=0;


        super.run();
    }
}

class SkillBlackEye extends Thread {
    JLabel blackEyelbl;

    SkillBlackEye(JLabel blackEyelbl) {
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

// ice 스킬 쓰레드
class SkillIce extends Thread {
    JLabel iceBackbl;

    SkillIce(JLabel iceBackbl) {
        this.iceBackbl = iceBackbl;
    }

    @Override
    public void run() {

        try {
            iceBackbl.setVisible(true);
            Thread.sleep(3000);
            iceBackbl.setVisible(false);
            test1.stop = 0;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
} // SkillIce

class MoveBackGround extends Thread {
    JLabel backlbl;

    MoveBackGround(JLabel backlbl) {
        this.backlbl = backlbl;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 15; i++) {
                backlbl.setLocation(0, backlbl.getLocation().y + 1);
                Thread.sleep(15);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

class MoveBlockGround extends Thread {
    JLabel[] blockArr;
    int moveX, moveY;

    MoveBlockGround(JLabel[] blockArr, int moveX, int moveY) {
        this.blockArr = blockArr;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    @Override
    public void run() {
        try {
            for (int k = 0; k < 10; k++) {
                Thread.sleep(10);
                for (int i = 0; i < blockArr.length; i++) {
                    blockArr[i].setLocation(blockArr[i].getLocation().x - moveX / 10,
                            blockArr[i].getLocation().y + moveY / 10);

                }
            }
        } catch (InterruptedException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }
}
// class MyThreadTest extends Thread {
// JLabel charlbl = null;
// Icon[] charArr = null;
// MyThreadTest(JLabel charlbl, Icon[] charArr) {
// this.charlbl = charlbl;
// this.charArr = charArr;
// }
// @Override
// public void run() {
// for (int j = 0; j < 10; j++) {

// for (int i = 0; i < charArr.length; i++) {
// System.out.println(i);
// try {
// charlbl.setIcon(charArr[i]);
// Thread.sleep(100);

// } catch (InterruptedException e1) {
// System.out.println("IE. Exception.");
// e1.printStackTrace();
// }
// }
// }
// }
// }
