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

import javax.swing.ImageIcon;
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
	String name, stateImg, stateMsg; //ÀÌ¸§, ÇÁ»ç, »ó¸Ş
	JTextArea textArea = Server.textArea;
	
	//»ç¿ëÀÚ°¡ Âü¿©ÇÏ´Â ¹æµé
	ArrayList<String> userName = new ArrayList<String>(); //userName
	ArrayList<ChattingRoom> joinRoomList = new ArrayList<ChattingRoom>(); //Âü¿©ÇÏ°í ÀÖ´Â ¹æ?
	ArrayList<User> friendsList = new ArrayList<User>(); //Ä£±¸¸®½ºÆ®
	ArrayList<String> multiChatUserList; //´ÜÅå¹æ À¯Àú ¸®½ºÆ®
	
	public User(Socket socket, Server server) {
		name = "newUser";
		stateImg = "src/img/basic.png";
		stateMsg = "»óÅÂ ¸Ş¼¼Áö";
		client_socket = socket;
		this.server = server;
		network();
	}
	
	public void addFreind(User user) { //Ä£±¸Ãß°¡
		friendsList.add(user);
	}
	
	public void EnterRoom(ChattingRoom room) {
		room.EnterRoom(this);//Ã¤ÆÃ¹æ ÀÔÀå
		this.joinRoomList.add(room);//À¯Àú°¡ ¼ÓÇÑ ¹æÀ» Ãß°¡
	}
	
	public void network() {
		try {
			is = client_socket.getInputStream();
			dis = new DataInputStream(is);
			os = client_socket.getOutputStream();
			dos = new DataOutputStream(os);
		
			textArea.append(name + "´ÔÀÌ ÀÔÀåÇß½À´Ï´Ù.\n");
			textArea.setCaretPosition(textArea.getText().length());	
	
		} catch (Exception e) {
			e.printStackTrace();
			//textArea.append("½ºÆ®¸² ¼ÂÆÃ ¿¡·¯\n");
			textArea.setCaretPosition(textArea.getText().length());
		}
	}
	
<<<<<<< HEAD
	public static final String CODE_100 = "CODE_100"; // 1:1 Ã¤ÆÃ¹æ ¸¸µé±â
	public static final String CODE_200 = "CODE_200"; // ±×·ì Ã¤ÆÃ¹æ ¸¸µé±â
	public static final String CODE_300 = "CODE_300"; // Ã¤ÆÃ¹æ
	public static final String CODE_400 = "CODE_400"; // Ã¤ÆÃ ¸Ş½ÃÁö
	public static final String CODE_500 = "CODE_500"; // À¯Àú ¾ÆÀÌµğ
	public static final String CODE_600 = "CODE_600"; // Á¢¼Ó ÁßÀÎ À¯Àú ¸®½ºÆ®
	public static final String CODE_700 = "CODE_700"; // »õ·Î Á¢¼ÓÇÑ À¯Àú
	public static final String CODE_800 = "CODE_800"; // ÀÌ¹Ì Á¸ÀçÇÏ´Â À¯Àú
	public static final String CODE_900 = "CODE_900"; // »ç¿ëÀÚ Á¤º¸ º¯°æ
