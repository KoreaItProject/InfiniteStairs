package swing;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.css.RGBColor;

import java.awt.event.*;
import java.util.prefs.BackingStoreException;
import java.awt.*;

public class GameCharSelectPanel extends JPanel implements ActionListener {

    int FramW = 1000, FramH = 900;
    static int charIdx = 0;
    static boolean player1 = false;
    String imgPath;
    int charSizeW = 300;
    int charSizeH = 600;
    int gifSize = 300;

    ImageIcon s[];
    Icon gif[];
    JLabel l;
    JButton readyBtn;
    JButton leftBtn;
    JButton rightBtn;

    int i, l1;
    JFrame frame;
    public GameCharSelectPanel(JFrame frame) {
        getSetting();
        this.frame=frame;

     
        // JPanel game2 = new JPanel();
        this.setLayout(null);
        this.setSize(FramW, FramH);
        
    

        leftBtn = new JButton("<<");
        rightBtn = new JButton(">>");

        leftBtn.setBounds(110, 640, 60, 30);
        rightBtn.setBounds(240, 640, 60, 30);

        this.add(leftBtn);
        this.add(rightBtn);

        // add(game2);

        leftBtn.addActionListener(this);
        rightBtn.addActionListener(this);

        ImageIcon readyIcon= imgMk("sub/ready.png", 200, 80, Image.SCALE_SMOOTH);
        JLabel readylbl1 = new JLabel(readyIcon);
        JLabel readylbl2 = new JLabel(readyIcon);
        readylbl1.setBounds(65, 100, 300, 120);
        readylbl2.setBounds(645, 100, 300, 120);
        this.add(readylbl1);
        this.add(readylbl2);
        
        s = new ImageIcon[3];
        gif = new Icon[3];
    

        for(int i=0;i<s.length;i++){
            s[i] =imgMk("character/char"+i+".gif", gifSize, gifSize,0);
        }
   
         


        l = new JLabel("", JLabel.CENTER);
        l.setBounds(50, 50, charSizeW, charSizeH + 200);
        l.setIcon(s[0]);

        //레디 버튼
        readyBtn = new JButton("ready");
        readyBtn.setBackground(new Color(100, 214, 245));
        readyBtn.setBounds(422, 615, 146, 80);
        readyBtn.addActionListener(this); // 이벤트 호출 메서드
        readyBtn.setFont(new Font("Gothic", Font.BOLD, readyBtn.getFont().getSize() + 23));
        this.add(readyBtn);
        this.add(l);
    

        
        ImageIcon whiteIcon = imgMk("sub/white.png",330, 490, Image.SCALE_SMOOTH);
        ImageIcon someone=imgMk("sub/someone.png",330, 490, Image.SCALE_SMOOTH);
        JLabel whiteIbl1=new JLabel();//왼쪽 캐릭터 div
        JLabel whiteIbl2=new JLabel();//오른쪽 캐릭터 div
        JLabel whiteIbl3=new JLabel();//밑에 설명 div
        JLabel whiteIbl4=new JLabel();//위에 방코드 div
        JLabel contents=new JLabel("<html><body style='font-size:16px;'><p>&ensp오른쪽 방향키 : 보는 방향으로 이동</p><p>&ensp왼쪽 방향키 : 방향 전환 이동</p><p>&ensp스페이스바 : 게이지 100%시 스킬사용</p></body></html>");
        
        whiteIbl1.setBounds(40, 215, 330, 490);
        whiteIbl2.setBounds(620, 215, 330, 490);
        whiteIbl3.setBounds(40, 725, 910, 120);
        contents.setBounds(40, 725, 910, 120);
        whiteIbl4.setBounds(40, 30, 910, 80);
        this.add(whiteIbl1);
        this.add(whiteIbl2);
        this.add(contents);
        this.add(whiteIbl3);
        this.add(whiteIbl4);
        whiteIbl1.setIcon(whiteIcon);
        whiteIbl2.setIcon(someone);
        whiteIbl3.setIcon(imgMk("sub/white.png",910, 120, Image.SCALE_SMOOTH));
        whiteIbl4.setIcon(imgMk("sub/white.png",910, 80, Image.SCALE_SMOOTH));


        //배경
        ImageIcon backImg = imgMk("sub/vs.png",FramW, FramH, Image.SCALE_SMOOTH);
        JLabel backlbl=new JLabel();
        backlbl.setBounds(0, 0, FramW, FramH);
        this.add(backlbl);
        backlbl.setIcon(backImg);


       



    } // 생성자

    public void getSetting() {
        Setting settings = new Setting();
        imgPath = settings.getImgPath();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leftBtn) {
            if (charIdx == 0) {
                charIdx += 2;
                l.setIcon(s[charIdx]);
                System.out.println(charIdx);
            } else {
                charIdx = charIdx - 1;
                l.setIcon(s[charIdx]);
                System.out.println(charIdx);
            }
        }
        if (e.getSource() == rightBtn) {
            if (charIdx == s.length - 1) {
                charIdx -= 2;
                l.setIcon(s[charIdx]);
                System.out.println(charIdx);
            } else {
                charIdx = charIdx + 1;
                l.setIcon(s[charIdx]);
                System.out.println(charIdx);
            }
        }

        if (e.getSource() == readyBtn) {
           frame.dispose();
            new GameStart().start();
        } else if (e.getSource() == rightBtn) {
            // charLabel
        }

    }

    // 이미지 생성쓰
    public ImageIcon imgMk(String path, int w, int h,int hint) {
        System.out.println(imgPath + path);
        return new ImageIcon(new ImageIcon(imgPath + path).getImage().getScaledInstance(w, h, hint));
    }

} // class

class GameStart extends Thread {

    public GameStart() {

    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new GameStartFrame(GameCharSelectPanel.charIdx,0);
        super.run();
    }

}


