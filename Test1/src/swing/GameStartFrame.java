package swing;

import javax.swing.*;

import swing.Bird.BirdAni;
import swing.Bird.BirdAni2;
import swing.Char.*;
import swing.Move.*;
import swing.Skill.*;
import swing.SocketServer.InfoDTO;
import swing.SocketServer.InfoDTO.Info;
import swing.SoundF.sound;
import swing.Sub.GaugeDown;
import swing.Sub.StartCount;
import swing.Sub.TimerCount;
import java.io.*;
import java.net.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;

public class GameStartFrame extends JFrame implements Runnable {
    ImageIcon[] birdIcon;
    String nick ;
    String imgPath;
    int FramW = 1000, FramH = 900, blockW = 100, blockH = 50, blockX = 450, blockY = 500,
            charW, charH, charX, charY, startBackH = -4140;
    int skillIdx = 1;
    public static int moveX = -110, moveY = 50, stop = 0;
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
    String otherNick ;
    int otherKeyCount = 0, otherMoveX = -110;
 
    private ObjectInputStream reader ;
    private ObjectOutputStream writer ;
    public JLabel otherCharlbl;
    public int otherSkill = 0;
    public String otherCharName;
    int otherCharW, otherCharH, otherCharX, otherCharY;
    public ImageIcon[] otherCharArr, otherCharDown;
    public int otherCharIdx;

    int betweenStep = 0;
    String roomId;

    public GameStartFrame(ObjectInputStream reader,
            ObjectOutputStream writer,
            String roomId,
            String nick,
            int charIdx,
            int otherCharIdx,
            int[] result,
            String otherNick) {
        this.reader=reader;
        this.writer=writer;
        this.roomId=roomId;  
        this.nick = nick;
        this.charIdx = charIdx;
        this.otherCharIdx = otherCharIdx;
        this.result=result;
        this.otherNick = otherNick;
      
        

      
        getSetting(charIdx, otherCharIdx);

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

        // 새
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
        JLabel[] hplbl = new JLabel[hp];
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

                if (stop == 0) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:

                            if (result[keyCount] == 0 && moveX < 0 || result[keyCount] == 1 && moveX > 0) {// 틀렸을때
                                downSoundFunc();
                                down(charlbl, charDown, charArr, gaugeBar, hplbl, comboJL2);// 틀렸다함수

                            } else {
                                moveSoundFunc();
                                moveX *= -1;
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2, comboJL2);
                                send(0);
                            }

                            break;

                        case KeyEvent.VK_RIGHT:

