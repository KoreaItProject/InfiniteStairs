package swing;

import javax.swing.*;

import org.w3c.dom.css.RGBColor;

import swing.Bird.BirdAni;

import swing.Char.*;
import swing.Move.*;
import swing.Skill.SkillBlackEye;
import swing.Skill.SkillIce;
import swing.SoundF.sound;
import swing.Sub.GaugeDown;
import swing.Sub.StartCount;
import swing.Sub.TimerCount;


import java.util.Random;

import java.awt.*;
import java.awt.event.*;

public class SingleStartFrame extends JFrame  {
    ImageIcon[] birdIcon;
    String imgPath;
    int FramW = 1000, FramH = 900, blockW = 100, blockH = 50, blockX = 450, blockY = 500,
            charW, charH, charX, charY, startBackH = -4140;
    int skillIdx = 1;
    public static int moveX = -110, moveY = 50, stop = 1;
    public static boolean downed=false;
    Image imgch;
    int keyCount = 0, combo = 0;
    int hp;
    public static boolean gameRunning = true;
    int blockCount;
    String charName;
    int gameStartCountW = 1000, gameStartCountH = 400;
    public static int gauge = 0;
    JProgressBar gaugeBar;
    public JLabel iceBackbl, blackEyelbl;
    int result[];
    public JLabel[] blockArr;
    public int charIdx;
    public int gaugeUpNum = 2;

    // 상대
    public int otherKeyCount = 0, otherMoveX = -110;
    int comHp;
    public JLabel otherCharlbl;
    public JLabel otherCharNicklbl;
    public int otherSkill = 0;
    public String otherCharName="COM";
    int otherCharW, otherCharH, otherCharX, otherCharY;
    public ImageIcon[] otherCharArr, otherCharDown;
    public int otherCharIdx;

    int betweenStep = 0;
    String roomId;
    String host;
    JLabel[] hplbl;
    JLabel winLoselbl;
    ImageIcon winLoseIcon;
    int comlvl [][];
    public static boolean isStartCount=true;
    public static int totalMoveX=0,totalMoveY=0,resetCount=0,totalBackMove=0;
    int slowPer=0,downPer=0;
    static public boolean comStop=true;

    static public int comCombo=0,comGauge=0;
    

