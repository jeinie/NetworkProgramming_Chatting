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
	
	public FriendPanel friendPanel; // ģ�����
	public ChatPanel chatPanel; //ä�ø��
	public ButtonPanel buttonPanel;
	public ChatListPanel chatlistPanel;
	public JLabel friendLabel; // 'ģ��' �ؽ�Ʈ
	public ProfilePanel profilePanel;
	
	public MainFrame f;
	CommandController controller = CommandController.getController();
	
	public StartPanel(MainFrame f, String user_id /*,String myStateMessage*/) {

		this.f = f;

		friendPanel = new FriendPanel(f, user_id); // ģ�� ���
		friendPanel.setBounds(80, 0, 315, 600);
		friendPanel.setBackground(Color.white);

		chatlistPanel = new ChatListPanel(f); //ä�� ���
		chatlistPanel.setBounds(80, 0, 315, 600);
		buttonPanel = new ButtonPanel(f);
		
		profilePanel = new ProfilePanel(user_id/*,String myStateMessage*/);
		profilePanel.setBounds(80, 0, 315, 600);
		
		/*chatlistPanel = new ChatListPanel(f);
		chatlistPanel.setBounds(0, 50, 600, 850);*/
		//controller.saveStartPanel(user_id,this);

		f.add(friendPanel);
		f.add(buttonPanel);

		//�޴�1 Ŭ��
		buttonPanel.getFriendBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.remove(chatlistPanel);
				f.remove(profilePanel);
				friendPanel.dataSetting();
				f.add(friendPanel);
				f.revalidate();
				f.repaint();

			}
		});
		
		//�޴�2 Ŭ��
		buttonPanel.getChatBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.remove(friendPanel);
				f.remove(profilePanel);
				chatlistPanel.setchatList();
				f.add(chatlistPanel);
				f.revalidate();
				f.repaint();
			}
		});
		
		//�޴�3 Ŭ��
		 buttonPanel.getSetBtn().addActionListener(new ActionListener() {
		 	@Override
		 	public void actionPerformed(ActionEvent e) {
		 		f.remove(friendPanel);
		 		f.remove(chatlistPanel);
		 		f.add(profilePanel);
		 		f.revalidate();
		 		f.repaint();
		 	}
		 });

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