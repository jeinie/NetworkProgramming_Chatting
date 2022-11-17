// ChatMsg.java ä�� �޽��� ObjectStream ��.
import java.io.Serializable;
import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	private String data;
	private String userName;
	private String roomID;
	public ImageIcon img;

	public ChatMsg(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
		//this.userName = userName;
		//this.roomID = roomID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
}