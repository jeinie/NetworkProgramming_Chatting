import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Scrollbar;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.protocol.Resultset;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendList extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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

	private JPanel tabPanel = new JPanel(); // 왼쪽 탭바
	private JPanel contentPanel = new JPanel(); // 친구목록, 채팅목록 띄우는 창
	private JPanel chatPanel; //채팅 패널
	private Scrollbar scroll; // 스크롤바
	private JScrollPane scrollPane;

	// MySQL
	static final String DB_URL = "jdbc:mysql://localhost:3306/network_db";
    static final String USER = "root";
    static final String PASSWORD = "1234";
    static final String QUERY = "SELECT * FROM users"; // 실행할 쿼리  

	private Vector<String> userVec = new Vector<String>(); // 데이터베이스에서 사용자 10명 가져온다.
	private Vector<String> imgVec = new Vector<>(); // 데이터베이스에서 사용자 10명의 프로필 사진을 가져온다.
	private Vector<String> msgVec = new Vector<String>();
	
	public JPanel userPrfPanel; // 내 프로필 이미지 패널
	public JLabel userPrfLabel; // 내 프로필 이미지 라벨
	
	public JPanel userMsgPanel; // 상태메세지 패널
	public JLabel userMsgLabel; // 상태메세지 라벨
	

	// create the frame
	public FriendList(String username, String ip_addr, String port_no) throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = connection.createStatement(); // Statement 생성 후 실행할 쿼리정보 등록
            ResultSet rs = stmt.executeQuery(QUERY); // 결과를 담을 ResultSet 생성 후 결과 담기
            // Extract data from result set
			int i = 0;
            while (rs.next()) {
                // Retrieve by column name
               //System.out.println("name: " + rs.getString(1));
				// 결과를 Vector 에 추가
				userVec.add(rs.getString(1));
				imgVec.add(rs.getString(2));
				msgVec.add(rs.getString(3));
				System.out.println(userVec.get(i));
				System.out.println(imgVec.get(i));
				System.out.println(msgVec.get(i));
				i++;
			}

			this.UserName = username;
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			setSize(400, 650);
			setBounds(100, 100, 386, 512);
			contentPane = new JPanel();
			setContentPane(contentPane);
			contentPane.setLayout(null);
			tabPanel.setLayout(null);
			setTitle("상상톡");
			//contentPanel.setBackground(Color.lightGray);

			//JScrollPane scrollPane = new JScrollPane(contentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			//scrollPane.setBounds(80, 0, 300, 485);
			//scrollPane.setBackground(Color.BLACK);
			//add(scrollPane);

			// 스크롤바
			/*scroll = new Scrollbar(Scrollbar.HORIZONTAL, 0, 10, 0, 25);
			scroll.setBounds(500, 400, 30, 200);
			contentPanel.add(scroll);*/

			// 왼쪽 탭바
			tabPanel.setBounds(0, 0, 60, 485);
			contentPane.add(tabPanel);

			// 친구목록 창
			contentPanel.setBackground(Color.WHITE);
			contentPanel.setLayout(null);
			//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPanel.setBounds(80, 0, 300, 485);
			contentPane.add(contentPanel);
			
			
			//채팅 창
			chatPanel = new JPanel();
			chatPanel.setBackground(Color.WHITE);
			chatPanel.setLayout(null);
			//chatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			chatPanel.setBounds(61, 0, 311, 485);
			contentPane.add(chatPanel);
			
			//메뉴 1 - 친구목록 창
			ImageIcon menu1 = new ImageIcon("src/Menu1.png");
			JButton menu1Btn = new JButton(menu1);
			menu1Btn.setBounds(10, 25, 55, 55);
			menu1Btn.setBorderPainted(false);
			menu1Btn.setToolTipText("친구");
			menu1Btn.setHorizontalTextPosition(JButton.CENTER);
			tabPanel.add(menu1Btn);

			JLabel FriendLabel = new JLabel("친구"); 
			FriendLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			FriendLabel.setBounds(5, 23, 76, 34);
			contentPanel.add(FriendLabel);
			
			//사람 메뉴 누르면 친구목록 창으로 
			class FriendAction extends MouseAdapter {
				public void mouseClicked(MouseEvent e) {
					chatPanel.setVisible(false);
					contentPanel.setVisible(true); // 친구목록
				}
			}
			FriendAction friendAction = new FriendAction();
			menu1Btn.addMouseListener(friendAction);

			//메뉴 2 - 채팅목록 창
			ImageIcon menu2 = new ImageIcon("src/Menu2.png");
			JButton menu2Btn = new JButton(menu2);
			menu2Btn.setBounds(10, 90, 55, 55);
			menu2Btn.setBorderPainted(false);
			menu2Btn.setToolTipText("채팅");
			menu2Btn.setHorizontalTextPosition(JButton.CENTER);
			tabPanel.add(menu2Btn);
		
			//채팅 메뉴 누르면 채팅목록 창으로
			class ChatAction extends MouseAdapter { 
				public void mouseClicked(MouseEvent e) {
					contentPanel.setVisible(false);
					chatPanel.setVisible(true); // 채팅목록
				}
			}
			ChatAction chatAction = new ChatAction();
			menu2Btn.addMouseListener(chatAction);

			int userIndex = 0;
			JTextArea textArea = new JTextArea();
			JScrollPane scroll = new JScrollPane(textArea);
			scroll.setBounds(80, 0, 300, 485);
			//this.add(scroll);
			while (userIndex < userVec.size()) {
				JLabel userNameLabel = new JLabel(userVec.get(userIndex).toString());
				System.out.println(userVec.get(userIndex).toString());
				userNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
				userNameLabel.setBounds(55, userIndex*50 + 75, 68, 15);
				userNameLabel.setOpaque(true);
				userNameLabel.setBackground(Color.WHITE);
				//textArea.add(userNameLabel);
				contentPanel.add(userNameLabel);

				userPrfPanel = new JPanel(); // 프로필 사진 패널
				userPrfPanel.setBackground(Color.WHITE);
				userPrfPanel.setBounds(5, userIndex*50+60, 35, 35);
				contentPanel.add(userPrfPanel);

				String imgUrl = imgVec.get(userIndex).toString();
				System.out.println(imgUrl);

				// 친구목록 프로필 이미지
				ImageIcon icon = new ImageIcon(imgUrl);
				Image img = icon.getImage();
				Image changeImg = img.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
				ImageIcon changeIcon = new ImageIcon(changeImg);
				userPrfLabel = new JLabel(changeIcon);
				userPrfPanel.add(userPrfLabel);
				
				//상태메세지
				userMsgLabel = new JLabel(msgVec.get(userIndex).toString());
				System.out.println(msgVec.get(userIndex).toString());
				userMsgLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
								
				userMsgPanel = new JPanel();
				userMsgPanel.setBackground(Color.WHITE);
				userMsgPanel.setBounds(120, userIndex*50+71, 30, 30);
				contentPanel.add(userMsgPanel);
				userMsgPanel.add(userMsgLabel);
				
				userIndex++;
				
			}
			
	
			System.out.println("mysql db 연결 성공");

		} catch(SQLException error) {
			System.out.println(error);
            System.out.println("DB 접속 오류");
		}
	}
}