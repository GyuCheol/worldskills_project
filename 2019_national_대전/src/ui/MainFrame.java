package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MainFrame extends FrameBase {
	JLabel caption = new JLabel("테스트");
	ArrayList<HotelData> hotelList = new ArrayList<>();
	float percent = 0f;
	boolean isAnimate = false;
	int index = 0;

	class HotelData {
		String name;
		Image image;
		
		public HotelData(String name, Image image) {
			super();
			this.name = name;
			this.image = image;
		}
	}
	
	class PicturePanel extends JPanel {
		
		@Override
		protected void paintComponent(Graphics g) {
			
			if (isAnimate) {
				int index2 = (index + 1) % hotelList.size();
				var img = hotelList.get(index).image;
				var img2 = hotelList.get(index2).image;
				int img_sx2 = (int) (getWidth() * (1f - percent));
				int img_dx1 = (int) (img.getWidth(this) * percent);
				int img2_dx1 = (int) (img2.getWidth(this) * percent);
				
				if (percent < 1) {
					g.drawImage(img, 0, 0, img_sx2, getHeight(), img_dx1, 0, img.getWidth(this), img.getHeight(this), this);					
				}
				
				g.drawImage(img2, img_sx2, 0, getWidth(), getHeight(), 0, 0, img2_dx1, img2.getHeight(this), this);
				
			} else {
				var img = hotelList.get(index).image;
				
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		}
		
	}
	
	public MainFrame() {
		super(450, 400, "메인");
		
		var np = createCompBorder(new JPanel(), new EmptyBorder(10, 0, 10, 0));
		
		np.add(createButton(new JButton("웨딩홀 검색"), e -> moveAction(new SearchFrame().setClosingAction(() -> new MainFrame().setVisible(true)), true)));
		np.add(createButton(new JButton("예약확인"), e -> checkReservation()));
		np.add(createButton(new JButton("인기 웨딩홀"), e -> moveAction(new PopularChartFrame().setClosedAction(() ->  new MainFrame().setVisible(true)), true)));
		np.add(createButton(new JButton("관리"), e -> moveAction(new AdminFrame(), true)));
		np.add(createButton(new JButton("종료"), e -> dispose()));	
		
		add(np, "North");
		
		var sp = createCompBorder(new JPanel(), new EmptyBorder(10, 0, 0, 0));
		
		sp.add(caption);
		
		add(new PicturePanel());
		add(sp, "South");
		
		try (var rs = stmt.executeQuery("SELECT wh.weddinghall_name, COUNT(1) AS cnt\r\n" + 
				"FROM wedding.weddinghall wh\r\n" + 
				"INNER JOIN reservation r ON wh.weddinghall_index = r.weddinghall_index\r\n" + 
				"WHERE r.pay = 1\r\n" + 
				"GROUP BY wh.weddinghall_index\r\n" + 
				"ORDER BY cnt DESC\r\n" + 
				"LIMIT 5;")) {
			
			while (rs.next()) {
				String name = rs.getString(1);
				
				hotelList.add(new HotelData(name, getToolkit().createImage("./지급자료/호텔이미지/" + name + "/" + name + " 1.jpg")));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		caption.setText("예약 1위 : " + hotelList.get(0).name);
		createThread(this::animate);
	}
	
	private void checkReservation() {
		String input = showInputDialog("예약번호를 입력하세요.");
		
		if (input != null) {
			
			try (var pst = con.prepareStatement("SELECT * FROM reservation WHERE reservation_code = ?")) {
				
				pst.setObject(1, input);
				var rs = pst.executeQuery();
				
				if (rs.next()) {
					revCode = input;
					moveAction(new ReservationCheckFrame()
							.setClosedAction(() -> new MainFrame().setVisible(true)), true);
				} else {
					eMsg("예약번호가 일치하지 않습니다.");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void animate() {
		
		try {
			Thread.sleep(2000);
			
			isAnimate = true;
			
			long cur = System.currentTimeMillis();
			
			while ( (System.currentTimeMillis() - cur) <= 2000 ) {
				percent = ((System.currentTimeMillis() - cur)) / 2000f;
				repaint();
				
				Thread.sleep(30);
			}
			
			isAnimate = false;			
			index++;
			index = index % hotelList.size();
			caption.setText("예약 " + (index + 1) + "위 : " + hotelList.get(index).name);
			repaint();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}

}
