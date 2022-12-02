package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

//import info.UserInfo;

public class Server extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JTextArea textArea; //���� �޼���, Ŭ���̾�Ʈ �޼��� ��� protected??
	private JTextField txtPortNumber;
	private final int portNum=30000; //��Ʈ��ȣ
	private JButton btnServerStart;
	private Server server;
	private ServerSocket socket; // ��������
	private Socket client_socket; // accept() ���� ������ client ���� //�������
	//private Vector UserVec = new Vector(); // ����� ����ڸ� ������ ����
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	
	private ClientManager clientManager;
	private RoomManager roomManager;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Server() {
		server = this;
		init(); //ȭ�鱸��
		clientManager = new ClientManager(server);
		roomManager = new RoomManager(server);
	}
	
	private void init() {
		 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 295, 300);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(13, 310, 80, 25);
		contentPane.add(lblNewLabel);
		
		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(100, 310, 190, 25);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);
		txtPortNumber.setEnabled(false);  // ���̻� ��Ʈ��ȣ ���� ���ϰ� ���´�
		
		btnServerStart = new JButton("Server Start");
		Action action = new Action();
		btnServerStart.addActionListener(action);
		btnServerStart.setBounds(12, 343, 280, 35);
		contentPane.add(btnServerStart);
	}

	class Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnServerStart) start();
		}
	}
	private void start() {
		try {
			socket = new ServerSocket(portNum);
			btnServerStart.setText("Chat Server Running..");
			btnServerStart.setEnabled(false); // ������ ���̻� �����Ű�� �� �ϰ� ���´�
			
			//socket�� ������
			if(socket != null) Connect(); 
			
		} catch (IOException e) {
			e.printStackTrace();
			textArea.append("������ �̹� ������Դϴ�...\n");
		}
	}
	
	private void Connect() {
		Thread th = new Thread(new Runnable() { // ����� ������ ���� ������
			@Override
			public void run() {
				while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
					try {
						textArea.append("Waiting new clients ...");
						client_socket = socket.accept(); // accept�� �Ͼ�� �������� ���� �����
						textArea.append("����� ����!!\n");
						
						//����� ������ �Ŵ������� �˸�
						//����� ��Ͽ� �߰�, ������ ������ ���� 
						clientManager.insertUser(client_socket);
						//usersUpdateFriendList(); 
						 
						/*ClientManager client = new ClientManager(socket, server);
						serverClientList.add(client);// �ش� ���Ϳ� ����� ��ü�� �߰�
						client.start(); // ���� ��ü�� ������ ����*/		
						} catch (IOException e) {
						textArea.append("accept error\n");
					} 
				}
			}
		});
		th.start();
	}
	
	public void exitUser(User user) {
		//backupUser,backup
		clientManager.exitUser(user);
		roomManager.exitUser(user);
		
	}
	
	/*�޽��� ó��*/
	public void broadcast(User user, String str, String roomTitle) {
		//RoomManager���� ��ε�ĳ���� ��� -> Room�� �ڽſ��� ���� ����ڵ鿡�� ��ε�ĳ����
		roomManager.broadcast(user, str, roomTitle);
	}
	
	//�̸�Ƽ�� �޼��� ó��
	public void broadcastEmoticon(User user, ImageIcon img, String roomTitle) {
		//RoomManager���� ��ε�ĳ���� ��� -> Room�� �ڽſ��� ���� ����ڵ鿡�� ��ε�ĳ����
		roomManager.broadcastImg(user, img, roomTitle);
	}
	
	//�̹��� �޼��� ó��
	public void broadcastImg(User user, ImageIcon img, String roomTitle) {
		//RoomManager���� ��ε�ĳ���� ��� -> Room�� �ڽſ��� ���� ����ڵ鿡�� ��ε�ĳ����
		roomManager.broadcastImg(user, img, roomTitle);
	}
	
	//�� ������ �߰��� ��� ������ �������� ����Ʈ�� ������Ʈ
	public void usersUpdateFriendList(String newUser) {
		clientManager.usersUpdateFriendList(newUser);
	}
	
		
	//������ �����̹����� ���¸޽��� ������ �ִٸ� ��� �������� ȭ���� ����
	public void userInfoUpdate(String userName,String stateImg,String stateMsg) {
		clientManager.userInfoUpdate(userName,stateImg,stateMsg);
	}

	public void createSingleChat(User user,String myName,String friendName) {
		ArrayList<User> users = new ArrayList<User>();
		User me = clientManager.searchByUserNameInOnline(myName);
		User friend = clientManager.searchByUserNameInOnline(friendName);
	
		//roomTitle
		String roomTitle = me.getName()+ ","+friend.getName(); //��Ÿ��Ʋ�� �ð� �߰�?
		if(friend==null) {
			//�̰� �ش� ģ���� ������ ���� ���¶�� �ϸ� �ɵ�
		}
		else { //���� ������!
			users.add(me);
			users.add(friend);
			ChattingRoom newRoom = roomManager.createRoom(users);
			newRoom.setRoomTitle(roomTitle);
			user.sendMsg(User.CODE_300+"//"+roomTitle);
			friend.sendMsg(User.CODE_300+"//"+roomTitle);
			
		}
		
	}
	
	public void createMultiChat(ArrayList<String> mutiChatUserList) {
		ArrayList<User> users = new ArrayList<User>();
		String roomTitle="";
		for(int i=0;i<mutiChatUserList.size();i++) {
			User user = clientManager.searchByUserNameInOnline(mutiChatUserList.get(i));
			users.add(user);
			
			//roomTitle�� �ð��߰�?
			if(i == mutiChatUserList.size()-1)//������ �����̸�
				roomTitle += user.getName();
			else
				roomTitle += user.getName()+",";
		}
		ChattingRoom newRoom = roomManager.createRoom(users);
		newRoom.setRoomTitle(roomTitle);
		for(int i=0;i<users.size();i++) { //�����ϴ� ��� �����鿡�� ä�ù��� �����
			users.get(i).sendMsg(User.CODE_300+"//"+roomTitle);
		}
		
	}
	
	public ArrayList<User> getFriendList(){
		return clientManager.getUserList();
	}

	public User isExistingUser(String userName) {
		User existingUser = clientManager.searchByUserNameInExist(userName);
		return existingUser;
	}

	public ArrayList<ChattingRoom> getJoinRooms(String userName) {//�̰� �ʴ��ΰǰ� ?
		
		return roomManager.getJoinRooms(userName);
	}
	
}