=======
	//ì´ë¦„ ìˆ˜ì •..?
	public static final String CODE_100 = "CODE_100"; // 1:1 ì±„íŒ…ë°© ë§Œë“¤ê¸°
	public static final String CODE_200 = "CODE_200"; // ê·¸ë£¹ ì±„íŒ…ë°© ë§Œë“¤ê¸°
	public static final String CODE_300 = "CODE_300"; // ì±„íŒ…ë°©
	public static final String CODE_400 = "CODE_400"; // ì±„íŒ… ë©”ì‹œì§€
	public static final String CODE_500 = "CODE_500"; // ìœ ì € ì•„ì´ë””
	public static final String CODE_600 = "CODE_600"; // ì ‘ì† ì¤‘ì¸ ìœ ì € ë¦¬ìŠ¤íŠ¸
	public static final String CODE_700 = "CODE_700"; // ìƒˆë¡œ ì ‘ì†í•œ ìœ ì €
	public static final String CODE_800 = "CODE_800"; // ì´ë¯¸ ì¡´ì¬í•˜ëŠ” ìœ ì €
	public static final String CODE_900 = "CODE_900"; // ì‚¬ìš©ì ì •ë³´ ë³€ê²½
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
	 
	//Ãß°¡
	public static final String CODE_410 = "CODE_410"; //ÀÌ¸ğÆ¼ÄÜ Àü¼Û
	public static final String CODE_420 = "CODE_420"; //ÀÌ¹ÌÁö Àü¼Û
		
		
	/*¸Ş½ÃÁö Ã³¸® ºÎºĞ*/
	public void InMessage(String str) {// »ç¿ëÀÚ ¸Ş¼¼Áö Ã³¸®
		String[] array = str.split("//");
<<<<<<< HEAD
		if(array[0].equals(CODE_100)) { //1:1 Ã¤ÆÃ¹æ ¸¸µé¸é (Ä£±¸ ÇÑ¸í ¼±ÅÃ)
=======
		if(array[0].equals(CODE_100)) { //1:1 ì±„íŒ…ë°© ë§Œë“¤ë©´ (ì¹œêµ¬ í•œëª… ì„ íƒ)
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61

			String myName = array[1];
			String friendName = array[2];
			String roomTitle = friendName; //±âº» ¹æ Å¸ÀÌÆ²= Ä£±¸ÀÌ¸§

			textArea.append(myName+","+friendName+"ÀÇ Ã¤ÆÃ¹æ »ı¼ºµÊ\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			server.createSingleChat(this,myName, friendName);
		}
<<<<<<< HEAD
		else if(array[0].equals(CODE_200)) {//´ÜÅå¹æ
=======
		else if(array[0].equals(CODE_200)) {//ë‹¨í†¡ë°©
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
			multiChatUserList = new ArrayList<String>();
			String msg="";
			for(int i=1;i<array.length;i++) {
				multiChatUserList.add(array[i]);
				if(i==array.length)
					msg+=array[i];
				else
					msg+=array[i]+",";
			}
			textArea.append(msg+" Ã¤ÆÃ¹æ »ı¼ºµÊ.\n");
			textArea.setCaretPosition(textArea.getText().length());
			server.createMultiChat(multiChatUserList);
		}
		else if(array[0].equals(CODE_500)) {
<<<<<<< HEAD
			//¼ÒÄÏ¿¬°á Á÷ÈÄ À¯Àú¾ÆÀÌµğ ¹Ş´Â ºÎºĞ
			textArea.append("À¯Àú¾ÆÀÌµğ ¼ö½Å : "+array[1]+"\n");
=======
			//ì†Œì¼“ì—°ê²° ì§í›„ ìœ ì €ì•„ì´ë”” ë°›ëŠ” ë¶€ë¶„
			textArea.append("ìœ ì €ì•„ì´ë”” ìˆ˜ì‹  : "+array[1]+"\n");
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
			textArea.setCaretPosition(textArea.getText().length());
			
			//±âÁ¸ÀÇ À¯ÀúÀÎÁö ÆÇº° ÈÄ ´Ù¸£°Ô ÀÛµ¿
			if(server.isExistingUser(array[1])!=null) {
				User existingUser=server.isExistingUser(array[1]);
				ArrayList<ChattingRoom> existingRooms = server.getJoinRooms(array[1]);
				
				setName(array[1]);
				setStateImg(existingUser.getStateImg());
				setStateMsg(existingUser.getStateMsg());
				for(int i=0;i<existingRooms.size();i++) {
					sendMsg(CODE_800+"//"+existingRooms.get(i).roomTitle+"//"+existingRooms.get(i).chat);
<<<<<<< HEAD
					System.out.println("User->±âÁ¸ Ã¤ÆÃ¹æ ¸ñ·Ï,³»¿ëÀü¼Û "+i);
=======
					System.out.println("User->ê¸°ì¡´ ì±„íŒ…ë°© ëª©ë¡,ë‚´ìš©ì „ì†¡ "+i);
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
				}
				
				textArea.append("ÀÌ¹Ì ÀÖ´Â »ç¿ëÀÚÀÔ´Ï´Ù.\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
			else {
				setName(array[1]);
				server.usersUpdateFriendList(array[1]);
				textArea.append("»õ·Î¿î »ç¿ëÀÚÀÔ´Ï´Ù.\n"); 
				textArea.setCaretPosition(textArea.getText().length());
			}
			
		}
<<<<<<< HEAD
		else if(array[0].equals(CODE_400)){ // ³»°¡ º¸³½ ¸Ş½ÃÁö
=======
		else if(array[0].equals(CODE_400)){ // ë‚´ê°€ ë³´ë‚¸ ë©”ì‹œì§€
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
			String roomTitle = array[1];
			str = array[2];
			textArea.append(roomTitle+" : "+str + "\n");
			textArea.setCaretPosition(textArea.getText().length());
			
			//¼­¹ö¿¡¼­ ºê·ÎµåÄ³½ºÆÃ(À¯Àú °´Ã¼µµ ÇÔ²² ³Ñ°ÜÁÜ)
			server.broadcast(this,str,roomTitle); 
		}
		else if(array[0].equals(CODE_600)) {
			friendsList = server.getFriendList();
			String msg = array[0]+"//";
			
			for(int i=0;i<friendsList.size();i++) {
				User user = friendsList.get(i);
				if(i==friendsList.size()-1) {//¸¶Áö¸· ¹è¿­ 
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
<<<<<<< HEAD
			textArea.append(array[1]+"´Ô »óÅÂ º¯°æ "+array[2]+" / "+array[3]+"\n");
=======
			textArea.append(array[1]+"ë‹˜ ìƒíƒœ ë³€ê²½ "+array[2]+" / "+array[3]+"\n");
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
			textArea.setCaretPosition(textArea.getText().length());
			this.stateImg = array[2];
			this.stateMsg = array[3];
			server.userInfoUpdate(array[1],array[2],array[3]);
		}
		else if(array[0].equals(CODE_420)) { //ÀÌ¹ÌÁö Àü¼Û
			String roomTitle = array[1];
			//str = array[2];
			ImageIcon image = new ImageIcon(array[2]);	
			//textArea.append(roomTitle+" : "+ image + "\n");
			textArea.append(roomTitle+" : ÀÌ¹ÌÁö º¸³¿ "+ "\n");
			//textArea.setCaretPosition(textArea.getText().length());
			
			//¼­¹ö¿¡¼­ ºê·ÎµåÄ³½ºÆÃ(À¯Àú °´Ã¼µµ ÇÔ²² ³Ñ°ÜÁÜ)
			server.broadcastImg(this,image,roomTitle); 
		}
		else {
			System.out.println("Áö¿øÇÏÁö ¾Ê´Â ¸Ş½ÃÁö À¯Çü");
		}
	}
	
	public void sendMsg(String str) {
		try {
			byte[] bb;		
			bb = str.getBytes();
			dos.write(bb); //.writeUTF(str);
		} 
		catch (IOException e) {
			textArea.append("¸Ş½ÃÁö ¼Û½Å ¿¡·¯ ¹ß»ı-user¿¡¼­\n");	
			textArea.setCaretPosition(textArea.getText().length());
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				// chatPanel¿¡¼­ ¿À´Â ¸Ş½ÃÁö
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
					//À¯Àú°¡ ³ª°£ »ç½ÇÀ» ¸Å´ÏÀú¿¡°Ô ¾Ë¸²
					//À¯Àú ¸®½ºÆ®¿¡¼­ »èÁ¦,¼­¹ö¿¡ ÇÁ¸°Æ®¸¦ À§ÀÓ
					server.exitUser(this);
					break;
				
				} catch (Exception e1) {
				
				}// catch¹® ³¡
			}// ¹Ù±ù catch¹®³¡
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