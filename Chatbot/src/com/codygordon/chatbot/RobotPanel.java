package com.codygordon.chatbot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class RobotPanel extends JPanel {

	private static final long serialVersionUID = 497371379732388589L;
	private BufferedImage img;
	
	private int width, height;
	
	public RobotPanel(int width, int height, String robot) {
		this.width = width;
		this.height = height;
		try {
			img = ImageIO.read(new File("src/Assets/" + robot));
		} catch(Exception e) {
			e.printStackTrace();
		}
		repaint();
	}
	
	private BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(resize(img, width, height), 0, 0, null);
	}
}