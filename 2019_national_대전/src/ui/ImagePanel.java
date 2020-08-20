package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ImagePanel extends JPanel {

	public Image img;
	public ActionListener action;
	
	public ImagePanel(Image img, ActionListener a) {
		this.img = img;
		
		action = a;
		
		setBorder(new LineBorder(Color.BLACK));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (action != null) {
					action.actionPerformed(new ActionEvent(ImagePanel.this, 0, ""));										
				}
			}
		});
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
	
	
}
