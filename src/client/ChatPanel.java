package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import server.User;

public class ChatPanel extends JPanel {
	
	private String name = LoginPanel.userID; // 현재 로그인한 유저
	
	private ChatFrame cf;
	private JLabel profile;
	private JTextPane textPaneChat;
	private TextField txtWrite;
	private JButton sendBtn;
	private JScrollPane chatScroll;
	public JButton createRoomBtn;

	private String roomTitle;
	private CommandController controller = CommandController.getController();
	
	public ChatPanel(ChatFrame cf,String roomTitle) {
		this.roomTitle = roomTitle;
		this.cf = cf;
		System.out.println("roomTitle = "+roomTitle);
		setLayout(null);
		setSize(400,600);
		setBackground(new Color(155, 187, 212));
		
		profile = new JLabel(roomTitle);
		profile.setBounds(0, 0, 565, 40);
		profile.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		profile.setOpaque(true);
		profile.setForeground(Color.white);
		profile.setBackground(new Color(0,0,0,122));
		cf.add(profile);
		
		//채팅 보이는 부분
		textPaneChat = new JTextPane();
		textPaneChat.setEditable(false);
		textPaneChat.setBounds(0, 40, 395, 400);
		textPaneChat.setBackground(new Color(155, 187, 212));
		textPaneChat.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
		//채팅 스크롤 
		chatScroll = new JScrollPane(textPaneChat);
		chatScroll.setBounds(0, 40, 395, 400);
		cf.add(chatScroll);
	
		//메세지 입력 부분
		txtWrite = new TextField();
		txtWrite.setBounds(0, 440, 320, 70);
		txtWrite.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
	    cf.add(txtWrite);

		sendBtn = new JButton("전송");
		sendBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		sendBtn.setBackground(new Color(247, 230, 0));
		sendBtn.setBounds(321, 440, 75, 69);
		cf.add(sendBtn);
		
	
		start(); //액션이벤트 지정 메소드
	}
	
	public void start() { // 액션이벤트 지정 메소드
		Myaction action = new Myaction(this.textPaneChat);
		sendBtn.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
		txtWrite.addActionListener(action);
	}

	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		public Myaction(JTextPane textPaneChat) {

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에서 Enter key 치면
			if (e.getSource() == sendBtn || e.getSource() == txtWrite) 
			{
				String msg = String.format("%s//%s//[%s] %s//\n", User.CODE_400, roomTitle, name, txtWrite.getText());

				String[] array = msg.split("//");
				System.out.println("현재 접속자: " + name);

				//채팅을 친 채팅방 이름, 유저네임, 메시지를 서버로 전달
				controller.send_Message(msg);				
				txtWrite.setText("");
				txtWrite.requestFocus();
			}

		}
	}
	
	public JTextPane getTextPaneChat() {
		return this.textPaneChat;
	}
	
	public void setTextPaneChat(JTextPane textPaneChat){
		this.textPaneChat = textPaneChat;
		
	}
}
