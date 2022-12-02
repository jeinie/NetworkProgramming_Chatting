package client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class AddRoomFrame extends JFrame{
	
	private ChatListPanel chatPanel;
	
	public AddRoomFrame(String userId) {
	
		setTitle("상상톡");
		setSize(400, 600);
		setResizable(false);

		setLayout(null);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 가운데

		Container c = getContentPane();
		
		c.add(new AddFriendPanel(this,userId));
		
		setVisible(true);
	}

}
