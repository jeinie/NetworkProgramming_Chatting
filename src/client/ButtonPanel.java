package client;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel{
	
	//메뉴 1 - 친구목록 창
	ImageIcon menu1 = new ImageIcon("src/img/menu1.png");
	JButton friendsBtn;

	//메뉴 2 - 채팅목록 창
	ImageIcon menu2 = new ImageIcon("src/img/menu2.png");
	JButton chatBtn;

	private MainFrame f;

	//private JButton setBtn = new JButton("Set");
	
	public ButtonPanel(MainFrame f) {
		this.f = f;
		
		setLayout(null);
		setBounds(0, 0, 80, 200);
		// setBackground(Color.blue);
		
		
		friendsBtn = new JButton(menu1);
		chatBtn = new JButton(menu2);
		
		friendsBtn.setBounds(12, 35, 60, 50);
		friendsBtn.setToolTipText("친구");
		friendsBtn.setBorderPainted(false);
		
		chatBtn.setBounds(12, 100, 60, 50);
		chatBtn.setToolTipText("채팅");
		chatBtn.setBorderPainted(false);

		f.add(friendsBtn);
		f.add(chatBtn);
		// f.add(setBtn);
	}
	
	public JButton getFriendBtn() {
		return friendsBtn;
	}
	
	public JButton getChatBtn() {
		return chatBtn;
	}
	
	// public JButton getSetBtn() {
	// 	return setBtn;
	// }
}
