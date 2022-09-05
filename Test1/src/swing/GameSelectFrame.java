package swing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;

public class GameSelectFrame extends JFrame implements ActionListener{
    
    int FramW = 1000, FramH = 900;
    static int charIdx=1;
    public GameSelectFrame(){
        
        setSize(FramW, FramH); // 컨테이너 크기 지정
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        // 레이블을 넣기 위한 패널 생성
        JLabel num1 = new JLabel("캐릭터");
        JPanel gameCharSelectPanel = new JPanel() ;
        gameCharSelectPanel.add(num1);
    
        JButton readyBtn = new JButton("확인");
    
        readyBtn.setBounds(500, 500, 100, 100);
        readyBtn.addActionListener(this); // 이벤트 호출 메서드
    
        gameCharSelectPanel.add(readyBtn); // 패널에 버튼 추가
    
        // playerLabel.setVisible(aFlag);
        add(gameCharSelectPanel);

        setVisible(true);
       
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        new GameStart().start();
    }

}
class GameStart extends Thread{

    public GameStart(){
      
    }
    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        new GameStartFrame(GameSelectFrame.charIdx);
        super.run();
    }
    
}
