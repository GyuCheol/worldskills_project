package ui;

import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

public class ReservationCheckFrame extends FrameBase {

	JTextField[] textFields = new JTextField[6];
	
	HashMap<String, Integer> priceMap = getMapInfo("mealtype", 2, 3);
	HashMap<String, Integer> mealIdMap = getMapInfo("mealtype", 2, 1);
	HashMap<String, Integer> weddingIdMap = getMapInfo("weddingtype", 2, 1);
	JComboBox<String> cbWedding;
	JComboBox<String> cbMeal;
	JCheckBox[] checkboxes = new JCheckBox[3];
	String date, address;
	
	int limitPeople;
	
	public ReservationCheckFrame() {
		super(610, 420, "예약확인");
		
		var cp = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
		var sp = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 15));
		
		for (int i = 0; i < 6; i++) {
			textFields[i] = createComp(new JTextField(), 470, 25);
			textFields[i].setEnabled(i == 5);
		}
		
		textFields[5] = createComp(textFields[5], 110, 25);
		
		cbWedding = createComp(new JComboBox<String>(), 450, 25);
		cbMeal = createComp(new JComboBox<String>(), 450, 25);
		
		JComponent[] comp = { textFields[0], textFields[1], textFields[2], textFields[3],
				cbWedding, cbMeal, textFields[4], textFields[5]};
		
		var columns = "웨딩홀명,주소,수용인원,홀사용료,예식형태,식사종류,식사비용,인원수".split(",");
		
		for (int i = 0; i < columns.length; i++) {
			cp.add(createComp(new JLabel(columns[i]), 50, 20));
			cp.add(comp[i]);
		}
		
		var checkboxColumns = "앨범제작,청첩장,드레스".split(",");
		
		for (int i = 0; i < checkboxColumns.length; i++) {
			checkboxes[i] = new JCheckBox(checkboxColumns[i]);
			cp.add(checkboxes[i]);
		}
		
		var buttonText = "청첩장 수정,예약 변경,예약 취소,결제하기,닫기".split(",");
		var btns = new JButton[buttonText.length];
		
		for (int i = 0; i < buttonText.length; i++) {
			btns[i] = createComp(new JButton(buttonText[i]), 100, 25);
			
			sp.add(btns[i]);
		}
		
		// 수정
		btns[0].addActionListener(e -> {
			moveAction(new LetterSelectionFrame(address, date)
					.setClosedAction(() -> setVisible(true)), false);
		});
		
		// 변경
		btns[1].addActionListener(e -> clickUpdate());
		
		// 취소
		btns[2].addActionListener(e -> {
			runSQL("DELETE FROM reservation WHERE reservation_code = " + revCode);
			iMsg("취소되었습니다.");
			moveAction(new MainFrame(), true);
		});
		
		// 결제
		btns[3].addActionListener(e -> moveAction(new PaymentFrame(revCode)
				.setClosedAction(() -> setVisible(true)), false));

		// 닫기
		btns[4].addActionListener(e -> moveAction(new MainFrame(), true));
		
		
		try (var rs = stmt.executeQuery("SELECT r.*, wh.weddinghall_name, wh.weddinghall_address, wh.weddinghall_accommodate, wh.weddinghall_fee, wt.weddingtype_name, mt.mealtype_name\r\n" + 
				", (SELECT GROUP_CONCAT(wt.weddingtype_name ORDER BY wt.weddingtype_name) FROM weddinghall_weddingtype wht INNER JOIN weddingtype wt ON wht.weddingtype_index = wt.weddingtype_index WHERE wht.weddinghall_index = wh.weddinghall_index) AS wed\r\n" + 
				", (SELECT GROUP_CONCAT(mt.mealtype_name) FROM weddinghall_mealtype whmt INNER JOIN mealtype mt ON whmt.mealtype_index = mt.mealtype_index WHERE whmt.weddinghall_index = wh.weddinghall_index) AS meal\r\n" + 
				"FROM wedding.reservation r\r\n" + 
				"INNER JOIN weddinghall wh ON r.weddinghall_index = wh.weddinghall_index\r\n" + 
				"INNER JOIN weddingtype wt ON r.weddingtype_index = wt.weddingtype_index\r\n" + 
				"INNER JOIN mealtype mt ON r.mealtype_index = mt.mealtype_index\r\n" + 
				"WHERE r.reservation_code = '" + revCode + "';")) {
			
			if (rs.next()) {
				
				textFields[0].setText(rs.getString(11));
				textFields[1].setText(rs.getString(12));
				textFields[2].setText(rs.getString(13));
				textFields[3].setText(rs.getString(14));
				
				address = rs.getString(12);
				date = rs.getString(9);
				
				limitPeople = rs.getInt(13);
				
				textFields[5].setText(rs.getString(3));
				
				cbMeal.addActionListener(e -> {
					if (cbMeal.getSelectedItem() != null) {
						textFields[4].setText(priceMap.get(cbMeal.getSelectedItem()).toString());
					}
				});
				
				for (var item : rs.getString(17).split(",")) {
					cbWedding.addItem(item);
				}
				
				for (var item : rs.getString(18).split(",")) {
					cbMeal.addItem(item);
				}
				
				
				cbWedding.setSelectedItem(rs.getString(15));
				cbMeal.setSelectedItem(rs.getString(16));
				
				for (int i = 0; i < 3; i++) {
					
					if (rs.getInt(6 + i) == 1) {
						checkboxes[i].setSelected(true);
					}
					
				}
				
				if (rs.getInt(10) == 1) {
					for (int i = 1; i <= 3; i++) {
						btns[i].setEnabled(false);
					}
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		add(cp);
		add(sp, "South");
		
	}
	
	private void clickUpdate() {
		try {
			int people = Integer.parseInt(textFields[5].getText());
			
			if (people > limitPeople) {
				throw new NumberFormatException();
			}
			
			try (var pst = con.prepareStatement("UPDATE reservation "
					+ "SET reservation_personnel = ?,"
					+ "weddingtype_index = ?,"
					+ "mealtype_index = ?,"
					+ "album = ?,"
					+ "letter = ?,"
					+ "dress = ? "
					+ "WHERE reservation_code = " + revCode)) {
			
				pst.setObject(1, people);
				pst.setObject(2, weddingIdMap.get(cbWedding.getSelectedItem()));
				pst.setObject(3, mealIdMap.get(cbMeal.getSelectedItem()));
				
				for (int i = 0; i < 3; i++) {
					pst.setObject(4 + i, checkboxes[i].isSelected() ? 1 : 0);
				}
				
				pst.executeUpdate();
			}
			
			iMsg("변경되었습니다.");
			
		} catch (NumberFormatException e1) {
			eMsg("인원수를 바르게 입력해주세요.");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// paid
		// revCode = "19060901";
		
		revCode = "19091614";
		
		// not paid
		new ReservationCheckFrame()
			.setVisible(true);
	}

}
