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
	static JTextArea textArea; //¼­¹ö ¸Ş¼¼Áö, Å¬¶óÀÌ¾ğÆ® ¸Ş¼¼Áö Ãâ·Â protected??
	private JTextField txtPortNumber;
	private final int portNum=30000; //Æ÷Æ®¹øÈ£
	private JButton btnServerStart;
	private Server server;
	private ServerSocket socket; // ¼­¹ö¼ÒÄÏ
	private Socket client_socket; // accept() ¿¡¼­ »ı¼ºµÈ client ¼ÒÄÏ //¿¬°á¼ÒÄÏ
	//private Vector UserVec = new Vector(); // ¿¬°áµÈ »ç¿ëÀÚ¸¦ ÀúÀåÇÒ º¤ÅÍ
	private static final int BUF_LEN = 128; // Windows Ã³·³ BUF_LEN À» Á¤ÀÇ
	
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
		init(); //È­¸é±¸¼º
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
		txtPortNumber.setEnabled(false);  // ´õÀÌ»ó Æ÷Æ®¹øÈ£ ¼öÁ¤ ¸øÇÏ°Ô ¸·´Â´Ù
		
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
			btnServerStart.setEnabled(false); // ¼­¹ö¸¦ ´õÀÌ»ó ½ÇÇà½ÃÅ°Áö ¸ø ÇÏ°Ô ¸·´Â´Ù
			
			//socketÀÌ ¿­¸®¸é
			if(socket != null) Connect(); 
			
		} catch (IOException e) {
			e.printStackTrace();
			textArea.append("¼ÒÄÏÀÌ ÀÌ¹Ì »ç¿ëÁßÀÔ´Ï´Ù...\n");
		}
	}
	
	private void Connect() {
		Thread th = new Thread(new Runnable() { // »ç¿ëÀÚ Á¢¼ÓÀ» ¹ŞÀ» ½º·¹µå
			@Override
			public void run() {
				while (true) { // »ç¿ëÀÚ Á¢¼ÓÀ» °è¼ÓÇØ¼­ ¹Ş±â À§ÇØ while¹®
					try {
						textArea.append("Waiting new clients ...");
						client_socket = socket.accept(); // accept°¡ ÀÏ¾î³ª±â Àü±îÁö´Â ¹«ÇÑ ´ë±âÁß
						textArea.append("»ç¿ëÀÚ Á¢¼Ó!!\n");
						
						//»ç¿ëÀÚ Á¢¼ÓÀ» ¸Å´ÏÀú¿¡°Ô ¾Ë¸²
						//»ç¿ëÀÚ ¸ñ·Ï¿¡ Ãß°¡, ½º·¹µå ½ÇÇàÀ» À§ÀÓ 
						clientManager.insertUser(client_socket);
						//usersUpdateFriendList(); 
						 
						/*ClientManager client = new ClientManager(socket, server);
						serverClientList.add(client);// ÇØ´ç º¤ÅÍ¿¡ »ç¿ëÀÚ °´Ã¼¸¦ Ãß°¡
						client.start(); // ¸¸µç °´Ã¼ÀÇ ½º·¹µå ½ÇÇà*/		
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
	
	/*¸Ş½ÃÁö Ã³¸®*/
	public void broadcast(User user, String str, String roomTitle) {
		//RoomManager¿¡°Ô ºê·ÎµåÄ³½ºÆÃ ¸í·É -> RoomÀÌ ÀÚ½Å¿¡°Ô ¼ÓÇÑ »ç¿ëÀÚµé¿¡°Ô ºê·ÎµåÄ³½ºÆÃ
		roomManager.broadcast(user, str, roomTitle);
	}
	
	//ÀÌ¹ÌÁö ¸Ş¼¼Áö Ã³¸®
	public void broadcastImg(User user, ImageIcon img, String roomTitle) {
		//RoomManager¿¡°Ô ºê·ÎµåÄ³½ºÆÃ ¸í·É -> RoomÀÌ ÀÚ½Å¿¡°Ô ¼ÓÇÑ »ç¿ëÀÚµé¿¡°Ô ºê·ÎµåÄ³½ºÆÃ
		roomManager.broadcastImg(user, img, roomTitle);
	}
	
	//»õ À¯Àú°¡ Ãß°¡µÉ °æ¿ì ±âÁ¸ÀÇ À¯ÀúµéÀÇ ¸®½ºÆ®¸¦ ¾÷µ¥ÀÌÆ®
	public void usersUpdateFriendList(String newUser) {
		clientManager.usersUpdateFriendList(newUser);
	}
	
		
	//À¯ÀúÀÇ »óÅÂÀÌ¹ÌÁö³ª »óÅÂ¸Ş½ÃÁö º¯°æÀÌ ÀÖ´Ù¸é ¸ğµç À¯ÀúµéÀÇ È­¸éÀ» °»½Å
	public void userInfoUpdate(String userName,String stateImg,String stateMsg) {
		clientManager.userInfoUpdate(userName,stateImg,stateMsg);
	}

	public void createSingleChat(User user,String myName,String friendName) {
		ArrayList<User> users = new ArrayList<User>();
		User me = clientManager.searchByUserNameInOnline(myName);
		User friend = clientManager.searchByUserNameInOnline(friendName);
	
		//roomTitle
		String roomTitle = me.getName()+ ","+friend.getName(); //·ëÅ¸ÀÌÆ²¿¡ ½Ã°£ Ãß°¡?
		if(friend==null) {
			//ÀÌ°Ç ÇØ´ç Ä£±¸ÀÇ Á¢¼ÓÀÌ ²÷±ä »óÅÂ¶ó°í ÇÏ¸é µÉµí
		}
		else { //¿©±ä Á¢¼ÓÁß!
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
			
			//roomTitle¿¡ ½Ã°£Ãß°¡?
			if(i == mutiChatUserList.size()-1)//¸¶Áö¸· À¯ÀúÀÌ¸§
				roomTitle += user.getName();
			else
				roomTitle += user.getName()+",";
		}
		ChattingRoom newRoom = roomManager.createRoom(users);
		newRoom.setRoomTitle(roomTitle);
<<<<<<< HEAD
		for(int i=0;i<users.size();i++) { //Âü¿©ÇÏ´Â ¸ğµç À¯Àúµé¿¡°Ô Ã¤ÆÃ¹æÀ» ¶ç¿öÁÜ
=======
		for(int i=0;i<users.size();i++) { //ì°¸ì—¬í•˜ëŠ” ëª¨ë“  ìœ ì €ë“¤ì—ê²Œ ì±„íŒ…ë°©ì„ ë„ì›Œì¤Œ
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
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

	public ArrayList<ChattingRoom> getJoinRooms(String userName) {//ÀÌ°Ç ÃÊ´ëÀÎ°Ç°¡ ?
		
		return roomManager.getJoinRooms(userName);
	}
	
}
