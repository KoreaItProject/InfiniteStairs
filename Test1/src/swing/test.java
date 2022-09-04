package swing;
import javax.swing.*;
import java.awt.*;
 
public class test extends JFrame{
    
 
    GridBagLayout gbl;
    JPanel gbjp;
     
    public test()
    {
        super("J프레임 테스트");  //프레임의 타이틀
        setSize(600,600);        //컨테이너 크기 지정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //기본 레이아웃을 FlowLayout으로 지정    좌우 간격 20px, 상하 간격 50px
        setLayout(new FlowLayout(FlowLayout.LEFT,20,50));
        
        //2행 2열의 그리드 패널 사용
        JPanel gjp = new JPanel(new GridLayout(2,2));
        gjp.add(new JLabel("여기는"));
        gjp.add(new JButton("b1"));
        gjp.add(new JLabel("그리드"));
        gjp.add(new JButton("b2"));
        
        //BorderLayout 사용
        JPanel bjp = new JPanel(new BorderLayout());
        bjp.add(new JButton("북"),BorderLayout.NORTH);
        bjp.add(new JButton("서"),BorderLayout.WEST);
        bjp.add(new JButton("동"),BorderLayout.EAST);
        bjp.add(new JButton("남"),BorderLayout.SOUTH);
        bjp.add(new JButton("센터"),BorderLayout.CENTER);
        
        //GridBagLayout 사용
        gbjp = new JPanel();
        gbl = new GridBagLayout();
        
        Label lblReceive = new Label("test",Label.RIGHT);
        JButton jb = new JButton("a");
        JButton jb2 = new JButton("b");
        
        gbjp.setLayout(gbl);
        
        gbinsert(jb, 0, 0, 1, 1);
        gbinsert(jb2, 1, 0, 1, 1);
        gbinsert(lblReceive, 0, 1, 1, 1);
        
        
        
        //null 사용
        JPanel njp = new JPanel(null);
        //njp.setLayout(null);
        for(int i=1;i<=5;i++) {
            JButton jbutton = new JButton(i+"번째 버튼");
            jbutton.setBounds(10, i*40,100,30);
            njp.add(jbutton);
        }
        
        
        
        //패널별 백그라운드 색상 지정
        gbjp.setBackground(Color.red);
        gjp.setBackground(Color.white);
        bjp.setBackground(Color.black);
        njp.setBackground(Color.white);
        
        //패널의 크기 지정
        gjp.setPreferredSize(new Dimension(170, 200));
        bjp.setPreferredSize(new Dimension(170, 200));
        gbjp.setPreferredSize(new Dimension(170, 200));
        njp.setPreferredSize(new Dimension(170, 200));
        
        add(gjp);
        add(bjp);
        add(gbjp);
        add(njp);
 
        setVisible(true);        //창을 보이게함

        
    }
    
    public void gbinsert(Component c, int x, int y, int w, int h){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill= GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbl.setConstraints(c,gbc);
        gbjp.add(c);
    }
    
    public static void main(String[] args) {
 
        new test();
 
    }
}