package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import server.User;

public class FriendPanel extends JPanel {

	private MainFrame f;
	int i;

	public ArrayList<UserInfo> userList;
	public ArrayList<UserInfo> friendList;
	private Vector<String> friendVector = new Vector<String>();
	public List<JLabel> friendName = new ArrayList<JLabel>();
	public JLabel Myprofile;
	public List<JLabel> friendImg = new ArrayList<JLabel>();
	public JLabel MyImg;
	public TextField searchFriend;
	public JScrollPane friendScroll;
	public JButton createRoomBtn;
	ImageIcon addRoom = new ImageIcon("src/img/addRoom.png");
	public JButton testBtn;
	private JLabel line;
	private LineBorder lb = new LineBorder(Color.LIGHT_GRAY, 1, true); //�׵θ�
	
	public String myStateMessage;
	
	public List<JLabel> stateLabel = new ArrayList<JLabel>();
	public JLabel myStateLabel;

	final String defaultImg = "src/img/basic.png";
	ImageIcon myStateImg = new ImageIcon("src/img/basic.png");
	JButton MyImgBtn;
	private String myStateImage = "src/img/basic.png";
	public CommandController controller = CommandController.getController();
	
	String user_id;

	public FriendPanel(MainFrame f, String user_id) {

		this.user_id = user_id;
		getAutoscrolls();
		this.f = f;

		setLayout(null);
		setBackground(new Color(168, 218, 255));
		
		//���� ���� �޾ƿͼ� �󺧻���,���̱�
		dataSetting();
		
		// 'ģ��' �ؽ�Ʈ ���̱�
		JLabel friendName = new JLabel("ģ��");
		friendName.setFont(new Font("���� ���", Font.BOLD, 18));
		friendName.setBounds(23, 23, 76, 34);
		add(friendName);
		
		line = new JLabel();
		line.setBounds(10, 135, 280, 1);
		line.setOpaque(true);
		line.setBackground(new Color(211, 211, 211));
		add(line);

		createRoomBtn = new JButton(addRoom);
		createRoomBtn.setBounds(240, 17, 40, 40);
		createRoomBtn.setBackground(new Color(255,225,231));
		createRoomBtn.setBorderPainted(false);
		add(createRoomBtn);
		
		createRoomBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddRoomFrame rf = new AddRoomFrame(user_id);
				
			}
		});
	}
	
	//�� ������ �����ؼ� ȭ�鰻��
	public void update(String userName) {
		UserInfo newUser = new UserInfo(userName);
		friendList.add(newUser);
		setFriendList(friendList);
		repaint();
		System.out.println("���ο� ����� ����: "+newUser.getName());
		System.out.println("friendPanel-> update ģ�� ��"+friendList.size());
		
	}
	
	//������ ���¸� �����ؼ� ȭ�鰻��
	public void updateState(String userName,String stateImg,String stateMsg) {
		for(int i=0;i<userList.size();i++) {
			UserInfo user = userList.get(i);
			if(user.getName().equals(userName)) {
				userList.remove(user);
				UserInfo reUser = new UserInfo(userName);
				reUser.setStateImg(stateImg);
				reUser.setStateMsg(stateMsg);
				userList.add(reUser);
				System.out.println("friendPanel->updateState userName="+reUser.getName());
				System.out.println("updateState result="+reUser.getStateImg(userName)+","+reUser.getStateMsg());
			}
		}
		
		removeAll();
		seperate();
		setMyField();
		setFriendList(friendList);
		repaint();
		
		JLabel friendName = new JLabel("ģ��");
		friendName.setFont(new Font("���� ���", Font.BOLD, 18));
		friendName.setBounds(23, 23, 76, 34);
		add(friendName);
		
		line = new JLabel();
		line.setBounds(10, 135, 280, 1);
		line.setOpaque(true);
		line.setBackground(new Color(211, 211, 211));
		add(line);
		
		createRoomBtn = new JButton(addRoom);
		createRoomBtn.setBounds(240, 17, 40, 40);
		createRoomBtn.setBackground(new Color(255,225,231));
		createRoomBtn.setBorderPainted(false);
		add(createRoomBtn);
		
		createRoomBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddRoomFrame rf = new AddRoomFrame(user_id);
				
			}
		});
		
	}
	
	public void dataSetting() {
		controller.send_Message(User.CODE_600);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		seperate();
		setMyField();
		setFriendList(friendList);
		repaint();
	}

	public void seperate() { //��������� ���ΰ� ģ���� ����
		friendList = new ArrayList<UserInfo>();// ģ����� �ʱ�ȭ

		userList = controller.getOnlineUserList();
		for (int i = 0; i < userList.size(); i++) {
			UserInfo user = userList.get(i);
			if (user.getName().equals(user_id)) { // ����
				myStateImage = user.getStateImg(user_id);
				myStateMessage = user.getStateMsg();
			} else { //������ �ƴ�=>ģ��
				friendList.add(user);
			}
		}
	}
	public void setMyField() {
		
		//����� ������ ����
		//MyImg = new JLabel(myStateImg);
		MyImg = new JLabel(new ImageIcon(myStateImage));
		MyImg.setBounds(10, 75, 50, 50);
		MyImg.setOpaque(true);
		MyImg.setBorder(lb);
		MyImg.setBackground(Color.white);
		add(MyImg);
		
		//����� �̸�
		Myprofile = new JLabel(user_id);
		Myprofile.setBounds(70, 75, 130, 50);
		Myprofile.setFont(new Font("���� ���", Font.PLAIN, 18));
		Myprofile.setOpaque(true);
		Myprofile.setBackground(Color.white);
		add(Myprofile);
			
		//����� ���� �޼���
		myStateLabel = new JLabel(myStateMessage);
		myStateLabel.setBounds(200, 75, 90, 50);
		myStateLabel.setFont(new Font("���� ���", Font.PLAIN, 11));
		myStateLabel.setBackground(Color.white);
		myStateLabel.setOpaque(true);
		add(myStateLabel);
	}
	
	public void setFriendList(ArrayList<UserInfo> friendList) {
		friendName.clear();
		friendImg.clear();
		stateLabel.clear();
	
		for (i = 0; i < friendList.size(); i++) {
			UserInfo user = friendList.get(i);
			System.out.println("friendList="+user.getName()+"/"+user.getStateImg(user_id)+"/"+user.getStateMsg());
			

			friendImg.add(new JLabel(new ImageIcon(user.getStateImg(user_id))));
			friendName.add(new JLabel(user.getName()));
			stateLabel.add(new JLabel(user.getStateMsg()));
			
			//ģ�� ������ ����
			friendImg.get(i).setBounds(10, 145 + (i * 55), 50, 50);
			friendImg.get(i).setBackground(Color.white);
			friendImg.get(i).setOpaque(true); 
			friendImg.get(i).setBorder(lb);
			
			//ģ�� �̸�
			friendName.get(i).setBounds(70, 145 + (i * 55), 130, 50);
			friendName.get(i).setFont(new Font("���� ���", Font.PLAIN, 18));
			friendName.get(i).setOpaque(true);
			friendName.get(i).setBackground(Color.white);
			
			//ģ�� ���
			stateLabel.get(i).setBounds(200, 145 + (i * 55), 90, 50);
			stateLabel.get(i).setFont(new Font("���� ���", Font.PLAIN, 11));
			stateLabel.get(i).setBackground(Color.white);
			stateLabel.get(i).setOpaque(true);

			add(friendName.get(i));
			add(friendImg.get(i));
			add(stateLabel.get(i));

			friendName.get(i).addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) { //ģ�� �̸� ������ �� ģ���� 1:1 ä�ù� ����
					// TODO Auto-generated method stub
					if(e.getClickCount()==2) {
						String friendName = ((JLabel) e.getSource()).getText();

						// �������� ä�ù� ���� ��û
						controller.send_Message(User.CODE_100 + "//" + user_id + "//" + friendName);

					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});
		}
	}

	public void createChatPanel(String friendName) {
		ChatFrame a = new ChatFrame(friendName);
		System.out.println("friendPanel -> " + friendName);
	}

}