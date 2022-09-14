package swing;

import java.awt.Image;


import javax.swing.*;

import swing.Bird.BirdAni;
import java.awt.event.*;
import java.awt.*;
public class GameRoomPan extends JPanel implements ActionListener{
    int frameW = 1000, frameH = 900;
    JFrame frame;
    String imgPath;
    JButton roomBtn1lbl,roomBtn2lbl,roomBtn3lbl,roomBtn4lbl;
    JTextArea codeText,nicktxt;
    JLabel  blacklbl,codelbl,blacklbl1;
    public GameRoomPan(JFrame frame){
        this.frame=frame; 
        this.setLayout(null);
        this.setSize(frameW, frameH);
        getSetting();

       
        JLabel nicklbl=new JLabel("닉네임을 입력해주세요");
        nicklbl.setFont(new Font("Gothic", Font.BOLD, nicklbl.getFont().getSize() + 20));
        nicklbl.setForeground(Color.white);
        this.add(nicklbl);
        nicklbl.setBounds(330,160,500,30);
        nicktxt= new JTextArea();
        this.add(nicktxt);
        nicktxt.setFont(new Font("Gothic", Font.BOLD, nicktxt.getFont().getSize()+10 ));
        nicktxt.setBounds(370,210,260,30);
            //닉네임 입력창
            ImageIcon black1 = imgMk("room/black.png",360, 130, Image.SCALE_SMOOTH);
            blacklbl1=new JLabel(black1);
            blacklbl1.setBounds(320, 140, 360, 130);
            this.add(blacklbl1);



         //버튼
         ImageIcon roomBtn1 = imgMk("room/roomBtn1.png",300, 80, Image.SCALE_SMOOTH);
         ImageIcon roomBtn2 = imgMk("room/roomBtn2.png",300, 80, Image.SCALE_SMOOTH);
         roomBtn1lbl=new JButton(roomBtn1);
         roomBtn2lbl=new JButton(roomBtn2);
         roomBtn1lbl.setBounds(350, 480, 296, 80);
         roomBtn2lbl.setBounds(350, 380, 296, 80);
         this.add(roomBtn1lbl);
         this.add(roomBtn2lbl);
         roomBtn1lbl.addActionListener(this);
         roomBtn2lbl.addActionListener(this);

         //코드 입력
         codeText =new JTextArea();
         codeText.setBounds(455, 490, 170, 25);
         this.add(codeText);
         codeText.setVisible(false);
         codeText.setFont(new Font("Gothic", Font.BOLD, codeText.getFont().getSize()+10 ));
         codelbl=new JLabel("코드입력");
         codelbl.setFont(new Font("Gothic", Font.BOLD, codelbl.getFont().getSize()+8 ));
         codelbl.setForeground(Color.white);
         codelbl.setBounds(365, 490, 170, 25);
         this.add(codelbl);


         //코드 입력 버튼
         roomBtn3lbl=new JButton("취소");
         roomBtn4lbl=new JButton("확인");
         roomBtn3lbl.setBounds(400, 520, 100, 30);
         roomBtn4lbl.setBounds(500, 520, 100, 30);
         this.add(roomBtn3lbl);
         this.add(roomBtn4lbl);
         roomBtn3lbl.addActionListener(this);
         roomBtn4lbl.addActionListener(this);
         roomBtn3lbl.setVisible(false);
         roomBtn4lbl.setVisible(false);
         
        //코드입력창
         ImageIcon black = imgMk("room/black.png",300, 80, Image.SCALE_SMOOTH);
         blacklbl=new JLabel(black);
         blacklbl.setBounds(350, 480, 294, 80);
         this.add(blacklbl);
         blacklbl.setVisible(false);
     

         //조작법
         JLabel contents=new JLabel("<html><body style='font-size:16px;'><p>&ensp 조작법 </p><p>&ensp오른쪽 방향키 : 보는 방향으로 이동</p><p>&ensp왼쪽 방향키 : 방향 전환 이동</p><p>&ensp스페이스바 : 게이지 100%시 스킬사용</p></body></html>");
         JLabel whiteIbl3=new JLabel(imgMk("sub/white.png",910, 140, Image.SCALE_SMOOTH));//밑에 설명 div
         whiteIbl3.setBounds(40, 680, 910, 140);
         contents.setBounds(40, 680, 910, 140);
         this.add(contents);
         this.add(whiteIbl3);
       
         //새
        ImageIcon [] birdIcon = new ImageIcon[2];
        birdIcon[0] = imgMk("bird/rbird.gif", 60, 60, 0);
        birdIcon[1] = imgMk("bird/lbird.gif", 70, 70, 0);
        JLabel[] birdJLabel = new JLabel[2];
        birdJLabel[0] = new JLabel(birdIcon[0]);
        birdJLabel[1] = new JLabel(birdIcon[1]);

        birdJLabel[0].setBounds(0, 100, 60, 60);
        birdJLabel[1].setBounds(1000, 250, 70, 70);

        add(birdJLabel[0]);
        add(birdJLabel[1]);

        new BirdAni(birdJLabel, birdIcon).start();
 



         //배경
         ImageIcon backImg = imgMk("room/backRoom.png",frameW, frameH, Image.SCALE_SMOOTH);
         JLabel backlbl=new JLabel(backImg);
         backlbl.setBounds(0, 0, frameW, frameH);
         this.add(backlbl);

 

 

    }
    public void getSetting() {
        Setting settings = new Setting();
        this.imgPath=settings.getImgPath();

    }


    // 이미지 생성쓰
    public ImageIcon imgMk(String path, int w, int h,int hint) {
        return new ImageIcon(new ImageIcon(imgPath + path).getImage().getScaledInstance(w, h, hint));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == roomBtn1lbl) {//방만들기 바로 입장됨
          ((GameSelectFrame)frame).showCharSelectPan();
         
            
         }else if(e.getSource() == roomBtn2lbl) {//방입장하기 버튼
            roomBtn1lbl.setVisible(false);
            codeText.setVisible(true);
            blacklbl.setVisible(true);
            codelbl.setVisible(true);
            roomBtn3lbl.setVisible(true);
            roomBtn4lbl.setVisible(true);
         }
         else if(e.getSource() == roomBtn3lbl) {//취소
            roomBtn1lbl.setVisible(true);
            codeText.setVisible(false);
            blacklbl.setVisible(false);
            codelbl.setVisible(false);
            roomBtn3lbl.setVisible(false);
            roomBtn4lbl.setVisible(false);
         }
         else if(e.getSource() == roomBtn4lbl) {//확인 코드값 가지고 입장

         }

         
        
    }
    
}
