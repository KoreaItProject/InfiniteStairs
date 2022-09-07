package swing;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.*;
import java.awt.*;

public class GameSelectFrame extends JFrame implements ActionListener {

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
    JLabel r;
    JButton readyBtn;
    JButton leftBtn;
    JButton rightBtn;

    int i, l1;

    public GameSelectFrame() {
        getSetting();

        setSize(FramW, FramH); // 프레임 크기 지정
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // 레이블을 넣기 위한 패널 생성

        // 패널 생성
        JPanel gameCharSelectPanel = new JPanel();
        // JPanel game2 = new JPanel();
        gameCharSelectPanel.setLayout(null);
        gameCharSelectPanel.setSize(FramW, FramH);

        leftBtn = new JButton("<<");
        rightBtn = new JButton(">>");

        leftBtn.setBounds(90, 700, 50, 30);
        rightBtn.setBounds(220, 700, 50, 30);

        gameCharSelectPanel.add(leftBtn);
        gameCharSelectPanel.add(rightBtn);

        // add(game2);

        leftBtn.addActionListener(this);
        rightBtn.addActionListener(this);

        r = new JLabel("Ready");
        r.setBounds(150, 10, 100, 100);
        r.setVisible(false);
        gameCharSelectPanel.add(r);
        
        s = new ImageIcon[3];
        gif = new Icon[3];
        Setting settings=new Setting();
        s[0] = new ImageIcon(new ImageIcon(settings.getImgPath()+"character/char0.gif").getImage().getScaledInstance(gifSize,
                gifSize, 0));
        s[1] = new ImageIcon(new ImageIcon(settings.getImgPath()+"character/char1.gif").getImage().getScaledInstance(gifSize,
                gifSize, 0));
        s[2] = new ImageIcon(new ImageIcon(settings.getImgPath()+"character/char2.gif").getImage().getScaledInstance(gifSize,
                gifSize, 0));

        l = new JLabel("", JLabel.CENTER);
        l.setBounds(30, 0, charSizeW, charSizeH + 200);
        l.setIcon(s[0]);

        readyBtn = new JButton("ready");
        readyBtn.setBackground(Color.GREEN);
        readyBtn.setBounds(400, 700, 140, 100);
        readyBtn.addActionListener(this); // 이벤트 호출 메서드
        gameCharSelectPanel.add(readyBtn);
        gameCharSelectPanel.add(l);
        // add(readyBtn); // 패널에 버튼 추가

        add(gameCharSelectPanel);
        setVisible(true);

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
            dispose();
            new GameStart().start();
        } else if (e.getSource() == rightBtn) {
            // charLabel
        }

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
        new GameStartFrame(GameSelectFrame.charIdx,0);
        super.run();
    }

}
