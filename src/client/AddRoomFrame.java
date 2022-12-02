package client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class AddRoomFrame extends JFrame{
	
	private ChatListPanel chatPanel;
	
	public AddRoomFrame(String userId) {
	
		setTitle("�����");
		setSize(400, 600);
		setResizable(false);

		setLayout(null);
		
		Dimension frameSize = this.getSize(); // ������ ������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // ȭ�� ���

		Container c = getContentPane();
		
		c.add(new AddFriendPanel(this,userId));
		
		setVisible(true);
	}

}
