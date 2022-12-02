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
		textArea.append(userList.size() +" : ÇöÀç º¤ÅÍ¿¡ ´ã°ÜÁø »ç¿ëÀÚ ¼ö\n");
		textArea.append("»ç¿ëÀÚ Á¢¼Ó¿Ï·á\n");
		user.start();//»õ·Î µé¾î¿Â À¯ÀúÀÇ ½º·¹µå ½ÇÇà
	}
	
	public void exitUser(User user) {
		userList.remove(user); // ¿¡·¯°¡³­(exitÇÑ) ÇöÀç °´Ã¼¸¦ º¤ÅÍ¿¡¼­ Áö¿î´Ù
		backupUserList.put(user.getName(),user);
		textArea.append(userList.size() +" : ÇöÀç º¤ÅÍ¿¡ ´ã°ÜÁø »ç¿ëÀÚ ¼ö\n");
		textArea.append("»ç¿ëÀÚ Á¢¼Ó ²÷¾îÁü ÀÚ¿ø ¹İ³³\n");
		textArea.setCaretPosition(textArea.getText().length());
		System.out.println("Backup UserInfo : "+user.getName()+"/"+backupUserList.get(user.getName()).getStateImg()+"/"+backupUserList.get(user.getName()).getStateMsg());
	}
	
	public void usersUpdateFriendList(String newUser) {
		textArea.append("»õ·Î¿î À¯Àú" + newUser + "¸¦ Á¦¿ÜÇÏ°í ¾÷µ¥ÀÌÆ®\n");
		textArea.setCaretPosition(textArea.getText().length());
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
<<<<<<< HEAD
			if (!user.getName().equals(newUser)) { // »õÀ¯Àú´Â Á¦¿Ü
				user.sendMsg(User.CODE_700 +"//"+user.getName()+ "//" + newUser);
				textArea.append(user.getName() + "´ÔÀÇ Ä£±¸¸ñ·Ï ¾÷µ¥ÀÌÆ®\n");
=======
			if (!user.getName().equals(newUser)) { // ìƒˆìœ ì €ëŠ” ì œì™¸
				user.sendMsg(User.CODE_700 +"//"+user.getName()+ "//" + newUser);
				textArea.append(user.getName() + "ë‹˜ì˜ ì¹œêµ¬ëª©ë¡ ì—…ë°ì´íŠ¸\n");
>>>>>>> 1297dacf4156845e3aceae8699e616031fd3ab61
				textArea.setCaretPosition(textArea.getText().length());
			}
		}
	}
	
	//À¯ÀúÀÇ »óÅÂÀÌ¹ÌÁö³ª »óÅÂ¸Ş½ÃÁö º¯°æÀÌ ÀÖ´Ù¸é ¸ğµç À¯ÀúµéÀÇ È­¸éÀ» °»½Å
	public void userInfoUpdate(String userName,String stateImg,String stateMsg) {
		//ÇØ´ç À¯ÀúÀÇ Á¤º¸¸¦ ¹Ù²Ù°í
		User chageUser = searchByUserNameInOnline(userName);
		chageUser.setStateImg(stateImg);
		chageUser.setStateMsg(stateMsg);
		textArea.append(searchByUserNameInOnline(userName).getName() + "´ÔÀÇ Á¤º¸ ¾÷µ¥ÀÌÆ®\n");
		textArea.append(searchByUserNameInOnline(userName).getStateImg()+"/"+searchByUserNameInOnline(userName).getStateMsg()+ "\n");
		textArea.setCaretPosition(textArea.getText().length());
		//¸ğµç À¯Àúµé È­¸é °»½Å ¸í·É
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
