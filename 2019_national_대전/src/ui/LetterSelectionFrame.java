package ui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LetterSelectionFrame extends FrameBase {
	
	int index = 0;
	JLabel lbTop = new JLabel("0번 이미지", 0);
	ImagePanel imgPanel = new ImagePanel(null, null);
	JTextField tfName1 = new JTextField();
	JTextField tfName2 = new JTextField();
	
	public LetterSelectionFrame(String address, String date) {
		super(300, 500, "청첩장 디자인 고르기");
		
		var sp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		imgPanel.setLayout(null);
		
		sp.add(createButton(new JButton("◀"), e -> updateInfo(-1)));
		sp.add(createButton(new JButton("결정"), e -> clickApply()));
		sp.add(createButton(new JButton("▶"), e -> updateInfo(1)));
		
		imgPanel.add(createComp(tfName1, 85, 130, 50, 25));
		imgPanel.add(createComp(tfName2, 160, 130, 50, 25));
		imgPanel.add(createComp(new JLabel(address), 100, 240, 190, 25));
		imgPanel.add(createComp(new JLabel(date), 100, 270, 190, 25));
		
		imgPanel.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(java.awt.event.MouseEvent e) {
				System.out.println(e.getX() + "," + e.getY());
			};
		});
		
		add(imgPanel);
		add(lbTop, "North");
		add(sp, "South");
		
		updateInfo(0);
	}
	
	private void clickApply() {
		
		if (tfName1.getText().length() == 0 || tfName2.getText().length() == 0) {
			eMsg("이름을 입력해주세요.");
		} else {
			iMsg("디자인 " + (index + 1) + "번으로 결정되었습니다.");
			dispose();
		}
		
	}
	
	private void updateInfo(int number) {
		index += number;
		
		if (index == -1) {
			index = 2;
		} else if (index == 3) {
			index = 0;
		}
		
		int imgNo = index + 1;
		
		lbTop.setText(imgNo + "번 이미지");
		imgPanel.img = new ImageIcon("./지급자료/청첩장/청첩장" + imgNo + ".jpg").getImage();
		
		repaint();
	}

	public static void main(String[] args) {
		new LetterSelectionFrame("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2019-09-13").setVisible(true);
	}

}
