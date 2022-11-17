import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OneToOneChat extends JFrame {
    private JPanel contentPane_2 = new JPanel();

    public OneToOneChat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(400, 650);
        setBounds(150, 150, 386, 512);
        setLayout(null);
        setTitle("1:1 채팅방");
        contentPane_2.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane_2);
        setVisible(true);
    }
    
}
