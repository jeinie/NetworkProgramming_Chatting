package client;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
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
	
	private String name = LoginPanel.userID; //현재 로그인한 유저
	
	private ChatFrame cf;
	private JLabel profile;
	private JTextPane textPaneChat;
	private TextField txtWrite;
	private JButton sendBtn, sendEmojiBtn, sendImageBtn, blank;
	private JScrollPane chatScroll;
	public JButton createRoomBtn;
	ImageIcon emoticon = new ImageIcon("src/img/emoticon.png");
	ImageIcon file = new ImageIcon("src/img/file.png");
	
	private Frame frame;
	private FileDialog fd;

	private String roomTitle;
	private CommandController controller = CommandController.getController();
	
	public ChatPanel(ChatFrame cf,String roomTitle) {
		this.roomTitle = roomTitle;
		this.cf = cf;
		System.out.println("roomTitle = "+roomTitle);
		setLayout(null);
		setSize(400,600);
		setBackground(new Color(155, 187, 212));
		
		profile = new JLabel(" "+roomTitle);
		profile.setBounds(0, 0, 400, 40);
		profile.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		profile.setOpaque(true);
		profile.setForeground(Color.white);
		profile.setBackground(new Color(0,0,0,122));
		cf.add(profile);
		
		//채팅 보이는 부분
		textPaneChat = new JTextPane();
		textPaneChat.setEditable(false);
		textPaneChat.setBounds(0, 40, 355, 400);
		textPaneChat.setBackground(new Color(155, 187, 212));
		textPaneChat.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
		//채팅 스크롤 
		chatScroll = new JScrollPane(textPaneChat);
		chatScroll.setBounds(0, 40, 395, 400);
		cf.add(chatScroll);
	
		//메세지 입력 부분
		txtWrite = new TextField();
		txtWrite.setBounds(0, 440, 400, 70);
		txtWrite.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
	    cf.add(txtWrite);

		sendBtn = new JButton("전송");
		sendBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		sendBtn.setBackground(new Color(247, 230, 0));
		sendBtn.setBounds(296, 510, 88, 50);
		cf.add(sendBtn);
		
		sendEmojiBtn = new JButton(emoticon);
		sendEmojiBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		sendEmojiBtn.setBackground(new Color(255,255,255));
		sendEmojiBtn.setBounds(0, 510, 50, 50);
		cf.add(sendEmojiBtn);
		
		sendImageBtn = new JButton(file);
		sendImageBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		sendImageBtn.setBackground(new Color(255,255,255));
		sendImageBtn.setBounds(50, 510, 50, 50);
		cf.add(sendImageBtn);
		
		blank = new JButton();
		blank.setBounds(100,510,196,50);
		blank.setBackground(Color.WHITE);
		cf.add(blank);
		
		
		start(); //액션이벤트 지정 메소드
	}
	
	public void start() { // 액션이벤트 지정 메소드
		Myaction action = new Myaction(this.textPaneChat);
		sendBtn.addActionListener(action); // 내부클래스로 액션 리스너를 상속받은 클래스로
		txtWrite.addActionListener(action);
		sendImageBtn.addActionListener(action);
		sendEmojiBtn.addActionListener(action);
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
<<<<<<< HEAD
				
=======

>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
				String[] array = msg.split("//");
				System.out.println("현재 접속자: " + name);
				
				//채팅을 친 채팅방 이름, 유저네임, 메시지를 서버로 전달
				controller.send_Message(msg);				
				txtWrite.setText("");  // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtWrite.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다	
		
			}
			else if(e.getSource() == sendEmojiBtn) { //이모티콘 버튼 누르면
				System.out.println("이모티콘 버튼 눌림");
			}
			else if(e.getSource() == sendImageBtn) { //사진 버튼 누르면
				System.out.println("이미지 첨부 버튼 눌림");
				  frame = new Frame("이미지첨부");
		            fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
		            //frame.setVisible(true);
		            fd.setDirectory(".\\");
		            fd.setVisible(true);
		            // System.out.println(fd.getDirectory() + fd.getFile());
		            if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0 ) {
		            	String msg = null;
		            	String img = fd.getDirectory() + fd.getFile();
		            	ImageIcon img2 = new ImageIcon(fd.getDirectory() + fd.getFile());
						msg = String.format("%s//%s//%s\n", User.CODE_420, roomTitle, img2); //일단 name 뺴버림 
						System.out.println("이미지 선택했어: " + msg);
		               //controller.send_Message(msg); 
		               controller.send_Image(msg);
		              // controller.append_Image(roomTitle, img2);
		            }
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
