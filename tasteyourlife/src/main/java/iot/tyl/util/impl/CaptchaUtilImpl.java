package iot.tyl.util.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import iot.tyl.util.CaptchaUtil;

@Component
public class CaptchaUtilImpl implements CaptchaUtil {
	
	private static final int len = 6;
	
	private static final int width = 16 * 2 + 12 * len;
	private static final int height = 12 * len;
	
	private static final int fontSize=18;
	private static final String font = "Arial";
	
	public String generateCaptcha() {
		StringBuffer captcha = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			int data = random.nextInt(36);
			char theChar;
			if (data < 10)
				theChar = (char)(data + '0');
			else 
				theChar = (char)(data - 10 + 'A');
			captcha.append(theChar);
		}
		return captcha.toString();
	}
	
	public String getImageBase64(String captcha) throws IOException {
		return  Base64.getEncoder().encodeToString(generatePng(captcha));
	}
	
	public byte[] generatePng(String captcha) throws IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandomColor(200, 250));
		g.fillRect(0, 0, width, height);
		drawRandomLine(g, 100);
		double rotate = Math.random() * Math.PI / 2 - Math.PI / 4;
		g.setFont(new Font(font, Font.PLAIN, fontSize));
        g.setColor(getRandomColor(20, 140));
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(rotate, width>>1, height>>1);
        g2d.drawString(captcha, width>>3 , height>>1);
        g2d.rotate(-rotate, width>>1 , height>>1);
        drawRandomLine(g, 50);
        drawRandomPoint(g);
        g.dispose();
        byte[] imageBytes = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", outStream);
		imageBytes = outStream.toByteArray();
		return imageBytes;
    	

	}
	
	private void drawRandomPoint(Graphics g) {
		Random random = new Random();
		int area = (int)(width * height * 0.05f);
		for (int i = 0; i < area; i++) {
			getRandomColor(0, 255);
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			g.drawLine(x, y, x, y);
		}
	}
	private void drawRandomLine(Graphics g, int count) {
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			if (i % 5 == 0) {
				g.setColor(getRandomColor(170, 210));
			}
			int x0 = random.nextInt(width);
			int y0 = random.nextInt(height);
			int x = random.nextInt(10) + 1;
			int y = random.nextInt(10) + 1;
			g.drawLine(x0, y0, x0 + x, y0 + y);
		}
	}
	
	private Color getRandomColor(int baseColor, int rangeColor) {
		Random random = new Random();
		if (baseColor > 255 || baseColor < 0)
			baseColor = random.nextInt(256);
		if (rangeColor > 255 || rangeColor < 0) 
			rangeColor = random.nextInt(256);
		if (baseColor > rangeColor) {
			int tmp = baseColor;
			baseColor = rangeColor;
			rangeColor = tmp;
		}
		int r = baseColor +  random.nextInt(rangeColor - baseColor);
		int g = baseColor +  random.nextInt(rangeColor - baseColor);
		int b = baseColor +  random.nextInt(rangeColor - baseColor);
		return new Color(r,g,b);
	}

}
