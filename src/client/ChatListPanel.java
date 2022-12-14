package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import server.User;

public class ChatListPanel extends JPanel {
	private JLabel Myprofile;
	private JLabel friendImg;
	private JLabel MyImg;
	private TextField searchFriend;

	private List<JLabel> chattingRoomList = new ArrayList<JLabel>();
	private List<JLabel> chatImg = new ArrayList<JLabel>();
	String chat = "src/img/basic.png";

	private CommandController controller = CommandController.getController();

	private MainFrame f;

	public ChatListPanel(MainFrame f) {
		this.f = f;
		setLayout(null);
		setSize(400, 600);
		setBackground(Color.white);
		
		// '채팅' 텍스트 붙이기
		JLabel chatLabel = new JLabel("채팅");
		chatLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		chatLabel.setBounds(23, 23, 76, 34);
		add(chatLabel);
				
		setchatList();
	}

	// 채팅방 리스트
	public void setchatList() {
		int i = 0;

		chattingRoomList = controller.getChatLabel();
		for (JLabel room : chattingRoomList)
			System.out.println("채팅방 리스트: " + room.getText().toString());

		for (JLabel room : chattingRoomList) {
			
			chatImg.add(new JLabel(new ImageIcon(chat)));
			chatImg.get(i).setBounds(10, 75 + (i * 55), 50, 50);
			chatImg.get(i).setOpaque(true);
			chatImg.get(i).setBackground(Color.white);
			add(chatImg.get(i));
			
			room.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			room.setOpaque(true);
			room.setBackground(Color.WHITE);
			room.setBounds(70, 75 + (i * 55), 200, 50);
			add(room);
			i++;

			room.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) { // 채팅목록을 누르면 기존 채팅창 띄우기
					if(e.getClickCount()==1) { 
						String roomTitle = room.getText();

						ChatFrame chattingroom = new ChatFrame(roomTitle);
						JTextPane temp = controller.getChattingRoomList().get(roomTitle);
						chattingroom.getChatPanel().getTextPaneChat().setText(temp.getText());
						controller.getChattingRoomList().remove(temp);
						controller.getChattingRoomList().put(roomTitle, chattingroom.getChatPanel().getTextPaneChat());
						
						chattingroom.repaint();
						
						String arr[] = temp.getText().split("\n\n");

						for (int i=0; i<arr.length; i++) {
						// 	chattingroom.repaint();

						// 	// 상대방이 보낸 메시지이면 왼쪽에 출력
							if (arr[i].charAt(0) =='[') {
								controller.append_Message(roomTitle, arr[i]);
						// 		chattingroom.getChatPanel().getTextPaneChat().setText(arr[i] + "\n\n");
							} else { // 내가 보낸 메시지이면 오른쪽에
								controller.append_My_Message(roomTitle, arr[i]);
						// 		chattingroom.getChatPanel().getTextPaneChat().setText(arr[i] + "\n\n");
							}
						}
						// controller.getChattingRoomList().remove(temp);
						// controller.getChattingRoomList().put(roomTitle, chattingroom.getChatPanel().getTextPaneChat());
						// chattingroom.repaint();
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});

		}
	}
}
