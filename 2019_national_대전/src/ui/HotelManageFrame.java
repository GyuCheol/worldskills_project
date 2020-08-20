package ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HotelManageFrame extends FrameBase {

	int index;
	int imgId;
	JTextField[] textFields = new JTextField[4];
	JPanel picturePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	JButton btnSubmit = new JButton("등록");
	ArrayList<JCheckBox> wedding = new ArrayList<>();
	ArrayList<JCheckBox> meal = new ArrayList<>();
	JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
	
	public HotelManageFrame() {
		super(780, 430, "등록");
		
		chooser.setFileFilter(new FileNameExtensionFilter("JPG 파일", "jpg"));
		
		var columns = "웨딩홀명,주소,수용인원,홀사용료".split(",");
		var cp = new JPanel(null);
		var sp = new JPanel();
		var weddingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		var mealPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		try {
			// Wedding
			try (var rs = stmt.executeQuery("SELECT * FROM weddingtype")) {
				while (rs.next()) {
					var chk = new JCheckBox(rs.getString(2));
					
					chk.setName(rs.getString(1));
					wedding.add(createComp(chk, 80, 13));
					weddingPanel.add(chk);
				}
			}
			
			// Meal
			try (var rs = stmt.executeQuery("SELECT * FROM mealtype")) {
				while (rs.next()) {
					var chk = new JCheckBox(rs.getString(2));
					
					chk.setName(rs.getString(1));
					meal.add(createComp(chk, 80, 13));
					mealPanel.add(chk);
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		for (int i = 0; i < columns.length; i++) {
			textFields[i] = new JTextField();
			
			cp.add(createComp(new JLabel(columns[i]), 10, 30 + 50 * i, 100, 25));
			cp.add(createComp(textFields[i], 110, 30 + 50 * i, 500, 25));
		}
		
		cp.add(createComp(new JLabel("예식형태"), 30, 220, 80, 25));
		cp.add(createComp(weddingPanel, 120, 220, 500, 26));
		
		cp.add(createComp(new JLabel("식사종류"), 30, 270, 80, 13));
		cp.add(createComp(mealPanel, 120, 270, 500, 13));
		
		sp.add(createComp(createButton(btnSubmit, e -> submit()), 100, 25));
		sp.add(createComp(createButton(new JButton("취소"), e -> dispose()), 100, 25));
		
		picturePanel.add(createImageField());
		
		// First Image
		add(cp);
		add(createComp(picturePanel, 130, 0), "West");
		add(sp, "South");
	}
	
	public HotelManageFrame(int index) {
		this();
		
		this.index = index;
		
		textFields[2].setEditable(false);
		
		btnSubmit.setText("수정");
		setTitle("수정");
		
		try {			
			// Data Load
			try (var rs = stmt.executeQuery("SELECT * FROM weddinghall WHERE weddinghall_index = " + index)) {
				if (rs.next()) {
					textFields[0].setText(rs.getString(2));
					textFields[1].setText(rs.getString(3));
					textFields[2].setText(rs.getString(4));
					textFields[3].setText(rs.getString(5));
				}
			}
			
			HashSet<String> weddingtype = new HashSet<>();
			HashSet<String> mealtype = new HashSet<>();
			
			// Wedding Type Load
			try (var rs = stmt.executeQuery("SELECT * FROM weddinghall_weddingtype WHERE weddinghall_index = " + index)) {
				while (rs.next()) {
					weddingtype.add(rs.getString(2));
				}
			}
			
			for (var chk : wedding) {
				if (weddingtype.contains(chk.getName())) {
					chk.setSelected(true);
				}
			}
			
			// Meal Type Load
			try (var rs = stmt.executeQuery("SELECT * FROM weddinghall_mealtype WHERE weddinghall_index = " + index)) {
				while (rs.next()) {
					mealtype.add(rs.getString(2));
				}
			}
			
			for (var chk : meal) {
				if (mealtype.contains(chk.getName())) {
					chk.setSelected(true);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		loadImages();
	}
	
	private ImagePanel createImageField() {
		var img = createComp(new ImagePanel(null, null), 130, 70);
		imgId++;
		
		final int curImageId = imgId;
		
		img.setCursor(new Cursor(Cursor.HAND_CURSOR));
		img.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				chooser.showOpenDialog(null);
				
				var file = chooser.getSelectedFile();
				
				if (file != null) {
					try {
						img.img = ImageIO.read(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// 다음 이미지 없을 시 생성
					
					if (curImageId < 5 && curImageId == picturePanel.getComponentCount()) {
						picturePanel.add(createImageField());
						picturePanel.revalidate();
					}
					
					picturePanel.repaint();
				}
			}
		});
		
		return img;
	}
	
	private void submit() {
		if (index != 0) {
			updateData();
		} else {
			createData();
		}
	}
	
	private void loadImages() {
		// 기존 이미지 삭제
		picturePanel.removeAll();
		imgId = 0;
		
		// Image Load
		String hotel = textFields[0].getText();
		try {
			var files = Files.list(Paths.get("./지급자료/호텔이미지/" + hotel)).toArray(Path[]::new);
			
			for (var file : files) {
				var img = createImageField();
				
				img.img = ImageIO.read(new File(file.toString()));
				
				picturePanel.add(img);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		picturePanel.repaint();
	}
	
	private void saveImages() {
		String hotel = textFields[0].getText();
		
		int index = 0;
		
		String dir = "./지급자료/호텔이미지/" + hotel;
		
		try {
			for (Component comp : picturePanel.getComponents()) {
				index++;
				
				var img = (ImagePanel) comp;
				
				if (img.img == null) {
					continue;
				}
				
				String fileName = dir + "/" + hotel + " " + index + ".jpg";
				
				if (Files.exists(Paths.get(fileName)) == false) {
					Files.createDirectories(Paths.get(dir));
					Files.writeString(Paths.get(fileName), "", StandardOpenOption.CREATE);
				}
				
				ImageIO.write((RenderedImage) img.img, "JPG", new File(fileName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateData() {
		// 수정 모드
		if (validation() == false) {
			return;
		}
		
		try {
			try (var pst = con.prepareStatement("UPDATE weddinghall SET weddinghall_name = ?, weddinghall_address = ?, weddinghall_accommodate = ?, weddinghall_fee = ? WHERE weddinghall_index = ?")) {
				pst.setObject(5, index);
				
				for (int i = 0; i < textFields.length; i++) {
					pst.setObject(i + 1, textFields[i].getText());
				}
				
				pst.execute();
			}
			
			runSQL("DELETE FROM weddinghall_weddingtype WHERE weddinghall_index = " + index);
			runSQL("DELETE FROM weddinghall_mealtype WHERE weddinghall_index = " + index);
			
			// weddingtype
			wedding.stream().filter(x -> x.isSelected()).forEach(x -> {
				runSQL("INSERT INTO weddinghall_weddingtype VALUES(" + index + ", " + x.getName() +")");
			});
			
			// mealtype
			meal.stream().filter(x -> x.isSelected()).forEach(x -> {
				runSQL("INSERT INTO weddinghall_mealtype VALUES(" + index + ", " + x.getName() +")");
			});
			
			saveImages();
			iMsg("수정이 완료되었습니다.");
			dispose();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	private boolean validation() {
		boolean flag = false;
		
		for (int i = 0; i < textFields.length; i++) {
			if (textFields[i].getText().length() == 0) {
				flag = true;
			}
		}
		
		if (flag || 
				wedding.stream().allMatch(x -> x.isSelected() == false) ||
				meal.stream().allMatch(x -> x.isSelected() == false)) {
			eMsg("빈칸을 입력해주세요");
			return false;
		}
		
		if (isNumeric(textFields[2].getText()) == false) {
			eMsg("수용인원을 바르게 입력해주세요.");
			
			return false;			
		}
		
		if (isNumeric(textFields[3].getText()) == false) {
			eMsg("홀사욜료를 바르게 입력해주세요.");
			
			return false;			
		}
				
		return true;
	}
	
	private void createData() {
		// 등록 모드
		if (validation() == false) {
			return;
		}
		
		try {
			
			int lastid = 1;
			
			try (var rs = stmt.executeQuery("SELECT IFNULL(MAX(weddinghall_index), 0) FROM weddinghall")) {
				rs.next();
				
				lastid = rs.getInt(1);
			}
			
			final int newId = lastid + 1;
			
			try (var pst = con.prepareStatement("INSERT INTO weddinghall VALUES(?, ?, ?, ?, ?)")) {
				System.out.println(newId);
				pst.setObject(1, newId);
				
				for (int i = 0; i < textFields.length; i++) {
					pst.setObject(i + 2, textFields[i].getText());
				}
				
				pst.execute();
			}
			
			// weddingtype
			wedding.stream().filter(x -> x.isSelected()).forEach(x -> {
				runSQL("INSERT INTO weddinghall_weddingtype VALUES(" + newId + ", " + x.getName() +")");
			});
			
			// mealtype
			meal.stream().filter(x -> x.isSelected()).forEach(x -> {
				runSQL("INSERT INTO weddinghall_mealtype VALUES(" + newId + ", " + x.getName() +")");
			});
			
			// Image 생성
			saveImages();
			iMsg("등록이 완료되었습니다.");
			dispose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new HotelManageFrame().setVisible(true);
	}

}
