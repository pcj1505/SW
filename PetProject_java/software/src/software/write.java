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
	String bad_word[] = { "�ù�", "����", "����", "����", "��ŷ", "������", "����" };
	db_connection a = new db_connection(); // ���� ��ü ����

	public write() {
		setLayout(null);
		setBackground(new Color(204, 255, 255));
		JTextField title = new JTextField(10);
		title.setBounds(20, 30, 325, 30);
		JTextArea aaa = new JTextArea(10, 10);
		aaa.setLineWrap(true);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1); // �׵θ� ����

		aaa.setBorder(lineBorder);
		title.setBorder(lineBorder);
		aaa.setBounds(20, 100, 325, 300);

		JButton set = new JButton("���");
		set.setFont(font);
		set.setBackground(new Color(204, 255, 255));
		set.setBounds(135, 410, 100, 30);
		add(set);
		add(title);
		add(aaa);

		// ���� 10���ڱ����� �Է�
		KeyAdapter eventHandler = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				if (((JTextField) ke.getSource()).getText().length() >= 10) {
					ke.consume();
				}
			}
		};
		title.addKeyListener(eventHandler);

		set.addActionListener(new ActionListener() { // ȸ������
			public void actionPerformed(ActionEvent e) {
				boolean check = false;
				String tl = title.getText();
				String str = aaa.getText();
				// ������ ��˻�
				for (int i = 0; i < bad_word.length; i++) {
					if (tl.contains(bad_word[i])) {
						check = true;
					} else if (str.contains(bad_word[i])) {
						check = true;
					}
				}
				int c = str.getBytes().length;
				if (c > 300) {
					JOptionPane.showMessageDialog(null, "���� �� �ʰ�", "", JOptionPane.WARNING_MESSAGE);
				} else if (check) {
					JOptionPane.showMessageDialog(null, "�弳 ����", "", JOptionPane.WARNING_MESSAGE);
				} else {
					a.user_write(db_connection.user, tl, str);
					title.setText("");
					aaa.setText("");
				}
			}
		});

	}

}
