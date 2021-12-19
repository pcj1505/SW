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
	// �弳 ���
	String bad_word[] = { "�ù�", "����", "����", "����", "��ŷ", "������", "������" };
	db_connection a = new db_connection(); // ���� ��ü ����

	public write() {
		setLayout(null);
		setBackground(new Color(204, 255, 255));
		JTextField title = new JTextField(10); // ����
		title.setBounds(20, 30, 325, 30);
		JTextArea aaa = new JTextArea(10, 10); // ���� ���� ��
		aaa.setLineWrap(true);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1); // �׵θ� ����

		aaa.setBorder(lineBorder); // �׵θ� ����
		title.setBorder(lineBorder);
		aaa.setBounds(20, 100, 325, 300);

		JCheckBox gonji = new JCheckBox("����", false); // ������ ���� üũ��
		gonji.setFont(font);
		gonji.setBackground(new Color(204, 255, 255));
		gonji.setBounds(235, 410, 60, 30);
		JButton set = new JButton("���"); // �� ��� ��ư
		set.setFont(font);
		set.setBackground(new Color(204, 255, 255));
		set.setBounds(135, 410, 100, 30);
		add(set);
		add(gonji);
		add(title);
		add(aaa);

		// ���� 10���ڱ����� �Է�
		KeyAdapter eventHandler = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) { // ���� ���ڵ��� �޾ƿͼ� üũ
				if (((JTextField) ke.getSource()).getText().length() >= 10) {
					ke.consume();
				}
			}
		};
		title.addKeyListener(eventHandler);

		gonji.addItemListener(new ItemListener() {
			// �α����� ���̵� kdi�� �ƴϸ� ������ üũ�ص� ���� ����� �� ����
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

		set.addActionListener(new ActionListener() { // ����ϱ�
			public void actionPerformed(ActionEvent e) {
				boolean check = false; // �� Ȯ���� ���� boolean ����
				String tl = title.getText(); // ������ ���ڸ� ������
				String str = aaa.getText();// ������ ���ڸ� ������
				// ������ ��˻�
				for (int i = 0; i < bad_word.length; i++) {
					if (tl.contains(bad_word[i])) { // �弳 ����Ʈ�� ���ϴ���
						check = true;
					} else if (str.contains(bad_word[i])) {
						check = true;
					}
				}
				int c = str.getBytes().length; // ���� �� ���� 300 ����Ʈ
				if (c > 300) {
					JOptionPane.showMessageDialog(null, "���� �� �ʰ�", "", JOptionPane.WARNING_MESSAGE);
				} else if (check) {
					JOptionPane.showMessageDialog(null, "�弳 ����", "", JOptionPane.WARNING_MESSAGE);
				} else {
					if (gonji.isSelected()) { // ���� üũ �Ǿ��ִ� ���
						a.user_write(db_connection.user, tl, str, gonji.getText());
						title.setText("");
						aaa.setText("");
					} else { // �ƴ� ��� �Ϲ� ��
						a.user_write(db_connection.user, tl, str, null);
						title.setText("");
						aaa.setText("");
					}
				}
			}
		});

	}

}
