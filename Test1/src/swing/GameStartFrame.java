package swing;


import javax.swing.*;
import swing.Char.*;
import swing.Move.*;
import swing.Skill.*;
import java.awt.*;
import java.awt.event.*;


public class GameStartFrame extends JFrame {

    String imgPath;
    int FramW = 1000, FramH = 900, blockW = 100, blockH = 50, blockX = 450, blockY = 500,
            charW = 200, charH = 200, charX = 400, charY = 310, startBackH = -4140;

    public static int moveX = -110, moveY = 50 ,stop = 0;
    Image imgch;
    int keyCount = 0, gauge = 100;
    int hp;
    boolean gameRunning=true;
    int blockCount;




    public GameStartFrame() {
        super("J프레임 테스트"); // 프레임의 타이틀   
        getSetting();

        setSize(FramW, FramH); // 컨테이너 크기 지정
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

      
       

        ImageIcon backgroundIcon = imgMk("backg.png",FramW,5000);

        // 레이블을 넣기 위한 패널 생성

        JPanel backPanel = new JPanel() ;
        backPanel.setLayout(null);

    

        //캐릭터
        ImageIcon[] charArr = new ImageIcon[12];
        for(int i=0;i<charArr.length;i++){
            charArr[i]=imgMk("snowChar/snowChar"+i+".png", charW, charH);
        }
        // 이미지 레이블 생성
        ImageIcon[] charDown = new ImageIcon[12];
        for(int i=0;i<charArr.length;i++){
            charDown[i]=imgMk("snowChar/snowChar"+(i+24)+".png", charW, charH);
        }
        


        //hp아이콘
        ImageIcon hpIcon =imgMk("hp.png", 50,50);
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

         // step 
        JLabel stepsJL = new JLabel("steps: ");
        JLabel stepsJL2 = new JLabel(keyCount + "");
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
        ImageIcon iceBackIcon = imgMk("iceback.png",FramW,FramH);
        JLabel iceBackbl = new JLabel(iceBackIcon);
        iceBackbl.setBounds(0, 0, FramW, FramH);
        backPanel.add(iceBackbl);
        iceBackbl.setVisible(false);

        // 스킬 아이콘
        ImageIcon blackEyeIcon = imgMk("blackEye.png",FramW,FramH);
        JLabel backlbl = new JLabel(backgroundIcon);
        JLabel charlbl = new JLabel(charArr[0]);
        JLabel blackEyelbl = new JLabel(blackEyeIcon);
        blackEyelbl.setBounds(0, 0, FramW, 900);
        blackEyelbl.setVisible(false);
        backPanel.add(blackEyelbl);

        

        // 블록아이콘
        JLabel[] blockArr = new JLabel[blockCount];
        int result[] = new int[blockCount];
        ImageIcon blockIcon = imgMk("block.png",blockW,blockH);
        backPanel.add(charlbl);
        for (int i = 0; i < blockArr.length; i++) {
            blockArr[i] = new JLabel(blockIcon);
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

        //키이벤트
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                if (stop == 0) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                  
                            if (result[keyCount] == 0 && moveX < 0 || result[keyCount] == 1 && moveX > 0) {//틀렸을때
                                down(charlbl, charDown, charArr, gaugeBar, hplbl);//틀렸다함수
                           
                            }else{
                                moveX *= -1;
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2);
                            }

                            break;

                        case KeyEvent.VK_RIGHT:

                            if (result[keyCount] == 1 && moveX < 0 || result[keyCount] == 0 && moveX > 0) {//틀렸을때
               
                                down(charlbl, charDown, charArr, gaugeBar, hplbl);//틀렸다함수
                            }else{
                                
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2);

                            }
                            break;
                            

                        case KeyEvent.VK_SPACE:

                            if (gauge >= 100) {
                                gaugeUp(gaugeBar, gauge = 0);
                                new SkillBlackEye(blackEyelbl).start();;
                               //new SkillIce(iceBackbl).start();
                            } else {

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


                }
            }
            if(gameRunning==false){
                new GameStartFrame();
                dispose();

            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    
  
    }
    //setting을 가져옴
    public void getSetting(){
        Setting settings=new Setting();  
        imgPath= settings.getImgPath();
        blockCount=settings.getBlockCount();
        hp=settings.getHp();
    }
    // 게이지 채워주는 함수
    public void gaugeUp(JProgressBar gaugeBar, int gauge) {
        gaugeBar.setValue(gauge);
    }
   


    //이미지 생성쓰
    public ImageIcon imgMk(String path,int w,int h){

        return new ImageIcon(new ImageIcon(imgPath+path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    //틀렸을때 함수
    public void down(JLabel charlbl,ImageIcon[] charDown,ImageIcon [] charArr,JProgressBar gaugeBar,JLabel [] hplbl){
        hp--;
        if(hp<=0){
            gameRunning=false;//죽음
        }else{
            new CharDown(charlbl, charDown, charArr).start();
            if(gauge<100&&gauge>0)
                 gaugeUp(gaugeBar, gauge-=6);
    
            for(int i=0;i<hplbl.length;i++){
                hplbl[i].setVisible(false);
    
            }
            for(int i=0;i<hp;i++){
                hplbl[i].setVisible(true);
            }
        }
    }
    //캐릭터 움직이는 함수
    public void moving(JLabel backlbl,JLabel[] blockArr,JLabel charlbl, ImageIcon[] charArr,JProgressBar gaugeBar,JLabel stepsJL2){
        new MoveBackGround(backlbl).start();
        new MoveBlock(blockArr, moveX, moveY).start();
        new CharAni(charlbl, charArr).start();
        if(gauge<100){
            gaugeUp(gaugeBar, gauge += 3);
        }
        keyCount++;
        stepsJL2.setText(keyCount + "");
    }
}
