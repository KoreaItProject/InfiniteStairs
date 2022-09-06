package swing;


import javax.swing.*;
import swing.Char.*;
import swing.Move.*;
import swing.Skill.*;
import swing.Sub.GaugeDown;
import swing.Sub.StartCount;
import swing.Sub.TimerCount;
import java.io.*;
import java.net.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;


public class GameStartFrame extends JFrame implements Runnable
{

	String nick="aa";
    String imgPath;
    int FramW = 1000, FramH = 900, blockW = 100, blockH = 50, blockX = 450, blockY = 500,
            charW , charH , charX , charY , startBackH = -4140;
    int skillIdx=1;
    public static int moveX = -110, moveY = 50 ,stop = 0;
    Image imgch;
    int keyCount = 0 ,combo=0;
    int hp;
    public static boolean gameRunning=true;
    int blockCount;
    String charName;
    int gameStartCountW = 1000, gameStartCountH = 400;
    public static int gauge = 0;
   JProgressBar gaugeBar;
   public JLabel iceBackbl , blackEyelbl;
    
    
   //상대
   	String otherNick="bb";
    int otherKeyCount=0,otherMoveX=-110;
    private Socket socket;
    private ObjectInputStream reader=null;
    private ObjectOutputStream writer=null;   
    public JLabel[] blockArr;
    public JLabel otherChar;
    public int otherSkill=0;

    public GameStartFrame(int charIdx) {
        super("J프레임 테스트"); // 프레임의 타이틀  

        getSetting(charIdx);


        setSize(FramW, FramH); // 컨테이너 크기 지정
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        // 레이블을 넣기 위한 패널 생성

        JPanel backPanel = new JPanel() ;
        backPanel.setLayout(null);
        //배경
        ImageIcon backgroundIcon = imgMk("backg.png",FramW,5000);


      //3-2-1-go
        ImageIcon[] ImgArr3 = new ImageIcon[10];
        ImageIcon[] ImgArr2 = new ImageIcon[10];
        ImageIcon[] ImgArr1 = new ImageIcon[10];
        ImageIcon[] ImgArrGo = new ImageIcon[10];
        for (int i = 0; i < ImgArr1.length; i++) {
            ImgArr3[i] = imgMk("count/3-0.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
            ImgArr2[i] = imgMk("count/2.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
            ImgArr1[i] =  imgMk("count/1.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
            ImgArrGo[i] =  imgMk("count/go.png", gameStartCountW - 100 * i, gameStartCountH - 40 * i);
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
   

       

   

        //캐릭터
        ImageIcon[] charArr = new ImageIcon[12];
        for(int i=0;i<charArr.length;i++){
            charArr[i]=imgMk(charName+"/"+charName+i+".png", charW, charH);
        }
        // 이미지 레이블 생성
        ImageIcon[] charDown = new ImageIcon[12];
        for(int i=0;i<charArr.length;i++){
            charDown[i]=imgMk(charName+"/"+charName+(i+24)+".png", charW, charH);
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
        gaugeBar = new JProgressBar();
        gaugeBar.setValue(gauge);
        backPanel.add(gaugeBar);
        gaugeBar.setBounds(-1, 835, 985, 40);

         // step
        JLabel stepsJL = new JLabel("step : ");
        JLabel stepsJL2 = new JLabel(keyCount + "");
        int size = stepsJL.getFont().getSize();
        stepsJL.setForeground(Color.BLUE);
        stepsJL2.setForeground(Color.BLUE);
        Font font = new Font("Gothic", Font.BOLD, size + 12);
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

         //time
         JLabel timelbl = new JLabel("5분 30초");
         timelbl.setForeground(Color.BLUE);
         timelbl.setFont(new Font("Gothic", Font.BOLD, size + 20));
         timelbl.setBounds(840, -470, 1000, 1000);
         backPanel.add(timelbl);

 

        // 스킬 아이스
        ImageIcon iceBackIcon = imgMk("iceback.png",FramW,FramH);
        iceBackbl = new JLabel(iceBackIcon);
        iceBackbl.setBounds(0, 0, FramW, FramH);
        backPanel.add(iceBackbl);
        iceBackbl.setVisible(false);

        // 스킬 블랙아이
        ImageIcon blackEyeIcon = imgMk("blackEye3.png",FramW,FramH);
        JLabel backlbl = new JLabel(backgroundIcon);
        blackEyelbl = new JLabel(blackEyeIcon);
        blackEyelbl.setBounds(0, 0, FramW, FramH);
        blackEyelbl.setVisible(false);
        backPanel.add(blackEyelbl);

        JLabel charlbl = new JLabel(charArr[0]);
        backPanel.add(charlbl);
        otherChar = new JLabel(charArr[0]);
        backPanel.add(otherChar);

        // 블록아이콘
        blockArr = new JLabel[blockCount];
        int result[] = new int[blockCount];
        ImageIcon blockIcon = imgMk("block.png",blockW,blockH);
        
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
        otherChar.setBounds(charX, charY, charW, charW);
        backlbl.setBounds(0, startBackH, FramW, 5000);
        // 컨테이너에 패널 추가
        add(backPanel);
        setVisible(true);

        service();
        //키이벤트
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                if (stop == 0) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                 
                            if (result[keyCount] == 0 && moveX < 0 || result[keyCount] == 1 && moveX > 0) {//틀렸을때
                                down(charlbl, charDown, charArr, gaugeBar, hplbl,comboJL2);//틀렸다함수
                           
                            }else{
                                moveX *= -1;
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2,comboJL2);
                                send(0);
                            }

                            break;

                        case KeyEvent.VK_RIGHT:

                            if (result[keyCount] == 1 && moveX < 0 || result[keyCount] == 0 && moveX > 0) {//틀렸을때
               
                                down(charlbl, charDown, charArr, gaugeBar, hplbl,comboJL2);//틀렸다함수
                            }else{
                               
                                moving(backlbl, blockArr, charlbl, charArr, gaugeBar, stepsJL2,comboJL2);
                                send(0);
                            }
                            break;
                           

                        case KeyEvent.VK_SPACE:

                            if (gauge >= 100) {
                                gaugeUp(gaugeBar, gauge = 0);
                                send(skillIdx);
                                
                               //new SkillIce(iceBackbl).start();
                            } else {

                            }

                            break;
                    } // switch
                } // if

            }

        });

