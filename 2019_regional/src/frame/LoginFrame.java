package frame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class LoginFrame extends FrameBase {

	private JTextField tfId = createComp(new JTextField(), 70, 5, 160, 25);
	private JPasswordField pfPw = createComp(new JPasswordField(), 70, 35, 160, 25);
	
	
	public LoginFrame() {
		super(340, 190, "로그인");
		
		add(createComp(
				createLabel(new JLabel("STARBOX", JLabel.CENTER), new Font("Franklin Gothic Heavy", 0, 24)),
				0, 40),
				BorderLayout.NORTH);
				
		add(createPanel(new JPanel(null), 
				createComp(new JLabel("ID : ", JLabel.RIGHT), 10, 0, 60, 30),
				createComp(new JLabel("PW : ", JLabel.RIGHT), 10, 30, 60, 30),
				tfId,
				pfPw,
				createComp(
						createButtonWithoutMargin("로그인", this::clickLogin),
						240, 0, 60, 70)
				), BorderLayout.CENTER);
		
		add(createPanel(new JPanel(), 
				createButton("회원가입", e -> {
					dispose();
					new SignUpFrame().setVisible(true);
				}),
				createButton("종료", e -> dispose())
			), BorderLayout.SOUTH);
	}
	
	private void clickLogin(ActionEvent e) {
		String id = tfId.getText();
		String pw = pfPw.getText();
		
		if (id.length() == 0 || pw.length() == 0) {
			eMsg("빈칸이 존재합니다.");
		} else if (id.equals("admin") && pw.equals("1234")) {
			openFrame(new AdminMenuFrame());
		} else {
			
			try (var pst = con.prepareStatement("SELECT * FROM user WHERE u_id = ? AND u_pw = ?")) {
				
				pst.setObject(1, tfId.getText());
				pst.setObject(2, pfPw.getText());
				
				var rs = pst.executeQuery();
				
				if (rs.next()) {
					// Login 성공
					userNo = rs.getInt(1);
					userName = rs.getString(4);
					userGrade = rs.getString(7);
					userPoint = rs.getInt(6);
					
					openFrame(new StarBoxFrame());
				} else {
					// 실패
					eMsg("회원정보가 틀립니다.다시입력해주세요.");
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new LoginFrame().setVisible(true);
	}

}
