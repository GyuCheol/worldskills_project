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
	JLabel lbTop = new JLabel("0�� �̹���", 0);
	ImagePanel imgPanel = new ImagePanel(null, null);
	JTextField tfName1 = new JTextField();
	JTextField tfName2 = new JTextField();
	
	public LetterSelectionFrame(String address, String date) {
		super(300, 500, "ûø�� ������ ����");
		
		var sp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		imgPanel.setLayout(null);
		
		sp.add(createButton(new JButton("��"), e -> updateInfo(-1)));
		sp.add(createButton(new JButton("����"), e -> clickApply()));
		sp.add(createButton(new JButton("��"), e -> updateInfo(1)));
		
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
			eMsg("�̸��� �Է����ּ���.");
		} else {
			iMsg("������ " + (index + 1) + "������ �����Ǿ����ϴ�.");
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
		
		lbTop.setText(imgNo + "�� �̹���");
		imgPanel.img = new ImageIcon("./�����ڷ�/ûø��/ûø��" + imgNo + ".jpg").getImage();
		
		repaint();
	}

	public static void main(String[] args) {
		new LetterSelectionFrame("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2019-09-13").setVisible(true);
	}

}
