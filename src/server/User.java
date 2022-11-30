package server;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class User extends Thread {
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private Socket client_socket; //user_socket	
	Server server;
	Socket socket;
	String name, stateImg, stateMsg; //�̸�, ����, ���
	JTextArea textArea = Server.textArea;
	
	//����ڰ� �����ϴ� ���
	ArrayList<String> userName = new ArrayList<String>(); //userName
	ArrayList<ChattingRoom> joinRoomList = new ArrayList<ChattingRoom>(); //�����ϰ� �ִ� ��?
	ArrayList<User> friendsList = new ArrayList<User>(); //ģ������Ʈ
	ArrayList<String> multiChatUserList; //����� ���� ����Ʈ
	
	public User(Socket socket, Server server) {
		name = "newUser";
		stateImg = "src/img/basic.png";
		stateMsg = "���� �޼���";
		client_socket = socket;
		this.server = server;
		network();
	}
	
	public void addFreind(User user) { //ģ���߰�
		friendsList.add(user);
	}
	
	public void EnterRoom(ChattingRoom room) {
		room.EnterRoom(this);//ä�ù� ����
		this.joinRoomList.add(room);//������ ���� ���� �߰�
	}
	
	public void network() {
		try {
			is = client_socket.getInputStream();
			dis = new DataInputStream(is);
			os = client_socket.getOutputStream();
			dos = new DataOutputStream(os);
		
			textArea.append(name + "���� �����߽��ϴ�.\n");
			textArea.setCaretPosition(textArea.getText().length());	
	
		} catch (Exception e) {
			e.printStackTrace();
			//textArea.append("��Ʈ�� ���� ����\n");
			textArea.setCaretPosition(textArea.getText().length());
		}
	}
	
	//�̸� ����..?
	public static final String SIGNAL_CREATE_SINGLECHAT = "SIGNAL_CREATE_SINGLECHAT";
	public static final String SIGNAL_CREATE_MULTICHAT = "SIGNAL_CREATE_MULTICHAT";
	public static final String SIGNAL_CREATE_ROOM_COMPLETE = "SIGNAL_CREATE_ROOM_COMPLETE";
	public static final String SIGNAL_NOMAL_MSG = "SIGNAL_NOMAL_MSG";
	public static final String SIGNAL_USER_ID = "SIGNAL_USER_ID";
	public static final String SIGNAL_ONLINE_USER_LIST = "SIGNAL_ONLINE_USER_LIST";
	//public static final String SIGNAL_UPDATE_FRIENDS_LIST = "SIGNAL_UPDATE_FRIENDS_LIST";
	public static final String SIGNAL_NEW_USER_CONNECT = "SIGNAL_NEW_USER_CONNECT";
	public static final String SIGNAL_EXIST_USER_CONNECT = "SIGNAL_EXIST_USER_CONNECT";
	public static final String SIGNAL_CHANGE_STATE = "SIGNAL_CHANGE_STATE_MSG";
	 
	/*�޽��� ó�� �κ�*/
	public void InMessage(String str) {// ����� �޼��� ó��
		String[] array = str.split("//");
		if(array[0].equals(SIGNAL_CREATE_SINGLECHAT)) { //1:1 ä�ù� ����� (ģ�� �Ѹ� ����)

			String myName = array[1];
			String friendName = array[2];
			String roomTitle = friendName; //�⺻ �� Ÿ��Ʋ= ģ���̸�

			textArea.append(myName+","+friendName+"�� ä�ù� ������\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			server.createSingleChat(this,myName, friendName);
		}
		else if(array[0].equals(SIGNAL_CREATE_MULTICHAT)) {//�����
			multiChatUserList = new ArrayList<String>();
			String msg="";
			for(int i=1;i<array.length;i++) {
				multiChatUserList.add(array[i]);
				if(i==array.length)
					msg+=array[i];
				else
					msg+=array[i]+",";
			}
			textArea.append(msg+" ä�ù� ������.\n");
			textArea.setCaretPosition(textArea.getText().length());
			server.createMultiChat(multiChatUserList);
		}
		else if(array[0].equals(SIGNAL_USER_ID)) {
			//���Ͽ��� ���� �������̵� �޴ºκ�
			textArea.append("�������̵� ���� : "+array[1]+"\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			//������ �������� �Ǻ� �� �ٸ��� �۵�
			if(server.isExistingUser(array[1])!=null) {
				User existingUser=server.isExistingUser(array[1]);
				ArrayList<ChattingRoom> existingRooms = server.getJoinRooms(array[1]);
				
				setName(array[1]);
				setStateImg(existingUser.getStateImg());
				setStateMsg(existingUser.getStateMsg());
				for(int i=0;i<existingRooms.size();i++) {
					sendMsg(SIGNAL_EXIST_USER_CONNECT+"//"+existingRooms.get(i).roomTitle+"//"+existingRooms.get(i).chat);
					System.out.println("User->���� ä�ù� ���,�������� "+i);
				}
				
				textArea.append("���� �����Դϴ�.\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
			else {
				setName(array[1]);
				//�� ������ �߰��� ��� ������ �������� ����Ʈ�� ������Ʈ
				server.usersUpdateFriendList(array[1]);
				textArea.append("���ο� ������Դϴ�.\n"); 
				textArea.setCaretPosition(textArea.getText().length());
			}
			
		}
		else if(array[0].equals(SIGNAL_NOMAL_MSG)){//�Ϲ� ä�� �޽���
			//textArea.append("����ڷκ��� ���� �޼��� : " + str+"\n");
			/* str => SIGNAL_NOMAL_MSG//ä�ù��̸�//[�����̸�] �޽���~~~ */
			String roomTitle = array[1];
			str = array[2];
			textArea.append(roomTitle+" : "+str + "\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			//�������� ��ε�ĳ����(���� ��ü�� �Բ� �Ѱ���)
			server.broadcast(this,str,roomTitle); 
		}
		else if(array[0].equals(SIGNAL_ONLINE_USER_LIST)) {
			/*friendsList = server.getFriendList();
			String msg = array[0];
			
			for(User user : friendsList) {
				msg += "//" + user.getNickname();
			}
			textArea.append("userList : "+msg+ "\n");
			textArea.setCaretPosition(textArea.getText().length());
			send_Message(msg);*/
			friendsList = server.getFriendList();
			String msg = array[0]+"//";
			
			for(int i=0;i<friendsList.size();i++) {
				User user = friendsList.get(i);
				if(i==friendsList.size()-1) {//������ �迭 
					msg+=user.getName()+"!!"+user.getStateImg()+"!!"+user.getStateMsg();
				}
				else {
					msg+=user.getName()+"!!"+user.getStateImg()+"!!"+user.getStateMsg()+"//";
				}
			}
			sendMsg(msg);
			textArea.append(msg+"\n");
			textArea.setCaretPosition(textArea.getText().length());
		}
		else if(array[0].equals(SIGNAL_CHANGE_STATE)) {
			//User.SIGNAL_CHANGE_STATE_MSG+"//"+user_id+"//"+stateImage+"//"+myStateMessage
			textArea.append(array[1]+"�� ���� ���� "+array[2]+" / "+array[3]+"\n");
			textArea.setCaretPosition(textArea.getText().length());
			this.stateImg = array[2];
			this.stateMsg = array[3];
			server.userInfoUpdate(array[1],array[2],array[3]);
		}
		else {
			System.out.println("�������� �ʴ� �޽��� ����");
		}
	}
	
	public void sendMsg(String str) {
		try {
			//dos.writeUTF(str);
			byte[] bb;		
			bb = str.getBytes();
			dos.write(bb); //.writeUTF(str);
		} 
		catch (IOException e) {
			textArea.append("�޽��� �۽� ���� �߻�\n");	
			textArea.setCaretPosition(textArea.getText().length());
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				//����ڿ��� �޴� �޼���(chatPanel���� ���� �޽���)
				//byte[] b = new byte[128];
				byte[] b = new byte[1024];
				dis.read(b);
				String msg = new String(b);
				msg = msg.trim();
				//String msg = dis.readUTF();
				InMessage(msg);
			} 
			catch (IOException e) 
			{
				try {
					dos.close();
					dis.close();
					client_socket.close();//�ڿ��ݳ�
					//������ ���� ����� �Ŵ������� �˸�
					//���� ����Ʈ���� ����,������ ����Ʈ�� ����
					server.exitUser(this);
					break;
				
				} catch (Exception e1) {
				
				}// catch�� ��
			}// �ٱ� catch����
		}
	}
	
	public ArrayList<ChattingRoom> getJoinRoomList() {
		return joinRoomList;
	}

	public void setJoinRoomList(ArrayList<ChattingRoom> joinRoomList) {
		this.joinRoomList = joinRoomList;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getNickname() {
		return name;
	}

	public void setNickname(String name) {
		this.name = name;
	}

	public String getStateImg() {
		return stateImg;
	}

	public void setStateImg(String stateImg) {
		this.stateImg = stateImg;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}
	
}
