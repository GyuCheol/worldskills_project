package ui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SearchFrame extends FrameBase {

	DefaultTableModel dtm = new DefaultTableModel();
	JComboBox<String> cbRegion = new JComboBox<>("��ü,�����,���ı�,������,�߱�,������,���ʱ�,��������,���α�".split(","));
	JComboBox<String> cbWeddingType = new JComboBox<>("��ü,�Ϲݿ���Ȧ,����,�Ͽ콺,ȣ�ڿ���Ȧ,�߿ܿ���,������,�������,ȸ��,����,��ȸ".split(","));
	JComboBox<String> cbMealType = new JComboBox<>("��ü,���,����,�ѽ�".split(","));
	JTextField[] textFields = { new JTextField(), new JTextField(), new JTextField(), new JTextField()};

	public SearchFrame() {
		super(900, 500, "�˻�");
		
		dtm.setColumnIdentifiers("id,�̸�,�ּ�,��������,�Ļ�����,�����ο�,Ȧ����,Ȧ����2".split(","));
		
		var wp = createCompBorder(createComp(new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15)), 250, 0), new EmptyBorder(100, 0, 0, 0));
		var table = new JTable(dtm);
		var jsp = new JScrollPane(table);
		
		table.removeColumn(table.getColumn("id"));
		table.removeColumn(table.getColumn("Ȧ����2"));
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				int row = table.getSelectedRow();
				
				if (row > -1) {
					moveAction(new ReservationFrame(dtm, row)
							.setClosingAction(() -> setVisible(true)), false);
				}
			};
		});
		
		add(wp, "West");
		add(jsp);
		
		wp.add(createComp(new JLabel("���� : ", 4), 80, 20));
		wp.add(createComp(cbRegion, 150, 25));
		wp.add(createComp(new JLabel("�������� : ", 4), 80, 20));
		wp.add(createComp(cbWeddingType, 150, 25));
		wp.add(createComp(new JLabel("�Ļ����� : ", 4), 80, 20));
		wp.add(createComp(cbMealType, 150, 25));
		
		wp.add(createComp(new JLabel("�����ο� : ", 4), 80, 20));
		wp.add(createComp(textFields[0], 70, 25));
		wp.add(new JLabel("~"));
		wp.add(createComp(textFields[1], 70, 25));
		
		wp.add(createComp(new JLabel("Ȧ���� : ", 4), 80, 20));
		wp.add(createComp(textFields[2], 70, 25));
		wp.add(new JLabel("~"));
		wp.add(createComp(textFields[3], 70, 25));
		
		wp.add(createButton(new JButton("�ʱ�ȭ"), e -> {
			cbMealType.setSelectedIndex(0);
			cbRegion.setSelectedIndex(0);
			cbWeddingType.setSelectedIndex(0);
			
			Arrays.stream(textFields).forEach(x -> x.setText(""));
			
			dtm.setRowCount(0);
		}));
		
		wp.add(createButton(new JButton("�˻�"), e -> search()));
	}

	public void search() {
		int[] values = { Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE };
		
		try {
			
			
			for (int i = 0; i < 4; i++) {
				if (textFields[i].getText().length() > 0) {
					values[i] = Integer.parseInt(textFields[i].getText());
				}
			}
			
			if (isNumeric(textFields[0].getText()) != isNumeric(textFields[1].getText()) || 
					isNumeric(textFields[2].getText()) != isNumeric(textFields[3].getText()) ||
					values[1] < values[0] || values[3] < values[2]) {
				eMsg("���ڸ� �ùٸ��� �Է����ּ���.");
				return;
			}
			
			try (var rs = stmt.executeQuery("SELECT * \r\n" + 
					", (SELECT GROUP_CONCAT(wt.weddingtype_name ORDER BY wt.weddingtype_name) FROM weddinghall_weddingtype wht INNER JOIN weddingtype wt ON wht.weddingtype_index = wt.weddingtype_index WHERE wht.weddinghall_index = wh.weddinghall_index) AS wed\r\n" + 
					", (SELECT GROUP_CONCAT(mt.mealtype_name) FROM weddinghall_mealtype whmt INNER JOIN mealtype mt ON whmt.mealtype_index = mt.mealtype_index WHERE whmt.weddinghall_index = wh.weddinghall_index) AS mt\r\n" + 
					"FROM wedding.weddinghall wh;")) {
				
				String sRegion = (String) cbRegion.getSelectedItem();
				String sWedding = (String) cbWeddingType.getSelectedItem();
				String sMeal = (String) cbMealType.getSelectedItem();
				
				dtm.setRowCount(0);
				
				while (rs.next()) {
					String region = rs.getString(3);
					String wedding = rs.getString(6);
					String meal = rs.getString(7);
					int fee = rs.getInt(5);
					int people = rs.getInt(4);
					
					if (cbRegion.getSelectedIndex() > 0 && region.contains(sRegion) == false) {
						continue;
					}
					
					if (cbWeddingType.getSelectedIndex() > 0 && wedding.contains(sWedding) == false) {
						continue;
					}
					
					if (cbMealType.getSelectedIndex() > 0 && meal.contains(sMeal) == false) {
						continue;
					}
					
					if (people < values[0] || people > values[1]) {
						continue;
					}
					
					if (fee < values[2] || fee > values[3]) {
						continue;
					}
					
					dtm.addRow(new Object[] {
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(6),
						rs.getString(7),
						rs.getInt(4),
						String.format("%,d��", rs.getInt(5)),
						rs.getInt(5)
					});
				}
			}
						
		} catch (NumberFormatException e) {
			eMsg("�����ο��� Ȧ����� ���ڸ� �Է� �����մϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new SearchFrame().setVisible(true);
	}

}
