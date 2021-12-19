package software;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

// 디비 연동
public class db_connection {
	Connection con = null;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String id = "c##soft";
	String password = "1234";
	boolean check = true; // 체크용 변수

	public db_connection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 적재 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("No Driver.");
		}
	}

	private void DB_Connect() {
		try {
			con = DriverManager.getConnection(url, id, password);
			System.out.print("");
		} catch (SQLException e) {
			System.out.println("Connection Fail");
		}
	}

	// 회원 아디, 비번
	static String user = "";
	static String pwd = "";

	protected void user_check(String id, String pw) { // 회원 로그인 확인
		String sql = "SELECT 비밀번호 FROM 회원 WHERE 아이디 = ?";
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id); // ?에 id 세팅
			ResultSet rs = pstmt.executeQuery(); // sql문 실행
			while (rs.next()) {
				pwd = rs.getString(1); // 첫번째 값
				if (pwd.equals(pw)) { // 입력한 비밀번호랑 일치하는지
					user = id;
					check = true;
					new main();
					break;
				} else {
					JOptionPane.showMessageDialog(null, "다시 입력하세요.");
					check = false;
				}
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 확인 함수
	public boolean ischeck() {
		return check;
	}

	protected int dog_dis() { // 강아지 적정 산책거리
		String sql = "SELECT 거리 FROM 회원, 견종 WHERE 아이디 = ? and " + "회원.견종 = 견종.견종이름";
		int distance = 0;
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user); //
			ResultSet rs = pstmt.executeQuery(); // select 문에 사용
			while (rs.next()) {
				distance = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return distance;
	}

	// Table에 들어갈 데이터 목록들 (헤더정보, 추가 될 row 개수)
	String col_head[] = { "글번호", "제목", "아이디" };
	@SuppressWarnings("serial")
	DefaultTableModel model = new DefaultTableModel(col_head, 0) {
		public boolean isCellEditable(int i, int c) {
			return false;
		} // 수정 불가
	};
	JTable table = new JTable(model);
	JScrollPane jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
	DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

	protected JScrollPane get_community() { // 커뮤니티 DB
		String sql = "SELECT 번호,제목,아이디 FROM 커뮤니티 order by decode(post,'공지',1), to_number(번호) desc";
		String TTT[] = new String[3]; // 거래내역 담은 배열
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(); // select 문에 사용
			model.setNumRows(0);
			while (rs.next()) {
				TTT[0] = rs.getString(1); // 번호
				TTT[1] = rs.getString(2); // 제목
				TTT[2] = rs.getString(3); // 아이디
				model.addRow(TTT); // 테이블에 추가
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setRowHeight(35); // 테이블 행 높이
		table.getColumn("글번호").setPreferredWidth(10); // 해당 열길이 조정
		table.getColumn("제목").setPreferredWidth(230);
		table.getColumn("아이디").setPreferredWidth(30);
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);

		}
		table.getTableHeader().setReorderingAllowed(false); // 컬럼들 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		table.getParent().setBackground(new Color(204, 255, 255));
		jsp.setPreferredSize(new Dimension(385, 400));
		return jsp;
	}

	// 게시글
	List<String> post = new ArrayList<String>(); // 가변리스트
	String post_list[]; // 게시글 담은 배열

	protected void show_post(String n) {
		String sql = "SELECT 제목,내용,아이디 FROM 커뮤니티 WHERE 번호 = ?";
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, n);
			ResultSet rs = pstmt.executeQuery(); // select 문에 사용
			while (rs.next()) {
				post.add(rs.getString(1)); // 제목
				post.add(rs.getString(2)); // 내용
				post.add(rs.getString(3)); // 아이디
			}
			post_list = post.toArray(new String[post.size()]); // 가변리스트를 배열로 변환

			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void user_write(String user_id, String title, String content, String post) {
		// 회원 글쓰기
		try {
			DB_Connect();
			// 프로시저 호출
			// 인자값으로 아이디, 제목, 내용, 공지인지 아닌지 넘겨줌
			CallableStatement cstmt = con.prepareCall("{call write(?, ?, ?, ?)}");
			cstmt.setString(1, user_id);
			cstmt.setString(2, title);
			cstmt.setString(3, content);
			cstmt.setString(4, post);
			cstmt.executeQuery();
			JOptionPane.showMessageDialog(null, "등록 완료");
			cstmt.close();
			con.close(); // finally 없이
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
