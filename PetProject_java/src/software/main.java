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
	db_connection a = new db_connection(); // 데베 객체 생성
	private CardLayout card = new CardLayout();
	// 프레임 전환용 패널
	private JPanel test = new JPanel();

	private JPanel ui1 = new JPanel(); // 산책창
	private JPanel ui2 = new JPanel(); // 커뮤니티 창
	private JPanel list = new JPanel();
	double distance = 0;

	public main() {
		// db_connection a = new db_connection(); // 데베 객체 생성
		// 프레임 제목, 위치, 크기 설정
		setLayout(null);
		setResizable(false);
		setTitle("App");
		setSize(380, 550);
		setLocationRelativeTo(null);
		setVisible(true); // 프레임 보이기
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		final JFXPanel fxPanel = new JFXPanel(); // 맵띄우는 패널
		fxPanel.setBounds(0, -48, 370, 380);

		JButton user_info = new JButton("내정보"); // 아랫단 메뉴 버튼들
		user_info.setFont(font);
		user_info.setBackground(new Color(204, 255, 255));
		JButton commu = new JButton("커뮤니티");
		commu.setFont(font);
		commu.setBackground(new Color(204, 255, 255));
		JButton walk = new JButton("산책하기");
		walk.setBackground(new Color(204, 255, 255));
		walk.setFont(font);

		test.setLayout(card); // 카드레이아웃
		add(test);
		test.setBounds(0, 0, 370, 450);

		ui1.setBackground(new Color(204, 255, 255)); // 메인 ui
		ui1.setLayout(null);
		ui2.setBackground(new Color(204, 255, 255)); // 커뮤니티 ui
		ui2.setLayout(null);

		// 이미지 받기
		// 강아지 아이콘
		JLabel img = new JLabel();
		ImageIcon dog = new ImageIcon("./image/2.png");
		Image dog1 = dog.getImage();
		Image changeImg = dog1.getScaledInstance(50, 45, Image.SCALE_SMOOTH);
		dog = new ImageIcon(changeImg);
		img.setIcon(dog);
		// 막대 아이콘
		JLabel img2 = new JLabel();
		ImageIcon bar = new ImageIcon("./image/barr.png");
		Image bar1 = bar.getImage();
		Image chimg2 = bar1.getScaledInstance(330, 100, Image.SCALE_SMOOTH);
		bar = new ImageIcon(chimg2);
		img2.setIcon(bar);
		// 산책버튼
		JButton n1 = new JButton("산책 시작");
		n1.setFont(font);
		n1.setBackground(new Color(204, 255, 255));
		n1.setBounds(135, 410, 100, 30);

		ui1.add(fxPanel);// 메인화면에 지도 추가
		ui1.add(n1);// 버튼 추가
		img.setBounds(1, 340, 100, 100);
		img2.setBounds(25, 340, 450, 100);
		ui1.add(img); // 이미지들 추가
		ui1.add(img2);

		JLabel dis = new JLabel("거리: "); // 거리 표시
		dis.setFont(font);
		dis.setBounds(10, 325, 150, 50);
		ui1.add(dis);

		JButton n2 = new JButton("글쓰기"); // 커뮤니티 화면에 글쓰기 버튼
		n2.setFont(font);
		n2.setBackground(new Color(204, 255, 255));
		n2.setBounds(275, 410, 80, 30);
		ui2.add(n2); // 버튼 추가
		list.setBackground(Color.black);
		list.setBounds(-5, -5, 390, 400);
		ui2.add(list);

		a.table.addMouseListener(this);

		test.add("walking", ui1); // 메인화면 별칭은 walking
		test.add("community", ui2);// 커뮤니티
		test.add("write", new write()); // 글쓰기 ui

		card.show(test, "walking"); // 첫화면은 walking

		add(walk);
		add(commu);
		add(user_info);
		walk.setBounds(-1, 450, 125, 62);
		commu.setBounds(123, 450, 125, 62);
		user_info.setBounds(242, 450, 125, 62);

		// 버튼 이벤트
		user_info.addActionListener(new ActionListener() { // 회원정보
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "미구현", "", JOptionPane.WARNING_MESSAGE);
				card.show(test, "walking");
			}
		});
		n1.addActionListener(new ActionListener() { // 산책시작
			public void actionPerformed(ActionEvent e) {
				String word = n1.getText(); // 산책시작 버튼의 문자를 가져옴
				distance = a.dog_dis(); // 해당 회원 강아지의 거리
				double devide = 380 / (distance * 1000); // 패널크기에 맞춰서 나눔
				if (word.equals("산책 시작")) { // 버튼의 문자를 가져왔을때 산책시작이면
					n1.setText("산책 취소"); // 산책 취소로 바뀜 버튼글자가
					Timer timer = new Timer(); // 타이머
					TimerTask task = new TimerTask() { // 거리 감소를 위함
						@Override
						public void run() {
							// TODO Auto-generated method stub
							// 거리가 0보다 크고 버튼 문자가 산책 시작이 아닌동안
							if (distance >= 0 && n1.getText() != "산책 시작") {
								int i = img.getX(); // 강아지 아이콘 x좌표를 가져옴
								distance = distance - 0.0015; // 거리 차감
								distance = Math.round(distance * 1000) / 1000.0;
								// 강아지 아이콘 위치를 갱신함
								img.setBounds((int) (i + devide), 340, 100, 100);
								// 차감되고있는 거리를 출력함
								dis.setText("거리: " + distance + " km");
							} else {
								// 위 조건에 맞지않으면 타이머 종료
								timer.cancel();
								JOptionPane.showMessageDialog(null, "산책 종료");
								// 산책 시작으로 다시 버튼 문자를 바꿈
								n1.setText("산책 시작");
								// 거리 리셋
								dis.setText("거리: ");
								// 강아지 이미지 위치 리셋
								img.setBounds(1, 340, 100, 100);
							}
						}
					};
					timer.schedule(task, 1000, 1000); // 실행 Task, 1초뒤 실행, 1초마다 반복
				} else {
					n1.setText("산책 시작");
					dis.setText("거리: ");
				}
			}
		});
		n2.addActionListener(new ActionListener() { // 글쓰기
			public void actionPerformed(ActionEvent e) {
				card.show(test, "write"); // 글쓰기 화면을 보여줌
			}
		});
		walk.addActionListener(new ActionListener() { // 산책하기
			public void actionPerformed(ActionEvent e) {
				card.show(test, "walking"); // 산책화면을 보여줌
			}
		});
		commu.addActionListener(new ActionListener() { // 커뮤니티
			public void actionPerformed(ActionEvent e) {
				card.show(test, "community"); // 커뮤니티 화면을 보여줌
				list.add(a.get_community()); // 게시글 리스트를 추가함 화면에
			}
		});

		Platform.runLater(new Runnable() { // 맵 출력을 위한 함수/ 잘 모름

			public void run() {
				initAndLoadWebView(fxPanel); // 함수에 해당 패널을 넘겨줌
			}

		});
	}

	public void mouseClicked(MouseEvent e) { // 게시글 더블 클릭시 해당 게시글을 열어줌
		int row = a.table.getSelectedRow();
		String w_n = (String) a.table.getModel().getValueAt(row, 0);
		if (e.getClickCount() == 2) { // 더블클릭시
			new post(w_n);
		}
	}

	private void initAndLoadWebView(final JFXPanel fxPanel) {
// 패널을 받아서 해당 url의 웹을 열어줌
		Group group = new Group();

		Scene scene = new Scene(group);

		fxPanel.setScene(scene);

		WebView webView = new WebView();

		group.getChildren().add(webView);

		webView.setMinSize(365, 380);

		webView.setMaxSize(365, 380);

		WebEngine webEngine = webView.getEngine();

		webEngine.load("https://m.map.naver.com/search2/site.naver?query=동의대학교&sm=hty&style=v5&code=11591545#/map");

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
