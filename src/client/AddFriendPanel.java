package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.User;

public class AddFriendPanel extends JPanel {

	private AddRoomFrame f;
	private JButton okBtn;
	ImageIcon ok = new ImageIcon("src/img/ok.png"); //친구 선택 후 확인 버튼
	private CommandController controller = CommandController.getController();
	private List<JLabel> friendList = new ArrayList<JLabel>();

	private List<String> AddFriendName = new ArrayList<String>();
	
	private String myId;
	public AddFriendPanel(AddRoomFrame f, String userId) {
		this.f = f;
		this.myId = userId;
		
		setLayout(null);
		setSize(400, 600);
		setBackground(Color.white);
		
		JLabel list = new JLabel("대화 상대 선택");
		list.setHorizontalAlignment(JLabel.LEFT); 
		list.setBounds(25, 15, 340, 50);
		list.setBackground(new Color(247, 230, 0));
		list.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		list.setOpaque(true);
		f.add(list);

		addList();

		okBtn = new JButton("확인");
		okBtn.setBounds(150, 500, 80, 45);
		okBtn.setBorderPainted(false); 
		okBtn.setEnabled(true);
		okBtn.setDisabledIcon(ok);
		okBtn.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		okBtn.setBackground(new Color(247, 230, 0));
		f.add(okBtn);

		okBtn.addActionListener(new ActionListener() {
			int j=1;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//선택된 친구 리스트
				String signal = User.CODE_200+"//"+myId;
				
				System.out.println("친구");
				for(String name:AddFriendName) {
					signal += "//"+name;
					System.out.println(j + name);
					j++;
				}
				controller.send_Message(signal);
				
				f.dispose();

			}
		});
	}

	public void addList() {
		friendList.clear();
		
		controller.send_Message(User.CODE_600);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		friendList = controller.getUserLabel();

		for (int i = 0,j=0; i < friendList.size(); i++,j++) {
			if(!friendList.get(i).getText().equals(myId)) { //본인은 제외하고 친구출력
				
				friendList.get(i).setBounds(25, 75 + (j * 51), 340, 50);
				friendList.get(i).setFont(new Font("맑은 고딕", Font.PLAIN, 17));
				friendList.get(i).setBackground(Color.WHITE);
				friendList.get(i).setOpaque(true);
				f.add(friendList.get(i));
				System.out.println("**" + friendList.get(i).getText());

				JLabel a = friendList.get(i);

				friendList.get(i).addMouseListener(new MouseListener() {
					boolean addFriend = false;
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						//boolean b = false;
						if (addFriend == false) {
							addFriend = true;
							AddFriendName.add(a.getText());
							System.out.println("친구 선택 ? : " + addFriend);
							a.setBackground(new Color(247, 230, 0));
						}

						else if (addFriend == true) {
							addFriend = false;
							AddFriendName.remove(a.getText());
							System.out.println("친구 선택 ? : " + addFriend);
							a.setBackground(Color.WHITE);
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
			else{
				--j;
			}
		}
		
	}

}
