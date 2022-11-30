package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import server.User;

public class ChatPanel extends JPanel {
	
	private String name = LoginPanel.userID;
	
	private ChatFrame cf;
	private JLabel profile;
	private JTextPane textPaneChat;
	private TextField txtWrite;
	private JButton sendBtn;
	private JScrollPane chatScroll;
	public JButton createRoomBtn;

	private String roomTitle;
	private CommandController controller = CommandController.getController();
	
	public ChatPanel(ChatFrame cf,String roomTitle) {
		this.roomTitle = roomTitle;
		this.cf = cf;
		System.out.println("roomTitle = "+roomTitle);
		setLayout(null);
		setSize(400,600);
		setBackground(new Color(155, 187, 212));
		
		profile = new JLabel(roomTitle);
		profile.setBounds(0, 0, 565, 40);
		profile.setFont(new Font("���� ���", Font.PLAIN, 20));
		profile.setOpaque(true);
		profile.setForeground(Color.white);
		profile.setBackground(new Color(0,0,0,122));
		cf.add(profile);
		
		//ä�� ���̴� �κ�
		textPaneChat = new JTextPane();
		textPaneChat.setEditable(false);
		textPaneChat.setBounds(0, 40, 395, 400);
		textPaneChat.setBackground(new Color(155, 187, 212));
		textPaneChat.setFont(new Font("���� ���", Font.PLAIN, 20));
		//textPaneChat.setHorizontalAlignment(textPaneChat.RIGHT_ALIGNMENT);
		//cf.add(textPaneChat);
		
		//ä�� ��ũ�� 
		chatScroll = new JScrollPane(textPaneChat);
		chatScroll.setBounds(0, 40, 395, 400);
		cf.add(chatScroll);
	
		//�޼��� �Է� �κ�
		txtWrite = new TextField();
		txtWrite.setBounds(0, 440, 320, 70);
		txtWrite.setFont(new Font("���� ���", Font.PLAIN, 20));
	    cf.add(txtWrite);

		sendBtn = new JButton("����");
		sendBtn.setFont(new Font("���� ���", Font.PLAIN, 15));
		sendBtn.setBackground(new Color(247, 230, 0));
		sendBtn.setBounds(321, 440, 75, 69);
		cf.add(sendBtn);
		
	
		start(); //�׼��̺�Ʈ ���� �޼ҵ�
		
		//RecieveMassage(); //�����κ��� �޽����� �����ϴ� ������
	}
	
	public void start() { // �׼��̺�Ʈ ���� �޼ҵ�
		Myaction action = new Myaction(this.textPaneChat);
		sendBtn.addActionListener(action); // ����Ŭ������ �׼� �����ʸ� ��ӹ��� Ŭ������
		txtWrite.addActionListener(action);
	}

	class Myaction implements ActionListener // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
	{
		public Myaction(JTextPane textPaneChat) {

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
			if (e.getSource() == sendBtn || e.getSource() == txtWrite) 
			{
				String msg = null;
				//msg = String.format("%s//%s//[%s] %s\n", User.SIGNAL_NOMAL_MSG, roomTitle, name, txtWrite.getText());
				msg = String.format("%s//%s//[%s] %s//%s\n", User.SIGNAL_NOMAL_MSG, roomTitle, name, txtWrite.getText(), name);

				String[] array = msg.split("//");
				/*
				 * if(name.equals(LoginPanel.userID)) {//������ ������ �޼����� �����ʿ� ���
				 * controller.AppendMyText(roomTitle, msg); } else controller.send_Message(msg);
				 */
					
				//ä���� ģ ä�ù� �̸�, ��������, �޽����� ������ ����
				controller.send_Message(msg);
				
				if(array[2].substring(1,5).equals(LoginPanel.userID)) {
					 msg.trim(); 
					 System.out.println("���������޼���");
					 int len = textPaneChat.getDocument().getLength();
					 textPaneChat.setCaretPosition(len);

					 Document doc = textPaneChat.getDocument(); SimpleAttributeSet
					 attributeSet = new SimpleAttributeSet();
					 StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_RIGHT);
					 StyleConstants.setForeground(attributeSet, Color.black);
					 StyleConstants.setBackground(attributeSet, Color.yellow);
					 ((StyledDocument) doc).setParagraphAttributes(len, 1, attributeSet, false);
					 
					 textPaneChat.replaceSelection((msg) + "\n");
				}
				
				//send_Message(msg);
				txtWrite.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
				txtWrite.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��	
			}

		}
		/*
		 * public void AppendMyText(String msg) { //������ ������ �޼����� �����ʿ� ��� msg =
		 * msg.trim(); int len = textPaneChat.getStyledDocument().getLength();
		 * textPaneChat.setCaretPosition(len);
		 * 
		 * StyledDocument doc = textPaneChat.getStyledDocument(); SimpleAttributeSet
		 * attributeSet = new SimpleAttributeSet();
		 * StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_RIGHT);
		 * StyleConstants.setForeground(attributeSet, Color.black);
		 * StyleConstants.setBackground(attributeSet, Color.yellow);
		 * doc.setParagraphAttributes(len, 1, attributeSet, false);
		 * 
		 * textPaneChat.replaceSelection(msg + "\n");
		 * 
		 * }
		 */

	}
	
	public JTextPane getTextPaneChat() {
		return this.textPaneChat;
	}
	
	public void setTextPaneChat(JTextPane textPaneChat){
		this.textPaneChat = textPaneChat;
		
	}

}
