package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import server.User;

public class Emoticon extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton emo1;
	private JButton emo2;
	private JButton emo3;
	private JButton emo4;
	private JButton emo5;
	private JButton emo6;
	private JButton emo7;
	private JButton emo8;
	private JButton emo9;
	private JButton emo10;
	private JButton emo11;
	private JButton emo12;

	private String UserName;
	private CommandController controller = CommandController.getController();
	
	ImageIcon good = new ImageIcon("src/img/good.png");
	ImageIcon cry = new ImageIcon("src/img/cry.png");
	ImageIcon happy = new ImageIcon("src/img/happy.png");
	ImageIcon imok = new ImageIcon("src/img/imok.png");
	ImageIcon cute = new ImageIcon("src/img/cute.png");
	ImageIcon shy = new ImageIcon("src/img/shy.png");
	ImageIcon sorry = new ImageIcon("src/img/sorry.png");
	ImageIcon study = new ImageIcon("src/img/study.png");
	ImageIcon icant = new ImageIcon("src/img/icant.png");
	ImageIcon question = new ImageIcon("src/img/question.png");
	ImageIcon kkk = new ImageIcon("src/img/kkk.png");
	ImageIcon bye = new ImageIcon("src/img/bye.png");
	private String roomTitle;

	public Emoticon(String UserName, String roomTitle) {
		this.UserName = UserName;
		this.roomTitle = roomTitle;
//		try {
//			socket = new Socket(ip_addr, Integer.parseInt(port_no));
//			oos = new ObjectOutputStream(socket.getOutputStream());
//			oos.flush();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		setTitle("이모티콘");
		setResizable(false);
		setBounds(0, 0, 400, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		emo1 = new JButton(good);
		emo1.setBackground(new Color(255, 255, 204));
		emo1.setBounds(10, 20, 90, 90);
		contentPane.add(emo1);
		EmoticonAction actionEmo = new EmoticonAction();
		emo1.addActionListener(actionEmo);
		
		emo2 = new JButton(happy);
		emo2.setBackground(new Color(255, 255, 255));
		emo2.setBounds(100, 20, 90, 90);
		contentPane.add(emo2);
		emo2.addActionListener(actionEmo);
		
		emo3 = new JButton(cry);
		emo3.setBackground(new Color(255, 255, 255));
		emo3.setBounds(190, 20, 90, 90);
		contentPane.add(emo3);
		emo3.addActionListener(actionEmo);

		emo4 = new JButton(imok);
		emo4.setBackground(new Color(255, 255, 255));
		emo4.setBounds(280, 20, 90, 90);
		contentPane.add(emo4);
		emo4.addActionListener(actionEmo);

		emo5 = new JButton(cute);
		emo5.setBackground(new Color(255, 255, 255));
		emo5.setBounds(10, 110, 90, 90);
		contentPane.add(emo5);
		emo5.addActionListener(actionEmo);

		emo6 = new JButton(shy);
		emo6.setBackground(new Color(255, 255, 255));
		emo6.setBounds(100, 110, 90, 90);
		contentPane.add(emo6);
		emo6.addActionListener(actionEmo);

		emo7 = new JButton(sorry);
		emo7.setBackground(new Color(255, 255, 255));
		emo7.setBounds(190, 110, 90, 90);
		contentPane.add(emo7);
		emo7.addActionListener(actionEmo);

		emo8 = new JButton(study);
		emo8.setBackground(new Color(255, 255, 255));
		emo8.setBounds(280, 110, 90, 90);
		contentPane.add(emo8);
		emo8.addActionListener(actionEmo);

		emo9 = new JButton(icant);
		emo9.setBackground(new Color(255, 255, 255));
		emo9.setBounds(10, 200, 90, 90);
		contentPane.add(emo9);
		emo9.addActionListener(actionEmo);

		emo10 = new JButton(question);
		emo10.setBackground(new Color(255, 255, 255));
		emo10.setBounds(100, 200, 90, 90);
		contentPane.add(emo10);
		emo10.addActionListener(actionEmo);
		
		emo11 = new JButton(kkk);
		emo11.setBackground(new Color(255, 255, 255));
		emo11.setBounds(190, 200, 90, 90);
		contentPane.add(emo11);
		emo11.addActionListener(actionEmo);
		
		emo12 = new JButton(bye);
		emo12.setBackground(new Color(255, 255, 255));
		emo12.setBounds(280, 200, 90, 90);
		contentPane.add(emo12);
		emo12.addActionListener(actionEmo);
		
	}
	
	class EmoticonAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			ImageIcon img = new ImageIcon("src/img/good.png");
			
			if(e.getSource()==emo1) {
				System.out.println("1번 이모티콘 클릭");
			   img = new ImageIcon("src/img/good.png");
			}
			else if(e.getSource()==emo2) {
				System.out.println("2번 이모티콘 클릭");
				img = new ImageIcon("src/img/cry.png");
			}
			else if(e.getSource()==emo3) {
				System.out.println("3번 이모티콘 클릭");
				img = new ImageIcon("src/img/happy.png");
			}
			else if(e.getSource()==emo4) {
				System.out.println("4번 이모티콘 클릭");
				img = new ImageIcon("src/img/imok.png");
			}
			else if(e.getSource()==emo5) {
				System.out.println("5번 이모티콘 클릭");
				img = new ImageIcon("src/img/cute.png");
			}
			else if(e.getSource()==emo6) {
				System.out.println("6번 이모티콘 클릭");
				img = new ImageIcon("src/img/shy.png");
			}
			else if(e.getSource()==emo7) {
				System.out.println("7번 이모티콘 클릭");
				img = new ImageIcon("src/img/sorry.png");
			}
			else if(e.getSource()==emo8) {
				System.out.println("8번 이모티콘 클릭");
				img = new ImageIcon("src/img/study.png");
			}
			else if(e.getSource()==emo9) {
				System.out.println("9번 이모티콘 클릭");
				img = new ImageIcon("src/img/icant.png");
			}
			else if(e.getSource()==emo10) {
				System.out.println("10번 이모티콘 클릭");
				img = new ImageIcon("src/img/question.png");
			}
			else if(e.getSource()==emo11) {
				System.out.println("11번 이모티콘 클릭");
				img = new ImageIcon("src/img/kkk.png");
			}
			else if(e.getSource()==emo12) {
				System.out.println("12번 이모티콘 클릭");
				img = new ImageIcon("src/img/bye.png");
			}
			
			
			String msg = String.format("%s//%s//%s\n", User.CODE_410, roomTitle, img); //일단 name 뺴버림 
			System.out.println("이모티콘 선택했어 ");
          
			controller.send_Image(msg);
		}
	}
	
	
	
		
	

}
