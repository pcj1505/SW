package software;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static software.login.font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class post extends JFrame {
	db_connection a = new db_connection(); // 데베 객체 생성
	Font font1 = new Font("맑은 고딕", Font.BOLD, 20);

	public post(String number) {
		setSize(379, 550);
		setLocationRelativeTo(null);
		setLayout(null);
		getContentPane().setBackground(new Color(204, 255, 255));
		a.show_post(number);
		setTitle(a.post_list[0]);
		JLabel one = new JLabel(a.post_list[0]); // 해당 글의 제목 받아오기
		one.setFont(font1);

		JPanel area = new JPanel(); // 패널 생성
		area.setLayout(null);
		JLabel two = new JLabel(a.post_list[1]); // 해당 글의 내용 받아오기
		two.setFont(font);
		JLabel three = new JLabel("작성자 " + a.post_list[2]); // 작성자
		three.setFont(font);
		one.setBounds(8, 15, 300, 30);
		two.setBounds(10, -100, 300, 300);
		three.setBounds(265, 25, 100, 30);
		JButton close = new JButton("목록"); // 게시글 리스트로 돌아가기 위한 목록 버튼
		close.setBackground(new Color(204, 255, 255));
		close.setFont(font);
		close.setBounds(270, 360, 80, 30);
		add(close);
		add(one);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1); // 테두리 설정
		area.setBorder(lineBorder); // 내용 테두리 설정
		area.add(two);
		area.setBounds(-1, 55, 365, 300);
		area.setBackground(Color.white);
		add(area);
		add(three);
		setVisible(true);

		// 버튼
		close.addActionListener(new ActionListener() { // 목록
			public void actionPerformed(ActionEvent e) {
				dispose(); // 창닫기
			}
		});
	}

}
