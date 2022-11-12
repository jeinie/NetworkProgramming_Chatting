import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
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

import com.mysql.cj.protocol.Resultset;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendList extends JFrame {
	
	ArrayList<String> userNameList = new ArrayList<String>();
	Map<String, ProfilePanel> userProfilePanel = new HashMap<String, ProfilePanel>();
	
	
	//private JTextField txtIpAddress;
	//private JTextField txtPortNumber;
	
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
	
	JPanel chatPanel; //채팅 패널

	private Vector<String> dataVec = new Vector<String>(); // 데이터베이스에서 사용자 10명 가져와서 다음 벡터

	// MySQL
	static final String DB_URL = "jdbc:mysql://localhost:3306/network_db";
    static final String USER = "root";
    static final String PASSWORD = "1234";
    static final String QUERY = "SELECT * FROM users"; // 실행할 쿼리    

	//create the frame
	public FriendList(String username, String ip_addr, String port_no) throws Exception {
		FriendList friendList = this;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = connection.createStatement(); // Statement 생성 후 실행할 쿼리정보 등록
            ResultSet rs = stmt.executeQuery(QUERY); // 결과를 담을 ResultSet 생성 후 결과 담기
            // Extract data from result set
			int i = 0;
            while (rs.next()) {
                // Retrieve by column name
                System.out.println("name: " + rs.getString(1));
				// 결과를 Vector 에 추가
				dataVec.add(rs.getString(1));
				System.out.println(dataVec.get(i));
				i++;
			}
            
       
				this.UserName = username;
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLocationRelativeTo(null);
				setSize(400, 650);
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

				ImageIcon Menu1 = new ImageIcon("src/Menu1.png"); //메뉴 1 - 친구목록 창
				JButton Menu1Btn = new JButton(Menu1);
				Menu1Btn.setBounds(0, 25, 60, 55);
				Menu1Btn.setBorderPainted(false);
				Menu1Btn.setToolTipText("친구");
				Menu1Btn.setHorizontalTextPosition(JButton.CENTER);
				contentPane.add(Menu1Btn);
				
				class FriendAction extends MouseAdapter { //사람 메뉴 누르면 친구목록 창으로 
					public void mouseClicked(MouseEvent e) {
						chatPanel.setVisible(false);
						contentPane1.setVisible(true);
					}
				}
				
				FriendAction friendAction = new FriendAction();
				Menu1Btn.addMouseListener(friendAction);
				
				ImageIcon Menu2 = new ImageIcon("src/Menu2.png"); //메뉴 2 - 채팅목록 창
				JButton Menu2Btn = new JButton(Menu2);
				Menu2Btn.setBounds(0, 80, 60, 55);
				Menu2Btn.setBorderPainted(false);
				Menu2Btn.setToolTipText("채팅");
				Menu2Btn.setHorizontalTextPosition(JButton.CENTER);
				contentPane.add(Menu2Btn);
				
				class ChatAction extends MouseAdapter { //채팅 메뉴 누르면 채팅목록 창으로 
					public void mouseClicked(MouseEvent e) {
						contentPane1.setVisible(false);
						chatPanel.setVisible(true);
					}
				}
				
				ChatAction chatAction = new ChatAction();
				Menu2Btn.addMouseListener(chatAction);
				
				JLabel FriendLabel = new JLabel("친구"); 
				FriendLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
				FriendLabel.setBounds(23, 23, 76, 34);
				contentPane1.add(FriendLabel);
				int index = 0;
				while (index < dataVec.size()) {
					JLabel userNameLabel = new JLabel(dataVec.get(index).toString());
					System.out.println(dataVec.get(index).toString());
					userNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
					userNameLabel.setBounds(77, 76 + index*50, 68, 15);
					contentPane1.add(userNameLabel);
					index++;
				}

				userPrfPanel = new JPanel(); // 내 프로필 사진 패널
				userPrfPanel.setBackground(Color.WHITE);
				userPrfPanel.setBounds(25, 65, 40, 40);
				contentPane1.add(userPrfPanel);

				ImageIcon icon = new ImageIcon("src/icon1.jpg"); //기본 프로필 이미지

				Image img = icon.getImage();
				Image changeImg = img.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
				ImageIcon changeIcon = new ImageIcon(changeImg);
				userPrfLabel = new JLabel(changeIcon);

				userPrfPanel.add(userPrfLabel);
				
				ImageIcon friendIcon = new ImageIcon("src/icon1.jpg");
				JLabel friendIconlabel = new JLabel(friendIcon);


				JLabel friendNameLabel = new JLabel("");
				friendNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
				friendNameLabel.setBounds(77, 134, 68, 15);
				contentPane1.add(friendNameLabel);
				
				//chatPanel
				chatPanel = new JPanel();
				chatPanel.setBackground(Color.WHITE);
				chatPanel.setLayout(null);
				chatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				chatPanel.setBounds(61, 0, 311, 485);
				contentPane.add(chatPanel);
				
				JLabel ChatLabel = new JLabel("채팅"); 
				ChatLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
				ChatLabel.setBounds(23, 23, 76, 34);
				chatPanel.add(ChatLabel);
				
            System.out.println("mysql db 연결 성공");
            

			// 클라이언트에 사용자 10명 데이터 보내기

		} catch(SQLException error) {
            System.out.println(error);
            System.out.println("DB 접속 오류");
        }
		
	}
	
	public class ProfilePanel extends JPanel { //프로필 사진
		//
	}
}
