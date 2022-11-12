import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class FriendList extends JFrame {
	
	ArrayList<String> userNameList = new ArrayList<String>();
	Map<String, ProfilePanel> userProfilePanel = new HashMap<String, ProfilePanel>();
	
	
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane1;
	private JTextField txtInput;
	private String UserName;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private JLabel lblUserName;
	// private JTextArea textArea;
	private JTextPane textArea;

	private Frame frame;
	private FileDialog fd;
	private JButton imgBtn;

	private JPanel userListPanel;
	private JLabel userListLabel;
	
	public JPanel userPrfPanel; // 내 프로필 이미지 패널
	public JLabel userPrfLabel; // 내 프로필 이미지 라벨

	//create the frame
	public FriendList(String username, String ip_addr, String port_no) {
		FriendList friendList = this;
		
		this.UserName = username;
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setSize(400, 600);
	      setBounds(100, 100, 386, 512);
	      contentPane = new JPanel();
	      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	      setContentPane(contentPane);
	      contentPane.setLayout(null);

	      contentPane1 = new JPanel();
	      contentPane1.setBackground(Color.WHITE);
	      contentPane1.setLayout(null);
	      contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
	      contentPane1.setBounds(61, 0, 311, 485);
	      contentPane.add(contentPane1);

	      JLabel FriendLabel = new JLabel("\uCE5C\uAD6C");
	      FriendLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
	      FriendLabel.setBounds(23, 23, 76, 34);
	      contentPane1.add(FriendLabel);

	      JLabel userNameLabel = new JLabel(username);
	      userNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
	      userNameLabel.setBounds(77, 76, 68, 15);
	      contentPane1.add(userNameLabel);

	      userPrfPanel = new JPanel(); // 내 프로필 사진 패널
	      userPrfPanel.setBackground(Color.WHITE);
	      userPrfPanel.setBounds(23, 66, 42, 42);
	      contentPane1.add(userPrfPanel);

	      ImageIcon icon = new ImageIcon("src/icon1.jpg");

	      Image img = icon.getImage();
	      Image changeImg = img.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
	      ImageIcon changeIcon = new ImageIcon(changeImg);
	      JLabel userImgLabel = new JLabel(changeIcon);

	      userPrfPanel.add(userImgLabel);


	      ImageIcon friendIcon = new ImageIcon("src/icon1.jpg");
	      JLabel friendIconlabel = new JLabel(friendIcon);


	      JLabel friendNameLabel = new JLabel("");
	      friendNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
	      friendNameLabel.setBounds(77, 134, 68, 15);
	      contentPane1.add(friendNameLabel);

	}
	
	public class ProfilePanel extends JPanel { //프로필 사진
		//
	}
}
