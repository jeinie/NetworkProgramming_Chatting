package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import server.Server;
import javax.swing.JTextArea;

public class ClientManager {
	Server server;

	protected static ArrayList<User> userList;
	protected static HashMap<String,User> backupUserList;

	
	private static JTextArea textArea = Server.textArea;

	public ClientManager(Server server) {
		userList = new ArrayList<User>();
		backupUserList = new HashMap<String,User>();
		this.server = server;
	}
	
	public User searchByUserNameInOnline(String userName) {
		for(int i=0;i<userList.size();i++) {
			if(userList.get(i).getName().equals(userName))
				return userList.get(i);
		}
		return null;
	}
	
	public User searchByUserNameInExist(String userName) {
		return backupUserList.get(userName);
	}
	
	public void insertUser(Socket socket) {
		User user = new User(socket, server);
		userList.add(user);
		textArea.append(userList.size() +" : ���� ���Ϳ� ����� ����� ��\n");
		textArea.append("����� ���ӿϷ�\n");
		user.start();//���� ���� ������ ������ ����
	}
	
	public void exitUser(User user) {
		userList.remove(user); // ��������(exit��) ���� ��ü�� ���Ϳ��� �����
		backupUserList.put(user.getName(),user);
		textArea.append(userList.size() +" : ���� ���Ϳ� ����� ����� ��\n");
		textArea.append("����� ���� ������ �ڿ� �ݳ�\n");
		textArea.setCaretPosition(textArea.getText().length());
		System.out.println("Backup UserInfo : "+user.getName()+"/"+backupUserList.get(user.getName()).getStateImg()+"/"+backupUserList.get(user.getName()).getStateMsg());
	}
	
	public void usersUpdateFriendList(String newUser) {
		textArea.append("���ο� ����" + newUser + "�� �����ϰ� ������Ʈ\n");
		textArea.setCaretPosition(textArea.getText().length());
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			if (!user.getName().equals(newUser)) { // �������� ����
				user.sendMsg(User.CODE_700 +"//"+user.getName()+ "//" + newUser);
				textArea.append(user.getName() + "���� ģ����� ������Ʈ\n");
				textArea.setCaretPosition(textArea.getText().length());
			}
		}
	}
	
	//������ �����̹����� ���¸޽��� ������ �ִٸ� ��� �������� ȭ���� ����
	public void userInfoUpdate(String userName,String stateImg,String stateMsg) {
		//�ش� ������ ������ �ٲٰ�
		User chageUser = searchByUserNameInOnline(userName);
		chageUser.setStateImg(stateImg);
		chageUser.setStateMsg(stateMsg);
		textArea.append(searchByUserNameInOnline(userName).getName() + "���� ���� ������Ʈ\n");
		textArea.append(searchByUserNameInOnline(userName).getStateImg()+"/"+searchByUserNameInOnline(userName).getStateMsg()+ "\n");
		textArea.setCaretPosition(textArea.getText().length());
		//��� ������ ȭ�� ���� ���
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			user.sendMsg(User.CODE_900+"//"+user.getName()+"//"+userName+"//"+stateImg+"//"+stateMsg);
		}
	}
	
	public static ArrayList<User> getUserList() {
		return userList;
	}

	public static void setUserList(ArrayList<User> userList) {
		ClientManager.userList = userList;
	}


}
