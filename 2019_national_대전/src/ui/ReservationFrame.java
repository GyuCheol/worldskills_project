package ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

public class ReservationFrame extends FrameBase {	
	int index;
	DefaultTableModel dtm;
	ImagePanel[] imgPanel = new ImagePanel[6];
	JTextField[] textFields = new JTextField[6];
	JComboBox<String> cbWedding;
	JComboBox<String> cbMeal;
	
	Calendar date = Calendar.getInstance();
	
	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat yymmdd = new SimpleDateFormat("yyMMdd");
	SimpleDateFormat ym = new SimpleDateFormat("yyyy년 M월");
	
	HashSet<String> reservedSet = new HashSet<>();
	CalendarPanel[] calendar = { new CalendarPanel(0), new CalendarPanel(1) };
	String lastClickedDate;
	JToggleButton lastClickedToggle;
	
	HashMap<String, Integer> priceMap = getMapInfo("mealtype", 2, 3);
	HashMap<String, Integer> mealIdMap = getMapInfo("mealtype", 2, 1);
	HashMap<String, Integer> weddingIdMap = getMapInfo("weddingtype", 2, 1);
	
	class CalendarPanel extends JPanel {
		
		JPanel cp = new JPanel(new GridLayout(7, 7));
		JLabel caption = new JLabel("", 0);
		int monthSync;
		
		CalendarPanel(int monthSync) {
			this.monthSync = monthSync;
			
			setLayout(new BorderLayout());
			
			add(caption, "North");
			add(cp);
			
			update();
		}
		
		void update() {
			cp.removeAll();
			
			for (var c : "일,월,화,수,목,금,토".split(",")) {
				cp.add(new JLabel(c, 0));
			}
			
			Calendar c = Calendar.getInstance();
			
			c.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + monthSync, 1);
			
			int m = c.get(Calendar.MONTH);
			
			caption.setText(ym.format(c.getTime()));
			
			c.add(Calendar.DATE, -(c.get(Calendar.DAY_OF_WEEK) - 1));
			
			for (int y = 0; y < 6; y++) {
				for (int x = 0; x < 7; x++) {
					
					if (c.get(Calendar.MONTH) == m) {
						var tog = new JToggleButton("" + c.get(Calendar.DATE));
						Calendar tmp = (Calendar) c.clone();
						
						tog.addActionListener(e -> clickDate(tog, tmp));
						
						cp.add(tog);
						
						tog.setMargin(new Insets(0, 0, 0, 0));
						
						if (reservedSet.contains(ymd.format(c.getTime()))) {
							tog.setEnabled(false);
						}
						
						if (ymd.format(c.getTime()).equals(lastClickedDate)) {
							tog.setSelected(true);
							lastClickedToggle = tog;
						}
						
					} else {
						cp.add(new JLabel());
					}
					
					c.add(Calendar.DATE, 1);
				}
			}
			
