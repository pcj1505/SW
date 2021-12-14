package software;

import static software.login.font;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class write extends JPanel {
	String bad_word[] = { "시발", "병신", "ㅅㅂ", "ㅂㅅ", "뻐킹", "개새끼", "새끼" };
	db_connection a = new db_connection(); // 데베 객체 생성

	public write() {
		setLayout(null);
		setBackground(new Color(204, 255, 255));
		JTextField title = new JTextField(10);
		title.setBounds(20, 30, 325, 30);
		JTextArea aaa = new JTextArea(10, 10);
		aaa.setLineWrap(true);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1); // 테두리 설정

		aaa.setBorder(lineBorder);
		title.setBorder(lineBorder);
		aaa.setBounds(20, 100, 325, 300);

		JButton set = new JButton("등록");
		set.setFont(font);
		set.setBackground(new Color(204, 255, 255));
		set.setBounds(135, 410, 100, 30);
		add(set);
		add(title);
		add(aaa);

		// 제목 10글자까지만 입력
		KeyAdapter eventHandler = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if (((JTextField) ke.getSource()).getText().length() >= 10) {
					ke.consume();
				}
			}
		};
		title.addKeyListener(eventHandler);

		set.addActionListener(new ActionListener() { // 회원정보
			public void actionPerformed(ActionEvent e) {
				boolean check = false;
				String tl = title.getText();
				String str = aaa.getText();
				// 간단한 욕검사
				for (int i = 0; i < bad_word.length; i++) {
					if (tl.contains(bad_word[i])) {
						check = true;
					} else if (str.contains(bad_word[i])) {
						check = true;
					}
				}
				int c = str.getBytes().length;
				if (c > 300) {
					JOptionPane.showMessageDialog(null, "글자 수 초과", "", JOptionPane.WARNING_MESSAGE);
				} else if (check) {
					JOptionPane.showMessageDialog(null, "욕설 포함", "", JOptionPane.WARNING_MESSAGE);
				} else {
					a.user_write(db_connection.user, tl, str);
					title.setText("");
					aaa.setText("");
				}
			}
		});

	}

}
