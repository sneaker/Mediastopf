package ms.client.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ImageWhiteFilterTest extends TestCase {
	
	private int imageHeight = 111;
	private int imageWidth = imageHeight;
	
	public ImageWhiteFilterTest(String testName) {
		super(testName);
	}
	
	public void testImageWhite() {
		BufferedImage image = createImage(new Color(255, 255, 255));
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isWhite);
	}
	
	public void testImageLargeWhite() {
		imageHeight = 1111;
		testImageWhite();
	}
	
	public void testImageWhiteBoundry() {
		BufferedImage image = createImage(new Color(242, 242, 242));
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isWhite);
	}
	
	public void testImageLargeWhiteBoundry() {
		imageHeight = 1111;
		testImageWhiteBoundry();
	}
	
	public void testImageNotWhite() {
		BufferedImage image = createImage(new Color(0, 0, 0));
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isWhite);
	}
	
	public void testImageLargeNotWhite() {
		imageHeight = 1111;
		testImageNotWhite();
	}
	
	public void testImageNotWhiteBoundry() {
		BufferedImage image = createImage(new Color(241, 241, 241));
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isWhite);
	}
	
	public void testImageLargeNotWhiteBoundry() {
		imageHeight = 1111;
		testImageNotWhiteBoundry();
	}
	
	public void testImage95PercentWhite() {
		int imageResolution = imageHeight*imageWidth;
		int split = round((double)imageResolution/100*5);
		BufferedImage image = createSplitImage(split);
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertTrue(isWhite);
	}
	
	public void testImageLarge95PercentWhite() {
		imageHeight = 1111;
		testImage95PercentWhite();
	}
	
	public void testImage94PercentWhite() {
		int imageResolution = imageHeight*imageWidth;
		int split = round((double)imageResolution/100*6);
		BufferedImage image = createSplitImage(split);
		boolean isWhite = ImageWhiteFilter.analyzeImage(image);
		assertFalse(isWhite);
	}
	
	public void testImageLarge94PercentWhite() {
		imageHeight = 1111;
		testImage94PercentWhite();
	}
	
	private int round(double d) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(0, RoundingMode.HALF_UP);
		return bd.intValue();
	}
	
	private BufferedImage createImage(Color c) {
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				image.setRGB(i, j, c.getRGB());
			}
		}
		return image;
	}
	
	private BufferedImage createSplitImage(int split) {
		int paintedPixels = 0;
		BufferedImage image = createImage(new Color(255, 255, 255));
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				if(split <= paintedPixels) {
					break;
				}
				image.setRGB(i, j, new Color(0, 0, 0).getRGB());
				paintedPixels++;
			}
		}
		return image;
	}
	
	public static Test suite() {
	    TestSuite suite = new TestSuite();
	    String[] testsMethods = { "testImageWhite", "testImageLargeWhite",
	    		"testImageWhiteBoundry", "testImageLargeWhiteBoundry",
	    		"testImageNotWhite", "testImageLargeNotWhite",
	    		"testImageNotWhiteBoundry", "testImageLargeNotWhiteBoundry",
	    		"testImage95PercentWhite", "testImageLarge95PercentWhite",
	    		"testImage94PercentWhite", "testImageLarge94PercentWhite" };
	    for (String t : testsMethods) {
			suite.addTest(new ImageWhiteFilterTest(t));
		}
	    return suite;
	}
}