			repaint();
		}
	}
	
	private void clickDate(JToggleButton tog, Calendar c) {
		if (tog.isSelected() == false) {
			lastClickedDate = null;
			lastClickedToggle = null;
			return;
		}

		Calendar today = Calendar.getInstance();
		String ymd1 = ymd.format(c.getTime());
		String ymd2 = ymd.format(today.getTime());
		
		if (ymd1.compareTo(ymd2) <= 0) {
			eMsg("이미 지난 날짜와 당일 예약은 불가능합니다.");
			tog.setSelected(false);
			
			return;
		}
		
		if (lastClickedToggle != null) {
			lastClickedToggle.setSelected(false);
		}
		
		lastClickedToggle = tog;
		lastClickedDate = ymd1;
		
	}
	
	public ReservationFrame(DefaultTableModel dtm, int index) {
		super(700, 950, "예약");
		
		this.dtm = dtm;
		this.index = index;
		
		for (int i = 0; i < imgPanel.length; i++) {
			imgPanel[i] = new ImagePanel(null, this::clickImg);
		}
		
		add(createButton(new JButton("◀"), e -> moveHotel(-1)), "West");
		add(createButton(new JButton("▶"), e -> moveHotel(1)), "East");
		add(createButton(new JButton("예약하기"), e -> clickReserve()), "South");
		
		var cp = new JPanel(new GridLayout(3, 1));
		var cp2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		var cp3 = new JPanel(new BorderLayout());
		var cp3_1 = new JPanel(new GridLayout(1, 2));
		var cp3_2 = new JPanel(new GridLayout(1, 2));
		var cp1 = new JPanel(new BorderLayout());
		var cp1_2 = createComp(new JPanel(new GridLayout(5, 1)), 80, 0);
		
		cp1.add(imgPanel[0]);
		cp1.add(cp1_2, "East");
		
		for (int i = 1; i < 6; i++) {
			cp1_2.add(imgPanel[i]);
			imgPanel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		
		for (int i = 0; i < 6; i++) {
			textFields[i] = createComp(new JTextField(), 450, 25);
			textFields[i].setEnabled(i == 5);
		}
		
		cbWedding = createComp(new JComboBox<String>(), 430, 25);
		cbMeal = createComp(new JComboBox<String>(), 430, 25);
		
		JComponent[] comp = { textFields[0], textFields[1], textFields[2], textFields[3], 
				cbWedding, cbMeal, textFields[4], textFields[5]};
		
		var columns = "웨딩홀명,주소,수용인원,홀사용료,예식형태,식사종류,식사비용,인원수".split(",");
		
		for (int i = 0; i < comp.length; i++) {
			cp2.add(createComp(new JLabel(columns[i]), 70, 20));
			cp2.add(comp[i]);
		}
		
		cp3_1.add(createButton(new JButton("◁"), x -> updateMonth(-1)));
		cp3_1.add(createButton(new JButton("▷"), x -> updateMonth(1)));
		
		cp3_2.add(calendar[0]);
		cp3_2.add(calendar[1]);
		
		cp3.add(cp3_1, "North");
		cp3.add(cp3_2);
		
		cp.add(cp1);
		cp.add(cp2);
		cp.add(cp3);
		
		add(cp);
		
		// 가격 맵
		try (var rs = stmt.executeQuery("SELECT * FROM mealtype")) {
			
			while (rs.next()) {
				priceMap.put(rs.getString(2), rs.getInt(3));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		cbMeal.addActionListener(e -> {
			if (cbMeal.getSelectedItem() != null) {
				textFields[4].setText(priceMap.get(cbMeal.getSelectedItem()).toString());				
			}
		});
		
		updateMonth(0);
		initInfo();
	}
	
	private void clickReserve() {
		
		if (lastClickedDate == null) {
			eMsg("날짜를 선택해주세요");
			return;
		}
		
		try {
			int people = Integer.parseInt(textFields[5].getText());
			int limit = (Integer) dtm.getValueAt(index, 5);
			
			if (people >= limit) {
				throw new NumberFormatException();
			}
			
			String reservedNo = yymmdd.format(ymd.parse(lastClickedDate)) + String.format("%02d", dtm.getValueAt(index, 0));
			
			int answer = JOptionPane.showInternalOptionDialog(null, 
					"<html>예약이 완료되었습니다.<br/>예약번호는 " + reservedNo + "입니다.</html>",
					"예약완료",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					"클립보드에 복사,확인".split(","),
					null);
			
			try (var pst = con.prepareStatement("INSERT INTO reservation VALUES(?, ?, ?, ?, ?, 0, 0, 0, ?, 0)")) {
				pst.setObject(1, reservedNo);
				pst.setObject(2, dtm.getValueAt(index, 0));
				pst.setObject(3, textFields[5].getText());
				pst.setObject(4, weddingIdMap.get(cbWedding.getSelectedItem()));
				pst.setObject(5, mealIdMap.get(cbMeal.getSelectedItem()));
				pst.setObject(6, lastClickedDate);
				
				pst.execute();
			}
			
			if (answer == JOptionPane.YES_OPTION) {
				var clipboard = getToolkit().getSystemClipboard();
				var content = new StringSelection(reservedNo);
						
				clipboard.setContents(content, null);
				
				iMsg("복사가 완료되었습니다.");
			}
			
		} catch (NumberFormatException e) {
			eMsg("인원수를 바르게 입력해주세요.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateMonth(int month) {
		date.add(Calendar.MONTH, month);
		
		Arrays.stream(calendar).forEach(x -> x.update());
	}
	
	private void moveHotel(int id) {
		index += id;
		
		if (index == -1) {
			index = dtm.getRowCount() - 1;
		} else if (index == dtm.getRowCount()) {
			index = 0;
		}
		
		lastClickedDate = null;
		initInfo();
	}
	
	private void initInfo() {
		// Info
		textFields[0].setText(dtm.getValueAt(index, 1).toString());
		textFields[1].setText(dtm.getValueAt(index, 2).toString());
		textFields[2].setText(dtm.getValueAt(index, 5).toString());
		textFields[3].setText(dtm.getValueAt(index, 7).toString());
		
		cbWedding.removeAllItems();
		cbMeal.removeAllItems();
		
		for (var item : dtm.getValueAt(index, 3).toString().split(",")) {
			cbWedding.addItem(item);
		}
		
		for (var item : dtm.getValueAt(index, 4).toString().split(",")) {
			cbMeal.addItem(item);
		}
		
		// Image Loading
		String hotel = (String) dtm.getValueAt(index, 1);
		
		try {
			var files = Files.list(Paths.get("./지급자료/호텔이미지/" + hotel)).toArray(Path[]::new);
			
			for (int i = 0; i < 5; i++) {
				if (i < files.length) {
					imgPanel[i + 1].img = ImageIO.read(new File(files[i].toUri()));
				} else {
					imgPanel[i + 1].img = null;
				}
			}
			
			imgPanel[0].img = imgPanel[1].img;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		reservedSet.clear();
		
		try (var rs = stmt.executeQuery("SELECT * FROM reservation WHERE weddinghall_index = " + dtm.getValueAt(index, 0))) {
			
			while (rs.next()) {
				reservedSet.add(rs.getString(9));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateMonth(0);
		repaint();
		
	}
	
	private void clickImg(ActionEvent e) {
		var img = (ImagePanel) (e.getSource());
		
		imgPanel[0].img = img.img;
		imgPanel[0].repaint();
	}

	public static void main(String[] args) {
		
		new SearchFrame().setVisible(true);
	}

}
