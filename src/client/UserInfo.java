package client;

import java.util.ArrayList;

public class UserInfo {

	private String name;
	private String stateImg;
	private String stateMsg;
	private boolean isConnect;//������
	
	
	public UserInfo() {
		name="user";
		isConnect = true;
		stateImg = "src/img/basic.png";
		stateMsg ="���� �޼���";
	}
	
	public UserInfo(String name) {
		this.name = name;
		stateImg = "src/img/basic.png";
		stateMsg = "���� �޼���";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}


	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}

	public String getStateImg(String name) {
		return stateImg;
	}

	public void setStateImg(String stateImg) {
		this.stateImg = stateImg;
	}
	
	
	
	
	
}
