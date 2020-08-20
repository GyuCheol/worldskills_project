package ui;

import java.awt.event.MouseAdapter;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AdminFrame extends FrameBase {

	JTextField tfSearch = new JTextField(20);
	DefaultTableModel dtm = new DefaultTableModel();
	
	public AdminFrame() {
		super(600, 300, "관리자");
		
		dtm.setColumnIdentifiers("id,이름,주소,예식형태,식사종류,수용인원,홀사용료".split(","));
		
		var table = new JTable(dtm) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		var jsp = new JScrollPane(table);
		var np = new JPanel();
		
		table.removeColumn(table.getColumn("id"));
		
		np.add(tfSearch);
		np.add(createButton(new JButton("검색"), e -> search()));
		np.add(createButton(new JButton("등록"), e -> moveAction(new HotelManageFrame()
				.setClosedAction(() -> setVisible(true)), false)));
		np.add(createButton(new JButton("닫기"), e -> moveAction(new MainFrame(), true)));
		
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int row = table.getSelectedRow();
				
				if (e.getClickCount() == 2 && row >= 0) {
					moveAction(new HotelManageFrame((Integer) dtm.getValueAt(row, 0))
							.setClosedAction(() -> setVisible(true)), false);	
				}
			};
			
		});
		
		add(np, "North");
		add(jsp);
		
	}
	
	private void search() {
		dtm.setRowCount(0);
		
		try (var pst = con.prepareStatement("SELECT * " +
				", (SELECT GROUP_CONCAT(wt.weddingtype_name ORDER BY wt.weddingtype_name) FROM weddinghall_weddingtype wht INNER JOIN weddingtype wt ON wht.weddingtype_index = wt.weddingtype_index WHERE wht.weddinghall_index = wh.weddinghall_index) AS wed\r\n" + 
				", (SELECT GROUP_CONCAT(mt.mealtype_name) FROM weddinghall_mealtype whmt INNER JOIN mealtype mt ON whmt.mealtype_index = mt.mealtype_index WHERE whmt.weddinghall_index = wh.weddinghall_index) AS mt\r\n" +
				"FROM weddinghall wh WHERE weddinghall_name LIKE ?")) {
			
			pst.setObject(1, "%" + tfSearch.getText() + "%");
			
			var rs = pst.executeQuery();
			
			while (rs.next()) {
				dtm.addRow(new Object[] {
					rs.getObject(1), rs.getObject(2), rs.getObject(3), rs.getObject(6), rs.getObject(7), rs.getObject(4), rs.getObject(5)
				});
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		new AdminFrame().setVisible(true);
	}

}
