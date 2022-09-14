package swing;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.*;
import java.util.prefs.BackingStoreException;
import java.awt.*;

public class GameSelectFrame extends JFrame {

    int FramW = 1000, FramH = 900;

    public GameSelectFrame() {

        setSize(FramW, FramH); // 프레임 크기 지정
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // 레이블을 넣기 위한 패널 생성

        // 패널 생성
        JPanel gameCharSelectPanel = new GameCharSelectPanel(this);
        add(gameCharSelectPanel);
        setVisible(true);

    } // 생성자

} // class
