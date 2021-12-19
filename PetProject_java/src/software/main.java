package software;

import static software.login.font;
import javafx.scene.Group;
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

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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

		final JFXPanel fxPanel = new JFXPanel(); // �ʶ��� �г�
		fxPanel.setBounds(0, -48, 370, 380);

		JButton user_info = new JButton("������"); // �Ʒ��� �޴� ��ư��
		user_info.setFont(font);
		user_info.setBackground(new Color(204, 255, 255));
		JButton commu = new JButton("Ŀ�´�Ƽ");
		commu.setFont(font);
		commu.setBackground(new Color(204, 255, 255));
		JButton walk = new JButton("��å�ϱ�");
		walk.setBackground(new Color(204, 255, 255));
		walk.setFont(font);

		test.setLayout(card); // ī�巹�̾ƿ�
		add(test);
		test.setBounds(0, 0, 370, 450);

		ui1.setBackground(new Color(204, 255, 255)); // ���� ui
		ui1.setLayout(null);
		ui2.setBackground(new Color(204, 255, 255)); // Ŀ�´�Ƽ ui
		ui2.setLayout(null);

		// �̹��� �ޱ�
		// ������ ������
		JLabel img = new JLabel();
		ImageIcon dog = new ImageIcon("./image/2.png");
		Image dog1 = dog.getImage();
		Image changeImg = dog1.getScaledInstance(50, 45, Image.SCALE_SMOOTH);
		dog = new ImageIcon(changeImg);
		img.setIcon(dog);
		// ���� ������
		JLabel img2 = new JLabel();
		ImageIcon bar = new ImageIcon("./image/barr.png");
		Image bar1 = bar.getImage();
		Image chimg2 = bar1.getScaledInstance(330, 100, Image.SCALE_SMOOTH);
		bar = new ImageIcon(chimg2);
		img2.setIcon(bar);
		// ��å��ư
		JButton n1 = new JButton("��å ����");
		n1.setFont(font);
		n1.setBackground(new Color(204, 255, 255));
		n1.setBounds(135, 410, 100, 30);

		ui1.add(fxPanel);// ����ȭ�鿡 ���� �߰�
		ui1.add(n1);// ��ư �߰�
		img.setBounds(1, 340, 100, 100);
		img2.setBounds(25, 340, 450, 100);
		ui1.add(img); // �̹����� �߰�
		ui1.add(img2);

		JLabel dis = new JLabel("�Ÿ�: "); // �Ÿ� ǥ��
		dis.setFont(font);
		dis.setBounds(10, 325, 150, 50);
		ui1.add(dis);

		JButton n2 = new JButton("�۾���"); // Ŀ�´�Ƽ ȭ�鿡 �۾��� ��ư
		n2.setFont(font);
		n2.setBackground(new Color(204, 255, 255));
		n2.setBounds(275, 410, 80, 30);
		ui2.add(n2); // ��ư �߰�
		list.setBackground(Color.black);
		list.setBounds(-5, -5, 390, 400);
		ui2.add(list);

		a.table.addMouseListener(this);

		test.add("walking", ui1); // ����ȭ�� ��Ī�� walking
		test.add("community", ui2);// Ŀ�´�Ƽ
		test.add("write", new write()); // �۾��� ui

		card.show(test, "walking"); // ùȭ���� walking

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
				String word = n1.getText(); // ��å���� ��ư�� ���ڸ� ������
				distance = a.dog_dis(); // �ش� ȸ�� �������� �Ÿ�
				double devide = 380 / (distance * 1000); // �г�ũ�⿡ ���缭 ����
				if (word.equals("��å ����")) { // ��ư�� ���ڸ� ���������� ��å�����̸�
					n1.setText("��å ���"); // ��å ��ҷ� �ٲ� ��ư���ڰ�
					Timer timer = new Timer(); // Ÿ�̸�
					TimerTask task = new TimerTask() { // �Ÿ� ���Ҹ� ����
						@Override
						public void run() {
							// TODO Auto-generated method stub
							// �Ÿ��� 0���� ũ�� ��ư ���ڰ� ��å ������ �ƴѵ���
							if (distance >= 0 && n1.getText() != "��å ����") {
								int i = img.getX(); // ������ ������ x��ǥ�� ������
								distance = distance - 0.0015; // �Ÿ� ����
								distance = Math.round(distance * 1000) / 1000.0;
								// ������ ������ ��ġ�� ������
								img.setBounds((int) (i + devide), 340, 100, 100);
								// �����ǰ��ִ� �Ÿ��� �����
								dis.setText("�Ÿ�: " + distance + " km");
							} else {
								// �� ���ǿ� ���������� Ÿ�̸� ����
								timer.cancel();
								JOptionPane.showMessageDialog(null, "��å ����");
								// ��å �������� �ٽ� ��ư ���ڸ� �ٲ�
								n1.setText("��å ����");
								// �Ÿ� ����
								dis.setText("�Ÿ�: ");
								// ������ �̹��� ��ġ ����
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
				card.show(test, "write"); // �۾��� ȭ���� ������
			}
		});
		walk.addActionListener(new ActionListener() { // ��å�ϱ�
			public void actionPerformed(ActionEvent e) {
				card.show(test, "walking"); // ��åȭ���� ������
			}
		});
		commu.addActionListener(new ActionListener() { // Ŀ�´�Ƽ
			public void actionPerformed(ActionEvent e) {
				card.show(test, "community"); // Ŀ�´�Ƽ ȭ���� ������
				list.add(a.get_community()); // �Խñ� ����Ʈ�� �߰��� ȭ�鿡
			}
		});

		Platform.runLater(new Runnable() { // �� ����� ���� �Լ�/ �� ��

			public void run() {
				initAndLoadWebView(fxPanel); // �Լ��� �ش� �г��� �Ѱ���
			}

		});
	}

	public void mouseClicked(MouseEvent e) { // �Խñ� ���� Ŭ���� �ش� �Խñ��� ������
		int row = a.table.getSelectedRow();
		String w_n = (String) a.table.getModel().getValueAt(row, 0);
		if (e.getClickCount() == 2) { // ����Ŭ����
			new post(w_n);
		}
	}

	private void initAndLoadWebView(final JFXPanel fxPanel) {
// �г��� �޾Ƽ� �ش� url�� ���� ������
		Group group = new Group();

		Scene scene = new Scene(group);

		fxPanel.setScene(scene);

		WebView webView = new WebView();

		group.getChildren().add(webView);

		webView.setMinSize(365, 380);

		webView.setMaxSize(365, 380);

		WebEngine webEngine = webView.getEngine();

		webEngine.load("https://m.map.naver.com/search2/site.naver?query=���Ǵ��б�&sm=hty&style=v5&code=11591545#/map");

	}

//	public static void main(String arg[]) {
//		new main();
//	}

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
