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
		
		friendsBtn.setBounds(0, 0, 80, 80);
		friendsBtn.setToolTipText("친구");
		friendsBtn.setBorderPainted(false);
		// friendsBtn.setForeground(Color.BLACK);
		// friendsBtn.setBackground(Color.WHITE);
		// friendsBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
		chatBtn.setBounds(0, 100, 80, 80);
		chatBtn.setToolTipText("채팅");
		chatBtn.setBorderPainted(false);
		// chatBtn.setForeground(Color.BLACK);
		// chatBtn.setBackground(Color.WHITE);
		// chatBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
		// setBtn.setBounds(400, 0, 200, 50);
		// setBtn.setForeground(Color.BLACK);
		// setBtn.setBackground(Color.WHITE);
		// setBtn.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));


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
