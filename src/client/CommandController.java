package client;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import client.ChatFrame;
import client.LoginPanel;
import client.MainFrame;
import client.StartPanel;
import client.ChatPanel;
import client.FriendPanel; 

import server.User;

public class CommandController {
	
	Socket socket = LoginPanel.socket;//사용자의 소켓을 받아옴
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private HashMap<String, JTextPane> chattingRoomList = new HashMap<String, JTextPane>();//key = roomTitle,value = textPane
	private HashMap<String, MainFrame> mainFrameList = new HashMap<String, MainFrame>();//key = userName,value = StartPanel
	private JTextPane textPane;
	private String roomTitle;
	private List<JLabel> ChatRoom = new ArrayList<JLabel>();
	private List<JLabel> userLabel = new ArrayList<JLabel>();
	private ArrayList<UserInfo> onlineUserList = new ArrayList<UserInfo>();
	private String userId;
	
	JPanel panel;
	private JLabel lblMouseEvent;
	private Graphics gc;
	private int pen_size = 2; // minimum 2
    // 그려진 Image를 보관하는 용도, paint() 함수에서 이용한다.
	private Image panelImage = null; 
	private Graphics gc2 = null;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	 
	public static CommandController controller;
	private CommandController(){
		//클라이언트가 서버로부터 메시지를 수신하는 스레드 생성후 실행
		RecieveMassage();
	}
	public static CommandController getController() {
		if(controller == null)
			controller = new CommandController();
		return controller;
	}
	
