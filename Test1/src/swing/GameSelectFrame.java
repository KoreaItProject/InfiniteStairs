package swing;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.prefs.BackingStoreException;
import java.awt.*;

public class GameSelectFrame extends JFrame {

    int FramW = 1000, FramH = 900;
    public JPanel panel;

    public GameSelectFrame() {

        setSize(FramW, FramH); // 프레임 크기 지정
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // 레이블을 넣기 위한 패널 생성

        // 패널 생성
        // JPanel gameCharSelectPanel = new GameCharSelectPanel(this);
        panel = new GameRoomPan(this);
        add(panel);
        setVisible(true);

    } // 생성자

    public void showCharSelectPan(ObjectInputStream reader, ObjectOutputStream writer, String roomId,
            String nick, int seed) {

        remove(panel);
        revalidate();
        repaint();
        panel = new GameCharSelectPanel(this, reader, writer, roomId, nick, seed);
        add(panel);
        revalidate();
        repaint();
        setVisible(true);

    }

    public void showRoomPan() {
        remove(panel);
        revalidate();
        repaint();
        panel = new GameRoomPan(this);
        add(panel);
        setVisible(true);
    }

} // class
