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
		super(340, 190, "�α���");
		
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
						createButtonWithoutMargin("�α���", this::clickLogin),
						240, 0, 60, 70)
				), BorderLayout.CENTER);
		
		add(createPanel(new JPanel(), 
				createButton("ȸ������", e -> {
					dispose();
					new SignUpFrame().setVisible(true);
				}),
				createButton("����", e -> dispose())
			), BorderLayout.SOUTH);
	}
	
	private void clickLogin(ActionEvent e) {
		String id = tfId.getText();
		String pw = pfPw.getText();
		
		if (id.length() == 0 || pw.length() == 0) {
			eMsg("��ĭ�� �����մϴ�.");
		} else if (id.equals("admin") && pw.equals("1234")) {
			openFrame(new AdminMenuFrame());
		} else {
			
			try (var pst = con.prepareStatement("SELECT * FROM user WHERE u_id = ? AND u_pw = ?")) {
				
				pst.setObject(1, tfId.getText());
				pst.setObject(2, pfPw.getText());
				
				var rs = pst.executeQuery();
				
				if (rs.next()) {
					// Login ����
					userNo = rs.getInt(1);
					userName = rs.getString(4);
					userGrade = rs.getString(7);
					userPoint = rs.getInt(6);
					
					openFrame(new StarBoxFrame());
				} else {
					// ����
					eMsg("ȸ�������� Ʋ���ϴ�.�ٽ��Է����ּ���.");
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