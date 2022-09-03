package swing;

import java.awt.Button;

import javax.swing.Icon;

import javax.swing.ImageIcon;

import javax.swing.JFrame;

import javax.swing.JLabel;



public class move extends JFrame {



	public move() {



		setLayout(null);



		Icon ic = new ImageIcon("image\\캡처1.png");

		JLabel lbl = new JLabel(ic);

		lbl.setBounds(150, 30, 150,150);

		add(lbl);

		Button btn = new Button("dddddd");

		add(btn);



		setTitle("움직이는 스머프");

		setBounds(100, 100, 1000, 1000);

		setVisible(true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		String[] imgs = { "캡처1.png", "캡처2.png", "캡처3.png", "캡처4.png", "캡처5.png", "캡처6.png", "캡처7.png", "캡처8.png",

				"캡처9.png", "캡처10.png", "캡처11.png", "캡처12.png" };



		int idx = 0;

		int pos = 5;

		while (true) {

			idx = idx + 1;

			try {



				Thread.sleep(50);
				Icon icon1 = new ImageIcon("image\\" + imgs[idx % imgs.length]);

				lbl.setIcon(icon1);

				int x = lbl.getX();

				int y = lbl.getY();

				System.out.println(x);

				if (x < 10) {

					pos = 5;

				} else if (x > 750) {

					pos = -5;

				}

				lbl.setLocation(x + pos, y);

			} catch (InterruptedException e) {

				// TODO Auto-generated catch block				e.printStackTrace();

			}



		}



	}

    public static void main(String[] args) {
        move m =new move();
    }


}


