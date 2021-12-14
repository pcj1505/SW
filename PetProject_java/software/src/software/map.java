package software;

import static software.login.font;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//trans
@SuppressWarnings("serial")
public class main extends JFrame implements MouseListener {
	db_connection a = new db_connection(); // ���� ��ü ����
	private CardLayout card = new CardLayout();
	// ������ ��ȯ�� �г�
	private JPanel test = new JPanel();

	private JPanel ui1 = new JPanel(); // ��åâ
	private JPanel ui2 = new JPanel(); // Ŀ�´�Ƽ â
	private JPanel list = new JPanel();
	double distance = 0;

	public main() {
		// db_connection a = new db_connection(); // ���� ��ü ����
		// ������ ����, ��ġ, ũ�� ����
		setLayout(null);
		setResizable(false);
		setTitle("App");
		setSize(380, 550);
		setLocationRelativeTo(null);
		setVisible(true); // ������ ���̱�
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton user_info = new JButton("������");
		user_info.setFont(font);
		user_info.setBackground(new Color(204, 255, 255));
		JButton commu = new JButton("Ŀ�´�Ƽ");
		commu.setFont(font);
		commu.setBackground(new Color(204, 255, 255));
		JButton walk = new JButton("��å�ϱ�");
		walk.setBackground(new Color(204, 255, 255));
		walk.setFont(font);

		test.setLayout(card);
		add(test);
		test.setBounds(0, 0, 370, 450);

		ui1.setBackground(new Color(204, 255, 255));
		ui1.setLayout(null);
		ui2.setBackground(new Color(204, 255, 255));
		ui2.setLayout(null);

		// �̹��� �ޱ�
		JLabel img = new JLabel();
		ImageIcon dog = new ImageIcon("./image/22.png");
		Image dog1 = dog.getImage();
		Image changeImg = dog1.getScaledInstance(50, 45, Image.SCALE_SMOOTH);
		dog = new ImageIcon(changeImg);
		img.setIcon(dog);

		JButton n1 = new JButton("��å ����");
		n1.setFont(font);
		n1.setBackground(new Color(204, 255, 255));
		n1.setBounds(135, 410, 100, 30);
		ui1.add(new map());
		ui1.add(n1);
		img.setBounds(1, 340, 100, 100);
		ui1.add(img);

		JLabel dis = new JLabel("�Ÿ�: ");
		dis.setFont(font);
		dis.setBounds(10, 325, 150, 50);
		ui1.add(dis);

		JButton n2 = new JButton("�۾���");
		n2.setFont(font);
		n2.setBackground(new Color(204, 255, 255));
		n2.setBounds(275, 410, 80, 30);
		ui2.add(n2);
		list.setBackground(Color.white);
		list.setBounds(0, 0, 370, 400);
		ui2.add(list);

		a.table.addMouseListener(this);

		test.add("walking", ui1);
		test.add("community", ui2);
		test.add("write", new write());

		card.show(test, "walking");

		add(walk);
		add(commu);
		add(user_info);
		walk.setBounds(-1, 450, 125, 62);
		commu.setBounds(123, 450, 125, 62);
		user_info.setBounds(242, 450, 125, 62);

		// ��ư �̺�Ʈ
		user_info.addActionListener(new ActionListener() { // ȸ������
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "�̱���", "", JOptionPane.WARNING_MESSAGE);
				card.show(test, "walking");
			}
		});
		n1.addActionListener(new ActionListener() { // ��å����
			public void actionPerformed(ActionEvent e) {
				String word = n1.getText();
				distance = a.dog_dis();
				double devide = 380 / (distance * 1000);
				if (word.equals("��å ����")) {
					n1.setText("��å ���");
					Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if (distance >= 0 && n1.getText() != "��å ����") {
								int i = img.getX();
								distance = distance - 0.0015;
								distance = Math.round(distance * 1000) / 1000.0;
								img.setBounds((int) (i + devide), 340, 100, 100);
								dis.setText("�Ÿ�: " + distance + " km");
							} else {
								timer.cancel();
								JOptionPane.showMessageDialog(null, "��å ����");
								n1.setText("��å ����");
								dis.setText("�Ÿ�: ");
								img.setBounds(1, 340, 100, 100);
							}
						}
					};
					timer.schedule(task, 1000, 1000); // ���� Task, 1�ʵ� ����, 1�ʸ��� �ݺ�
				} else {
					n1.setText("��å ����");
					dis.setText("�Ÿ�: ");
				}
			}
		});
		n2.addActionListener(new ActionListener() { // �۾���
			public void actionPerformed(ActionEvent e) {
				card.show(test, "write");
			}
		});
		walk.addActionListener(new ActionListener() { // ��å�ϱ�
			public void actionPerformed(ActionEvent e) {
				card.show(test, "walking");
			}
		});
		commu.addActionListener(new ActionListener() { // Ŀ�´�Ƽ
			public void actionPerformed(ActionEvent e) {
				card.show(test, "community");
				list.add(a.get_community());
			}
		});

	}

	public void mouseClicked(MouseEvent e) {
		int row = a.table.getSelectedRow();
		int w_n = Integer.parseInt((String) a.table.getModel().getValueAt(row, 0));
		if (e.getClickCount() == 2) { // ����Ŭ����
			new post(w_n);
		}
	}

	public static void main(String arg[]) {
		new main();
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
}


[map.java]
package software;

import java.awt.Image;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class map extends JPanel {

	public map() {
		setLayout(null);
		setBounds(0, 0, 380, 340);
		

		try {
			String imageUrl = "http://maps.google.com/staticmap?center=40,26&zoom=1&size=150x112&maptype=satellite&key=ABQIAAAAgb5KEVTm54vkPcAkU9xOvBR30EG5jFWfUzfYJTWEkWk2p04CHxTGDNV791-cU95kOnweeZ0SsURYSA&format=jpg";
			String destinationFile = "image.jpg";
			String str = destinationFile;
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		add(new JLabel(new ImageIcon(
				(new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH))));

		setVisible(true);
		pack();

	}

	private void pack() {
		// TODO Auto-generated method stub
	}
}