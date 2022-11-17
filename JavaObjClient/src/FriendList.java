import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	private JPanel userPrfPanel; // 내 프로필 이미지 패널
	private JLabel userPrfLabel; // 내 프로필 이미지 라벨
	
	private JPanel userMsgPanel; // 상태메세지 패널
	private JLabel userMsgLabel; // 상태메세지 라벨
	private Object changeProfileBtn; //프로필 변경 버튼
	private JLabel userNameLabel; // 친구 이름 라벨
	private JPanel contentPane_2; // 1:1 채팅방 창

	private Frame frame;
	private FileDialog fd;
	

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

			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);

			JPanel contentPane_1 = new JPanel();
			contentPane_1.setBackground(Color.WHITE);
			contentPane_1.setLayout(null);
			contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane_1.setBounds(80, 0, 300, 485);
			contentPane.add(contentPane_1);

			// 친구목록 창
			JPanel userListPanel = new JPanel();
			userListPanel.setBackground(Color.WHITE);
			userListPanel.setPreferredSize(new Dimension(287, 1000));
			userListPanel.setLayout(null);

			// 왼쪽 탭바
			tabPanel.setBounds(0, 0, 60, 485);
			contentPane.add(tabPanel);

			JScrollPane scrollPane = new JScrollPane(userListPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
   	        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
			scrollPane.setBounds(12, 150, 287, 300);		  
			scrollPane.setViewportView(userListPanel);
			contentPane_1.add(scrollPane);
			
			//채팅 창
			chatPanel = new JPanel();
			chatPanel.setBackground(Color.WHITE);
			chatPanel.setLayout(null);
			//chatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			chatPanel.setBounds(80, 0, 300, 485);
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
			FriendLabel.setBounds(23, 23, 76, 34);
			contentPane_1.add(FriendLabel);
			
			//프로필 변경 버튼
			JButton changeProfileBtn = new JButton("프로필 변경");
		    changeProfileBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		    changeProfileBtn.setContentAreaFilled(false);
		    changeProfileBtn.setFocusPainted(false);
		    changeProfileBtn.setBounds(20, 120, 100, 20);
		    contentPane_1.add(changeProfileBtn);
		    
			//사람 메뉴 누르면 친구목록 창으로 
			class FriendAction extends MouseAdapter {
				public void mouseClicked(MouseEvent e) {
					chatPanel.setVisible(false);
					contentPane_1.setVisible(true); // 친구목록
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
					contentPane_1.setVisible(false);
					chatPanel.setVisible(true); // 채팅목록
				}
			}
			ChatAction chatAction = new ChatAction();
			menu2Btn.addMouseListener(chatAction);

			// 친구 이름 클릭 이벤트
			class ClickUserLabel extends MouseAdapter {
				public void mouseClicked(MouseEvent e) {
					System.out.println("친구 이름 클릭");
					new OneToOneChat();
				}
			}

			int userIndex = 0;
			JTextArea textArea = new JTextArea();
			JScrollPane scroll = new JScrollPane(textArea);
			scroll.setBounds(80, 0, 300, 485);
			
			int j = 0;
			while(userIndex < userVec.size()) {
				if (userVec.get(userIndex).toString().equals(UserName)) { // 나 자신이면
					JLabel mySelf = new JLabel(userVec.get(userIndex).toString());
					System.out.println(userVec.get(userIndex).toString());
					mySelf.setFont(new Font("맑은 고딕", Font.BOLD, 15));
					mySelf.setBounds(65, userIndex*50 + 75, 68, 15);
					mySelf.setOpaque(true);
					mySelf.setBackground(Color.WHITE);
					contentPane_1.add(mySelf);

					// 자신의 프로필 이미지
					userPrfPanel = new JPanel(); // 프로필 사진 패널
					userPrfPanel.setBackground(Color.WHITE);
					userPrfPanel.setBounds(15, 63, 42, 42);
					contentPane_1.add(userPrfPanel);

					String imgUrl = imgVec.get(userIndex).toString();
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
					userMsgPanel.setBounds(160, 80, 30, 30);
					contentPane_1.add(userMsgPanel);
					userMsgPanel.add(userMsgLabel);
	
				} else { // 친구인 경우
					JLabel userNameLabel = new JLabel(userVec.get(userIndex).toString());
					System.out.println(userVec.get(userIndex).toString());
					userNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
					userNameLabel.setBounds(55, j*50 + 30, 68, 15);
					userNameLabel.setOpaque(true);
					userNameLabel.setBackground(Color.WHITE);
					userListPanel.add(userNameLabel);

					// 친구 프로필 이미지
					userPrfPanel = new JPanel(); // 프로필 사진 패널
					userPrfPanel.setBackground(Color.WHITE);
					userPrfPanel.setBounds(10, j*50 + 10, 41, 41);
					userListPanel.add(userPrfPanel);

					String imgUrl = imgVec.get(userIndex).toString();
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
					userMsgPanel.setBounds(160, userIndex*50 + 30, 30, 30);
					userListPanel.add(userMsgPanel);
					userMsgPanel.add(userMsgLabel);

					// 친구 이름 누르면 해당 친구와의 1:1 채팅창 열림
					ClickUserLabel clickUserLabel = new ClickUserLabel();
					userNameLabel.addMouseListener(clickUserLabel);
				}
				userIndex++;
				j++;
			}			
	
			System.out.println("mysql db 연결 성공");
			SendMessage(port_no);

		} catch(SQLException error) {
			System.out.println(error);
            System.out.println("DB 접속 오류");
		}

		class RefreshProfile extends MouseAdapter { // 선택한 프사를 보내기

	         public void mouseClicked(MouseEvent e) { // 단톡방 초대하기
	            refreshPanel();
	         }

		}
		
		RefreshProfile refreshProfile = new RefreshProfile();
		
		class SelectProfile extends MouseAdapter {
			public void mouseClicked(MouseEvent e) { // 단톡방 초대하기
		            
		        repaint();

		        if (e.getSource() == changeProfileBtn) {
		            frame = new Frame("이미지첨부");
		            fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
		            fd.setVisible(true);
		          
		            if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
		                ChatMsg obcm = new ChatMsg(username, "700", "IMG"); // 코드 700과 함께 선택한 프로필 이미지 보냄
		                ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
		                
		                obcm.img = img;
		                  
		                refreshMyProfile(fd.getDirectory() + fd.getFile());
		                //SendObject(obcm);

		            }
		        }
		    }
		}	
		
		SelectProfile selectProfile = new SelectProfile();
		((Component) changeProfileBtn).addMouseListener(selectProfile);
	}

	
	public void refreshMyProfile(String img) {
		setMyImg(img);
	}
		

	public void setMyImg(String img) {
		userPrfPanel.revalidate();
		userPrfPanel.repaint();
		userPrfLabel.revalidate();
		userPrfLabel.repaint();
		     
		userPrfPanel.remove(userPrfLabel);
		revalidate();
		repaint();

		ImageIcon icon1 = new ImageIcon(img);
		Image img2 = icon1.getImage();
		Image changeImg1 = img2.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
		ImageIcon changeIcon1 = new ImageIcon(changeImg1);

		userPrfLabel.setIcon(changeIcon1);
		userPrfPanel.add(userPrfLabel);
	}
		
	public void refreshPanel() {
		int userIndex = 0;
		for (int i = 0; i < userVec.size(); i++) {

		    String eachUserName = userVec.get(userIndex).toString();
		        
		    if (userVec.get(userIndex).toString().equals(eachUserName))
		        continue;

		} // 나자신의 프로필 사진을 갱신
		this.revalidate();
		this.repaint();
	}

	// Server Message를 수신해서 화면에 표시
	/*class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					
					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					
					//시간출력
					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
					Date time = new Date();
					String time1 = format.format(time);
					
					
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s] %s", cm.getId(), cm.getData());
					} else
						continue;
					
					//msg = msg + "\n" + time1 + "\n";
					
					switch (cm.getCode()) {
					case "200": // chat message
						if(UserName.equals(cm.getId())) //본인 메세지 오른쪽
							AppendMyText(time1 + msg);
						else if("SERVER".equals(cm.getId()))
							AppendServerText(msg);
						else
							AppendText(msg);
						break;
					case "300": // Image 첨부
						AppendText("[" + cm.getId() + "]");
						AppendImage(cm.img);
						break;
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}*/

	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				msg = txtInput.getText();
				SendMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			// dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
			ChatMsg obcm = new ChatMsg(UserName, "200", msg);
			oos.writeObject(obcm);
			System.out.println("서버로 메시지 보냄");
		} catch (IOException e) {
			// AppendText("dos.write() error");
			//AppendText("oos.writeObject() error");
			try {
//				dos.close();
//				dis.close();
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			//AppendText("SendObject Error");
		}
	}

	// 화면에 출력
	/*public void AppendText(String msg) {
		// textArea.append(msg + "\n");
		//AppendIcon(icon1);
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		
		//화면 왼쪽에 출력
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_LEFT);
		doc.setParagraphAttributes(len, 1, attributeSet, false);
		
		textArea.replaceSelection(msg + "\n");
	}*/

}
