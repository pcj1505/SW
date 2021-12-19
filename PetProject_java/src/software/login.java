package software;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//login
@SuppressWarnings("serial")
public class login extends JFrame {
	static Font font = new Font("���� ���", Font.BOLD, 12);
	db_connection a = new db_connection(); // ���� ��ü ����

	public login() {
		setLayout(null);
		setVisible(true);
		setSize(380, 550);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setLocationRelativeTo(null); // ��� ȭ�� ǥ
		// TODO Auto-generated constructor stub
		setTitle("�α���");
		getContentPane().setBackground(Color.white);
		// �̹��� �ޱ�
		JLabel img = new JLabel();
		ImageIcon dog = new ImageIcon("./image/1.jpg");
		Image dog1 = dog.getImage();
		Image changeImg = dog1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		dog = new ImageIcon(changeImg);
		img.setIcon(dog);
		add(img);
		img.setBounds(125, 20, 100, 100); // �̹�ġ ��ġ

		JLabel user_id = new JLabel("���̵�");
		add(user_id);
		user_id.setBounds(45, 180, 50, 50);
		user_id.setFont(font);

		JTextField ui = new JTextField(10); // id �޴� �ʵ�
		add(ui);
		ui.setBounds(105, 195, 150, 25);

		JLabel user_pw = new JLabel("��й�ȣ");
		add(user_pw);
		user_pw.setBounds(45, 220, 50, 50);
		user_pw.setFont(font);

		JPasswordField pr = new JPasswordField(10); // pw �޴� �ʵ�
		add(pr);
		pr.setBounds(105, 235, 150, 25);

		JButton logbtn = new JButton("�α���"); // �α��� ��ư
		logbtn.setBackground(new Color(204, 255, 255));
		add(logbtn);
		logbtn.setBounds(270, 195, 75, 65);

		JButton user_ = new JButton("ȸ�����"); // ȸ����� ��ư �̱���
		user_.setBackground(new Color(204, 255, 255));
		add(user_);
		user_.setBounds(125, 280, 100, 30);
		// ��ư ������
		logbtn.setFont(font);
		user_.setFont(font);

		// �α��� ��ư �̺�Ʈ
		logbtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String id = ui.getText();
				String pwd = pr.getText();
				a.user_check(id, pwd);
				if (a.ischeck()) { // �α��� ������
					dispose(); // �α��� â �ݱ�
				}
			}
		});

	}

	public static void main(String arg[]) {
		new login();
	}
}
