package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import client.ChatPanel.Myaction;
import server.User;

public class LoginPanel extends JPanel {
	/* 네트워크용 변수 */
	private final String ip = "127.0.0.1";
	private final int port = 30000;
	public static Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	public static String userID;
	private CommandController controller;

	private JTextField userName; // user 이름 입력
	private LoginFrame lf;
	private String id;
	private StartPanel startPanel;
	final String logo = "src/img/sangsang.png";
	private JButton btnNewButton;

	private JLabel Logo;

	public LoginPanel(LoginFrame loginFrame) {

		this.lf = loginFrame;
		setBackground(new Color(255, 222, 0));
		setLayout(null);
		setSize(400, 600);

		Logo = new JLabel(new ImageIcon(logo));
		Logo.setBounds(0,0, 380, 380);
		Logo.setOpaque(false);
		lf.add(Logo);

		JLabel idLabel = new JLabel("사용자 이름 ");
		idLabel.setBounds(130, 300, 120, 30);
		idLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		lf.add(idLabel);

		userName = new JTextField();
		userName.setBounds(70, 350, 200, 50);
		lf.add(userName);
		
		btnNewButton = new JButton("확인");
		btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		btnNewButton.setBounds(280, 350, 70, 50);
		lf.add(btnNewButton);

		start();
	}
	
	public void start() { // 액션이벤트 지정 메소드
		Myaction action = new Myaction();
		btnNewButton.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
		userName.addActionListener(action);
	}

	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		public Myaction() {

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == btnNewButton || e.getSource() == userName) {
				String user_id = userName.getText().trim();
				userID = user_id;

				if (user_id != null) { // 유저아이디 빈칸이면 안됨
					network();// 서버와 연결
					MainFrame f = new MainFrame(userID);
					lf.dispose();
				}
			}

		}

	}

	/* 네트워크 소켓 연결 */

	public void network() {
		try {
			socket = new Socket(ip, port);
			if (socket != null) // socket이 null값이 아닐때 즉! 연결되었을때
			{
				controller = CommandController.getController();// 맨처음
																// getController하는
																// 부분->여기서 생성됨
				Connection(); // 연결 메소드를 호출
			}
		} catch (UnknownHostException e) {

		} catch (IOException e) {
			System.out.println("Client 소켓 접속 에러!!\n");
		}
	}

	public void Connection() { // 실직적인 메소드 연결부분
		try { // 스트림 설정
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			// textArea.append("스트림 설정 에러!!\n");
			System.out.println("스트림 설정 에러!!\n");
		}
		if (controller == null) {
			System.out.println("controller == null");
		} else {
			controller.send_Message(User.SIGNAL_USER_ID + "//" + userID); // 정상적으로 연결되면 나의 아이디를 전송
			//이후에 바로 SIGNAL_ONLINE_USER_LIST를 보내기때문에 서버가 처리할 시간을 주어야 함.
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public StartPanel getStartPanel() {
		return startPanel;
	}

	public void setStartPanel(StartPanel startPanel) {
		this.startPanel = startPanel;
	}
}
