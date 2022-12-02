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
	static JTextArea textArea; //서버 메세지, 클라이언트 메세지 출력 protected??
	private JTextField txtPortNumber;
	private final int portNum=30000; //포트번호
	private JButton btnServerStart;
	private Server server;
	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓 //연결소켓
	//private Vector UserVec = new Vector(); // 연결된 사용자를 저장할 벡터
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	
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
		init(); //화면구성
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
		txtPortNumber.setEnabled(false);  // 더이상 포트번호 수정 못하게 막는다
		
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
			btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
			
			//socket이 열리면
			if(socket != null) Connect(); 
			
		} catch (IOException e) {
			e.printStackTrace();
			textArea.append("소켓이 이미 사용중입니다...\n");
		}
	}
	
	private void Connect() {
		Thread th = new Thread(new Runnable() { // 사용자 접속을 받을 스레드
			@Override
			public void run() {
				while (true) { // 사용자 접속을 계속해서 받기 위해 while문
					try {
						textArea.append("Waiting new clients ...");
						client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
						textArea.append("사용자 접속!!\n");
						
						//사용자 접속을 매니저에게 알림
						//사용자 목록에 추가, 스레드 실행을 위임 
						clientManager.insertUser(client_socket);
						//usersUpdateFriendList(); 
						 
						/*ClientManager client = new ClientManager(socket, server);
						serverClientList.add(client);// 해당 벡터에 사용자 객체를 추가
						client.start(); // 만든 객체의 스레드 실행*/		
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
	
	/*메시지 처리*/
	public void broadcast(User user, String str, String roomTitle) {
		//RoomManager에게 브로드캐스팅 명령 -> Room이 자신에게 속한 사용자들에게 브로드캐스팅
		roomManager.broadcast(user, str, roomTitle);
	}
	
	//이모티콘 메세지 처리
	public void broadcastEmoticon(User user, ImageIcon img, String roomTitle) {
		//RoomManager에게 브로드캐스팅 명령 -> Room이 자신에게 속한 사용자들에게 브로드캐스팅
		roomManager.broadcastImg(user, img, roomTitle);
	}
	
	//이미지 메세지 처리
	public void broadcastImg(User user, ImageIcon img, String roomTitle) {
		//RoomManager에게 브로드캐스팅 명령 -> Room이 자신에게 속한 사용자들에게 브로드캐스팅
		roomManager.broadcastImg(user, img, roomTitle);
	}
	
	//새 유저가 추가될 경우 기존의 유저들의 리스트를 업데이트
	public void usersUpdateFriendList(String newUser) {
		clientManager.usersUpdateFriendList(newUser);
	}
	
		
	//유저의 상태이미지나 상태메시지 변경이 있다면 모든 유저들의 화면을 갱신
	public void userInfoUpdate(String userName,String stateImg,String stateMsg) {
		clientManager.userInfoUpdate(userName,stateImg,stateMsg);
	}

	public void createSingleChat(User user,String myName,String friendName) {
		ArrayList<User> users = new ArrayList<User>();
		User me = clientManager.searchByUserNameInOnline(myName);
		User friend = clientManager.searchByUserNameInOnline(friendName);
	
		//roomTitle
		String roomTitle = me.getName()+ ","+friend.getName(); //룸타이틀에 시간 추가?
		if(friend==null) {
			//이건 해당 친구의 접속이 끊긴 상태라고 하면 될듯
		}
		else { //여긴 접속중!
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
			
			//roomTitle에 시간추가?
			if(i == mutiChatUserList.size()-1)//마지막 유저이름
				roomTitle += user.getName();
			else
				roomTitle += user.getName()+",";
		}
		ChattingRoom newRoom = roomManager.createRoom(users);
		newRoom.setRoomTitle(roomTitle);
		for(int i=0;i<users.size();i++) { //참여하는 모든 유저들에게 채팅방을 띄워줌
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

	public ArrayList<ChattingRoom> getJoinRooms(String userName) {//이건 초대인건가 ?
		
		return roomManager.getJoinRooms(userName);
	}
	
}
