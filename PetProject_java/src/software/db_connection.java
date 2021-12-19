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

// ��� ����
public class db_connection {
	Connection con = null;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String id = "c##soft";
	String password = "1234";
	boolean check = true; // üũ�� ����

	public db_connection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� ���� ����");
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

	// ȸ�� �Ƶ�, ���
	static String user = "";
	static String pwd = "";

	protected void user_check(String id, String pw) { // ȸ�� �α��� Ȯ��
		String sql = "SELECT ��й�ȣ FROM ȸ�� WHERE ���̵� = ?";
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id); // ?�� id ����
			ResultSet rs = pstmt.executeQuery(); // sql�� ����
			while (rs.next()) {
				pwd = rs.getString(1); // ù��° ��
				if (pwd.equals(pw)) { // �Է��� ��й�ȣ�� ��ġ�ϴ���
					user = id;
					check = true;
					new main();
					break;
				} else {
					JOptionPane.showMessageDialog(null, "�ٽ� �Է��ϼ���.");
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

	// Ȯ�� �Լ�
	public boolean ischeck() {
		return check;
	}

	protected int dog_dis() { // ������ ���� ��å�Ÿ�
		String sql = "SELECT �Ÿ� FROM ȸ��, ���� WHERE ���̵� = ? and " + "ȸ��.���� = ����.�����̸�";
		int distance = 0;
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user); //
			ResultSet rs = pstmt.executeQuery(); // select ���� ���
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

	// Table�� �� ������ ��ϵ� (�������, �߰� �� row ����)
	String col_head[] = { "�۹�ȣ", "����", "���̵�" };
	@SuppressWarnings("serial")
	DefaultTableModel model = new DefaultTableModel(col_head, 0) {
		public boolean isCellEditable(int i, int c) {
			return false;
		} // ���� �Ұ�
	};
	JTable table = new JTable(model);
	JScrollPane jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	// DefaultTableCellHeaderRenderer ���� (��� ������ ����)
	DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

	protected JScrollPane get_community() { // Ŀ�´�Ƽ DB
		String sql = "SELECT ��ȣ,����,���̵� FROM Ŀ�´�Ƽ order by decode(post,'����',1), to_number(��ȣ) desc";
		String TTT[] = new String[3]; // �ŷ����� ���� �迭
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(); // select ���� ���
			model.setNumRows(0);
			while (rs.next()) {
				TTT[0] = rs.getString(1); // ��ȣ
				TTT[1] = rs.getString(2); // ����
				TTT[2] = rs.getString(3); // ���̵�
				model.addRow(TTT); // ���̺� �߰�
			}
			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setRowHeight(35); // ���̺� �� ����
		table.getColumn("�۹�ȣ").setPreferredWidth(10); // �ش� ������ ����
		table.getColumn("����").setPreferredWidth(230);
		table.getColumn("���̵�").setPreferredWidth(30);
		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// ������ ���̺��� ColumnModel�� ������
		TableColumnModel tcmSchedule = table.getColumnModel();
		// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);

		}
		table.getTableHeader().setReorderingAllowed(false); // �÷��� �̵� �Ұ�
		table.getTableHeader().setResizingAllowed(false); // �÷� ũ�� ���� �Ұ�
		table.getParent().setBackground(new Color(204, 255, 255));
		jsp.setPreferredSize(new Dimension(385, 400));
		return jsp;
	}

	// �Խñ�
	List<String> post = new ArrayList<String>(); // ��������Ʈ
	String post_list[]; // �Խñ� ���� �迭

	protected void show_post(String n) {
		String sql = "SELECT ����,����,���̵� FROM Ŀ�´�Ƽ WHERE ��ȣ = ?";
		try {
			DB_Connect();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, n);
			ResultSet rs = pstmt.executeQuery(); // select ���� ���
			while (rs.next()) {
				post.add(rs.getString(1)); // ����
				post.add(rs.getString(2)); // ����
				post.add(rs.getString(3)); // ���̵�
			}
			post_list = post.toArray(new String[post.size()]); // ��������Ʈ�� �迭�� ��ȯ

			pstmt.close();
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void user_write(String user_id, String title, String content, String post) {
		// ȸ�� �۾���
		try {
			DB_Connect();
			// ���ν��� ȣ��
			// ���ڰ����� ���̵�, ����, ����, �������� �ƴ��� �Ѱ���
			CallableStatement cstmt = con.prepareCall("{call write(?, ?, ?, ?)}");
			cstmt.setString(1, user_id);
			cstmt.setString(2, title);
			cstmt.setString(3, content);
			cstmt.setString(4, post);
			cstmt.executeQuery();
			JOptionPane.showMessageDialog(null, "��� �Ϸ�");
			cstmt.close();
			con.close(); // finally ����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
