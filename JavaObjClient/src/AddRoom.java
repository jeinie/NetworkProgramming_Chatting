import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import MySQL.DAO;

import MySQL.DAO;

public class AddRoom extends JFrame implements ActionListener, MouseListener{

	// MySQL
	static final String DB_URL = "jdbc:mysql://localhost:3306/network_db";
	static final String USER = "root";
	static final String PASSWORD = "1234";
	static final String QUERY = "SELECT * FROM users"; // ������ ����  

	//private JTable table;
	private JPanel contentPane;
	private DAO dao;
	private String[][] data;
	private String[] count; //ģ�� ��
	private JCheckBox[] checkBox; //���ùڽ�
	
	ImageIcon ok_before = new ImageIcon("src/img/ok_before.png"); //ģ�� ���� �� Ȯ�� ��ư
	ImageIcon ok = new ImageIcon("src/img/ok.png"); //ģ�� ���� �� Ȯ�� ��ư
	ImageIcon cancel = new ImageIcon("src/img/cancel.png"); //��ҹ�ư
	
	@SuppressWarnings("serial")
	public AddRoom(String username, String ip_addr, String port_no) {
		setTitle("�����");
		setSize(380,600);
		setLocationRelativeTo(null); 
		
		setBounds(100, 100, 386, 512);
	
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		
		/*
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 65, 284, 379);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		*/
		
		
		JLabel selectUsers = new JLabel("��ȭ��� ����");
		selectUsers.setFont(new Font("���� ���", Font.BOLD, 18));
		selectUsers.setBounds(27, 23, 114, 30);
		contentPane.add(selectUsers);
	
		data = DAO.getFriends(username);
		//count = new String[] {"ģ��" + data.length };
		
		System.out.println(Arrays.toString(count));
	
		checkBox = new JCheckBox[data.length];
		
		// ���� ������ ģ�� ��� ����
		for (int i=0; i<data.length; i++) {
			//System.out.println(data[i].toString());
			System.out.println(Arrays.deepToString(data[i]));
			String friends = Arrays.deepToString(data[i]);
			JLabel friend = new JLabel(friends); 
			friend.setBounds(70, 50+i*60, 300, 100);
			friend.setFont(new Font("���� ���", Font.BOLD, 15));
			friend.setOpaque(true);
			friend.setBackground(Color.WHITE);
			//contentPane.add(friend);
			checkBox[i] = new JCheckBox(friends);
			checkBox[i].setBorderPainted(false);
			checkBox[i].setBounds(50, 60+i*60, 300, 50);
			checkBox[i].setBackground(Color.WHITE);
			checkBox[i].setFont(new Font("���� ���", Font.BOLD, 15));
			contentPane.add(checkBox[i]);
			
		}
	
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JButton okBtn = new JButton(ok_before);
		okBtn.setBounds(150, 400, 80, 40);
		okBtn.setBorderPainted(false); 
		okBtn.setEnabled(false);
		contentPane.add(okBtn);
		
		JButton cancelBtn = new JButton(cancel);
		cancelBtn.setBounds(240, 400, 80, 40);
		okBtn.setBorderPainted(false); 
		okBtn.setEnabled(false);
		contentPane.add(cancelBtn);
		
		//ģ���� �����ϸ� 
		for (int i=0; i<data.length; i++) {
			if(checkBox[i].isSelected()) {
				
			}
		
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