                            if (result[keyCount] == 1 && moveX < 0 || result[keyCount] == 0 && moveX > 0) {// 틀렸을때
                                downSoundFunc();
                                down(charlbl, charDown, charArr, gaugeBar, hplbl, comboJL2);// 틀렸다함수
                            } else {
                                moveSoundFunc();
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2, comboJL2);
                                send(0);
                                if (keyCount == 1) {
                                    birdJLabel[0].setVisible(true);
                                    birdJLabel[1].setVisible(true);
                                    new BirdAni(birdJLabel, birdIcon).start();

                                }
                                new BirdAni2(birdJLabel, birdIcon, moveX).start();
                            }
                            break;

                        case KeyEvent.VK_SPACE:

                            if (charIdx == 2 && gauge >= 100 && hp == 10) {
                                System.out.println("하트가 10개 입니다.");
                            } else {
                                if (gauge >= 100) {
                                    if (charIdx == 2) {
                                        miraSkillSoundFunc();
                                        hp++;
                                        gaugeUp(gaugeBar, gauge = 0);
                                        for (int i = 0; i < hp; i++) {
                                            hplbl[i].setVisible(true);
                                        }
                                    } else {
                                        skillSoundFunc();
                                        gaugeUp(gaugeBar, gauge = 0);
                                        send(charIdx + 1);
                                        if (keyCount == 1) {
                                            birdJLabel[0].setVisible(true);
                                            birdJLabel[1].setVisible(true);
                                            new BirdAni(birdJLabel, birdIcon).start();
                                        }
                                        new BirdAni2(birdJLabel, birdIcon, moveX).start();
                                    }
                                }
                            }

                            break;
                    } // switch
                } // if

            }

        });

        // 카운트 스타트
        countGo(jl3, ImgArr3, ImgArr2, ImgArr1, ImgArrGo);

        // 창닫을 경우
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // System.exit(0);
                try {
                    // InfoDTO dto = new InfoDTO(nickName,Info.EXIT);
                    InfoDTO dto = new InfoDTO();
                    dto.setCommand(Info.EXIT);
                    writer.writeObject(dto); // 역슬러쉬가 필요가 없음
                    writer.flush();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(this);
        t2.start();
        // 프레임 메인쓰레드
        try {

            TimerCount timerCount = new TimerCount();
            timelbl.setText(timerCount.getTime());
            Thread.sleep(3100);
            timerCount.start();
            new GaugeDown().start();
            new BackAni(backlbl, backgroundIcon).start();

            while (gameRunning) {
                otherMove();
                timelbl.setText(timerCount.getTime());
                otherCheck(otherCharlbl, upChecklbl, downChecklbl, betweenlbl);
                gaugeUp(gaugeBar, gauge);
                Thread.sleep(180);

            }

            if (gameRunning == false) {
                gameRunning = true;
                try {

                    InfoDTO dto = new InfoDTO();
                    dto.setCommand(Info.EXIT);
                    writer.writeObject(dto); // 역슬러쉬가 필요가 없음
                    writer.flush();

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }

        } catch (InterruptedException e1) {

            e1.printStackTrace();
        }

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
    public void down(JLabel charlbl, ImageIcon[] charDown, ImageIcon[] charArr, JProgressBar gaugeBar, JLabel[] hplbl,
            JLabel comboJL2) {
        hp--;
        combo = 0;
        comboJL2.setText(combo + "");
        if (hp <= 0) {
            gameRunning = false;// 죽음
        } else {
            new CharDown(charlbl, charDown, charArr).start();
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
        new MoveBackGround(backlbl).start();
        new MoveBlock(blockArr, moveX, moveY).start();
        new CharAni(charlbl, charArr, moveX).start();
        if (gauge < 100) {
            gaugeUp(gaugeBar, gauge += ((gaugeUpNum + (combo * 0.04)) <= 5 ? (gaugeUpNum + (combo * 0.04)) : 5));
        }
        keyCount++;
        combo++;
        stepsJL2.setText(keyCount + "");
        comboJL2.setText(combo + "");

        if (keyCount == 1) {
            sd.birdSound();
        }
    }

    //// 소켓보내기
    public void send(int skillIdx) {

        try {
            // 서버로 보냄
            InfoDTO dto = new InfoDTO();
            dto.setStep(keyCount);
            dto.setCommand(Info.SEND);
            dto.setNickName(nick);
            dto.setMoveX(moveX);
            dto.setStep(keyCount);
            dto.setSkill(skillIdx);
            writer.writeObject(dto);
            writer.flush();
            dto.setSkill(0);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

  

    //// 서버로부터 데이터 받기
    @Override
    public void run() {

        InfoDTO dto = null;
       

        while (true) {
      

            try {
                dto = (InfoDTO)(reader.readObject());
                System.out.println(123);
                if(dto.getRoomId()!=null&&dto.getRoomId().equals(roomId)){
                    System.out.println(dto.getRoomId());
                    if (dto.getCommand() == Info.SEND) {
                        if (dto.getNickName() != null && dto.getNickName().equals(otherNick)) {
                            System.out.println(dto.getNickName());
                            otherKeyCount = dto.getStep();
                            otherMoveX = dto.getMoveX();
                            new CharAni(otherCharlbl, otherCharArr, dto.getMoveX()).start();
                            if (dto.getSkill() == 1) {
                                new SkillIce(iceBackbl).start();
                                sd.iceSkillSound();
                            } else if (dto.getSkill() == 2) {
                                new SkillBlackEye(blackEyelbl).start();
                                sd.blackEyeSkillSound();
                            }
                        }
                    }

                }
               

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        }
    }

    public void otherMove() {
        otherCharlbl.setLocation(blockArr[otherKeyCount].getLocation().x + otherCharX - 450,
                blockArr[otherKeyCount].getLocation().y + otherCharY - 500);
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
