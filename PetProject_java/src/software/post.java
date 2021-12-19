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
	db_connection a = new db_connection(); // ���� ��ü ����
	Font font1 = new Font("���� ���", Font.BOLD, 20);

	public post(String number) {
		setSize(379, 550);
		setLocationRelativeTo(null);
		setLayout(null);
		getContentPane().setBackground(new Color(204, 255, 255));
		a.show_post(number);
		setTitle(a.post_list[0]);
		JLabel one = new JLabel(a.post_list[0]); // �ش� ���� ���� �޾ƿ���
		one.setFont(font1);

		JPanel area = new JPanel(); // �г� ����
		area.setLayout(null);
		JLabel two = new JLabel(a.post_list[1]); // �ش� ���� ���� �޾ƿ���
		two.setFont(font);
		JLabel three = new JLabel("�ۼ��� " + a.post_list[2]); // �ۼ���
		three.setFont(font);
		one.setBounds(8, 15, 300, 30);
		two.setBounds(10, -100, 300, 300);
		three.setBounds(265, 25, 100, 30);
		JButton close = new JButton("���"); // �Խñ� ����Ʈ�� ���ư��� ���� ��� ��ư
		close.setBackground(new Color(204, 255, 255));
		close.setFont(font);
		close.setBounds(270, 360, 80, 30);
		add(close);
		add(one);
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1); // �׵θ� ����
		area.setBorder(lineBorder); // ���� �׵θ� ����
		area.add(two);
		area.setBounds(-1, 55, 365, 300);
		area.setBackground(Color.white);
		add(area);
		add(three);
		setVisible(true);

		// ��ư
		close.addActionListener(new ActionListener() { // ���
			public void actionPerformed(ActionEvent e) {
				dispose(); // â�ݱ�
			}
		});
	}

}
