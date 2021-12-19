package software;

import static software.login.font;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class write extends JPanel {
	// 욕설 목록
	String bad_word[] = { "시발", "병신", "ㅅㅂ", "ㅂㅅ", "뻐킹", "개새끼", "ㄱㅅㄲ" };
	db_connection a = new db_connection(); // 데베 객체 생성

	public write() {
		setLayout(null);
		setBackground(new Color(204, 255, 255));
		JTextField title = new JTextField(10); // 제목
		title.setBounds(20, 30, 325, 30);
		JTextArea aaa = new JTextArea(10, 10); // 내용 쓰는 곳
		aaa.setLineWrap(true);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1); // 테두리 설정

		aaa.setBorder(lineBorder); // 테두리 설정
		title.setBorder(lineBorder);
		aaa.setBounds(20, 100, 325, 300);

		JCheckBox gonji = new JCheckBox("공지", false); // 공지로 쓸지 체크란
		gonji.setFont(font);
		gonji.setBackground(new Color(204, 255, 255));
		gonji.setBounds(235, 410, 60, 30);
		JButton set = new JButton("등록"); // 글 등록 버튼
		set.setFont(font);
		set.setBackground(new Color(204, 255, 255));
		set.setBounds(135, 410, 100, 30);
		add(set);
		add(gonji);
		add(title);
		add(aaa);

		// 제목 10글자까지만 입력
		KeyAdapter eventHandler = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) { // 쓰는 글자들을 받아와서 체크
				if (((JTextField) ke.getSource()).getText().length() >= 10) {
					ke.consume();
				}
			}
		};
		title.addKeyListener(eventHandler);

		gonji.addItemListener(new ItemListener() {
			// 로그인한 아이디가 kdi가 아니면 공지를 체크해도 글을 등록할 수 없음
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (db_connection.user.equals("kdi")) {
						set.setEnabled(true);
					} else {
						set.setEnabled(false);
					}
				} else
					set.setEnabled(true);
			}

		});

		set.addActionListener(new ActionListener() { // 등록하기
			public void actionPerformed(ActionEvent e) {
				boolean check = false; // 욕 확인을 위한 boolean 변수
				String tl = title.getText(); // 제목의 문자를 가져옴
				String str = aaa.getText();// 내용의 문자를 가져옴
				// 간단한 욕검사
				for (int i = 0; i < bad_word.length; i++) {
					if (tl.contains(bad_word[i])) { // 욕설 리스트에 속하는지
						check = true;
					} else if (str.contains(bad_word[i])) {
						check = true;
					}
				}
				int c = str.getBytes().length; // 글자 수 제한 300 바이트
				if (c > 300) {
					JOptionPane.showMessageDialog(null, "글자 수 초과", "", JOptionPane.WARNING_MESSAGE);
				} else if (check) {
					JOptionPane.showMessageDialog(null, "욕설 포함", "", JOptionPane.WARNING_MESSAGE);
				} else {
					if (gonji.isSelected()) { // 공지 체크 되어있는 경우
						a.user_write(db_connection.user, tl, str, gonji.getText());
						title.setText("");
						aaa.setText("");
					} else { // 아닌 경우 일반 글
						a.user_write(db_connection.user, tl, str, null);
						title.setText("");
						aaa.setText("");
					}
				}
			}
		});

	}

}
