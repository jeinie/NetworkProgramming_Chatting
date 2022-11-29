package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextPane;

public class ChatFrame extends JFrame{
	
	private JTextPane textArea;
	private ChatPanel chatPanel;
	
	public ChatFrame(String roomName) {
		setTitle("상상톡");
		setSize(400,600);
		setResizable(false);
		
		/*setUndecorated(true);*/
		//setBackground(new Color(155, 187, 212));

		setLayout(null);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈

		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 가운데

		Container c = getContentPane();
		
		this.chatPanel = new ChatPanel(this,roomName);
		c.add(chatPanel);
		
		setVisible(true);
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

	public void setChatPanel(ChatPanel chatPanel) {
		this.chatPanel = chatPanel;
	}
	
	
	
	
}