	//chatPanel의 TextArea에 글자 붙임
		public void append_My_Message(String roomTitle, String str) { // 오른쪽에 나와야 함
			// 채팅방 찾고
			setTextPane(chattingRoomList.get(roomTitle));
			
			String arr[] = str.split(" ");
			String message = "";

			for (int i=1; i<arr.length; i++) {
				message += arr[i] + " ";
			}
			
			if(textPane==null) {
				//초대된 사람이라 채팅방에 입장은 했지만 화면이 안떴을때
				System.out.println("Commandcontroller -> textPane==null");
			}
			else {
				StyledDocument doc = textPane.getStyledDocument();
				SimpleAttributeSet right = new SimpleAttributeSet();
				StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
				StyleConstants.setForeground(right, Color.BLACK);
				StyleConstants.setBackground(right, Color.YELLOW);
				doc.setParagraphAttributes(doc.getLength(), 1, right, false);
				try {
					doc.insertString(doc.getLength(), message+"\n\n", right );
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				
				int len = textPane.getDocument().getLength(); // same value as
				textPane.setCaretPosition(len); // place caret at the end (with no selection)			
			}
				
		}
		
	public void append_Message(String roomTitle, String str) {//chatPanel의 TextArea에 글자 붙임
		
		setTextPane(chattingRoomList.get(roomTitle)); //룸이름을 key로 텍스트를 붙일 텍스트팬을 해쉬맵에서 찾아서 지정
		
		// [user2] hello, nice to meet you.
				String arr[] = str.split(" ");
				String message = "";

				for (int i=1; i<arr.length; i++) {
					message += arr[i] + " ";
				}
				String user = str.split(" ")[0];
				
		if(textPane==null) {
			//초대된 사람이라 채팅방에 입장은 했지만 화면이 안떴을때
			System.out.println("Commandcontroller -> textPane==null");
			
		}
		else {
			StyledDocument doc = textPane.getStyledDocument();
			SimpleAttributeSet left = new SimpleAttributeSet();
			StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(left, Color.BLACK);
			StyleConstants.setBackground(left, Color.WHITE);
			doc.setParagraphAttributes(doc.getLength(), 1, left, false);
			try {
				doc.insertString(doc.getLength(), user+"\n", left);
				doc.insertString(doc.getLength(), message+"\n\n", left );
				
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int len = textPane.getDocument().getLength(); // same value as
			textPane.setCaretPosition(len); // place caret at the end (with no selection)
		}
			
	}
	
	public void append_Icon(ImageIcon icon) {
		int len = textPane.getDocument().getLength();
		textPane.setCaretPosition(len); // place caret at the end (with no selection)
		textPane.insertIcon(icon);
	}
	
	
	public void append_Image(String roomTitle, ImageIcon image) {
		setTextPane(chattingRoomList.get(roomTitle)); 
		
		Image ori_img = image.getImage();
	    Image new_img;
	    ImageIcon new_icon;
	    int width, height;
	    double ratio;
	    width = image.getIconWidth();
	    height = image.getIconHeight();
	    // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
	    if (width > 200 || height > 200) {
	       if (width > height) { // 가로 사진
	          ratio = (double) height / width;
	          width = 200;
	          height = (int) (width * ratio);
	       } else { // 세로 사진
	          ratio = (double) width / height;
	          height = 200;
	          width = (int) (height * ratio);
	       }
	       new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	       new_icon = new ImageIcon(new_img);
	       textPane.insertIcon(new_icon);
	     } else {
	       textPane.insertIcon(image);
	       new_img = ori_img;
	     }
	    int len = textPane.getDocument().getLength();
	     textPane.setCaretPosition(len);
	     textPane.replaceSelection("\n");

	     //textPane.insertIcon(image);
	    // ImageViewAction viewaction = new ImageViewAction();
	     //new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
	     //panelImage = ori_img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_DEFAULT);

	    //gc2.drawImage(ori_img,  0,  0, panel.getWidth(), panel.getHeight(), panel);
	   // gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
	    
	
	}
	
	public void RecieveMassage() { // 스레드를 돌려서 서버로부터 메세지를 수신
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			System.out.println("스트림 설정 에러!!\n");
		}
		Thread th = new Thread(new Runnable() { //서버에서 메시지 받는 스레드
			@SuppressWarnings("null")
			@Override
			public void run() {
				while (true) {
					try {
						//byte[] b = new byte[128];
						byte[] b = new byte[1024];
						dis.read(b);
						String msg = new String(b);
						msg = msg.trim();
						System.out.println("CommandControll -> 서버로부터의 메시지 : "+msg);
						String[] array = msg.split("//");
						
						//채팅방 생성 완료시->채팅방 이름을 전달받아 채팅방의 모든 인원 채팅방 띄우기(강제)
						if(array[0].equals(User.CODE_300)) {
							roomTitle = array[1];
							if(chattingRoomList.get(roomTitle)==null) { //처음 생성되는 방
								System.out.println("CommandControll -> roomTitle "+roomTitle);
								ChatFrame a = new ChatFrame(roomTitle); //처음 방이 생성된 경우 채팅방창을 띄움
								chattingRoomList.put(roomTitle, a.getChatPanel().getTextPaneChat());	//채팅방들의 TextPane을 해쉬맵으로 저장
								
								JLabel room = new JLabel(roomTitle);
								ChatRoom.add(room); //처음 방이 생성되면 채팅목록에 추가함
								for(JLabel j:ChatRoom) {
									System.out.println("채팅방 리스트 추가 -> " + j.getText().toString());
								}
							}
							else {
								//채팅방이 존재할 경우
								JOptionPane.showMessageDialog(null, "이미 존재하는 채팅방입니다.");
								
							}
							
						}
						else if(array[0].equals(User.CODE_400)) {
							//String message = User.SIGNAL_NOMAL_MSG+"//"+roomTitle+"//"+str;
							roomTitle= array[1];
							// array[2] = [user1] hello
							// [] 제거
							String charsToRemove = "[]";
							String who = array[2].split(" ")[0]; // 누가 보냈는지 / 나? 아님 상대방?
							for (char c : charsToRemove.toCharArray()) {
								who = who.replace(String.valueOf(c), "");
							}
							String str = array[2]; // 보낸 메시지 (append 할 때 나누자)

							System.out.println("보낸 사람: " + who);
							System.out.println("현재 접속자: " + userId);
							if (who.equals(userId)) { // 내가 보낸 메시지이면
								append_My_Message(roomTitle, str);
							} else { // 상대방이 보낸 메시지이면
								append_Message(roomTitle,str);
							}
						}
						else if(array[0].equals(User.CODE_600)) {
							userLabel.clear();
							onlineUserList.clear(); //접속중인 유저 리스트 초기화
							for(int i=1; i<array.length;i++) {
								String[] userInformation = array[i].split("!!");
								
								userLabel.add(new JLabel(userInformation[0]));

								UserInfo user = new UserInfo();
								user.setName(userInformation[0]);
								user.setStateImg(userInformation[1]);
								user.setStateMsg(userInformation[2]);
								System.out.println("CommandController->"+userInformation[0]+userInformation[1]+userInformation[2]);
								onlineUserList.add(user);
							}
						}
						else if(array[0].equals(User.CODE_700)) {
							System.out.println("CommandController->CODE_700 userName="+array[2]);
							//friendPanel의 dataSetting을 호출해야함
							mainFrameList.get(array[1]).getStartPanel().friendPanel.update(array[2]);
						}
						else if(array[0].equals(User.CODE_800)) {
							/*CODE_800+"//"+existingRooms.get(i).roomTitle+"//"+existingRooms.get(i).chat*/
							JLabel room = new JLabel(array[1]);
							ChatRoom.add(room);
							
							JTextPane temp = new JTextPane();
							temp.setText(array[2]);
							chattingRoomList.put(array[1], temp);	//채팅방들의 TextPane을 해쉬맵으로 저장
							
						}
						else if(array[0].equals(User.CODE_900)) {
							//유저의 상태이미지나 상태메시지 변경이 있다면 모든 유저들의 화면을 갱신
							/*User.CODE_900+"//"+user.getName()+"//"+userName+"//"+stateImg+"//"+stateMsg*/
							UserInfo user = searchByUserName(array[2]);
							onlineUserList.remove(user);
							UserInfo reUser = new UserInfo(array[2]);
							reUser.setStateImg(array[3]);
							reUser.setStateMsg(array[4]);
							onlineUserList.add(reUser);
							System.out.println("CommandController->CODE_900 "+searchByUserName(array[2]).getName()+searchByUserName(array[2]).getStateImg(array[2])+searchByUserName(array[2]).getStateMsg());
							mainFrameList.get(array[1]).getStartPanel().friendPanel.updateState(array[2],array[3],array[4]);
							
						}
						else if(array[0].equals(User.CODE_410)) {
							//String message = User.CODE_420+"//"+roomTitle+"//"+img;
							roomTitle= array[1];
							//String str = array[2];
							ImageIcon image = new ImageIcon(array[2]);	//선택한 이미지 경로
							append_Image(roomTitle,image);
							
						}
						else if(array[0].equals(User.CODE_420)) {
							//String message = User.CODE_420+"//"+roomTitle+"//"+img;
							roomTitle= array[1];
							//String str = array[2];
							ImageIcon image = new ImageIcon(array[2]);	//선택한 이미지 경로
							append_Image(roomTitle,image);
							
						}
						else {
							System.out.println("CommandController-> 지원하지 않는 메시지 형식입니다.");
						}
		
						
					} catch (IOException e) {
						append_Message(roomTitle,"메세지 수신 에러!!\n");
						// 서버와 소켓 통신에 문제가 생겼을 경우 소켓을 닫는다
						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							break; // 에러 발생하면 while문 종료
						} catch (IOException e1) {
						}
					}
				} // while문 끝
			}// run메소드 끝
		});
		th.start();
	}

