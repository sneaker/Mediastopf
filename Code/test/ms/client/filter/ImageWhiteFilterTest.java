package ms.client.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Test;

public class ImageWhiteFilterTest {
	
	private static final int IMAGE_HEIGHT = 100;
	private static final int IMAGE_WIDTH = IMAGE_HEIGHT;

	@Test
	public void testImageWhite() {
		BufferedImage image = createImage(new Color(255, 255, 255));
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isWhite);
		
		image = createImage(new Color(230, 230, 230));
		isWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isWhite);
	}
	
	@Test
	public void testImageNotWhite() {
		BufferedImage image = createImage(new Color(0, 0, 0));
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isWhite);
		
		image = createImage(new Color(229, 229, 229));
		isWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isWhite);
	}
	
	@Test
	public void testImage95PercentWhite() {
		int split = IMAGE_WIDTH/100*95;
		BufferedImage image = createSplitImage(split);
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isWhite);
	}
	
	@Test
	public void testImage94PercentWhite() {
		int split = IMAGE_WIDTH/100*94;
		BufferedImage image = createSplitImage(split);
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isWhite);
	}
	
	private BufferedImage createImage(Color c) {
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				image.setRGB(i, j, c.getRGB());
			}
		}
		return image;
	}
	
	private BufferedImage createSplitImage(int startBlack) {
		BufferedImage image = createImage(new Color(255, 255, 255));
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = startBlack; j < image.getHeight(); j++) {
				image.setRGB(i, j, new Color(0, 0, 0).getRGB());
			}
		}
		return image;
	}
}
