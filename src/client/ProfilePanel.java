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

//���� settingpanel....�̾�����
public class ProfilePanel extends JPanel {

	private JLabel MyName;
	private JLabel MyImg;
	final String defaultImg = "src/img/basic.png";
	final String defaultMsg = "���� �޼���";
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
				System.out.println("���� �ٲٱ� ��ư ����");
		           if (e.getSource() == MyImg) {
		               frame = new Frame("�̹���÷��");
		               fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
		               // frame.setVisible(true);
		               fd.setDirectory(".\\");
		               fd.setVisible(true);
		               System.out.println(fd.getDirectory() + fd.getFile());
		               if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
		            		stateImage = fd.getDirectory()+fd.getFile();
							MyImg.setIcon(new ImageIcon(stateImage));
							repaint();
							
							controller.send_Message(User.CODE_900+"//"+user_id+"//"+stateImage+"//"+myStateMessage);
							//���� �����ϰ� ģ�����â���� ���ư��� friendpanel�� �����..
							
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
		MyName.setFont(new Font("���� ���", Font.BOLD, 20));
		MyName.setOpaque(false);
		add(MyName);

		profileMessage = new JLabel(defaultMsg);/* == myStateMessage */
		profileMessage.setBounds(90, 220, 100, 40);
		profileMessage.setHorizontalAlignment(JLabel.CENTER);
		profileMessage.setFont(new Font("���� ���", Font.PLAIN, 18));
		profileMessage.setOpaque(true);
		profileMessage.setBackground(Color.WHITE);
		add(profileMessage);

		profileMessage.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
					myStateMessage = JOptionPane.showInputDialog("���¸޼����� �Է��ϼ���");
					profileMessage.setText(myStateMessage);
					repaint();
					
					controller.send_Message(User.CODE_900+"//"+user_id+"//"+stateImage+"//"+myStateMessage);
					////////////////////////////////////////
					//       ���¸޼��� �ٲٰ� ������ ����                       //
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
				System.out.println("���� �ٲٱ� ��ư ����");
		           if (e.getSource() == MyImg) {
		               frame = new Frame("�̹���÷��");
		               fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
		               // frame.setVisible(true);
		               fd.setDirectory(".\\");
		               fd.setVisible(true);
		               System.out.println(fd.getDirectory() + fd.getFile());
		               if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
		            		stateImage = fd.getDirectory()+fd.getFile();
							MyImg.setIcon(new ImageIcon(stateImage));
							repaint();
							
							controller.send_Message(User.CODE_900+"//"+user_id+"//"+stateImage+"//"+myStateMessage);
							//���� �����ϰ� ģ�����â���� ���ư��� friendpanel�� �����
							
							
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
