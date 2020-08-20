package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PopularChartFrame extends FrameBase {
	
	JComboBox<String> cbCategories = new JComboBox<String>();
	ArrayList<PieData> dataList = new ArrayList<PopularChartFrame.PieData>();
	Color[] colors = { Color.BLACK, Color.BLUE, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.DARK_GRAY, Color.CYAN, Color.PINK, Color.LIGHT_GRAY };
	float percent = 0f;
	JPanel sp = new JPanel(null);
	int total = 0;
	int selectedIndex = -1;
	DefaultTableModel dtm = new DefaultTableModel();
	JScrollPane jsp = new JScrollPane(new JTable(dtm)); 
			
	static class PieData {
		String name;
		int value;
		Color color;
		int index;
		
		public PieData(String name, int value, Color color, int index) {
			this.name = name;
			this.value = value;
			this.color = color;
			this.index = index; 
		}
	}
	
	class PieChart extends JPanel {
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			float start = 0f;
			
			Graphics2D g2d = (Graphics2D) g; 
			
			for (int i = 0; i < dataList.size(); i++) {
				float angle = 360f * ((float) dataList.get(i).value / total);
				float end = angle * percent;

				Arc2D.Float arc = new Arc2D.Float(10, 10, 270, 270, start, end, Arc2D.PIE);
				
				if (i != selectedIndex) {
					g2d.setColor(dataList.get(i).color);
				} else {
					g2d.setColor(Color.magenta);
				}
				
				g2d.fill(arc);
				
				start += angle;
			}
			
			g2d.setColor(Color.WHITE);
			g2d.fillOval(10 + 85, 10 + 85, 100, 100);
			
		}
		
	}
	
	public PopularChartFrame() {
		super(320, 600, "인기 차트");
		
		setLayout(null);
		
		dtm.setColumnIdentifiers("이름,주소,홀사용료".split(","));
		
		add(createComp(cbCategories, 5, 5, 280, 30));
		add(createComp(new PieChart(), 15, 50, 280, 280));
		add(createComp(sp, 5, 340, 280, 300));
		add(createComp(jsp, 320, 35, 300, 500));
		
		String[] query = {
				"SELECT wt.weddingtype_name, COUNT(1), wt.weddingtype_index\r\n" + 
				"FROM wedding.reservation r\r\n" + 
				"INNER JOIN weddingtype wt ON wt.weddingtype_index = r.weddingtype_index\r\n" + 
				"WHERE r.pay = 1\r\n" + 
				"GROUP BY wt.weddingtype_index\r\n" + 
				"ORDER BY COUNT(1) DESC;",
				
				"SELECT mt.mealtype_name, COUNT(1), mt.mealtype_index\r\n" + 
				"FROM wedding.reservation r\r\n" + 
				"INNER JOIN mealtype mt ON mt.mealtype_index = r.mealtype_index\r\n" + 
				"WHERE r.pay = 1\r\n" + 
				"GROUP BY mt.mealtype_index\r\n" + 
				"ORDER BY COUNT(1) DESC;"
		};
		
		cbCategories.addActionListener(e -> {
			sp.removeAll();
			
			hideList();
			
			if (cbCategories.getSelectedIndex() == -1) {
				return;				
			}
			
			ArrayList<Color> colorList = new ArrayList<>(Arrays.asList(colors));
			Random rnd = new Random();
			
			dataList.clear();
			total = 0;
			selectedIndex = -1;
			
			try (var rs = stmt.executeQuery(query[cbCategories.getSelectedIndex()])) {
				
				while (rs.next()) {
					int r = rnd.nextInt(colorList.size());
					Color color = colorList.get(r);
					
					colorList.remove(r);
					
					total += rs.getInt(2);
					dataList.add(new PieData(rs.getString(1), rs.getInt(2), color, rs.getInt(3)));
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			updateLegends();
			createThread(this::animate);
		});
		
		cbCategories.addItem("인기 웨딩 종류");
		cbCategories.addItem("인기 식사 종류");
	}
	
	private void updateLegends() {
		sp.removeAll();
		
		for (int i = 0; i < dataList.size(); i++) {
			var data = dataList.get(i);
			var lb = createComp(new JLabel(), 70, i * 20, 15, 15);
			var lb2 = createComp(new JLabel(String.format("%s : %d개", data.name, data.value)), 120, i * 20, 100, 15);
			
			lb.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			lb.setOpaque(true);
			
			if (i != selectedIndex) {
				lb.setBackground(data.color);
			} else {
				lb.setBackground(Color.magenta);
			}
			
			lb2.setFont(new Font("돋움", 0, 12));
			
			final int id = i;
			
			lb.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					selectedIndex = id;
					
					showList();
					updateLegends();
					repaint();
				}
				
			});
			
			sp.add(lb);
			sp.add(lb2);
		}
		
		sp.repaint();
	}
	
	private void hideList() {
		setSize(320, 600);
		jsp.setVisible(false);
	}
	
	private void showList() {
		setSize(650, 600);
		jsp.setVisible(true);
		dtm.setRowCount(0);
		
		String field = cbCategories.getSelectedIndex() == 0 ? "weddingtype" : "mealtype";
		String sql = String.format("SELECT * \r\n" + 
				"FROM wedding.weddinghall w\r\n" + 
				"WHERE weddinghall_index IN (SELECT weddinghall_index FROM weddinghall_%1$s WHERE %1$s_index = " + dataList.get(selectedIndex).index + ");", field);
		
		try (var rs = stmt.executeQuery(sql)) {
		
			while (rs.next()) {
				dtm.addRow(new Object[] {
						rs.getObject(2),
						rs.getObject(3),
						String.format("%,d원", rs.getInt(5))
				});
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void animate() {
		percent = 0f;
		cbCategories.setEnabled(false);
		
		long l = System.currentTimeMillis();
		
		try {
			while ((System.currentTimeMillis() - l) <= 2000) {
				percent = (System.currentTimeMillis() - l) / 2000f;
				repaint();
				Thread.sleep(30);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		isRun = false;
		cbCategories.setEnabled(true);
		percent = 1f;
		repaint();
	}
	
	public static void main(String[] args) {
		new PopularChartFrame().setVisible(true);
	}

}
