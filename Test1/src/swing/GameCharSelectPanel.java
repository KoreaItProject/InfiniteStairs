package swing;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.css.RGBColor;

import swing.SocketServer.InfoDTO;
import swing.SocketServer.InfoDTO.Info;
import swing.SoundF.sound;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.prefs.BackingStoreException;
import java.awt.*;

public class GameCharSelectPanel extends JPanel implements ActionListener ,Runnable {

    int FramW = 1000, FramH = 900;
    static int charIdx = 0;
    static boolean player1 = false;
    String imgPath;
    int charSizeW = 300;
    int charSizeH = 600;
    int gifSize = 300;

    //상대 들어왔나 안들어왔나
    ImageIcon someone;
    JLabel whiteIbl2;
    ImageIcon whiteIcon;

    ImageIcon s[];
    Icon gif[];
    JLabel l;
    JButton readyBtn;
    JButton leftBtn;
    JButton rightBtn;

    int ready=0;

    public static JLabel readylbl1,readylbl2;

    sound sd = new sound();
    int i, l1;
    JFrame frame;
    
    public static String roomId,nick;
    public static int seed;
    public static Socket socket;
    public static ObjectInputStream reader = null;
    public static ObjectOutputStream writer = null;
    public static Thread t1 ;



    public GameCharSelectPanel(JFrame frame,Socket socket,ObjectInputStream reader,ObjectOutputStream writer,String roomId, String nick,int seed) {
        getSetting();
        this.frame = frame;
        this.socket=socket;
        this.reader=reader;
        this.writer=writer;
        this.roomId=roomId;
        this.nick=nick;
        this.seed=seed;

        t1=new Thread(this);

        // JPanel game2 = new JPanel();
        this.setLayout(null);
        this.setSize(FramW, FramH);

        // 사운드 재생
        selectSoundStartFunc();

        leftBtn = new JButton("<<");
        rightBtn = new JButton(">>");

        leftBtn.setBounds(110, 640, 60, 30);
        rightBtn.setBounds(240, 640, 60, 30);

        this.add(leftBtn);
        this.add(rightBtn);


        // add(game2);

        leftBtn.addActionListener(this);
        rightBtn.addActionListener(this);

        ImageIcon readyIcon = imgMk("sub/ready.png", 200, 80, Image.SCALE_SMOOTH);
        readylbl1 = new JLabel(readyIcon);
        readylbl2 = new JLabel(readyIcon);
        readylbl1.setBounds(65, 100, 300, 120);
        readylbl2.setBounds(645, 100, 300, 120);
        this.add(readylbl1);
        this.add(readylbl2);
        readylbl1.setVisible(false);
        readylbl2.setVisible(false);



        s = new ImageIcon[3];
        gif = new Icon[3];

        for (int i = 0; i < s.length; i++) {
            s[i] = imgMk("character/char" + i + ".gif", gifSize, gifSize, 0);
        }

        l = new JLabel("", JLabel.CENTER);
        l.setBounds(50, 50, charSizeW, charSizeH + 200);
        l.setIcon(s[0]);

        // 레디 버튼
        readyBtn = new JButton("ready");
        readyBtn.setBackground(new Color(100, 214, 245));
        readyBtn.setBounds(422, 615, 146, 80);
        readyBtn.addActionListener(this); // 이벤트 호출 메서드
        readyBtn.setFont(new Font("Gothic", Font.BOLD, readyBtn.getFont().getSize() + 23));
        this.add(readyBtn);
        this.add(l);

        whiteIcon = imgMk("sub/white.png", 330, 490, Image.SCALE_SMOOTH);
        someone = imgMk("sub/someone.png", 330, 490, Image.SCALE_SMOOTH);
        JLabel whiteIbl1 = new JLabel();// 왼쪽 캐릭터 div
        whiteIbl2 = new JLabel();// 오른쪽 캐릭터 div
        JLabel whiteIbl3 = new JLabel();// 밑에 설명 div
        JLabel whiteIbl4 = new JLabel();// 위에 방코드 div
        JLabel contents = new JLabel(
                "<html><body style='font-size:16px;'><p>&ensp오른쪽 방향키 : 보는 방향으로 이동</p><p>&ensp왼쪽 방향키 : 방향 전환 이동</p><p>&ensp스페이스바 : 게이지 100%시 스킬사용</p></body></html>");

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
        whiteIbl2.setIcon(whiteIcon);
        whiteIbl3.setIcon(imgMk("sub/white.png", 910, 120, Image.SCALE_SMOOTH));
        whiteIbl4.setIcon(imgMk("sub/white.png", 910, 80, Image.SCALE_SMOOTH));

        // 배경
        ImageIcon backImg = imgMk("sub/vs.png", FramW, FramH, Image.SCALE_SMOOTH);
        JLabel backlbl = new JLabel();
        backlbl.setBounds(0, 0, FramW, FramH);
        this.add(backlbl);
        backlbl.setIcon(backImg);

   

        service("입장");
        t1.start();


    } // 생성자

    public void getSetting() {
        Setting settings = new Setting();
        imgPath = settings.getImgPath();

    }

    // 서버 연결부
    public void service(String message) {
       
        try {
            // 연결시 서버에 보내는 코드

                InfoDTO dto = new InfoDTO();
                dto.setNickName(nick);
                dto.setRoomId(roomId);
                dto.setMessage(message);
                dto.setCommand(Info.STATE);
                writer.writeObject(dto); // 역슬러쉬가 필요가 없음
                writer.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

       

    }

    @Override
    public void run() {
        InfoDTO dto = null;
        while (true) {
           
            try {
                dto =  (InfoDTO)reader.readObject();
                System.out.println(dto.getRoomId());
                if(dto.getRoomId()!=null&&dto.getRoomId().equals(roomId)){
                   

                    if(dto.getNickName()!=null&&!dto.getNickName().equals(nick)){ 
                        if (dto.getCommand() == Info.STATE) {
                            
                            if(dto.getMessage()!=null&&dto.getMessage().equals("입장")){//내가 방주인이고 상대가 입장했음
                                System.out.println(dto.getNickName()+"님 입장하셨습니다.");
                                whiteIbl2.setIcon(someone);
                                dto.setNickName(nick);
                                dto.setRoomId(roomId);
                                dto.setMessage("입장확인");
                                dto.setCommand(Info.STATE);
                                writer.writeObject(dto); // 역슬러쉬가 필요가 없음
                                writer.flush();
                            }else if(dto.getMessage()!=null&&dto.getMessage().equals("입장확인")){//내가 입장했고 상대가 내 입장을 확인했음
                                System.out.println(dto.getNickName()+"님 방에 입장하였습니다");
                                whiteIbl2.setIcon(someone);
                              
                            }


                        }else if(dto.getCommand() == Info.EXIT){
                            System.out.println("상대종료");
                            whiteIbl2.setIcon(whiteIcon);

                            
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
            selectSoundStopFunc();
            if(ready==0){
                readylbl1.setVisible(true);
                ready=1;
            }else{
                readylbl1.setVisible(false);
                ready=0;
            }
               

        } else if (e.getSource() == rightBtn) {
            // charLabel
        }

    }

    public void selectSoundStartFunc() {

        sd.selectSoundStart();

    }

    public void selectSoundStopFunc() {
        sd.selectSoundStop();
    }

    // 이미지 생성쓰
    public ImageIcon imgMk(String path, int w, int h, int hint) {
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
        new GameStartFrame(GameCharSelectPanel.charIdx, 0);
        super.run();
    }

}
