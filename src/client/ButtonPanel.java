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

	//메뉴 3 - 프로필 변경 창
	ImageIcon menu3 = new ImageIcon("src/img/setting.png");
	JButton setBtn;

	private MainFrame f;
	
	public ButtonPanel(MainFrame f) {
		this.f = f;
		
		setLayout(null);
		setBounds(0, 0, 80, 200);		
		
		friendsBtn = new JButton(menu1);
		chatBtn = new JButton(menu2);
		setBtn = new JButton(menu3);
		
		friendsBtn.setBounds(12, 35, 60, 50);
		friendsBtn.setToolTipText("친구");
		friendsBtn.setBorderPainted(false);
		
		chatBtn.setBounds(12, 100, 60, 50);
		chatBtn.setToolTipText("채팅");
		chatBtn.setBorderPainted(false);
		
		setBtn.setBounds(15, 490, 49, 50); //y:165
		setBtn.setToolTipText("프로필 변경");
		setBtn.setBorderPainted(false);

		f.add(friendsBtn);
		f.add(chatBtn);
		f.add(setBtn);
	}
	
	public JButton getFriendBtn() {
		return friendsBtn;
	}
	
	public JButton getChatBtn() {
		return chatBtn;
	}
	
	 public JButton getSetBtn() {
	 	return setBtn;
	 }
}
