package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PaymentFrame extends FrameBase {

	ImagePanel signPanel = new ImagePanel(null, e -> new SignFrame().setVisible(true));
	
	class SignPanel extends JPanel {
		BufferedImage img = new BufferedImage(320, 200, BufferedImage.TYPE_INT_RGB);
		
		public SignPanel() {
			var g = img.getGraphics();
			
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.setColor(Color.RED);
			
			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					g.fillOval(e.getX(), e.getY(), 20, 20);
					repaint();
				}
			});
			
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
		
	}
	
	class SignFrame extends FrameBase {

		public SignFrame() {
			super(350, 300, "사인");
			
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
			
			var sp = new SignPanel();
			
			add(createComp(sp, 320, 200));
			add(createButton(new JButton("확인"), e -> {
				// 이미지 짜르기 위해 subimage 사용
				signPanel.img = sp.img.getSubimage(0, 0, signPanel.getWidth(), signPanel.getHeight());
				signPanel.repaint();		
				dispose();
			}));
			
		}
		
	}
	
	public PaymentFrame(String revCode) {
		super(300, 630, "결제하기");
		
		var lbCaption = createComp(new JLabel("결제 서비스", 0), 0, 40);
		
		lbCaption.setOpaque(true);
		lbCaption.setBackground(Color.red);
		lbCaption.setForeground(Color.white);
		lbCaption.setFont(new Font("굴림", Font.BOLD, 24));
		lbCaption.setBorder(new LineBorder(Color.BLACK));
		
		var sp = new JPanel();
		var cp = new JPanel(null);
		var cp_p1 = new JPanel(new GridLayout(8, 1));
		
		String sql = "SELECT r.*, wh.weddinghall_name, wh.weddinghall_address, wh.weddinghall_accommodate, wh.weddinghall_fee, wt.weddingtype_name, mt.mealtype_name\r\n" + 
				", (SELECT GROUP_CONCAT(wt.weddingtype_name ORDER BY wt.weddingtype_name) FROM weddinghall_weddingtype wht INNER JOIN weddingtype wt ON wht.weddingtype_index = wt.weddingtype_index WHERE wht.weddinghall_index = wh.weddinghall_index) AS wed\r\n" + 
				", (SELECT GROUP_CONCAT(mt.mealtype_name) FROM weddinghall_mealtype whmt INNER JOIN mealtype mt ON whmt.mealtype_index = mt.mealtype_index WHERE whmt.weddinghall_index = wh.weddinghall_index) AS meal\r\n" + 
				", mt.mealtype_price\r\n" + 
				", IF(album = 1, '신청', '신청안함')\r\n" + 
				", IF(letter = 1, '신청', '신청안함')\r\n" + 
				", IF(dress = 1, '신청', '신청안함')\r\n" + 
				", CONCAT(weddinghall_fee + (mt.mealtype_price * r.reservation_personnel) + IF(album = 1, 100000, 0) + IF(letter = 1, 150000, 0) + IF(dress = 1, 200000, 0), '원') " +
				"FROM wedding.reservation r\r\n" + 
				"INNER JOIN weddinghall wh ON r.weddinghall_index = wh.weddinghall_index\r\n" + 
				"INNER JOIN weddingtype wt ON r.weddingtype_index = wt.weddingtype_index\r\n" + 
				"INNER JOIN mealtype mt ON r.mealtype_index = mt.mealtype_index\r\n" + 
				"WHERE r.reservation_code = '" + revCode + "'";
		
		// 기본 데이터 초기화
		try (var rs = stmt.executeQuery(sql)) {
			
			if (rs.next()) {
				var columns = "웨딩홀명,홀사용료,식사비용,인원수,앨범제작,청첩장,드레스, 총 금액".split(",");
				var idList = new int[] {11, 14, 19, 3, 20, 21, 22, 23};
				
				for (int i = 0; i < columns.length; i++) {
					var pnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 7));
					
					pnl.setBorder(new LineBorder(Color.BLACK));
					
					pnl.add(createComp(new JLabel(columns[i]), 70, 20));
					pnl.add(createComp(new JLabel(rs.getObject(idList[i]).toString()), 150, 20));
					
					if (i % 2 == 0) {
						pnl.setBackground(Color.WHITE);
					}
					
					cp_p1.add(pnl);
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		cp.add(createComp(cp_p1, 5, 5, 275, 300));
		cp.add(createComp(new JLabel("서명란", 0), 5, 305, 275, 25));
		
		cp.add(createComp(signPanel, 5, 330, 275, 175));
		
		sp.add(createButton(new JButton("결제"), e -> {
			runSQL("UPDATE reservation SET pay = 1 WHERE reservation_code = '" + revCode + "'");
			
			iMsg("결제가 완료되었습니다.");
			dispose();
		}));
		sp.add(createButton(new JButton("취소"), e -> dispose()));
		
		
		add(lbCaption, "North");
		add(cp);
		add(sp, "South");
	}

	public static void main(String[] args) {
		new PaymentFrame("19091741").setVisible(true);
	}

}