        //카운트 스타트
        countGo(jl3, ImgArr3, ImgArr2, ImgArr1, ImgArrGo);
        
        //창닫을 경우
        this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){ 
				//System.exit(0);
				try{
					//InfoDTO dto = new InfoDTO(nickName,Info.EXIT);
					InfoDTO dto = new InfoDTO();
					dto.setCommand(Info.EXIT);
					writer.writeObject(dto);  //역슬러쉬가 필요가 없음
					writer.flush();
				}catch(IOException io){
					io.printStackTrace();
				}
			}
		});
        
        //프레임 메인쓰레드
        try {
        
            TimerCount timerCount=new TimerCount();
            timelbl.setText(timerCount.getTime());
            Thread.sleep(3100);
            timerCount.start();
        	new GaugeDown().start();
   
                while(gameRunning){    
                	otherMove(otherKeyCount);
                     timelbl.setText(timerCount.getTime());
                    Thread.sleep(50);
                    gaugeUp(gaugeBar, gauge);
   
                    
                }
                if(gameRunning==false){
                    timerCount.stop();
                    gameRunning=true;
                    dispose();
                    new GameStartFrame(charIdx);
                   
               
                 }
         
           
        } catch (InterruptedException e1) {

            e1.printStackTrace();
        }
       
 
    }
    
    //setting을 가져옴
    public void getSetting(int charIdx){
        Setting settings=new Setting();  
        imgPath= settings.getImgPath();
        blockCount=settings.getBlockCount();
        hp=settings.getHp();
        charW = settings.getCharW()[charIdx];
        charH = settings.getCharH()[charIdx];
        charX = settings.getCharX()[charIdx];
        charY = settings.getCharY()[charIdx];
        charName=settings.getCharName()[charIdx];
    }
        // 시작 카운트
    public void countGo(JLabel jl3, ImageIcon[] ImgArr3, ImageIcon[] ImgArr2,
        ImageIcon[] ImgArr1,
        ImageIcon[] ImgArrGo) {
        new StartCount(jl3, ImgArr3, ImgArr2, ImgArr1, ImgArrGo).start();
    }

   
    


    //이미지 생성쓰
    public ImageIcon imgMk(String path,int w,int h){

        return new ImageIcon(new ImageIcon(imgPath+path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    //틀렸을때 함수
    public void down(JLabel charlbl,ImageIcon[] charDown,ImageIcon [] charArr,JProgressBar gaugeBar,JLabel [] hplbl,JLabel comboJL2){
        hp--;
        combo=0;
        comboJL2.setText(combo + "");
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
    public void moving(JLabel backlbl,JLabel[] blockArr,JLabel charlbl, ImageIcon[] charArr,JProgressBar gaugeBar,JLabel stepsJL2,JLabel comboJL2){
        new MoveBackGround(backlbl).start();
        new MoveBlock(blockArr, moveX, moveY).start();
        new CharAni(charlbl, charArr).start();
        if(gauge<100){
            gaugeUp(gaugeBar, gauge += 3);
        }
        keyCount++;
        combo++;
        stepsJL2.setText(keyCount + "");
        comboJL2.setText(combo + "");
    }
    
    
    
    ////소켓보내기
    public void send(int skillIdx) {
    	
        try{
            //서버로 보냄
            String msg="";
            InfoDTO dto = new InfoDTO();
            dto.setStep(keyCount);
            if(msg.equals("exit")){
                dto.setCommand(Info.EXIT);
            } else {
                dto.setCommand(Info.SEND);
                dto.setNickName(nick);
                dto.setMoveX(moveX);
                dto.setStep(keyCount);
                dto.setSkill(skillIdx);
            }
            writer.writeObject(dto);
            writer.flush();
            dto.setSkill(0);
        }catch(IOException io){
            io.printStackTrace();
        }
       
    }
    
    //서버 연결부
    public void service(){
        try{
            socket = new Socket("58.224.48.139",9500);
            reader= new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("전송 준비 완료!");
           
        } catch(UnknownHostException e ){
            System.out.println("서버를 찾을 수 없습니다.");
            e.printStackTrace();
            System.exit(0);
        } catch(IOException e){
            System.out.println("서버와 연결이 안되었습니다.");
            e.printStackTrace();
            System.exit(0);
        }
        
        
        try{
        	//연결시 서버에 보내는 코드
            InfoDTO dto = new InfoDTO();
            dto.setNickName(nick);
            dto.setStep(keyCount);
            dto.setMoveX(moveX);
            dto.setCommand(Info.JOIN);
            writer.writeObject(dto);  //역슬러쉬가 필요가 없음
            writer.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
       
        //스레드 생성
       
        Thread t = new Thread(this);
        t.start();

    }
    ////서버로부터 데이터 받기
    @Override
    public void run(){
        
        InfoDTO dto= null;
        while(true){
            try{
                dto = (InfoDTO) reader.readObject();
                if(dto.getCommand()==Info.EXIT){  //서버로부터 내 자신의 exit를 받으면 종료됨
                    reader.close();
                    writer.close();
                    socket.close();
                    System.exit(0);
                } else if(dto.getCommand()==Info.SEND){
   
					 if(dto.getNickName()!=null&&dto.getNickName().equals(otherNick)) {
						 otherKeyCount=dto.getStep(); 
						 otherMoveX=dto.getMoveX();
						
						 if(dto.getSkill()==1) {
							 new SkillIce(iceBackbl).start();
						 }else if(dto.getSkill()==2) {
							 new SkillBlackEye(blackEyelbl).start();
						 }else if(dto.getSkill()==3) {
							 new SkillIce(iceBackbl).start();
						 }
					 }
	
                }
            }catch(IOException e){
                e.printStackTrace();
            }catch(ClassNotFoundException e){
            	
            	
            	
            	
                e.printStackTrace();
            }  
        }
    }
    public void otherMove(int step) {
    	otherChar.setLocation(blockArr[step].getLocation().x-20,blockArr[step].getLocation().y-130);

    	
    }
    // 게이지 채워주는 함수
    public void gaugeUp(JProgressBar gaugeBar, int gauge) {
        gaugeBar.setValue(gauge);
    }
   
}

