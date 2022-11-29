package client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class MainFrame extends JFrame {

	public StartPanel startPanel;
	public CommandController controller = CommandController.getController();

	//�޴� 1 - ģ����� â
	private JButton friendsBtn;
	

	//�޴� 2 - ä�ø�� â
	private JButton chatBtn;
	
	public MainFrame(String userID/*, String myStateMessage*/) {
		setTitle("�����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 600);
		setResizable(false);
		
		/*setUndecorated(true);
		setBackground(new Color(0,0,0,122));*/
		setLayout(null);
		
		startPanel = new StartPanel(this,userID/*,myStateMessage*/);
		
		Dimension frameSize = this.getSize(); // ������ ������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������

		this.setLocation((screenSize.width - frameSize.width), (screenSize.height - screenSize.height)); // ȭ�� ���� ���

		Container c = getContentPane();
		c.add(startPanel);
		
		setVisible(true);
		
		controller.saveMainFrame(userID,this);
	}

	public StartPanel getStartPanel() {
		return startPanel;
	}

	public void setStartPanel(StartPanel startPanel) {
		this.startPanel = startPanel;
	}
	
	
}
