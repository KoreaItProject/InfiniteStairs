package swing;

import javax.swing.*;

import swing.Bird.BirdAni;
import swing.Bird.BirdAni2;

import java.awt.*;
import java.awt.event.*;

public class GameNickPan extends JPanel implements ActionListener {
    
    int frameW = 1000, frameH = 900;
    JFrame frame;
    String imgPath;
    JButton btn;
    JTextArea nicktxt;
    static boolean running = true;




    public GameNickPan(JFrame frame) {
        this.frame=frame; 
        this.setLayout(null);
        this.setSize(frameW, frameH);
        getSetting();

        JLabel nicklbl=new JLabel("닉네임을 입력해주세요");
        nicklbl.setFont(new Font("Gothic", Font.BOLD, nicklbl.getFont().getSize() + 23));
        nicklbl.setForeground(Color.white);
        this.add(nicklbl);
        nicklbl.setBounds(310,120,500,30);

        nicktxt= new JTextArea();
        this.add(nicktxt);
        nicktxt.setFont(new Font("Gothic", Font.BOLD, nicktxt.getFont().getSize()+10 ));
        nicktxt.setBounds(360,170,260,30);

        btn = new JButton();
        btn.setText("확인");
        btn.setBackground(new Color(100, 214, 245));
        btn.setBounds(410, 220, 160, 50);
        btn.addActionListener(this); // 이벤트 호출 메서드
        btn.setFont(new Font("Gothic", Font.BOLD, btn.getFont().getSize() + 23));
        this.add(btn);

        JLabel contents=new JLabel("<html><body style='font-size:16px;'><p>&ensp 조작법 </p><p>&ensp오른쪽 방향키 : 보는 방향으로 이동</p><p>&ensp왼쪽 방향키 : 방향 전환 이동</p><p>&ensp스페이스바 : 게이지 100%시 스킬사용</p></body></html>");
        JLabel whiteIbl3=new JLabel(imgMk("sub/white.png",910, 140, Image.SCALE_SMOOTH));//밑에 설명 div
        whiteIbl3.setBounds(40, 680, 910, 140);
        contents.setBounds(40, 680, 910, 140);
        this.add(contents);
        this.add(whiteIbl3);

        ImageIcon [] birdIcon = new ImageIcon[2];
        birdIcon[0] = imgMk("bird/rbird.gif", 60, 60, 0);
        birdIcon[1] = imgMk("bird/lbird.gif", 70, 70, 0);
        JLabel[] birdJLabel = new JLabel[2];
        birdJLabel[0] = new JLabel(birdIcon[0]);
        birdJLabel[1] = new JLabel(birdIcon[1]);

        birdJLabel[0].setBounds(0, 450, 60, 60);
        birdJLabel[1].setBounds(1000, 600, 70, 70);

        add(birdJLabel[0]);
        add(birdJLabel[1]);

        new BirdAni(birdJLabel, birdIcon).start();

        //배경
        ImageIcon backImg = imgMk("back/backg.png",frameW, frameH, Image.SCALE_SMOOTH);
        JLabel backlbl=new JLabel();
        backlbl.setBounds(0, 0, frameW, frameH);
        this.add(backlbl);
        backlbl.setIcon(backImg);


        


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
        if (e.getSource() == btn) {
            ((GameSelectFrame)frame).nick=nicktxt.getText();
            running=false;
            //((GameSelectFrame)frame).showCharSelectPan();
           ((GameSelectFrame)frame).showRoomPan();
            
         }
        
    }
}
