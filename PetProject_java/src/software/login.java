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
	static Font font = new Font("맑은 고딕", Font.BOLD, 12);
	db_connection a = new db_connection(); // 데베 객체 생성

	public login() {
		setLayout(null);
		setVisible(true);
		setSize(380, 550);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setLocationRelativeTo(null); // 가운데 화면 표
		// TODO Auto-generated constructor stub
		setTitle("로그인");
		getContentPane().setBackground(Color.white);
		// 이미지 받기
		JLabel img = new JLabel();
		ImageIcon dog = new ImageIcon("./image/1.jpg");
		Image dog1 = dog.getImage();
		Image changeImg = dog1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		dog = new ImageIcon(changeImg);
		img.setIcon(dog);
		add(img);
		img.setBounds(125, 20, 100, 100); // 이미치 위치

		JLabel user_id = new JLabel("아이디");
		add(user_id);
		user_id.setBounds(45, 180, 50, 50);
		user_id.setFont(font);

		JTextField ui = new JTextField(10); // id 받는 필드
		add(ui);
		ui.setBounds(105, 195, 150, 25);

		JLabel user_pw = new JLabel("비밀번호");
		add(user_pw);
		user_pw.setBounds(45, 220, 50, 50);
		user_pw.setFont(font);

		JPasswordField pr = new JPasswordField(10); // pw 받는 필드
		add(pr);
		pr.setBounds(105, 235, 150, 25);

		JButton logbtn = new JButton("로그인"); // 로그인 버튼
		logbtn.setBackground(new Color(204, 255, 255));
		add(logbtn);
		logbtn.setBounds(270, 195, 75, 65);

		JButton user_ = new JButton("회원등록"); // 회원등록 버튼 미구현
		user_.setBackground(new Color(204, 255, 255));
		add(user_);
		user_.setBounds(125, 280, 100, 30);
		// 버튼 디자인
		logbtn.setFont(font);
		user_.setFont(font);

		// 로그인 버튼 이벤트
		logbtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String id = ui.getText();
				String pwd = pr.getText();
				a.user_check(id, pwd);
				if (a.ischeck()) { // 로그인 성공시
					dispose(); // 로그인 창 닫기
				}
			}
		});

	}

	public static void main(String arg[]) {
		new login();
	}
}
