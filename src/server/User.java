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
	String name, stateImg, stateMsg; //이름, 프사, 상메
	JTextArea textArea = Server.textArea;
	
	//사용자가 참여하는 방들
	ArrayList<String> userName = new ArrayList<String>(); //userName
	ArrayList<ChattingRoom> joinRoomList = new ArrayList<ChattingRoom>(); //참여하고 있는 방?
	ArrayList<User> friendsList = new ArrayList<User>(); //친구리스트
	ArrayList<String> multiChatUserList; //단톡방 유저 리스트
	
	public User(Socket socket, Server server) {
		name = "newUser";
		stateImg = "src/img/basic.png";
		stateMsg = "상태 메세지";
		client_socket = socket;
		this.server = server;
		network();
	}
	
	public void addFreind(User user) { //친구추가
		friendsList.add(user);
	}
	
	public void EnterRoom(ChattingRoom room) {
		room.EnterRoom(this);//채팅방 입장
		this.joinRoomList.add(room);//유저가 속한 방을 추가
	}
	
	public void network() {
		try {
			is = client_socket.getInputStream();
			dis = new DataInputStream(is);
			os = client_socket.getOutputStream();
			dos = new DataOutputStream(os);
		
			textArea.append(name + "님이 입장했습니다.\n");
			textArea.setCaretPosition(textArea.getText().length());	
	
		} catch (Exception e) {
			e.printStackTrace();
			//textArea.append("스트림 셋팅 에러\n");
			textArea.setCaretPosition(textArea.getText().length());
		}
	}
	
	//이름 수정..?
	public static final String CODE_100 = "CODE_100"; // 1:1 채팅방 만들기
	public static final String CODE_200 = "CODE_200"; // 그룹 채팅방 만들기
	public static final String CODE_300 = "CODE_300"; // 채팅방
	public static final String CODE_400 = "CODE_400"; // 채팅 메시지
	public static final String CODE_500 = "CODE_500"; // 유저 아이디
	public static final String CODE_600 = "CODE_600"; // 접속 중인 유저 리스트
	public static final String CODE_700 = "CODE_700"; // 새로 접속한 유저
	public static final String CODE_800 = "CODE_800"; // 이미 존재하는 유저
	public static final String CODE_900 = "CODE_900"; // 사용자 정보 변경
	 
	/*메시지 처리 부분*/
	public void InMessage(String str) {// 사용자 메세지 처리
		String[] array = str.split("//");
		if(array[0].equals(CODE_100)) { //1:1 채팅방 만들면 (친구 한명 선택)

			String myName = array[1];
			String friendName = array[2];
			String roomTitle = friendName; //기본 방 타이틀= 친구이름

			textArea.append(myName+","+friendName+"의 채팅방 생성됨\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			server.createSingleChat(this,myName, friendName);
		}
		else if(array[0].equals(CODE_200)) {//단톡방
			multiChatUserList = new ArrayList<String>();
			String msg="";
			for(int i=1;i<array.length;i++) {
				multiChatUserList.add(array[i]);
				if(i==array.length)
					msg+=array[i];
				else
					msg+=array[i]+",";
			}
			textArea.append(msg+" 채팅방 생성됨.\n");
			textArea.setCaretPosition(textArea.getText().length());
			server.createMultiChat(multiChatUserList);
		}
		else if(array[0].equals(CODE_500)) {
			//소켓연결 직후 유저아이디 받는 부분
			textArea.append("유저아이디 수신 : "+array[1]+"\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			//기존의 유저인지 판별 후 다르게 작동
			if(server.isExistingUser(array[1])!=null) {
				User existingUser=server.isExistingUser(array[1]);
				ArrayList<ChattingRoom> existingRooms = server.getJoinRooms(array[1]);
				
				setName(array[1]);
				setStateImg(existingUser.getStateImg());
				setStateMsg(existingUser.getStateMsg());
				for(int i=0;i<existingRooms.size();i++) {
					sendMsg(CODE_800+"//"+existingRooms.get(i).roomTitle+"//"+existingRooms.get(i).chat);
					System.out.println("User->기존 채팅방 목록,내용전송 "+i);
				}
				
				textArea.append("이미 있는 사용자입니다.\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
			else {
				setName(array[1]);
				server.usersUpdateFriendList(array[1]);
				textArea.append("새로운 사용자입니다.\n"); 
				textArea.setCaretPosition(textArea.getText().length());
			}
			
		}
		else if(array[0].equals(CODE_400)){ // 내가 보낸 메시지
			String roomTitle = array[1];
			str = array[2];
			textArea.append(roomTitle+" : "+str + "\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			//서버에서 브로드캐스팅(유저 객체도 함께 넘겨줌)
			server.broadcast(this,str,roomTitle); 
		}
		else if(array[0].equals(CODE_600)) {
			friendsList = server.getFriendList();
			String msg = array[0]+"//";
			
			for(int i=0;i<friendsList.size();i++) {
				User user = friendsList.get(i);
				if(i==friendsList.size()-1) {//마지막 배열 
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
		else if(array[0].equals(CODE_900)) {
			textArea.append(array[1]+"님 상태 변경 "+array[2]+" / "+array[3]+"\n");
			textArea.setCaretPosition(textArea.getText().length());
			this.stateImg = array[2];
			this.stateMsg = array[3];
			server.userInfoUpdate(array[1],array[2],array[3]);
		}
		else {
			System.out.println("지원하지 않는 메시지 유형");
		}
	}
	
	public void sendMsg(String str) {
		try {
			byte[] bb;		
			bb = str.getBytes();
			dos.write(bb); //.writeUTF(str);
		} 
		catch (IOException e) {
			textArea.append("메시지 송신 에러 발생\n");	
			textArea.setCaretPosition(textArea.getText().length());
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				// chatPanel에서 오는 메시지
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
					client_socket.close();
					//유저가 나간 사실을 매니저에게 알림
					//유저 리스트에서 삭제,서버에 프린트를 위임
					server.exitUser(this);
					break;
				
				} catch (Exception e1) {
				
				}// catch문 끝
			}// 바깥 catch문끝
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