    public SingleStartFrame(int[] result,int charIdx,int comCharIdx,int lvl) {

        getSetting(charIdx, comCharIdx);

        this.charIdx = charIdx;
        this.otherCharIdx = comCharIdx;
        this.result = result;

        setSize(FramW, FramH); // 컨테이너 크기 지정
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        // 레이블을 넣기 위한 패널 생성

        JPanel backPanel = new JPanel();
        backPanel.setLayout(null);
    
        // 3-2-1-go
        ImageIcon[] ImgArr3 = new ImageIcon[10];
        ImageIcon[] ImgArr2 = new ImageIcon[10];
        ImageIcon[] ImgArr1 = new ImageIcon[10];
        ImageIcon[] ImgArrGo = new ImageIcon[10];
        for (int i = 0; i < ImgArr1.length; i++) {
            ImgArr3[i] = imgMk("count/3-0.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
            ImgArr2[i] = imgMk("count/2.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
            ImgArr1[i] = imgMk("count/1.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
            ImgArrGo[i] = imgMk("count/go.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
        }

        JLabel jl3 = new JLabel(ImgArr3[0]);
        JLabel jl2 = new JLabel(ImgArr2[0]);
        JLabel jl1 = new JLabel(ImgArr1[0]);
        JLabel jlGo = new JLabel(ImgArrGo[0]);
      
        jl3.setBounds(0, 0, gameStartCountW, gameStartCountH);
        jl2.setBounds(0, 0, gameStartCountW, gameStartCountH);
        jl1.setBounds(0, 0, gameStartCountW, gameStartCountH);
        jlGo.setBounds(0, 0, gameStartCountW, gameStartCountH);

        backPanel.add(jl3);
        backPanel.add(jl2);
        backPanel.add(jl1);
        backPanel.add(jlGo);

        jl2.setVisible(false);
        jl1.setVisible(false);
        jlGo.setVisible(false);

        // WinLose Label
        winLoseIcon = imgMk("sub/white.png", 330, 490);
        winLoselbl = new JLabel();

        winLoselbl.setBounds(350, 115, 330, 490);
        backPanel.add(winLoselbl);
        winLoselbl.setIcon(winLoseIcon);
        winLoselbl.setVisible(false);
        // WinLose Label

        // 본인 화살표
        ImageIcon ImgMyArrow = new ImageIcon();
        ImgMyArrow = imgMk("character/myArrow.png", 30, 30);
        JLabel jlMyArrow = new JLabel(ImgMyArrow);
        if (charIdx == 1) {
            jlMyArrow.setBounds(charX + 50, charY - 40, 30, 30);
        } else if (charIdx == 2) {
            jlMyArrow.setBounds(charX + 25, charY - 40, 30, 30);
        } else {
            jlMyArrow.setBounds(charX + 80, charY - 40, 30, 30);
        }
        backPanel.add(jlMyArrow);
        // 본인 화살표

        //새
        birdIcon = new ImageIcon[2];
        birdIcon[0] = new ImageIcon(new ImageIcon(imgPath +
                "bird/rbird.gif").getImage().getScaledInstance(60, 60, 0));
        birdIcon[1] = new ImageIcon(new ImageIcon(imgPath +
                "bird/lbird.gif").getImage().getScaledInstance(50, 50, 0));
        JLabel[] birdJLabel = new JLabel[2];

        birdJLabel[0] = new JLabel(birdIcon[0]);
        birdJLabel[1] = new JLabel(birdIcon[1]);

        birdJLabel[0].setBounds(-100, 100, 60, 60);
        birdJLabel[1].setBounds(1100, 230, 50, 50);

        birdJLabel[0].setVisible(false);
        birdJLabel[1].setVisible(false);
        backPanel.add(birdJLabel[0]);
        backPanel.add(birdJLabel[1]);
       

        // 캐릭터
        ImageIcon[] charArr = new ImageIcon[12];
        otherCharArr = new ImageIcon[12];
        for (int i = 0; i < charArr.length; i++) {
            charArr[i] = imgMk(charName + "/" + charName + i + ".png", charW, charH);
            otherCharArr[i] = imgMk(otherCharName + "/" + otherCharName + i + ".png", otherCharW, otherCharH);
        }
        //
        ImageIcon[] charDown = new ImageIcon[12];
        otherCharDown = new ImageIcon[12];
        for (int i = 0; i < charArr.length; i++) {
            charDown[i] = imgMk(charName + "/" + charName + (i + 24) + ".png", charW, charH);
            otherCharDown[i] = imgMk(otherCharName + "/" + otherCharName + (i + 24) + ".png", otherCharW, otherCharH);
        }

        // hp아이콘
        ImageIcon hpIcon = imgMk("hp.png", 50, 50);
        hplbl = new JLabel[hp];
        if (charIdx == 2) {
            hp = 10;
            for (int i = 0; i < hp; i++) {
                hplbl[i] = new JLabel(hpIcon);
                hplbl[i].setBounds(10 + 40 * i, 10, 50, 50);
                backPanel.add(hplbl[i]);
            }
        } else {
            for (int i = 0; i < hp; i++) {
                hplbl[i] = new JLabel(hpIcon);
                hplbl[i].setBounds(10 + 40 * i, 10, 50, 50);
                backPanel.add(hplbl[i]);
            }
        }

        // 상대가 위에 있아래 있는지 보여주는 화살표
        ImageIcon upCheckIcon = imgMk("sub/upCheck.png", 40, 30);
        ImageIcon downCheckIcon = imgMk("sub/downCheck.png", 40, 30);
        JLabel upChecklbl = new JLabel(upCheckIcon);
        JLabel downChecklbl = new JLabel(downCheckIcon);
        JLabel betweenlbl = new JLabel(betweenStep + "");
        upChecklbl.setBounds(300, 10, 40, 30);
        downChecklbl.setBounds(300, 805, 40, 30);
        betweenlbl.setBounds(300, 20, 70, 200);
        Font font = new Font("Gothic", Font.BOLD, betweenlbl.getFont().getSize() + 15);
        // betweenlbl.setBackground(Color.YELLOW);
        betweenlbl.setForeground(Color.orange);
        betweenlbl.setFont(font);
        backPanel.add(upChecklbl);
        backPanel.add(downChecklbl);
        backPanel.add(betweenlbl);
        upChecklbl.setVisible(false);
        downChecklbl.setVisible(false);
        betweenlbl.setVisible(false);

        // 게이지바5
        gaugeBar = new JProgressBar();
        gaugeBar.setValue(gauge);
        gaugeBar.setForeground(new Color(214, 26, 98));
        backPanel.add(gaugeBar);
        gaugeBar.setBounds(-1, 840, 996, 40);
      
        // step
        JLabel stepsJL = new JLabel("step : ");
        JLabel stepsJL2 = new JLabel(keyCount + "");
        int size = stepsJL.getFont().getSize();
        stepsJL.setForeground(Color.BLUE);
        stepsJL2.setForeground(Color.BLUE);
        font = new Font("Gothic", Font.BOLD, size + 12);
        stepsJL.setFont(font);
        stepsJL2.setFont(font);
        stepsJL.setBounds(30, -430, 1000, 1000);
        stepsJL2.setBounds(100, -430, 1000, 1000);
        backPanel.add(stepsJL);
        backPanel.add(stepsJL2);

        // combo
        JLabel comboJL = new JLabel("combo : ");
        JLabel comboJL2 = new JLabel(keyCount + "");
        comboJL.setForeground(Color.BLUE);
        comboJL2.setForeground(Color.BLUE);
        comboJL.setFont(font);
        comboJL2.setFont(font);
        comboJL.setBounds(30, -390, 1000, 1000);
        comboJL2.setBounds(130, -390, 1000, 1000);
        backPanel.add(comboJL);
        backPanel.add(comboJL2);

        // time
        JLabel timelbl = new JLabel("5분 30초");
        timelbl.setForeground(Color.BLUE);
        timelbl.setFont(new Font("Gothic", Font.BOLD, size + 20));
        timelbl.setBounds(840, -470, 1000, 1000);
        backPanel.add(timelbl);


        // 스킬 아이스
        ImageIcon iceBackIcon = imgMk("iceback.png", FramW, FramH);
        iceBackbl = new JLabel(iceBackIcon);
        iceBackbl.setBounds(0, 0, FramW, FramH);
        backPanel.add(iceBackbl);
        iceBackbl.setVisible(false);

        // 스킬 블랙아이
        ImageIcon blackEyeIcon = imgMk("blackEye3.png", FramW, FramH);

        blackEyelbl = new JLabel(blackEyeIcon);
        blackEyelbl.setBounds(0, 0, FramW, FramH);
        blackEyelbl.setVisible(false);
        backPanel.add(blackEyelbl);

        JLabel charlbl = new JLabel(charArr[0]);
        backPanel.add(charlbl);
        otherCharlbl = new JLabel(otherCharArr[0]);
        backPanel.add(otherCharlbl);

        // 상대 닉네임
        otherCharNicklbl = new JLabel("COMPUTER");
        otherCharNicklbl.setBounds(455, 300, 150, 30);
        otherCharNicklbl.setFont(new Font("Gothic", Font.BOLD, otherCharNicklbl.getFont().getSize() + 8));
        backPanel.add(otherCharNicklbl);
       

        // 블록아이콘
        blockArr = new JLabel[blockCount];
        ImageIcon blockIcon = imgMk("block.png", blockW, blockH);

        for (int i = 0; i < result.length; i++) {
            blockArr[i] = new JLabel(blockIcon);
            blockArr[i].setBounds(blockX, blockY, blockW, blockH);
            backPanel.add(blockArr[i]);

            if (result[i] == 0) {
                blockX -= 110;

            } else {
                blockX += 110;

            }
            blockY -= 50;

        }

        backPanel.setSize(FramW, FramH);

        // 초기 위치

        charlbl.setBounds(charX, charY, charW, charW);
        otherCharlbl.setBounds(otherCharX, otherCharY, otherCharW, otherCharW);
        // 배경
        ImageIcon[] backgroundIcon = new ImageIcon[9];
        for (int i = 0; i < backgroundIcon.length; i++) {
            backgroundIcon[i] = imgMk("back/backg" + i + ".png", FramW, 5000);
        }

        JLabel backlbl = new JLabel(backgroundIcon[0]);
        backPanel.add(backlbl);
        backlbl.setBounds(0, startBackH, FramW, 5000);
        // 컨테이너에 패널 추가
        add(backPanel);
        setVisible(true);



        // 키이벤트
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
      
                if (stop == 0&&!downed) {
                   
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:

                            if (result[keyCount] == 0 && moveX < 0 || result[keyCount] == 1 && moveX > 0) {// 틀렸을때
                                downSoundFunc();
                                down(charlbl, charDown, charArr, gaugeBar, hplbl, comboJL2,backlbl);// 틀렸다함수

                            } else {
                                moveSoundFunc();
                                moveX *= -1;
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2, comboJL2);
                            }

                            break;

                        case KeyEvent.VK_RIGHT:

                            if (result[keyCount] == 1 && moveX < 0 || result[keyCount] == 0 && moveX > 0) {// 틀렸을때
                                downSoundFunc();
                                down(charlbl, charDown, charArr, gaugeBar, hplbl, comboJL2,backlbl);// 틀렸다함수
                            } else {
                                moveSoundFunc();
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2, comboJL2);
                                if (keyCount == 1) {
                                    birdJLabel[0].setVisible(true);
                                    birdJLabel[1].setVisible(true);
                                    new BirdAni(birdJLabel, birdIcon).start();

                                }
                                
                            }
                            break;

                        case KeyEvent.VK_SPACE:

                            
                                if (gauge >= 100) {
                                    if(charIdx == 0){
                                        skillSoundFunc();
                                        gaugeUp(gaugeBar, gauge = 0);
                                        new Thread(new Runnable(){public void run() {
                                            try {System.out.println("얼음 스킬 발동");
                                                comStop=true;Thread.sleep(1500); comStop=false;
                                            } catch (InterruptedException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        };}).start();
                                    }else if(charIdx == 1){
                                        skillSoundFunc();
                                        gaugeUp(gaugeBar, gauge = 0);
                                        new Thread(new Runnable(){public void run() {
                                            try {System.out.println("시야 방해 발동");
                                                downPer=3;slowPer=30;Thread.sleep(4500); downPer=0;slowPer=0;
                                            } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block

                                            }
                                        };}).start();
                               
                                    }else if (charIdx == 2 && hp != 10) {
                                        miraSkillSoundFunc();
                                        hp++;
                                        gaugeUp(gaugeBar, gauge = 0);
                                        for (int i = 0; i < hp; i++) {
                                            hplbl[i].setVisible(true);
                                        }
                                    }
                                }
                            

                            break;
                    } // switch
                } // if

            }

        });

        countGo(jl3, ImgArr3, ImgArr2, ImgArr1, ImgArrGo);

       
        // 카운트 스타트

        // 프레임 메인쓰레드
        /* */
     

            TimerCount timerCount = new TimerCount();
            timelbl.setText(timerCount.getTime());
            timerCount.start();
            new GaugeDown(1).start();
            new GaugeDown(2).start();
            new BackAni(backlbl, backgroundIcon).start();

            
            Thread gameRunningThread= new Thread(
                new Runnable() {
                  public void run() {
                    while (gameRunning) {
                        timelbl.setText(timerCount.getTime());
                        otherCheck(otherCharlbl, upChecklbl, downChecklbl, betweenlbl);
                        otherMove();
                        gaugeUp(gaugeBar, gauge);
                        
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
        
                    }
                  };  
                }
            );
          gameRunningThread.start();
         
          

        Thread ComThread= new Thread(//컴퓨터 관련 코드
            new Runnable() {
                Random rand = new Random();
                long speed=comlvl[0][lvl];
                int i=0;
                int same=0,dif=0 ,stack=0;
              public void run() {
              
                while (gameRunning) {         
                    try {
                        if(!comStop){
                            System.out.println("comGauge"+comGauge);
                            if(comCharIdx==0&&comGauge>=100){
                                new SkillIce(iceBackbl, 1).start();
                                comGauge=0;
                            } else if(comCharIdx==1&&comGauge>=100){
                                new SkillBlackEye(blackEyelbl).start();
                                comGauge=0;
                            }else if(comCharIdx==2&&comGauge>=100&&comHp<10){
                                System.out.println("발동");
                                comHp++;
                                comGauge=0; 
                                System.out.println(comHp);
                            }

                        // 상황에 따른 컴퓨터 입력속도
                            if(result.length-10>=otherKeyCount){//끝나기 10칸 전에만 발동된다.
                                if(stack<=0){
                                    for(int i=otherKeyCount;i<=otherKeyCount+2;i++){//3칸 앞을보며
                                        if(result[i]==result[i+1]){
                                            same++;//3칸 앞이 모두 연속 된것을 찾는다
                                            dif=0;
                                        }else{
                                            dif++;//3칸 앞이 모두 다른 된것을 찾는다
                                            same=0;
                                        }
                                
                                        if(same>=3||dif>=3){//3칸 앞이 모두 연속 또는 다르다면
                                            System.out.println();
                                            stack=3;//3칸을 저장한다
                                            Thread.sleep((speed/3));

                                        }else{//아닐 경우 정상 움직임

                                                if(randPer(rand,1)){//확률적으로 빠르게 이동한다.
                                                    Thread.sleep((speed/4));
                                                }else if(randPer(rand,3)){//확률적으로 빠르게 이동한다.
                                                    Thread.sleep((speed/3));
                                                }else if(randPer(rand,4)){
                                                    Thread.sleep(speed/2);
                                                }else{
                                                    Thread.sleep(speed);//기본 속도
                                                }
                                            

                                        }
                                    }
                                }else{//저장된 칸이 있을경우 그 칸수만큼은 빨리 이동한다.
                                    Thread.sleep((speed/3));
                                    if(stack>0)
                                        stack--;
                                }
                                    
                        
                            }else{
                                Thread.sleep(speed);
                            }
                                

                            if(randPer(rand,comlvl[1][lvl]+downPer)){//틀렸다면
                                i++;
                            
                                if(i>=comHp){
                                    otherKeyCount=0;
                                    otherMoveX=-otherMoveX;
                                } 
                                if (gauge < 100 && gauge > 0)
                                        comGauge -= 6;
                                comCombo=0;
                                new CharDown(otherCharlbl, otherCharDown, otherCharArr,3).start();
                                Thread.sleep(720);

                            }else{//안틀렸다면
                                if(randPer(rand,2+slowPer)){//머뭇거릴수 있다.
                                    Thread.sleep(comlvl[2][lvl]);
                                }
                                if((0>otherMoveX?0:1)!=result[otherKeyCount])
                                    otherMoveX=-otherMoveX;
                                new CharAni(otherCharlbl, otherCharArr, otherMoveX).start();
                                otherKeyCount++;//이동한다.
                                comCombo++;
                                if (comGauge < 100) {
                                    comGauge += ((gaugeUpNum + (comCombo * 0.05)) <= 5 ? (gaugeUpNum + (comCombo * 0.05)) :5);
                                }
                            
                            }
                        }else{
                            Thread.sleep(3);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

    
                }
              };  
              public boolean randPer(Random rand,int per){
                if(per>=(rand.nextInt(100)+1)){
                    return true;
                }
                return false;
                  
           
                
              }
            }
        );
        ComThread.start();
       

    } // 생성자

    sound sd = new sound();

    public void skillSoundFunc() {
        sd.skillsound();
    }

    public void miraSkillSoundFunc() {
        sd.miraSkillSound();
    }

    public void inGameSoundFunc() {
        sd.inGameSoundStart();
    }

    public void moveSoundFunc() {
        sd.moveSound();
    }

    public void downSoundFunc() {
        sd.downSound();
    }

    // setting을 가져옴
    public void getSetting(int charIdx, int otherCharIdx) {
        Setting settings = new Setting();
        imgPath = settings.getImgPath();
        blockCount = settings.getBlockCount();
        hp = settings.getHp()[charIdx];
        comHp= settings.getHp()[otherCharIdx];
        charW = settings.getCharW()[charIdx];
        charH = settings.getCharH()[charIdx];
        charX = settings.getCharX()[charIdx];
        charY = settings.getCharY()[charIdx];
        charName = settings.getCharName()[charIdx];
        otherCharH = settings.getCharH()[otherCharIdx];
        otherCharW = settings.getCharW()[otherCharIdx];
        otherCharX = settings.getCharX()[otherCharIdx];
        otherCharY = settings.getCharY()[otherCharIdx];
        otherCharName = settings.getCharName()[otherCharIdx];
        host = settings.getHost();
        comlvl=settings.getComlvl();

    }

    // 시작 카운트
    public void countGo(JLabel jl3, ImageIcon[] ImgArr3, ImageIcon[] ImgArr2,
            ImageIcon[] ImgArr1,
            ImageIcon[] ImgArrGo) {
        new StartCount(jl3, ImgArr3, ImgArr2, ImgArr1, ImgArrGo).start();

        sd.countDownSound();
        inGameSoundFunc();
    }

    // 이미지 생성쓰
    public ImageIcon imgMk(String path, int w, int h) {

        return new ImageIcon(new ImageIcon(imgPath + path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    // 틀렸을때 함수
    public void down(JLabel charlbl, ImageIcon[] charDown, ImageIcon[] charArr, JProgressBar gaugeBar, JLabel[] hplbl, JLabel comboJL2 , JLabel backlbl) {
        hp--;
        combo = 0;
        comboJL2.setText(combo + "");
        if (hp <= 0) { 
            System.out.println(totalMoveX+"죽음"+totalMoveY);
            hp=hplbl.length;
            for (int i = 0; i < hplbl.length; i++) {
                hplbl[i].setVisible(false);
            }
            for (int i = 0; i < hp; i++) {
                hplbl[i].setVisible(true);
            }
            new MoveBlockReset(blockArr, totalMoveX,totalMoveY).start();
            new MoveBackGroundReset(backlbl, totalBackMove).start();
            resetCount++;
            totalMoveX=0;
            totalMoveY=0;
            totalBackMove=0;
            keyCount=0;
            moveX = -110;
          
            new CharDown(charlbl, charDown, charArr,1).start();

            

        } else {
            new CharDown(charlbl, charDown, charArr,1).start();
            if (gauge < 100 && gauge > 0)
                gaugeUp(gaugeBar, gauge -= 6);

            for (int i = 0; i < hplbl.length; i++) {
                hplbl[i].setVisible(false);

            }
            for (int i = 0; i < hp; i++) {
                hplbl[i].setVisible(true);
            }
        } // charIdx == 2거나 아닐때
    }

    // 캐릭터 움직이는 함수
    public void moving(JLabel backlbl, JLabel[] blockArr, JLabel charlbl, ImageIcon[] charArr, JProgressBar gaugeBar,
            JLabel stepsJL2, JLabel comboJL2) {
        new MoveBackGround(backlbl,1).start();
        new MoveBlock(blockArr, moveX, moveY,1).start();
        new CharAni(charlbl, charArr, moveX).start();
        if (gauge < 100) {
            gaugeUp(gaugeBar, gauge += ((gaugeUpNum + (combo * 0.05)) <= 5 ? (gaugeUpNum + (combo * 0.05)) :5));
        }
        keyCount++;
        combo++;
        stepsJL2.setText(keyCount + "");
        comboJL2.setText(combo + "");

        if (keyCount == 1) {
            sd.birdSound();
        }
    }

   



    public void otherMove() {
        otherCharlbl.setLocation(blockArr[otherKeyCount].getLocation().x + otherCharX - 450,
                blockArr[otherKeyCount].getLocation().y + otherCharY - 500);

        otherCharNicklbl.setLocation(blockArr[otherKeyCount].getLocation().x + 45 + otherCharX - 450,
                blockArr[otherKeyCount].getLocation().y - 30 + otherCharY - 500);
        betweenStep = otherKeyCount - keyCount;

        // -20 -130
    }

    // 게이지 채워주는 함수
    public void gaugeUp(JProgressBar gaugeBar, int gauge) {
        gaugeBar.setValue(gauge);
    }

    public void otherCheck(JLabel otherCharlbl, JLabel upChecklbl, JLabel downChecklbl, JLabel betweenlbl) {
        // -170 780

        if (-170 < otherCharlbl.getLocation().y && 780 > otherCharlbl.getLocation().y) {
            upChecklbl.setVisible(false);
            downChecklbl.setVisible(false);
            betweenlbl.setVisible(false);
        } else if (-170 >= otherCharlbl.getLocation().y) {
            betweenlbl.setVisible(true);
            upChecklbl.setVisible(true);
            downChecklbl.setVisible(false);

            betweenlbl.setText(betweenStep + "");

            if (0 < otherCharlbl.getLocation().x + 85 && otherCharlbl.getLocation().x + 85 <= 935) {
                upChecklbl.setLocation(otherCharlbl.getLocation().x + 80, upChecklbl.getLocation().y);
                betweenlbl.setLocation(otherCharlbl.getLocation().x + 85, upChecklbl.getLocation().y - 60);
            } else if (otherCharlbl.getLocation().x <= 0) {
                upChecklbl.setLocation(10, upChecklbl.getLocation().y);
                betweenlbl.setLocation(15, upChecklbl.getLocation().y - 60);
            } else {
                upChecklbl.setLocation(950, upChecklbl.getLocation().y);
                betweenlbl.setLocation(951, upChecklbl.getLocation().y - 60);
            }
            // 935
        } else {
            betweenlbl.setVisible(true);
            upChecklbl.setVisible(false);
            downChecklbl.setVisible(true);
            betweenlbl.setText(-betweenStep + "");

            if (0 < otherCharlbl.getLocation().x + 85 && otherCharlbl.getLocation().x + 85 <= 935) {
                downChecklbl.setLocation(otherCharlbl.getLocation().x + 80, downChecklbl.getLocation().y);
                betweenlbl.setLocation(otherCharlbl.getLocation().x + 85, downChecklbl.getLocation().y - 115);
            } else if (otherCharlbl.getLocation().x <= 0) {
                downChecklbl.setLocation(10, downChecklbl.getLocation().y);
                betweenlbl.setLocation(15, downChecklbl.getLocation().y - 115);
            } else {
                downChecklbl.setLocation(950, downChecklbl.getLocation().y);
                betweenlbl.setLocation(951, downChecklbl.getLocation().y - 115);
            }
            // 935
            // 55
        }
    }

}