package client;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import server.User;

//원래 settingpanel....이었던것
public class ProfilePanel extends JPanel {

	private JLabel MyName;
	private JLabel MyImg;
	final String defaultImg = "src/img/basic.png";
	final String defaultMsg = "상태 메세지";
	private JLabel line;
	private JLabel profileMessage;
	private Frame frame;
	private FileDialog fd;
	
	private String myStateMessage;
	private String stateImage = "src/img/basic.png";

	//final String stateList[] = { "happy.png", "crying.png", "angry.png", "in-love.png", "laughing.png", "secret.png" };
	private List<ImageIcon> stateImg = new ArrayList<ImageIcon>();
	private List<JLabel> stateLabel = new ArrayList<JLabel>();
	private CommandController controller = CommandController.getController();

	public ProfilePanel(String user_id/* ,String myStateMessage */) {
		setLayout(null);
		setSize(400, 600);
		setBackground(Color.white);

		MyImg = new JLabel(new ImageIcon(defaultImg));
		MyImg.setBounds(90, 30, 100, 100);
		add(MyImg);
		
		/*MyImg.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("프사 바꾸기 버튼 선택");
		           if (e.getSource() == MyImg) {
		               frame = new Frame("이미지첨부");
		               fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
		               // frame.setVisible(true);
		               fd.setDirectory(".\\");
		               fd.setVisible(true);
		               System.out.println(fd.getDirectory() + fd.getFile());
		               if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
		            		stateImage = fd.getDirectory()+fd.getFile();
							MyImg.setIcon(new ImageIcon(stateImage));
							repaint();
							
							controller.send_Message(User.CODE_900+"//"+user_id+"//"+stateImage+"//"+myStateMessage);
							//프사 변경하고 친구목록창으로 돌아가면 friendpanel이 사라짐..
							
		               }
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

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});*/

		MyName = new JLabel(user_id);
		MyName.setBounds(90, 130, 100, 50);
		MyName.setHorizontalAlignment(JLabel.CENTER);
		MyName.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		MyName.setOpaque(false);
		add(MyName);

		profileMessage = new JLabel(defaultMsg);/* == myStateMessage */
		profileMessage.setBounds(90, 220, 100, 40);
		profileMessage.setHorizontalAlignment(JLabel.CENTER);
		profileMessage.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		profileMessage.setOpaque(true);
		profileMessage.setBackground(Color.WHITE);
		add(profileMessage);

		profileMessage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
					myStateMessage = JOptionPane.showInputDialog("상태메세지를 입력하세요");
					profileMessage.setText(myStateMessage);
					repaint();
					
					controller.send_Message(User.CODE_900+"//"+user_id+"//"+stateImage+"//"+myStateMessage);
					////////////////////////////////////////
					//       상태메세지 바꾸고 서버로 전송                       //
					////////////////////////////////////////
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

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		setStateImg(user_id);

	}
	
	public void setStateImg(String user_id) {	
		
		MyImg.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("프사 바꾸기 버튼 선택");
		           if (e.getSource() == MyImg) {
		               frame = new Frame("이미지첨부");
		               fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
		               // frame.setVisible(true);
		               fd.setDirectory(".\\");
		               fd.setVisible(true);
		               System.out.println(fd.getDirectory() + fd.getFile());
		               if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
		            		stateImage = fd.getDirectory()+fd.getFile();
							MyImg.setIcon(new ImageIcon(stateImage));
							repaint();
							
							controller.send_Message(User.CODE_900+"//"+user_id+"//"+stateImage+"//"+myStateMessage);
							//프사 변경하고 친구목록창으로 돌아가면 friendpanel이 사라짐
							
							
		               }
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

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
	

	}
}
