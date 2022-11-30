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
	
	private String name = LoginPanel.userID;
	
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
		//textPaneChat.setHorizontalAlignment(textPaneChat.RIGHT_ALIGNMENT);
		//cf.add(textPaneChat);
		
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
		
		//RecieveMassage(); //서버로부터 메시지를 수신하는 스레드
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
				String msg = null;
				//msg = String.format("%s//%s//[%s] %s\n", User.SIGNAL_NOMAL_MSG, roomTitle, name, txtWrite.getText());
				msg = String.format("%s//%s//[%s] %s//%s\n", User.SIGNAL_NOMAL_MSG, roomTitle, name, txtWrite.getText(), name);

				String[] array = msg.split("//");
				/*
				 * if(name.equals(LoginPanel.userID)) {//본인이 보내는 메세지면 오른쪽에 출력
				 * controller.AppendMyText(roomTitle, msg); } else controller.send_Message(msg);
				 */
					
				//채팅을 친 채팅방 이름, 유저네임, 메시지를 서버로 전달
				controller.send_Message(msg);
				
				if(array[2].substring(1,5).equals(LoginPanel.userID)) {
					 msg.trim(); 
					 System.out.println("내가보낸메세지");
					 int len = textPaneChat.getDocument().getLength();
					 textPaneChat.setCaretPosition(len);

					 Document doc = textPaneChat.getDocument(); SimpleAttributeSet
					 attributeSet = new SimpleAttributeSet();
					 StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_RIGHT);
					 StyleConstants.setForeground(attributeSet, Color.black);
					 StyleConstants.setBackground(attributeSet, Color.yellow);
					 ((StyledDocument) doc).setParagraphAttributes(len, 1, attributeSet, false);
					 
					 textPaneChat.replaceSelection((msg) + "\n");
				}
				
				//send_Message(msg);
				txtWrite.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtWrite.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다	
			}

		}
		/*
		 * public void AppendMyText(String msg) { //본인이 보내는 메세지면 오른쪽에 출력 msg =
		 * msg.trim(); int len = textPaneChat.getStyledDocument().getLength();
		 * textPaneChat.setCaretPosition(len);
		 * 
		 * StyledDocument doc = textPaneChat.getStyledDocument(); SimpleAttributeSet
		 * attributeSet = new SimpleAttributeSet();
		 * StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_RIGHT);
		 * StyleConstants.setForeground(attributeSet, Color.black);
		 * StyleConstants.setBackground(attributeSet, Color.yellow);
		 * doc.setParagraphAttributes(len, 1, attributeSet, false);
		 * 
		 * textPaneChat.replaceSelection(msg + "\n");
		 * 
		 * }
		 */

	}
	
	public JTextPane getTextPaneChat() {
		return this.textPaneChat;
	}
	
	public void setTextPaneChat(JTextPane textPaneChat){
		this.textPaneChat = textPaneChat;
		
	}

}
