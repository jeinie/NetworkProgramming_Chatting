package client;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StartPanel extends JPanel {
	
	public FriendPanel friendPanel; // 模备格废
	public ChatPanel chatPanel;
	public ButtonPanel buttonPanel;
	public ChatListPanel chatlistPanel;
	public JLabel friendLabel; // '模备' 咆胶飘
	// public SettingPanel settingPanel;
	
	public MainFrame f;
	CommandController controller = CommandController.getController();
	
	public StartPanel(MainFrame f, String user_id /*,String myStateMessage*/) {

		this.f = f;
		
		// '模备' 咆胶飘
		// friendLabel = new JLabel("模备");
		// friendLabel.setFont(new Font("讣篮 绊雕", Font.BOLD, 18));
		// friendLabel.setBounds(0, 20, 315, 48);
		// friendLabel.setBackground(Color.white);
		
		// JPanel friendsPanel = new JPanel();
		// friendsPanel.setBounds(80, 0, 315, 48);
		// friendsPanel.setBackground(Color.white);
		// friendsPanel.add(friendLabel);

		friendPanel = new FriendPanel(f, user_id); // 模备 格废
		friendPanel.setBounds(80, 0, 315, 600);
		friendPanel.setBackground(Color.white);

		chatlistPanel = new ChatListPanel(f);
		chatlistPanel.setBounds(80, 0, 315, 520);
		buttonPanel = new ButtonPanel(f);
		
		// friendPanel.add(friendLabel);
		// settingPanel = new SettingPanel(user_id/*,String myStateMessage*/);
		// settingPanel.setBounds(0, 50, 600, 850);
		
		/*chatlistPanel = new ChatListPanel(f);
		chatlistPanel.setBounds(0, 50, 600, 850);*/
		//controller.saveStartPanel(user_id,this);

		f.add(friendPanel);
		f.add(buttonPanel);
		// buttonPanel.setBackground(Color.blue);

		buttonPanel.getChatBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.remove(friendPanel);
				// f.remove(settingPanel);
				chatlistPanel.setchatList();
				f.add(chatlistPanel);
				f.revalidate();
				f.repaint();
			}
		});

		buttonPanel.getFriendBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.remove(chatlistPanel);
				// f.remove(settingPanel);
				friendPanel.dataSetting();
				f.add(friendPanel);
				f.revalidate();
				f.repaint();

			}
		});

		// buttonPanel.getSetBtn().addActionListener(new ActionListener() {
		// 	@Override
		// 	public void actionPerformed(ActionEvent e) {
		// 		f.remove(friendPanel);
		// 		f.remove(chatlistPanel);
		// 		f.add(settingPanel);
		// 		f.revalidate();
		// 		f.repaint();
		// 	}
		// });

	}
	
	public ChatListPanel getChatlistPanel() {
		return chatlistPanel;
	}

	public void setChatlistPanel(ChatListPanel chatlistPanel) {
		this.chatlistPanel = chatlistPanel;
	}

	public FriendPanel getFriendPanel() {
		return friendPanel;
	}

	public void setFriendPanel(FriendPanel friendPanel) {
		this.friendPanel = friendPanel;
	}

	

}