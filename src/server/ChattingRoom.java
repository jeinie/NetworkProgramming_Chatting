package server;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ChattingRoom {

	private ArrayList<User> userList;
	private User roomOwner; //����
	String roomTitle; //�� Ÿ��Ʋ
	String chat="\n";//ä�ó���
	
	public ChattingRoom() {
		userList = new ArrayList<User>();
	}
	
	public ChattingRoom(User user) { //������ ���� ���� ��
		userList = new ArrayList<User>();
		userList.add(user);
		this.roomOwner = user;
		roomTitle = "NewChatting";
	}
	
	public ChattingRoom(ArrayList<User> users) { //�������� ������ ���� ���� ��
		this.userList = users;
		this.roomOwner = users.get(0); //ù��° ������ ��������
		roomTitle = "NewChatting";
	}
	
	public void EnterRoom(User newUser) {
		userList.add(newUser);
	}
	
	public boolean isUserJoin(User user) { //ã������ ������ �� �濡 �������̶�� true,�ƴϸ� false
		for(int i=0;i<userList.size();i++)
			if(userList.get(i).equals(user))
				return true;
		
		return false;
	}
	public void ExitRoom(User user) {
		userList.remove(user);
		if(user==roomOwner) {//������ ������� �ٽ� ù��° ������� ��������
			roomOwner = userList.get(0);
			System.out.println("ChattingRoom-> Owner User Exit, Owner Change");
		}
		
		if(userList.size()<1) {//��� �ο��� �� ���� �����ٸ� ���� ����
			RoomManager.removeRoom(this);
			System.out.println("ChattingRoom-> All User Exit, Room Delete");
			return;
		}
	}
	
	//����� ��� �����鿡�� ��ε�ĳ����
	public void broadcast(String str) {
		String message = User.CODE_400+"//"+roomTitle+"//"+str;
		chat += str+"\n"+"\n";
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			System.out.println("userList: " + userList);
			user.sendMsg(message);
		}
	}
	
	//�̹��� ��ε�ĳ����
	public void broadcastEmoticon(ImageIcon emoji) {
		String message = User.CODE_410+"//"+roomTitle+"//"+emoji;
		System.out.println("���������");
		chat += emoji+"\n"+"\n";
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			System.out.println("userList: " + userList);
			user.sendMsg(message);
		}
	}
	
	//�̹��� ��ε�ĳ����
	public void broadcastImg(ImageIcon img) {
		String message = User.CODE_420+"//"+roomTitle+"//"+img;
		chat += img+"\n"+"\n";
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			System.out.println("userList: " + userList);
			user.sendMsg(message);
		}
	}
	public int getUserCount() {
		return userList.size();
	}
	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public User getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(User roomOwner) {
		this.roomOwner = roomOwner;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
}