	public void send_Message(String str) { // 서버로 메세지를 보내는 메소드
		try {
			byte[] bb;
			bb = str.getBytes();
			dos.write(bb); //.writeUTF(str);
			System.out.println("컨트롤러 보내지니");
			System.out.println("너 뭐야 : " + bb); //여기까지는 image_msg로 출력

		
		} catch (IOException e) {
			//textArea.append("메세지 송신 에러!!\n");
			append_Message(roomTitle,"메세지 송신 에러!!\n");//여기의 roomTitle은 의미가 없음(추후에 더 보완!!)
		}

	}
	
	public void send_Image(String msg) { 
		try {
			byte[] bb;
			bb = msg.getBytes();
			dos.write(bb); //.writeUTF(str);
			//oos.writeObject(img);

		} catch (IOException e) {
			//textArea.append("메세지 송신 에러!!\n");
			append_Message(roomTitle,"메세지 송신 에러!!\n");//여기의 roomTitle은 의미가 없음(추후에 더 보완!!)]
		}
	}

	
	public void saveMainFrame(String userId,MainFrame mainFrame) {
		this.userId = userId;
		mainFrameList.put(userId, mainFrame);
	}
	
	public UserInfo searchByUserName(String userName) {
		for(int i=0;i<onlineUserList.size();i++) {
			if(onlineUserList.get(i).getName().equals(userName))
				return onlineUserList.get(i);
		}
		return null;
	}
	
	public String getroomTitle() {
		return roomTitle;
	}
	public JTextPane getTextPane() {
		System.out.println(textPane.getText().toString());
		return textPane;
	}
	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}
	
	public List<JLabel> getChatLabel() {
		return ChatRoom; 
	}
	public List<JLabel> getUserLabel() {
		return userLabel;
	}
	public void setUserLabel(List<JLabel> userLabel) {
		this.userLabel = userLabel;
	}
	public HashMap<String, JTextPane> getChattingRoomList() {
		return chattingRoomList;
	}
	public void setChattingRoomList(HashMap<String, JTextPane> chattingRoomList) {
		this.chattingRoomList = chattingRoomList;
	}
	public HashMap<String, MainFrame> getMainFrameList() {
		return mainFrameList;
	}
	public void setMainFrameList(HashMap<String, MainFrame> mainFrameList) {
		this.mainFrameList = mainFrameList;
	}
	public ArrayList<UserInfo> getOnlineUserList() {
		return onlineUserList;
	}
	public void setOnlineUserList(ArrayList<UserInfo> onlineUserList) {
		this.onlineUserList = onlineUserList;
	}
	
	

}

