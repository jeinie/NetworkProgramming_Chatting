package client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;


import javax.swing.JFrame;
import javax.swing.JTextPane;

public class ChatFrame extends JFrame{
	
	private JTextPane textArea;
	private ChatPanel chatPanel;
	
	public ChatFrame(String roomTitle) {
		setTitle(roomTitle);
		setSize(400,600);
		setResizable(false);

		setLayout(null);
		
		Dimension frameSize = this.getSize(); // ������ ������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������

		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // ȭ�� ���

		Container c = getContentPane();
		
		this.chatPanel = new ChatPanel(this,roomTitle);
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
