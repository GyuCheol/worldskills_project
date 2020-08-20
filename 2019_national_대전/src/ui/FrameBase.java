package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.mysql.cj.protocol.Resultset;

public class FrameBase extends JFrame {

	protected static Connection con;
	protected static Statement stmt;
	protected boolean isRun = true;
	
	protected static String revCode;
	
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/wedding?serverTimezone=UTC", "user", "1234");
			stmt = con.createStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public FrameBase setClosingAction(Runnable runnable) {
		// 폼 종료시 추가 동작을 위해 지정하는 Builder 패턴
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				runnable.run();
			}
		});
		
		return this;
	}
	
	public FrameBase setClosedAction(Runnable runnable) {
		// 폼 종료시 추가 동작을 위해 지정하는 Builder 패턴
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				runnable.run();
			}
		});
		
		return this;
	}
	
	protected void createThread(Runnable runnable) {
		
		// Dispose든, X 버튼이든 Frame 종료시 동작함
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				isRun = false;
			}
		});
		
		isRun = true;
		
		new Thread(() -> {
			
			while (isRun) {
				runnable.run();
			}
			
		}).start();
		
	}
	
	protected void moveAction(FrameBase frame, boolean isDispose) {
		// Closing 회피함.
		
		if (isDispose)  {
			dispose();
		} else {
			setVisible(false);
		}
		
		frame.setVisible(true);
	}
	
	public void eMsg(String text) {
		JOptionPane.showOptionDialog(null, text, "MESSAGE", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, 
				"확인".split(","), null);
	}
	
	public String showInputDialog(String text) {
		var panel = createComp(new JPanel(null), 200, 45);
		var tf = new JTextField();
		
		panel.add(createComp(new JLabel(text), 0, 0, 200, 20));
		panel.add(createComp(tf, 0, 20, 200, 25));
		
		int answer = JOptionPane.showOptionDialog(null, panel, "입력", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				"확인,취소".split(","), null);
		
		if (answer == JOptionPane.OK_OPTION) {
			return tf.getText();
		} else {
			return null;
		}
		
	}
	
	public HashMap<String, Integer> getMapInfo(String table, int name, int id) {
		var map = new HashMap<String, Integer>();
		
		try (var rs = stmt.executeQuery("SELECT * FROM " + table)) {
			
			while (rs.next()) {
				map.put(rs.getString(name), rs.getInt(id));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public void runSQL(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void iMsg(String text) {
		JOptionPane.showOptionDialog(null, text, "MESSAGE", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
				"확인".split(","), null);
	}
	
	public boolean isNumeric(String text) {
		
		try {
			int i = Integer.parseInt(text);
			
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public FrameBase(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setDefaultCloseOperation(2);
		setLocationRelativeTo(null);
	}
	
	public <T extends JComponent> T createComp(T comp, int width, int height) {
		comp.setPreferredSize(new Dimension(width, height));
		
		return comp;
	}
	
	public <T extends JComponent> T createCompBorder(T comp, Border border) {
		comp.setBorder(border);
		
		return comp;
	}
	
	public <T extends JComponent> T createComp(T comp, int x, int y, int width, int height) {
		comp.setBounds(x, y, width, height);
		
		return comp;
	}
	
	public JButton createButton(JButton btn, ActionListener action) {
		btn.addActionListener(action);
		
		return btn;
	}
	

}
